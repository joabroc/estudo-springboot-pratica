package org.example.analytics.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Test
    void generateTokenDeveCriarTokenValido() {
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username("user")
                .password("pass")
                .roles("USER")
                .build();

        String token = jwtService.generateToken(user);

        assertThat(token).isNotNull();
        assertThat(jwtService.extractUsername(token)).isEqualTo("user");
        assertThat(jwtService.isTokenValid(token, user)).isTrue();
    }

    @Test
    void isTokenValidDeveRetornarFalseParaTokenInvalido() {
        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username("user")
                .password("pass")
                .roles("USER")
                .build();
        UserDetails otherUser = org.springframework.security.core.userdetails.User.builder()
                .username("other")
                .password("pass")
                .roles("USER")
                .build();

        String token = jwtService.generateToken(user);

        assertThat(jwtService.isTokenValid(token, otherUser)).isFalse();
    }
}
