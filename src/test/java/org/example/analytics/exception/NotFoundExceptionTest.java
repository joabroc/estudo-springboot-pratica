package org.example.analytics.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotFoundExceptionTest {

    @Test
    void devePreservarMensagemRecebidaNoConstrutor() {
        NotFoundException exception = new NotFoundException("registro não localizado");

        assertThat(exception)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("registro não localizado");
    }
}

