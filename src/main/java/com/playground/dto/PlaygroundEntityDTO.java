package com.playground.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PlaygroundEntityDTO {

    private UUID objectId;
    private String comment;
}
