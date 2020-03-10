package com.playground.client.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AddressDTO {

    private UUID id;
    private AddressType addressType;
    private String country;
    private String city;
    private String street;
    private String houseNumber;
    private String floor;
    private String zipCode;
    private String additionalAddress;
    private String info;
    private Instant createdAt;
    private Instant updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
