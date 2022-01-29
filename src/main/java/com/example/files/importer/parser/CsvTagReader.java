package com.example.files.importer.parser;

import com.example.files.importer.config.ConfigProperties;
import com.example.files.importer.entity.Tag;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Value;
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

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@Component
public class CsvTagReader implements TagReader {

    static final int USER_ID = 0;
    static final int MOVIE_ID = 1;
    static final int TAG = 2;

    private final Clock clock;
    private final ConfigProperties configProperties;

    public CsvTagReader(Clock clock, ConfigProperties configProperties) {
        this.clock = clock;
        this.configProperties = configProperties;
    }

    @Override
    public List<Tag> read(InputStream initialStream) {
        try (var fileReader = new BufferedReader(new InputStreamReader(initialStream,
                configProperties.getConfigValue("encoding")))) {

            var format = CSVFormat.Builder.create(CSVFormat.DEFAULT).setHeader()
                    .setIgnoreEmptyLines(true)
                    .build();

            var csvParser = new CSVParser(fileReader, format);

            return readDataFromRecords(csvParser);
        } catch (IOException e) {
            throw new RuntimeException("Fail to read csv data: " + e.getMessage());
        }
    }

    private List<Tag> readDataFromRecords(CSVParser parser) throws IOException {

        var maxNumberOfRecords =  parseInt(configProperties.getConfigValue("max_number_of_records"));
        var tags = new ArrayList<Tag>();
        HeaderTagValidator.check(parser.getHeaderNames());
        for (var record : parser.getRecords()) {
            if (TagRecordValidator.isValid(record) && tags.size() < maxNumberOfRecords) {
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
