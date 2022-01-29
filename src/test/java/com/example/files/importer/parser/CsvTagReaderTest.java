package com.example.files.importer.parser;

import com.example.files.importer.entity.Tag;
import com.example.files.importer.exception.BadIdException;
import com.example.files.importer.exception.BlankValueException;
import com.example.files.importer.exception.MaxSizeTagException;
import org.junit.jupiter.api.Assertions;
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
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CsvTagReaderTest {

    @InjectMocks
    private CsvTagReader tagReader;

    @Mock
    private Clock clock;

    private final Instant InstantNow = Instant.now();

    @Test
    void shouldReadTags() throws FileNotFoundException {

        final List<Tag> expectedTags = Arrays.asList(
                Tag.builder().userId(2L).movieId(60756L).tag("funny").timestamp(Timestamp.from(InstantNow)).build(),
                Tag.builder().userId(2L).movieId(60756L).tag("Highly quotable").timestamp(Timestamp.from(InstantNow)).build(),
                Tag.builder().userId(2L).movieId(60756L).tag("will ferrell").timestamp(Timestamp.from(InstantNow)).build(),
                Tag.builder().userId(2L).movieId(89774L).tag("Boxing story").timestamp(Timestamp.from(InstantNow)).build());

        final String path = "src\\test\\java\\com\\example\\files\\importer\\parser\\tags.csv";
        Mockito.when(clock.instant()).thenReturn(InstantNow, InstantNow, InstantNow, InstantNow);

        final List<Tag> result = tagReader.read(new FileInputStream(path));

        Assertions.assertEquals(expectedTags, result);
    }

    @Test
    void shouldThrowBlankValueException() {
        Mockito.when(clock.instant()).thenReturn(InstantNow, InstantNow, InstantNow, InstantNow);
        BlankValueException thrown = Assertions.assertThrows(BlankValueException.class, () -> {
            final String path = "src\\test\\java\\com\\example\\files\\importer\\parser\\tags_with_blank_value.csv";
            tagReader.read(new FileInputStream(path));
        });
    }

    @Test
    void shouldThrowBadIdException() {
        Mockito.when(clock.instant()).thenReturn(InstantNow, InstantNow, InstantNow, InstantNow);
        BadIdException thrown = Assertions.assertThrows(BadIdException.class, () -> {
            final String path = "src\\test\\java\\com\\example\\files\\importer\\parser\\tags_with_bad_id.csv";
            tagReader.read(new FileInputStream(path));
        });
    }

    @Test
    void shouldThrowMaxSizeTagException() {
        Mockito.when(clock.instant()).thenReturn(InstantNow, InstantNow, InstantNow, InstantNow);
        MaxSizeTagException thrown = Assertions.assertThrows(MaxSizeTagException.class, () -> {
            final String path = "src\\test\\java\\com\\example\\files\\importer\\parser\\tags_with_to_big_size_value.csv";
            tagReader.read(new FileInputStream(path));
        });
    }
}