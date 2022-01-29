package com.example.files.importer.parser;

import com.example.files.importer.exception.BadIdException;
import com.example.files.importer.exception.BlankValueException;
import com.example.files.importer.exception.MaxSizeTagException;
import org.apache.commons.csv.CSVRecord;

import static com.example.files.importer.parser.CsvTagReader.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;

class TagRecordValidator {
    static boolean isValid(CSVRecord record) {
        return !isSomeValueBlank(record) && isIdNumeric(record) && isProperSizeOfTag(record);
    }

    private static boolean isSomeValueBlank(CSVRecord record) {
        if (record.get(USER_ID).isBlank() || record.get(MOVIE_ID).isBlank() || record.get(TAG).isBlank()) {
            throw new BlankValueException("Some value in record is blank.");
        }
        return false;
    }

    private static boolean isIdNumeric(CSVRecord record) {
        if (!isNumeric(record.get(USER_ID)) || !isNumeric(record.get(MOVIE_ID))) {
            throw new BadIdException("ID has to be numeric.");
        }
        return true;
    }

    private static boolean isProperSizeOfTag(CSVRecord record) {
        final int MAX_SIZE_TAG = 100;
        if (record.get(TAG).length() > MAX_SIZE_TAG) {
            throw new MaxSizeTagException("Size of tag is to big.");
        }
        return true;
    }
}
