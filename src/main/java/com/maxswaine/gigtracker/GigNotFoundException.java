package com.maxswaine.gigtracker;

public class GigNotFoundException extends RuntimeException{
    public GigNotFoundException() {
        super("Couldn't find that gig.");
    }

}
