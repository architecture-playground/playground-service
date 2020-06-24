package com.playground.controller;

import com.playground.dto.NotificationRequestDTO;
import com.playground.service.PlaygroundNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Imitate controller for some kind of web-hooks
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/playground/entity/notification")
@RestController
public class PlaygroundNotificationController {

    private PlaygroundNotificationService playgroundNotificationService;

    @PostMapping(
            consumes = MimeTypeUtils.APPLICATION_JSON_VALUE
    )
    public void handleNotification(@RequestBody NotificationRequestDTO request) {
        log.info("Handle notification request {}", request);
        playgroundNotificationService.handleNotification(request);
    }
}
