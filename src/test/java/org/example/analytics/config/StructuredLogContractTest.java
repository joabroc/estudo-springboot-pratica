package org.example.analytics.config;

import org.example.analytics.exception.GlobalExceptionHandler;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StructuredLogContractTest {

    private MockMvc mockMvc;
    private Logger filterLogger;
    private Logger exceptionLogger;
    private InMemoryAppender appender;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PingController(), new ThrowingController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilters(new CorrelationIdFilter())
                .build();

        filterLogger = (Logger) LogManager.getLogger(CorrelationIdFilter.class);
        exceptionLogger = (Logger) LogManager.getLogger(GlobalExceptionHandler.class);
        appender = new InMemoryAppender("structured-log-contract-appender");
        appender.start();
        filterLogger.addAppender(appender);
        exceptionLogger.addAppender(appender);
        filterLogger.setLevel(Level.INFO);
        exceptionLogger.setLevel(Level.INFO);
    }

    @AfterEach
    void tearDown() {
        if (filterLogger != null && appender != null) {
            filterLogger.removeAppender(appender);
        }
        if (exceptionLogger != null && appender != null) {
            exceptionLogger.removeAppender(appender);
        }
        if (appender != null) {
            appender.stop();
        }
    }

    @Test
    void deveRegistrarAccessLogComCamposHttpECorrelacao() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk());

        LogEvent accessEvent = appender.getEvents().stream()
                .filter(event -> event.getMessage().getFormattedMessage().contains("event=api.access"))
                .findFirst()
                .orElseThrow();

        String httpMethod = String.valueOf((Object) accessEvent.getContextData().getValue("http.method"));
        String httpPath = String.valueOf((Object) accessEvent.getContextData().getValue("http.path"));
        String httpStatus = String.valueOf((Object) accessEvent.getContextData().getValue("http.status"));
        String correlationId = String.valueOf((Object) accessEvent.getContextData().getValue("correlationId"));
        String traceId = String.valueOf((Object) accessEvent.getContextData().getValue("traceId"));

        assertThat(httpMethod).isEqualTo("GET");
        assertThat(httpPath).isEqualTo("/ping");
        assertThat(httpStatus).isEqualTo("200");
        assertThat(correlationId).isNotBlank();
        assertThat(traceId).isNotBlank();
    }

    @Test
    void deveRegistrarApiErrorEAccessLogComStatus500() throws Exception {
        mockMvc.perform(get("/erro-500"))
                .andExpect(status().isInternalServerError());

        LogEvent apiErrorEvent = appender.getEvents().stream()
                .filter(event -> event.getMessage().getFormattedMessage().contains("event=api.error"))
                .findFirst()
                .orElseThrow();

        String errorPath = String.valueOf((Object) apiErrorEvent.getContextData().getValue("http.path"));
        String errorMethod = String.valueOf((Object) apiErrorEvent.getContextData().getValue("http.method"));

        assertThat(errorMethod).isEqualTo("GET");
        assertThat(errorPath).isEqualTo("/erro-500");

        LogEvent accessEvent = appender.getEvents().stream()
                .filter(event -> event.getMessage().getFormattedMessage().contains("event=api.access"))
                .reduce((first, second) -> second)
                .orElseThrow();

        String httpStatus = String.valueOf((Object) accessEvent.getContextData().getValue("http.status"));
        assertThat(httpStatus).isEqualTo("500");
    }

    @RestController
    static class PingController {

        @GetMapping("/ping")
        String ping() {
            return "pong";
        }
    }

    @RestController
    static class ThrowingController {

        @GetMapping("/erro-500")
        String error() {
            throw new IllegalStateException("falha simulada");
        }
    }

    static class InMemoryAppender extends AbstractAppender {

        private final List<LogEvent> events = new ArrayList<>();

        protected InMemoryAppender(String name) {
            super(name, null, null, true, Property.EMPTY_ARRAY);
        }

        @Override
        public void append(LogEvent event) {
            events.add(event.toImmutable());
        }

        List<LogEvent> getEvents() {
            return events;
        }
    }
}

