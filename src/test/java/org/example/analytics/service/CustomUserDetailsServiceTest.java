package org.example.analytics.service;

import org.example.analytics.model.User;
import org.example.analytics.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void loadUserByUsernameDeveRetornarUsuarioQuandoEncontrado() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("pass");
        user.setRole("USER");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        var result = service.loadUserByUsername("user");

        assertThat(result).isSameAs(user);
        verify(userRepository).findByUsername("user");
    }

    @Test
    void loadUserByUsernameDeveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.loadUserByUsername("user"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found: user");
        verify(userRepository).findByUsername("user");
    }
}
