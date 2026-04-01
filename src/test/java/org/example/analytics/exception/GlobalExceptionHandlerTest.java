package org.example.analytics.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ThrowingController())
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void deveRetornarNotFoundQuandoNotFoundExceptionForLancada() throws Exception {
        mockMvc.perform(get("/erros/not-found"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Transação não encontrada com id: 77"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(content().string(containsString("\"data\"")));
    }

    @Test
    void deveRetornarErroInternoQuandoExcecaoGenericaForLancada() throws Exception {
        mockMvc.perform(get("/erros/generico"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Erro interno no servidor"))
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(content().string(containsString("\"data\"")));
    }

    @RestController
    static class ThrowingController {

        @GetMapping("/erros/not-found")
        String notFound() {
            throw new NotFoundException("Transação não encontrada com id: 77");
        }

        @GetMapping("/erros/generico")
        String genericError() {
            throw new IllegalStateException("falha inesperada");
        }
    }
}

