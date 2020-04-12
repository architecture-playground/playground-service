package com.playground.service;

import com.playground.converter.EntityPayloadConverter;
import com.playground.dto.EntityPayloadDTO;
import com.playground.model.EntityPayload;
import com.playground.repository.EntityPayloadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

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
}
