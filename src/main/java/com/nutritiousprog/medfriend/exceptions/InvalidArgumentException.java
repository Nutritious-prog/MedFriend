package com.nutritiousprog.medfriend.exceptions;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException(String errorMessage) {
        super(errorMessage);
    }
}
