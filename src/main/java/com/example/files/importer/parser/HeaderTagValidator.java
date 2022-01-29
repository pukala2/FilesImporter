package com.example.files.importer.parser;

import com.example.files.importer.exception.HeaderTagException;

import java.util.Arrays;
import java.util.List;

public class HeaderTagValidator {

    private static final List<String> requiredHeaderNames = Arrays.asList("userId", "movieId", "tag", "timestamp");

    static void check(List<String> headerNames) {
        if (!requiredHeaderNames.equals(headerNames)) {
            throw new HeaderTagException("Header is not correct.");
        }
    }
}
