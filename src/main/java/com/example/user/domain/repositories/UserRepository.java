package com.example.user.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user.domain.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNomeAndSalarioAndExperiencia(String nome, double salario, String experiencia);
       // Métodos customizados para busca e verificação de dados baseado em um campo da entidade
    Optional<User> findByEmail(String email); // Retorna o usuário pelo seu email
    boolean existsByEmail(String email); // Verifica se o usuário existe pelo seu email
    
}
