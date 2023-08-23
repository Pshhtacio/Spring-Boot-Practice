package com.thoughtworks.springboot.exception;

public class EmployeeCreateException extends RuntimeException{
    public EmployeeCreateException() {
        super("Employee was not created");
    }
    public EmployeeCreateException(String message) {
        super(message);
    }
}
