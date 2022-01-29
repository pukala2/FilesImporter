package com.example.files.importer.parser;

import com.example.files.importer.entity.Tag;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;

@Component
public class CsvTagReader implements TagReader {

    static final int USER_ID = 0;
    static final int MOVIE_ID = 1;
    static final int TAG = 2;

    static final int MAX_NUMBER_OF_RECORDS = 15000;

    private final Clock clock;

    public CsvTagReader(Clock clock) {
        this.clock = clock;
    }

    @Override
    public List<Tag> read(InputStream initialStream) {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(initialStream, StandardCharsets.UTF_8))) {

            var format = CSVFormat.Builder.create(CSVFormat.DEFAULT).setHeader().setSkipHeaderRecord(true)
                    .setIgnoreEmptyLines(true)
                    .build();

            CSVParser csvParser = new CSVParser(fileReader, format);

            return readDataFromRecords(csvParser);
        } catch (IOException e) {
            throw new RuntimeException("Fail to read csv data: " + e.getMessage());
        }
    }

    private List<Tag> readDataFromRecords(CSVParser parser) throws IOException {

        var tags = new ArrayList<Tag>();
        for (var record : parser.getRecords()) {
            if (TagRecordValidator.isValid(record) && tags.size() <= MAX_NUMBER_OF_RECORDS) {
                tags.add(Tag.builder()
                        .userId(parseLong(record.get(USER_ID)))
                        .movieId(parseLong(record.get(MOVIE_ID)))
                        .tag(record.get(TAG))
                        .timestamp(Timestamp.from(clock.instant()))
                        .build());
            }
        }
        return tags;
    }
}
