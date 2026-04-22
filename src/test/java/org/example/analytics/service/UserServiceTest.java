package org.example.analytics.service;

import org.example.analytics.model.User;
import org.example.analytics.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUserDeveSalvarUsuarioComSenhaCriptografada() {
        Map<String, String> request = Map.of("username", "user", "password", "pass", "role", "USER");
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");
        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("user");
        savedUser.setPassword("encodedPass");
        savedUser.setRole("USER");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.registerUser(request);

        assertThat(result).isSameAs(savedUser);
        verify(userRepository).findByUsername("user");
        verify(passwordEncoder).encode("pass");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUserDeveLancarExcecaoQuandoUsernameJaExiste() {
        Map<String, String> request = Map.of("username", "user", "password", "pass");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> userService.registerUser(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Username already exists");
        verify(userRepository).findByUsername("user");
        verifyNoMoreInteractions(passwordEncoder, userRepository);
    }

    @Test
    void authenticateUserDeveRetornarTokenQuandoAutenticacaoSucesso() {
        String username = "user";
        String password = "pass";
        Authentication auth = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getPrincipal()).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("token123");

        String result = userService.authenticateUser(username, password);

        assertThat(result).isEqualTo("token123");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(userDetails);
    }

    @Test
    void authenticateUserDeveLancarExcecaoQuandoAutenticacaoFalha() {
        String username = "user";
        String password = "pass";
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Bad credentials"));

        assertThatThrownBy(() -> userService.authenticateUser(username, password))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Bad credentials");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(jwtService);
    }
}
