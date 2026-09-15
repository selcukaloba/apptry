package com.selcukaloba.apptry.handling;

import com.selcukaloba.apptry.exception.BaseException;
import com.selcukaloba.apptry.exception.ErrorMessage;
import com.selcukaloba.apptry.exception.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException ex, WebRequest request, Locale locale)
    {
        ErrorMessage errorMessage = ex.getErrorMessage();
        MessageType messageType = errorMessage.getMessageType();
        String code = messageType.getCode();
        HttpStatus status = messageType.getHttpStatus();

        String localizedMessage = messageSource.getMessage(messageType.getMessage(), null, messageType.getMessage(), locale);
        if(errorMessage.getDetail()!=null && !errorMessage.getDetail().isEmpty())
        {
            localizedMessage += " : " + errorMessage.getDetail();
        }
        return ResponseEntity.status(status).body(createApiError(status, code, localizedMessage, request));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request)
    {
        Map<String, List<String>> map = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String fieldName = fieldError.getField();
            map.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(fieldError.getDefaultMessage());
        }

        for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
            String objectName = objectError.getObjectName();
            map.computeIfAbsent(objectName, k -> new ArrayList<>()).add(objectError.getDefaultMessage());
        }

        MessageType messageType = MessageType.VALIDATION_ERROR;
        HttpStatus httpStatus = messageType.getHttpStatus();
        return ResponseEntity.status(httpStatus).body(createApiError(httpStatus, messageType.getCode(), map, request));
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ApiError<String>> handleGeneralException(Exception ex, WebRequest request, Locale locale)
    {
        log.error("Unexpected error on path: {}", request.getDescription(false), ex);
        MessageType messageType = MessageType.GENERAL_EXCEPTION;
        String localizedMessage = messageSource.getMessage(messageType.getMessage(), null, "Unexpected error occured.", locale);

        return ResponseEntity.status(messageType.getHttpStatus()).body(createApiError(messageType.getHttpStatus(), messageType.getCode(), localizedMessage, request));
    }

    private <E> ApiError<E> createApiError(HttpStatus status, String errorCode, E message, WebRequest request)
    {
        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(status.value());

        ExceptionDetails<E> exceptionDetails = new ExceptionDetails<>();
        exceptionDetails.setPath(request.getDescription(false).replace("uri=", ""));
        exceptionDetails.setErrorCode(errorCode);
        exceptionDetails.setErrorTime(LocalDateTime.now());
        exceptionDetails.setMessage(message);
        exceptionDetails.setHostname(getHostName());
        exceptionDetails.setStatus(status.value());

        apiError.setExceptionDetails(exceptionDetails);
        return apiError;
    }

    private String getHostName() {
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "unknown-host";
        }
    }
}
