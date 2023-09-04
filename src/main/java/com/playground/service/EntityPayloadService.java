package com.playground.service;

import com.playground.converter.EntityPayloadConverter;
import com.playground.dto.EntityPayloadDTO;
import com.playground.dto.Payload;
import com.playground.model.EntityPayload;
import com.playground.repository.EntityPayloadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EntityPayloadService {

    private final EntityPayloadRepository payloadRepository;
    private final EntityPayloadConverter payloadConverter;
    private final RandomPayloadGenerator payloadGenerator;

    public List<EntityPayloadDTO> getAllPayloads() {
        return payloadConverter.toDtos(payloadRepository.findAll());
    }

    public List<EntityPayloadDTO> createRandom(Integer quantity) {
        List<EntityPayload> toCreate = new LinkedList<>();
        for (int i = 0; i < quantity; i++) {
            toCreate.add(payloadGenerator.randomEntity());
        }
        return payloadConverter.toDtos(payloadRepository.saveAll(toCreate));
    }

    public EntityPayloadDTO createFromPayload(EntityPayloadDTO payloadDTO) {
        Payload payload = payloadDTO.getPayload();
        log.info("Request to create entity with payload of type {}", payload.getType());

        EntityPayload toCreate = new EntityPayload();
        toCreate.setPayload(payload);

        return payloadConverter.toDto(payloadRepository.save(toCreate));
    }
}
