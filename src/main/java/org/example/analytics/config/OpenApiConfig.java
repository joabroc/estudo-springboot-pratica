package org.example.analytics.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI analyticsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Analytics API")
                        .description("Documentacao dos endpoints de categorias, estabelecimentos e transacoes.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Joabe Borges - Desenvolvedor da Aplicação")
                                .email("joaberochaborges@hotmail.com")));
    }
}

