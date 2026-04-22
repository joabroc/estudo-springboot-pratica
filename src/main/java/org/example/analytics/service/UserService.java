package org.example.analytics.service;

import org.example.analytics.model.User;
import org.example.analytics.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User registerUser(Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        String role = request.get("role");

        if (userRepository.findByUsername(username).isPresent()) {
            log.info("event=service.call entity=user operation=register outcome=failure reason=username_exists username={}", username);
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role != null ? role : "USER");

        User savedUser = userRepository.save(user);
        log.info("event=service.call entity=user operation=register outcome=success id={}", savedUser.getId());
        return savedUser;
    }

    public String authenticateUser(String username, String password) {
        log.debug("event=service.call entity=user operation=authenticate outcome=start username={}", username);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
            log.debug("event=service.call entity=user operation=authenticate outcome=success username={}", username);
            return token;
        }

        log.info("event=service.call entity=user operation=authenticate outcome=failure username={}", username);
        throw new IllegalArgumentException("Invalid credentials");
    }
}
