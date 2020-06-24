package com.playground.util;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionalWrapper {

    @Transactional
    public void runInTransaction(Runnable runnable) {
        runnable.run();
    }
}
