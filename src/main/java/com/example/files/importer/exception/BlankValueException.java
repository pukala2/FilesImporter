package com.example.files.importer.exception;

public class BlankValueException extends RuntimeException {

    public BlankValueException(String message) {
        super(message);
    }

    public BlankValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
