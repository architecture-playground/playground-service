package com.playground.dto;

import com.playground.model.PlaygroundEntityStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationRequestDTO {

    private UUID playgroundEntityId;
    private PlaygroundEntityStatus status;
    private Boolean succeed;
}
