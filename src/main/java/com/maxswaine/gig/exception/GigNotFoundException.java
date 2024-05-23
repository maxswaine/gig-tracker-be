package com.maxswaine.gig.exception;

public class GigNotFoundException extends RuntimeException {
    public GigNotFoundException(String id) {
        super("Gig not found with id: " + id);
    }
}
