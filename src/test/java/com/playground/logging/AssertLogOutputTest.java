package com.playground.logging;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.playground.PlaygroundIntegrationTest;
import com.playground.util.LoggingUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

@PlaygroundIntegrationTest
public class AssertLogOutputTest {

    //logger here is used to spy the log messages
    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

    private ListAppender<ILoggingEvent> logAppender;

    @BeforeEach
    public void setUp() {
        logAppender = new ListAppender<>();
        logAppender.start();
        LOGGER.addAppender(logAppender);
    }

    @AfterEach
    public void tearDown() {
        LOGGER.detachAppender(logAppender);
    }

    @Test
    public void testLogging() {
        LoggingUtil.error("Catch it!");
        assertThat(logErrorEntry().getFormattedMessage()).isEqualTo("Catch it!");
    }

    private ILoggingEvent logErrorEntry() {
        return logAppender.list.stream()
                .filter(e -> e.getLevel() == Level.ERROR)
                .findFirst()
                .orElseThrow(() -> new AssertionError("ERROR log event is expected"));
    }
}
