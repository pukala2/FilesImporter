package com.example.files.importer.exception;

public class MaxSizeTagException extends RuntimeException {

    public MaxSizeTagException(String message) {
        super(message);
    }

    public MaxSizeTagException(String message, Throwable cause) {
        super(message, cause);
    }
}
