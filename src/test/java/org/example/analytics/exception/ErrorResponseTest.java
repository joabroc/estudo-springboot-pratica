package org.example.analytics.exception;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseTest {

    @Test
    void construtorEGettersDevemRefletirValoresInformados() {
        LocalDateTime data = LocalDateTime.of(2026, 3, 31, 10, 45);

        ErrorResponse response = new ErrorResponse("erro de teste", 400, data);

        assertThat(response.getMessage()).isEqualTo("erro de teste");
        assertThat(response.getStatus()).isEqualTo(400);
        assertThat(response.getData()).isEqualTo(data);
    }

    @Test
    void settersDevemAtualizarCampos() {
        ErrorResponse response = new ErrorResponse("inicial", 200, LocalDateTime.of(2026, 1, 1, 0, 0));
        LocalDateTime novaData = LocalDateTime.of(2026, 4, 1, 8, 30);

        response.setMessage("atualizado");
        response.setStatus(500);
        response.setData(novaData);

        assertThat(response.getMessage()).isEqualTo("atualizado");
        assertThat(response.getStatus()).isEqualTo(500);
        assertThat(response.getData()).isEqualTo(novaData);
    }
}

