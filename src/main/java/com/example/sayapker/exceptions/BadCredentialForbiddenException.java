package com.example.sayapker.exceptions;

public class BadCredentialForbiddenException extends RuntimeException {
    public BadCredentialForbiddenException(String message) {
        super(message);
    }
}
