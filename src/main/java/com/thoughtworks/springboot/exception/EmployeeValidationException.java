package com.thoughtworks.springboot.exception;

public class EmployeeValidationException extends RuntimeException {
    public EmployeeValidationException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
