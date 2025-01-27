package com.example.user.domain.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.user.domain.models.User;

import reactor.core.publisher.Mono;

@Service
public class SpringClient {

    private final WebClient webClient;

    public SpringClient(@Value("${api.base.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    // Método para buscar um usuário específico para login com base no nome, salário e experiência.
    public Mono<User> loginUser(String nome, Double salario, String experiencia) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/user/login")
                    .queryParam("nome", nome)  // Envia o nome como parâmetro da query
                    .queryParam("salario", salario) // Envia o salário como parâmetro da query
                    .queryParam("experiencia", experiencia) // Envia a experiência como parâmetro da query
                    .build())
                .retrieve()
                .bodyToMono(User.class) // Espera um objeto User como resposta.
                .onErrorMap(throwable -> new RuntimeException("Login failed: " + throwable.getMessage())); // Tratamento de erros.
    }
}
