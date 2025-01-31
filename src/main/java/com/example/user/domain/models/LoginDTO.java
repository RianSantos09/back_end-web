package com.example.user.domain.models;

public class LoginDTO {
    // Atributos da classe
    private String nome;         // Nome do usuário para login
    private double salario;      // Salário do usuário para login
    private String experiencia;  // Experiência do usuário para login

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
    public double getSalario() {
        return salario;
    }

    // Setter para o atributo 'salario'
    public void setSalario(double salario) {
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