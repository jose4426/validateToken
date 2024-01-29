package com.example.validateLogin.exceptions;


public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(final String msg) {
        super( msg);
    }
}
