package com.selcukaloba.apptry.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String detail;
    private MessageType messageType;

    public String generateErrorMessage()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(messageType.getMessage());
        if(detail!=null)
        {
            builder.append(" : ").append(detail);
        }
        return builder.toString();
    }
}
