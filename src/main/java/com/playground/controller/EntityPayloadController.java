package com.playground.controller;

import com.playground.dto.EntityPayloadDTO;
import com.playground.service.EntityPayloadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atteo.evo.inflector.English;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/playground/payload")
@RestController
public class EntityPayloadController {

    private final EntityPayloadService payloadService;

    @GetMapping("/all")
    public List<EntityPayloadDTO> getAll() {
        log.info("Request all payloads");
        return payloadService.getAllPayloads();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/random")
    public List<EntityPayloadDTO> createRandom(@RequestParam Integer quantity) {
        log.info("Request to create {} random {}", quantity, English.plural("payload"));
        return payloadService.createRandom(quantity);
    }
}
