package org.example.analytics.config;

import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CorrelationIdFilterTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new PingController())
                .addFilters(new CorrelationIdFilter())
                .build();
    }

    @AfterEach
    void tearDown() {
        ThreadContext.clearAll();
    }

    @Test
    void deveGerarIdsQuandoNaoVieremNoHeader() throws Exception {
        mockMvc.perform(get("/ping"))
                .andExpect(status().isOk())
                .andExpect(header().exists(CorrelationIdFilter.CORRELATION_ID_HEADER))
                .andExpect(header().exists(CorrelationIdFilter.TRACE_ID_HEADER));

        assertThat(ThreadContext.get("correlationId")).isNull();
        assertThat(ThreadContext.get("traceId")).isNull();
    }

    @Test
    void devePreservarIdsQuandoVieremNoHeader() throws Exception {
        mockMvc.perform(get("/ping")
                        .header(CorrelationIdFilter.CORRELATION_ID_HEADER, "corr-123")
                        .header(CorrelationIdFilter.TRACE_ID_HEADER, "trace-999"))
                .andExpect(status().isOk())
                .andExpect(header().string(CorrelationIdFilter.CORRELATION_ID_HEADER, "corr-123"))
                .andExpect(header().string(CorrelationIdFilter.TRACE_ID_HEADER, "trace-999"));

        assertThat(ThreadContext.get("correlationId")).isNull();
        assertThat(ThreadContext.get("traceId")).isNull();
    }

    @RestController
    static class PingController {

        @GetMapping("/ping")
        String ping() {
            return "pong";
        }
    }
}

