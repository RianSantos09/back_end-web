package com.example.user.domain.models;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user.domain.exceptions.user.NoUsersToListException;
import com.example.user.domain.exceptions.user.UserIdNotFoundException;
import com.example.user.domain.repositories.UserPagesRepository;
import com.example.user.domain.repositories.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service // Indica que esta classe é um serviço gerenciado pelo Spring
@RequiredArgsConstructor // Gera um construtor com as dependências obrigatórias (injeção de dependência)
public class UserService {
    private final UserRepository userRepository; // Repositório para operações de banco de dados relacionadas a User
    private final UserPagesRepository userPagesRepository; // Repositório para operações de paginação de User

    // Método para buscar um usuário pelo ID
    public User findById(int id) {
        // Busca o usuário pelo ID e lança uma exceção se não for encontrado
        return userRepository.findById((long) id)
                .orElseThrow(() -> new UserIdNotFoundException("Usuário de Id: " + id + " não foi encontrado!"));
    }

    // Método para buscar todos os usuários
    public List<User> findAll() {
        List<User> allUsers = userRepository.findAll(); // Busca todos os usuários no banco de dados

        if (allUsers.isEmpty()) {
            // Lança uma exceção se não houver usuários para listar
            throw new NoUsersToListException("Não há usuários para listar!");
        }

        return allUsers; // Retorna a lista de usuários
    }

    // Método para listar usuários com paginação
    public Page<User> listUsers(Pageable pageable) {
        // Retorna uma página de usuários com base nos parâmetros de paginação
        return userPagesRepository.findAll(pageable);
    }

    // Método para autenticar um usuário com base no nome, salário e experiência
    public Optional<User> login(String nome, Double salario, String experiencia) {
        // Busca usuários com base no nome, salário e experiência
        List<User> users = userRepository.findByNomeAndSalarioAndExperiencia(nome, salario, experiencia);

        if (!users.isEmpty()) {
            // Retorna o primeiro usuário encontrado
            return Optional.of(users.get(0));
        } else {
            // Lança uma exceção se nenhum usuário for encontrado
            throw new RuntimeException("Usuário não encontrado com essas credenciais");
        }
    }

    // Método para salvar um novo usuário (com validação e transação)
    @Transactional // Garante que a operação seja executada dentro de uma transação
    public User save(@Valid User user) {
        // Verifica se já existe um usuário com o mesmo nome, salário e experiência
        List<User> users = userRepository.findByNomeAndSalarioAndExperiencia(user.getNome(), user.getSalario(), user.getExperiencia());

        if (!users.isEmpty()) {
            // Lança uma exceção se já existir um usuário com as mesmas informações
            throw new RuntimeException("Já existe um usuário com o mesmo nome, salário e experiência");
        }

        User newUser = new User(); // Cria um novo usuário (aqui você pode adicionar lógica adicional, como criptografia de senha)
        return userRepository.save(newUser); // Salva o novo usuário no banco de dados
    }

    // Método para deletar um usuário pelo ID
    public boolean delete(int id) {
        User user = findById(id); // Busca o usuário pelo ID (lança exceção se não existir)
        userRepository.delete(user); // Deleta o usuário do banco de dados
        return true; // Retorna true indicando que a operação foi bem-sucedida
    }
}