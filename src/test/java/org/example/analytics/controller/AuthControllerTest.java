package org.example.analytics.controller;

import org.example.analytics.model.User;
import org.example.analytics.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController controller;

    @Test
    void registerDeveDelegarRegistroAoServiceESucesso() {
        Map<String, String> request = Map.of("username", "user", "password", "pass", "role", "USER");
        User savedUser = new User();
        savedUser.setId(1L);
        when(userService.registerUser(request)).thenReturn(savedUser);

        ResponseEntity<?> response = controller.register(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("User registered successfully");
        verify(userService).registerUser(request);
    }

    @Test
    void registerDeveRetornarErroQuandoServiceLancaExcecao() {
        Map<String, String> request = Map.of("username", "user", "password", "pass");
        doThrow(new IllegalArgumentException("Username already exists")).when(userService).registerUser(request);

        ResponseEntity<?> response = controller.register(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Username already exists");
        verify(userService).registerUser(request);
    }

    @Test
    void loginDeveDelegarAutenticacaoAoServiceERetornarToken() {
        Map<String, String> request = Map.of("username", "user", "password", "pass");
        when(userService.authenticateUser("user", "pass")).thenReturn("token123");

        ResponseEntity<?> response = controller.login(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Map.of("token", "token123"));
        verify(userService).authenticateUser("user", "pass");
    }

    @Test
    void loginDeveRetornarErroQuandoServiceLancaExcecao() {
        Map<String, String> request = Map.of("username", "user", "password", "pass");
        doThrow(new IllegalArgumentException("Invalid credentials")).when(userService).authenticateUser("user", "pass");

        ResponseEntity<?> response = controller.login(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Invalid credentials");
        verify(userService).authenticateUser("user", "pass");
    }
}
