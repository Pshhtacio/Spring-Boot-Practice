package com.thoughtworks.springbootemployee.exception;

public class EmployeeValidationException extends RuntimeException {
    public EmployeeValidationException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
