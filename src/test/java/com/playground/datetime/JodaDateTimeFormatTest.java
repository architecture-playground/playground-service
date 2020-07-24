package com.playground.datetime;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JodaDateTimeFormatTest {

    @Test
    void testParseFromString() {
        DateTime time = DateTime.parse("2020-12-25T01:00:00");

        assertThat(time).isNotNull();
    }
}
