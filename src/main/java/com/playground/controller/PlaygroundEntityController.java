package com.playground.controller;

import com.playground.dto.CreatedPlaygroundEntityDTO;
import com.playground.dto.PlaygroundEntityDTO;
import com.playground.service.PlaygroundEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController("v1")
public class PlaygroundEntityController {

    private final PlaygroundEntityService playgroundEntityService;

    @GetMapping("/entity/all")
    public List<PlaygroundEntityDTO> getAll() {
        log.info("Request all playground entities");
        return playgroundEntityService.getAllEntities();
    }

    @PostMapping("/entity")
    public CreatedPlaygroundEntityDTO create(@RequestBody PlaygroundEntityDTO dto) {
        log.info("Request to create entity {}", dto);
        return playgroundEntityService.createOne(dto);
    }
}
