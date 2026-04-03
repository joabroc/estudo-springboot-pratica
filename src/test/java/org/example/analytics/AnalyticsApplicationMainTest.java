package org.example.analytics;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

class AnalyticsApplicationMainTest {

    @Test
    void mainDeveInicializarSpringApplication() {
        String[] args = new String[]{"--spring.main.banner-mode=off"};

        try (MockedStatic<SpringApplication> springApplication = mockStatic(SpringApplication.class)) {
            AnalyticsApplication.main(args);

            springApplication.verify(() -> SpringApplication.run(AnalyticsApplication.class, args));
        }
    }
}

