package com.example.user.domain.models;

public class UserGetResponseDTO {
    private String nome;
    private Double salario;
    private String experiencia;

    // Construtores, Getters e Setters

    public UserGetResponseDTO(String nome, Double salario, String experiencia) {
        this.nome = nome;
        this.salario = salario;
        this.experiencia = experiencia;
    }

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }
}
