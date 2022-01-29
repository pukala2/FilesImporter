package com.example.files.importer.utils;

import org.springframework.web.multipart.MultipartFile;

public class CsvHelper {

    private final static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }
}
