package com.playground.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playground.dto.EmailPayload;
import com.playground.dto.MessageType;
import com.playground.dto.Payload;
import com.playground.dto.PaymentPayload;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.playground.dto.EmailPayload.EMAIL_PAYLOAD_TYPE;
import static org.assertj.core.api.Assertions.assertThat;

public class JsonSubTypesTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testPolymorphicSerialization() throws Exception {
        List<Payload> payloads = new ArrayList<>();

        payloads.add(new EmailPayload("customer@gmail.coom", MessageType.EMAIL_CONFIRMATION));
        payloads.add(new PaymentPayload(UUID.randomUUID()));

        String json = objectMapper.writeValueAsString(payloads);
        assertThat(json).isNotBlank();

        Payload[] deserializedPayloads = objectMapper.readValue(json, Payload[].class);
        assertThat(deserializedPayloads[0]).isInstanceOf(EmailPayload.class);
        assertThat(deserializedPayloads[1]).isInstanceOf(PaymentPayload.class);
    }

    @Test
    void testDeserializeFromMap() {
        Map<String, Object> sourceMap = Map.of(
                "type", EMAIL_PAYLOAD_TYPE,
                "email", "customer@gmail.coom",
                "messageType", MessageType.EMAIL_CONFIRMATION
        );

        Payload result = objectMapper.convertValue(sourceMap, Payload.class);
        System.out.println(result);
    }
}
