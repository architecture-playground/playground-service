package com.playground.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@JsonTypeName(PaymentPayload.PAYMENT_PAYLOAD_TYPE)
@Data
public class PaymentPayload implements EntityPayload {

    public static final String PAYMENT_PAYLOAD_TYPE = "payment_payload";

    private String type = PAYMENT_PAYLOAD_TYPE;
    private UUID orderId;

    public PaymentPayload(UUID orderId) {
        this.orderId = orderId;
    }
}
