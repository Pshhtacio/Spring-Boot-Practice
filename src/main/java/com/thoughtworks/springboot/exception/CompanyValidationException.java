package com.thoughtworks.springboot.exception;

public class CompanyValidationException extends RuntimeException {
    public CompanyValidationException(String message) {
        super(message);
    }
}
