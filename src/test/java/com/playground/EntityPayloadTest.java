package com.playground;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.dto.EmailPayload;
import com.playground.dto.EntityPayload;
import com.playground.dto.MessageType;
import com.playground.dto.PaymentPayload;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityPayloadTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testPolymorphicSerialization() throws Exception {
        List<EntityPayload> payloads = new ArrayList<>();

        payloads.add(new EmailPayload("customer@gmail.coom", MessageType.EMAIL_CONFIRMATION));
        payloads.add(new PaymentPayload(UUID.randomUUID()));

        String json = objectMapper.writeValueAsString(payloads);
        assertThat(json).isNotBlank();

        EntityPayload[] deserializedPayloads = objectMapper.readValue(json, EntityPayload[].class);
        assertThat(deserializedPayloads[0]).isInstanceOf(EmailPayload.class);
        assertThat(deserializedPayloads[1]).isInstanceOf(PaymentPayload.class);
    }
}
