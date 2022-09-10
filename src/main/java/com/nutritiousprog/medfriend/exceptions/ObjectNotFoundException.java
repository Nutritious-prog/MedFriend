package com.nutritiousprog.medfriend.exceptions;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
