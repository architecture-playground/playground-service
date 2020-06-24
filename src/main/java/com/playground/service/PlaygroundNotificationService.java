package com.playground.service;

import com.playground.dto.NotificationRequestDTO;
import com.playground.model.PlaygroundEntity;
import com.playground.repository.PlaygroundEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaygroundNotificationService {

    private final PlaygroundEntityRepository playgroundEntityRepository;

    @Transactional
    public void handleNotification(NotificationRequestDTO request) {
        PlaygroundEntity forUpdate = playgroundEntityRepository.findForPessimisticWriteById(request.getPlaygroundEntityId());
        log.info("Entity {} selected for update", forUpdate.getId());
        forUpdate.setStatus(request.getStatus());
        forUpdate.setSucceed(request.getSucceed());
        playgroundEntityRepository.save(forUpdate);
    }
}
