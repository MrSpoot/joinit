package com.weaw.joinit.exceptions;

public class UnknownEventException extends RuntimeException {
    public UnknownEventException() {
        super("Unknown event");
    }
}
