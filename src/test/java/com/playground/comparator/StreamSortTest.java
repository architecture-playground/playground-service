package com.playground.comparator;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamSortTest {

    @Test
    void testMinWhenNullsFirst() {
        DateWrapper[] data = {date(1), date(2), date(null), date(4), date(5), date(6), date(null), date(8), date(9), date(null)};

        DateTime nullsFirst = Arrays.stream(data)
                .min(Comparator.comparing(DateWrapper::getDateTime, Comparator.nullsFirst(DateTime::compareTo)))
                .map(DateWrapper::getDateTime)
                .orElse(null);

        assertThat(nullsFirst).isNull();
    }

    @Test
    void testMinWhenNullsLast() {
        DateWrapper[] data = {date(1), date(2), date(null), date(4), date(5), date(6), date(null), date(8), date(9), date(null)};

        DateTime nullsFirst = Arrays.stream(data)
                .min(Comparator.comparing(DateWrapper::getDateTime, Comparator.nullsLast(DateTime::compareTo)))
                .map(DateWrapper::getDateTime)
                .orElse(null);

        assertThat(nullsFirst).isNotNull();
    }

    @Test
    void testMin() {
        DateWrapper minusOneDay = date(1);
        DateWrapper minusTwoDays = date(2);
        DateWrapper[] data = {date(null), minusOneDay, minusTwoDays, date(null)};

        DateTime nullsFirst = Arrays.stream(data)
                .min(Comparator.comparing(DateWrapper::getDateTime, Comparator.nullsLast(DateTime::compareTo)))
                .map(DateWrapper::getDateTime)
                .orElse(null);

        assertThat(nullsFirst).isEqualTo(minusTwoDays.getDateTime());
    }

    @Test
    void testMaxAndNullsFirst() {
        DateWrapper minusOneDay = date(1);
        DateWrapper minusTwoDays = date(2);
        DateWrapper[] data = {date(null), minusOneDay, minusTwoDays, date(null)};

        DateTime nullsFirst = Arrays.stream(data)
                .max(Comparator.comparing(DateWrapper::getDateTime, Comparator.nullsFirst(DateTime::compareTo)))
                .map(DateWrapper::getDateTime)
                .orElse(null);

        assertThat(nullsFirst).isEqualTo(minusOneDay.getDateTime());
    }

    public static DateWrapper date(Integer minusDays) {
        return new DateWrapper(minusDays == null ? null : DateTime.now().minusDays(minusDays));
    }

    private static class DateWrapper {

        public final DateTime dateTime;

        private DateWrapper(DateTime dateTime) {
            this.dateTime = dateTime;
        }

        public DateTime getDateTime() {
            return dateTime;
        }
    }
}
