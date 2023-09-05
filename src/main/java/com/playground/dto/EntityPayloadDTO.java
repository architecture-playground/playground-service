package com.playground.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class EntityPayloadDTO {

    private UUID id;
    private Payload payload;
}
