package com.playground.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingUtil {

    public static void error(String msg) {
        log.error(msg);
    }
}
