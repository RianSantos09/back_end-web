package com.example.user.domain.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

// DTO para passar dados para o método de login (login do usuário)
public record UserCreateDTO(
    // Atributos e validação de campos
    @NotBlank(message = "O campo 'nome' é obrigatório!")
    String nome,

    @Min(value = 0, message = "O salário não pode ser negativo!")
    Double salario,  // Alterado de 'idade' para 'salario'

    @NotBlank(message = "O campo 'experiência' é obrigatório!")
    String experiencia  // Alterado de 'email' para 'experiencia'
) {}
