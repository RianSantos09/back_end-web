package com.example.user.domain.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user.domain.exceptions.user.NoUsersToListException;
import com.example.user.domain.exceptions.user.UserIdNotFoundException;
import com.example.user.domain.models.User;
import com.example.user.domain.repositories.UserPagesRepository;
import com.example.user.domain.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// Essa é o Service da aplicação (contém as regras de negócio e principais validações da aplicação)
public class UserService {
    private final UserRepository userRepository;
    private final UserPagesRepository userPagesRepository;

    public User findById(int id) { // Obter o usuário pelo Id dele
        return userRepository.findById((long) id).orElseThrow(() -> new UserIdNotFoundException("Usuário de Id: " + id + " não foi encontrado!")); // Busca no repositório, se não encontrar, retorna uma exception customizada
    }

    public List<User> findAll() { // Obter todos os usuários 
        List<User> allUsers = userRepository.findAll(); // Busca no repositório todos os usuários existentes/cadastrados

        if(allUsers.isEmpty())
            throw new NoUsersToListException("Não há usuários para listar!"); // Se não houver nenhum usuário, retorna uma exception

        return allUsers;
    }

    public Page<User> listUsers(Pageable pageable) { // Método de paginação de dados
        return userPagesRepository.findAll(pageable); 
    }

public Optional<User> login(String nome, Double salario, String experiencia) {
    List<User> users = userRepository.findByNomeAndSalarioAndExperiencia(nome, salario, experiencia);

    if (!users.isEmpty()) {
        // Retorna o primeiro usuário da lista
        return Optional.of(users.get(0));
    } else {
        // Caso o usuário não seja encontrado
        throw new RuntimeException("Usuário não encontrado com essas credenciais");
    }
}

@Transactional
public User save(@Valid User user) {
    List<User> users = userRepository.findByNomeAndSalarioAndExperiencia(user.getNome(), user.getSalario(), user.getExperiencia());

    if (!users.isEmpty()) {
        // Se já existir um usuário com essas informações, retorna um erro
        throw new RuntimeException("Já existe um usuário com o mesmo nome, salário e experiência");
    }

    User newUser = new User(); // Cria o novo usuário com os dados recebidos
    return userRepository.save(newUser); // Salva o usuário no banco de dados
}

public boolean delete(int id) {
    User user = findById(id); // Verifica se o usuário existe pelo seu Id
    userRepository.delete(user); // Deleta da base de dados
    return true;
}
}
