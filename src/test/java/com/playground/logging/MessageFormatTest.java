package com.playground.logging;

import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageFormatTest {

    @Test
    void testMapInMessageFormat() {
        Map<String, String> map = new HashMap<>();

        map.put("ke1", "value1");
        map.put("ke2", "value2");
        map.put("ke3", "value3");

        assertThat(MessageFormat.format("{0}", map)).isEqualTo("{ke1=value1, ke3=value3, ke2=value2}");
    }

    @Test
    void testStreamInMessageFormat() {
        Stream<String> stream = List.of("one", "two", "three").stream();

        assertThat(MessageFormat.format("{0}", stream)).startsWith("java.util.stream.ReferencePipeline$");
    }
}
