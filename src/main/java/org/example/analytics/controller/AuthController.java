package org.example.analytics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.analytics.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Operações de registro e login de usuários")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Registrar novo usuário", description = "Cadastra um novo usuário no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Nome de usuário já existe")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        long start = System.currentTimeMillis();
        String username = request.get("username");
        log.info("event=api.request entity=user operation=register outcome=start username={}", username);

        try {
            userService.registerUser(request);
            log.info("event=api.request entity=user operation=register outcome=success durationMs={} username={}",
                    System.currentTimeMillis() - start, username);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            log.info("event=api.request entity=user operation=register outcome=failure durationMs={} username={} reason={}",
                    System.currentTimeMillis() - start, username, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Login de usuário", description = "Autentica um usuário e retorna um token JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        long start = System.currentTimeMillis();
        String username = request.get("username");
        log.info("event=api.request entity=user operation=login outcome=start username={}", username);

        try {
            String token = userService.authenticateUser(username, request.get("password"));
            log.info("event=api.request entity=user operation=login outcome=success durationMs={} username={}",
                    System.currentTimeMillis() - start, username);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            log.info("event=api.request entity=user operation=login outcome=failure durationMs={} username={} reason={}",
                    System.currentTimeMillis() - start, username, e.getMessage());
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }
}
