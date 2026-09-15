package com.selcukaloba.apptry.handling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError<E> {
    private Integer status;
    private ExceptionDetails<E> exceptionDetails;
}
