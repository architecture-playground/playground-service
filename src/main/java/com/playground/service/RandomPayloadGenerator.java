package com.playground.service;

import com.playground.dto.EmailPayload;
import com.playground.dto.MessageType;
import com.playground.dto.Payload;
import com.playground.dto.PaymentPayload;
import com.playground.model.EntityPayload;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RandomPayloadGenerator {

    private final AtomicInteger roundRobin = new AtomicInteger(1);

    public EntityPayload randomEntity() {
        EntityPayload newEntity = new EntityPayload();
        newEntity.setPayload(randomPayload());
        return newEntity;
    }

    private Payload randomPayload() {
        if (roundRobin.compareAndSet(1, 2)) {
            return new EmailPayload("random_confirmation@gmail.com", MessageType.EMAIL_CONFIRMATION);
        } else if (roundRobin.compareAndSet(2, 3)) {
            return new EmailPayload("random_confirmation_reminder@gmail.com", MessageType.EMAIL_CONFIRMATION_REMINDER);
        } else if (roundRobin.compareAndSet(3, 1)) {
            return new PaymentPayload(UUID.randomUUID());
        } else {
            throw new IllegalStateException("Unexpected round robin state " + roundRobin);
        }
    }
}