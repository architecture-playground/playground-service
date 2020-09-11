package com.playground.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

public class NestedJsonObjectSerializationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testPolymorphicSerialization() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("sns_message.json");
        assertThat(is).isNotNull();

        String json = IOUtils.toString(is, StandardCharsets.UTF_8.name());

        ForwardedMessageDTO msgDTO = new ForwardedMessageDTO();
        msgDTO.setPayload(json);

        String forwardedJsonAsStr = new ObjectMapper().writeValueAsString(msgDTO);
        System.out.println(forwardedJsonAsStr);
    }

    private static class ForwardedMessageDTO {

        private String payload;

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }
    }
}
