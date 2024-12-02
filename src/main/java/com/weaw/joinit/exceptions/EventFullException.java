package com.weaw.joinit.exceptions;

public class EventFullException extends RuntimeException {
    public EventFullException() {
        super("Event is full");
    }
}
