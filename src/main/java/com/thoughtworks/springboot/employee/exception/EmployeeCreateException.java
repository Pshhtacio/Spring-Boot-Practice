package com.thoughtworks.springboot.employee.exception;

public class EmployeeCreateException extends RuntimeException{
    public EmployeeCreateException() {
        super("Employee was not created");
    }
    public EmployeeCreateException(String message) {
        super(message);
    }
}
