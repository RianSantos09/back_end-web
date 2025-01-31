package com.example.user.domain.models;

public class UserGetResponseDTO {
    // Atributos da classe
    private String nome;        // Nome do usuário
    private Double salario;     // Salário do usuário
    private String experiencia; // Experiência do usuário

    // Construtor com parâmetros para inicializar os atributos
    public UserGetResponseDTO(String nome, Double salario, String experiencia) {
        this.nome = nome;
        this.salario = salario;
        this.experiencia = experiencia;
    }

    // Métodos Getters e Setters

    // Getter para o atributo 'nome'
    public String getNome() {
        return nome;
    }

    // Setter para o atributo 'nome'
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter para o atributo 'salario'
    public Double getSalario() {
        return salario;
    }

    // Setter para o atributo 'salario'
    public void setSalario(Double salario) {
        this.salario = salario;
    }

    // Getter para o atributo 'experiencia'
    public String getExperiencia() {
        return experiencia;
    }

    // Setter para o atributo 'experiencia'
    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }
}