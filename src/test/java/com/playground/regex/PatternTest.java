package com.playground.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class PatternTest {

    @Test
    void testCollectionInMessageFormat() {
        Pattern pattern = Pattern.compile(".*Controller");

        assertThat(pattern.matcher("WebController").matches()).isTrue();
        assertThat(pattern.matcher("WebService").matches()).isFalse();
    }
}
