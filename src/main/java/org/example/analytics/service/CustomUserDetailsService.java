package org.example.analytics.service;

import org.example.analytics.model.User;
import org.example.analytics.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("event=service.call entity=user operation=loadUserByUsername outcome=start username={}", username);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.info("event=service.call entity=user operation=loadUserByUsername outcome=failure username={} reason=user_not_found", username);
                    return new UsernameNotFoundException("User not found: " + username);
                });
        log.debug("event=service.call entity=user operation=loadUserByUsername outcome=success username={}", username);
        return user;
    }
}
