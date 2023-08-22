package com.thoughtworks.springboot.company.exception;

public class CompanyValidationException extends RuntimeException {
    public CompanyValidationException(String message) {
        super(message);
    }
}
