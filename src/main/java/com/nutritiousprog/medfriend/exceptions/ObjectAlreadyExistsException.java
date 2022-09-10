package com.nutritiousprog.medfriend.exceptions;

public class ObjectAlreadyExistsException extends RuntimeException {
    public ObjectAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
