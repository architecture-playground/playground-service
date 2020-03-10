package com.playground.controller;

import com.playground.dto.CreatedPlaygroundEntityDTO;
import com.playground.dto.PlaygroundEntityDTO;
import com.playground.service.PlaygroundEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.atteo.evo.inflector.English;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/entity")
@RestController
public class PlaygroundEntityController {

    private final PlaygroundEntityService playgroundEntityService;

    @GetMapping("/all")
    public List<PlaygroundEntityDTO> getAll() {
        log.info("Request all playground entities");
        return playgroundEntityService.getAllEntities();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/random")
    public void createRandom(@RequestParam Integer quantity) {
        log.info("Request to create {} {}", quantity, English.plural("entity"));
        playgroundEntityService.createRandom(quantity);
    }

    @PostMapping
    public CreatedPlaygroundEntityDTO create(@RequestBody PlaygroundEntityDTO dto) {
        log.info("Request to create entity {}", dto);
        return playgroundEntityService.createOne(dto);
    }
}
