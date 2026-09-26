package com.selcukaloba.apptry.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
public enum MessageType {
    VALIDATION_ERROR("2001", "validation_error", HttpStatus.BAD_REQUEST),

    //authorization
    NO_RECORD_EXISTS("1001", "user.no_record_exists", HttpStatus.NOT_FOUND),
    USERNAME_NOT_FOUND("1002", "user.not_found", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("1003", "user.already_exists", HttpStatus.CONFLICT),
    INVALID_REFRESH_TOKEN("1004", "refreshtoken.invalid", HttpStatus.UNAUTHORIZED),
    EXPIRED_REFRESH_TOKEN("1005", "refreshtoken.expired", HttpStatus.UNAUTHORIZED),
    USERNAME_OR_PASSWORD_INVALID("1006", "password_username.invalid", HttpStatus.UNAUTHORIZED),

    //post
    POST_NOT_FOUND("2001", "post.not_found", HttpStatus.NOT_FOUND),
    POST_NOT_OWNER("2002", "post.not_owner", HttpStatus.UNAUTHORIZED),

    //comment
    COMMENT_NOT_FOUND("3001", "comment.not_found", HttpStatus.NOT_FOUND),
    COMMENT_NOT_OWNER("3002", "comment.not_owner", HttpStatus.UNAUTHORIZED),

    //like
    ALREADY_LIKED("4001", "liked_already", HttpStatus.ALREADY_REPORTED),
    LIKE_NOT_FOUND("4002", "like.not_found", HttpStatus.BAD_REQUEST),

    //general
    GENERAL_EXCEPTION("9999", "general.exception", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message, code;
    private final HttpStatus httpStatus;
    MessageType(String code, String message, HttpStatus httpStatus)
            {
                this.code = code;
                this.message = message;
                this.httpStatus = httpStatus;
            }
}
