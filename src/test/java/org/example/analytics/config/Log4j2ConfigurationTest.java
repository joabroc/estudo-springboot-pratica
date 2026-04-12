package org.example.analytics.config;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class Log4j2ConfigurationTest {

    @Test
    void deveReferenciarJsonTemplateLayoutNaConfiguracaoPrincipal() throws IOException {
        String config = lerRecurso("log4j2-spring.xml");

        assertThat(config).contains("JsonTemplateLayout");
        assertThat(config).contains("log4j2-event-template.json");
    }

    @Test
    void deveConterCamposEstruturadosCriticosNoTemplateJson() throws IOException {
        String template = lerRecurso("log4j2-event-template.json");

        assertThat(template).contains("\"@timestamp\"");
        assertThat(template).contains("\"traceId\"");
        assertThat(template).contains("\"correlationId\"");
        assertThat(template).contains("\"http.method\"");
        assertThat(template).contains("\"http.path\"");
        assertThat(template).contains("\"http.status\"");
        assertThat(template).contains("\"service.name\"");
        assertThat(template).contains("\"exception.stacktrace\"");
    }

    private String lerRecurso(String nomeArquivo) throws IOException {
        try (var inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(nomeArquivo)) {
            assertThat(inputStream).isNotNull();
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}

