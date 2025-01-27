package com.example.user.infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.user.domain.models.User;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Usando a porta aleatória para evitar conflitos
public class SpringClientIntegrationTest {

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        // Configura o WebTestClient para apontar para a porta definida automaticamente
        webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost").build();
    }

    @Test
    public void testGetAllUsers() {
        // Testa a requisição GET para obter todos os usuários
        webTestClient.get()
                .uri("/user")  // Certifique-se de que o endpoint "/user" está correto
                .exchange()  // Envia a requisição
                .expectStatus().isOk()  // Verifica que o status HTTP é 200 OK
                .expectBodyList(User.class)  // Espera uma lista de usuários na resposta
                .consumeWith(response -> {
                    List<User> users = response.getResponseBody();
                    assertThat(users).isNotNull();
                    assertThat(users).isNotEmpty();
                });
    }
}
