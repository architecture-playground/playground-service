package com.playground.datetime;

import org.junit.jupiter.api.*;

import java.time.*;

import static org.assertj.core.api.Assertions.*;

public class OffsetDatetimeAdjustmentTest {

    @Test
    void testParseDatetimeWithMinMax() {
        OffsetDateTime dateTime = OffsetDateTime.parse("2023-11-30T18:00:00Z");
        assertThat(dateTime).isNotNull();
        System.out.println("Parsed datetime: " + dateTime);

        var dateTimeMinus_0_Days = dateTime.minusDays(0);
        System.out.println("Parsed dateTimeMinus_0_Days: " + dateTimeMinus_0_Days);

        var dateTimeMinus_0_Days_MIN = dateTimeMinus_0_Days.with(LocalTime.MIN);
        var dateTimeMinus_0_Days_MAX = dateTimeMinus_0_Days.with(LocalTime.MAX);
        System.out.println("Parsed dateTimeMinus_0_Days_MIN: " + dateTimeMinus_0_Days_MIN);
        System.out.println("Parsed dateTimeMinus_0_Days_MAX: " + dateTimeMinus_0_Days_MAX);

        var dateTimeMinus_3_Days = dateTime.minusDays(3);
        System.out.println("Parsed dateTimeMinus_3_Days: " + dateTimeMinus_3_Days);

        var dateTimeMinus_3_Days_MIN = dateTimeMinus_3_Days.with(LocalTime.MIN);
        var dateTimeMinus_3_Days_MAX = dateTimeMinus_3_Days.with(LocalTime.MAX);
        System.out.println("Parsed dateTimeMinus_3_Days_MIN: " + dateTimeMinus_3_Days_MIN);
        System.out.println("Parsed dateTimeMinus_3_Days_MAX: " + dateTimeMinus_3_Days_MAX);
    }
}
