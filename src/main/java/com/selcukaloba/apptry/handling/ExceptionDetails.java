package com.selcukaloba.apptry.handling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDetails<E>{
    private String errorCode;
    private String path;
    private String hostname;
    private int status;
    private LocalDateTime errorTime;
    private E message;
}
