package com.gjv.basicTapi.exception;

public class UserUnderLegalAgeException extends Exception {

    public UserUnderLegalAgeException() {
        super("Error: User is under the legal age.");
    }
}
