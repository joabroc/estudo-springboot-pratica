package org.example.analytics.repository;

import org.example.analytics.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void deveSalvarEBuscarUsuarioPorUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole("USER");

        User salvo = repository.save(user);
        Optional<User> encontrado = repository.findByUsername("testuser");

        assertThat(salvo.getId()).isNotNull();
        assertThat(encontrado).isPresent();
        assertThat(encontrado.orElseThrow().getUsername()).isEqualTo("testuser");
        assertThat(encontrado.orElseThrow().getPassword()).isEqualTo("password");
        assertThat(encontrado.orElseThrow().getRole()).isEqualTo("USER");
    }
}
