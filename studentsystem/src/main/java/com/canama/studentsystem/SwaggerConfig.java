package com.canama.studentsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SwaggerConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Globales CORS-Mapping für alle Endpunkte
                registry.addMapping("/**") // Erlaube alle API-Routen
                        .allowedOrigins("http://localhost:3000", "https://deine-domain.com") // Erlaubte Frontend-URLs
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Erlaubte HTTP-Methoden
                        .allowedHeaders("*") // Erlaube alle Headers
                        .allowCredentials(true); // Erlaube Authentifizierungsinformationen wie Cookies

                // Optional: Spezifisches CORS-Mapping für Swagger UI (falls separat behandelt werden muss)
                registry.addMapping("/swagger-ui/**")
                        .allowedOrigins("http://localhost:3000", "https://deine-domain.com")
                        .allowedMethods("GET", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);

                // Optional: Spezifisches CORS-Mapping für OpenAPI-Dokumentation
                registry.addMapping("/v3/api-docs/**")
                        .allowedOrigins("http://localhost:3000", "https://deine-domain.com")
                        .allowedMethods("GET", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}