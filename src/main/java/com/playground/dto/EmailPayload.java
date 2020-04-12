package com.playground.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonTypeName(EmailPayload.EMAIL_PAYLOAD_TYPE)
@Data
public class EmailPayload implements Payload {

    public static final String EMAIL_PAYLOAD_TYPE = "email_payload";

    private String type = EMAIL_PAYLOAD_TYPE;
    private String email;
    private MessageType messageType;

    public EmailPayload(String email, MessageType messageType) {
        this.email = email;
        this.messageType = messageType;
    }
}
