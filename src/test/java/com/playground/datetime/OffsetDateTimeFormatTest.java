package com.playground.datetime;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class OffsetDateTimeFormatTest {

    @Test
    void testDatetimeAsFilename() {
        OffsetDateTime offsetDateTime = OffsetDateTime.parse("2021-10-03T11:00:00+01:00");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
        String formattedDateTime = offsetDateTime.format(formatter);

        assertThat(formattedDateTime).isEqualTo("2021-10-03_11-00");
    }
}
