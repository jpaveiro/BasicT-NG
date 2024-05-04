package com.gjv.basicTapi.exception;

public class UnderAgeException extends Exception {

    public UnderAgeException() {
        super("Error: User is underage.");
    }
}
