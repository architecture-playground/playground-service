package com.playground.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = PaymentPayload.class, name = PaymentPayload.PAYMENT_PAYLOAD_TYPE),
        @JsonSubTypes.Type(value = EmailPayload.class, name = EmailPayload.EMAIL_PAYLOAD_TYPE)
})
public interface Payload {

    String getType();
}
