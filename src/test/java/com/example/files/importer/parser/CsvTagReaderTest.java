package com.example.files.importer.parser;

import com.example.files.importer.config.ConfigProperties;
import com.example.files.importer.entity.Tag;
import com.example.files.importer.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class CsvTagReaderTest {

    @InjectMocks
    private CsvTagReader tagReader;

    @Mock
    private Clock clock;

    @Mock
    private ConfigProperties configProperties;

    private final Instant instantNow = Instant.now();

    @BeforeEach
    void prepare() {
        Mockito.when(configProperties.getConfigValue("encoding")).thenReturn("UTF-8");
        Mockito.when(configProperties.getConfigValue("max_number_of_records")).thenReturn("10");
    }

    @Test
    void shouldReadTags() throws FileNotFoundException {

        var expectedTags = Arrays.asList(
                Tag.builder().userId(2L).movieId(60756L).tag("funny").timestamp(Timestamp.from(instantNow)).build(),
                Tag.builder().userId(2L).movieId(60756L).tag("Highly quotable").timestamp(Timestamp.from(instantNow)).build(),
                Tag.builder().userId(2L).movieId(60756L).tag("will ferrell").timestamp(Timestamp.from(instantNow)).build(),
                Tag.builder().userId(2L).movieId(89774L).tag("Boxing story").timestamp(Timestamp.from(instantNow)).build());

        Mockito.when(clock.instant()).thenReturn(instantNow, instantNow, instantNow, instantNow);

        var path = "src/test/resources/tags_short.csv";
        var result = tagReader.read(new FileInputStream(path));

        Assertions.assertEquals(expectedTags, result);
    }

    @Test
    void shouldReadMaxNumberOfRecords() throws FileNotFoundException {

        var expectedTags = Arrays.asList(
                Tag.builder().userId(2L).movieId(60756L).tag("funny").timestamp(Timestamp.from(instantNow)).build(),
                Tag.builder().userId(2L).movieId(60756L).tag("Highly quotable").timestamp(Timestamp.from(instantNow)).build());

        Mockito.when(configProperties.getConfigValue("max_number_of_records")).thenReturn("2");
        Mockito.when(clock.instant()).thenReturn(instantNow, instantNow, instantNow, instantNow);

        var path = "src/test/resources/tags_short.csv";
        var result = tagReader.read(new FileInputStream(path));

        Assertions.assertEquals(expectedTags, result);
    }

    @Test
    void shouldThrowBlankValueException() {
        Assertions.assertThrows(BlankValueException.class, () -> {
            Mockito.when(clock.instant()).thenReturn(instantNow, instantNow, instantNow, instantNow);
            var path = "src/test/resources/tags_with_blank_value.csv";
            tagReader.read(new FileInputStream(path));
        });
    }

    @Test
    void shouldThrowBadIdException() {
        Assertions.assertThrows(BadIdException.class, () -> {
            Mockito.when(clock.instant()).thenReturn(instantNow, instantNow, instantNow, instantNow);
            var path = "src/test/resources/tags_with_bad_id.csv";
            tagReader.read(new FileInputStream(path));
        });
    }

    @Test
    void shouldThrowMaxSizeTagException() {
        Assertions.assertThrows(MaxSizeTagException.class, () -> {
            Mockito.when(clock.instant()).thenReturn(instantNow, instantNow, instantNow, instantNow);
            var path = "src/test/resources/tags_with_to_big_size_value.csv";
            tagReader.read(new FileInputStream(path));
        });
    }

    @Test
    void shouldThrowHeaderTagException() {
        Assertions.assertThrows(HeaderTagException.class, () -> {
            var path = "src/test/resources/tags_with_bad_header.csv";
            tagReader.read(new FileInputStream(path));
        });
    }

//    @Test
//    void shouldThrowTypeFileException() {
//        Assertions.assertThrows(TypeFileException.class, () -> {
//            var path = "src/test/resources/tags.txt";
//            tagReader.read(new FileInputStream(path));
//        });
//    }


}