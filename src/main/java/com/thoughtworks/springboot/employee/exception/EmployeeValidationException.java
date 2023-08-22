package com.thoughtworks.springboot.employee.exception;

public class EmployeeValidationException extends RuntimeException {
    public EmployeeValidationException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
