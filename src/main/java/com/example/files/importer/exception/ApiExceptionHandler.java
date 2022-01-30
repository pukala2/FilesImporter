package com.example.files.importer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {BadIdException.class})
    public ResponseEntity<Object> handleApiRequestException(BadIdException e) {
        var badRequest = HttpStatus.BAD_REQUEST;
        var apiException = new ApiException(e.getMessage(), badRequest);
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {BlankValueException.class})
    public ResponseEntity<Object> handleApiRequestException(BlankValueException e) {
        var badRequest = HttpStatus.BAD_REQUEST;
        var apiException = new ApiException(e.getMessage(), badRequest);
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {MaxSizeTagException.class})
    public ResponseEntity<Object> handleApiRequestException(MaxSizeTagException e) {
        var badRequest = HttpStatus.BAD_REQUEST;
        var apiException = new ApiException(e.getMessage(), badRequest);
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {HeaderTagException.class})
    public ResponseEntity<Object> handleApiRequestException(HeaderTagException e) {
        var badRequest = HttpStatus.BAD_REQUEST;
        var apiException = new ApiException(e.getMessage(), badRequest);
        return new ResponseEntity<>(apiException, badRequest);
    }
}
