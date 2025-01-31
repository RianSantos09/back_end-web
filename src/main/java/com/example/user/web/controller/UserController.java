package com.example.user.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user.domain.exceptions.user.AuthenticationException;
import com.example.user.domain.models.LoginDTO;
import com.example.user.domain.models.User;
import com.example.user.domain.models.UserCreateDTO;
import com.example.user.domain.models.UserGetResponseDTO;
import com.example.user.domain.services.UserService;
import com.example.user.infrastructure.mappers.UserMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/user") // Define o caminho base para todas as rotas deste controlador
@CrossOrigin("*") // Permite requisições de qualquer origem (CORS)
@RequiredArgsConstructor // Gera um construtor com as dependências obrigatórias (injeção de dependência)
public class UserController {
    private final UserService userService; // Serviço que contém a lógica de negócio
    private final UserMapper userMapper = UserMapper.INSTANCE; // Mapper para converter entre DTOs e entidades

    // Endpoint para buscar um usuário pelo ID
    @GetMapping("/{id}")
    public UserGetResponseDTO findById(@PathVariable int id) {
        User user = userService.findById(id); // Busca o usuário pelo ID
        return userMapper.userToUserGetResponseDTO(user); // Converte a entidade User para UserGetResponseDTO
    }

    // Endpoint para listar todos os usuários
    @GetMapping
    public List<UserGetResponseDTO> findAll() {
        List<User> allUsers = userService.findAll(); // Busca todos os usuários
        return allUsers.stream()
                .map(userMapper::userToUserGetResponseDTO) // Converte cada User para UserGetResponseDTO
                .collect(Collectors.toList()); // Coleta os resultados em uma lista
    }

    // Endpoint para listar usuários com paginação
    @GetMapping("/pages")
    public List<UserGetResponseDTO> listUsers(Pageable pageable) {
        Page<User> users = userService.listUsers(pageable); // Busca os usuários paginados
        return users.map(userMapper::userToUserGetResponseDTO).getContent(); // Converte e retorna a lista de usuários
    }

    // Endpoint para autenticação (login) de usuário
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO data) {
        try {
            // Tenta autenticar o usuário com nome, salário e experiência
            userService.login(data.getNome(), data.getSalario(), data.getExperiencia());
            return ResponseEntity.ok("Login efetuado com sucesso"); // Retorna sucesso
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(e.getMessage()); // Retorna erro 401 em caso de falha na autenticação
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // Retorna erro 400 em caso de outros erros
        }
    }

    // Endpoint para criar um novo usuário
    @PostMapping
    public UserGetResponseDTO save(@RequestBody @Valid UserCreateDTO data) {
        // Converte o DTO (UserCreateDTO) para a entidade User
        User newUser = userService.save(userMapper.userCreateDTOToUser(data));
        // Converte a entidade User para o DTO de resposta (UserGetResponseDTO)
        return userMapper.userToUserGetResponseDTO(newUser);
    }

    // Endpoint para deletar um usuário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        boolean deleted = userService.delete(id); // Tenta deletar o usuário

        if (deleted) {
            return ResponseEntity.ok("Usuário de Id: " + id + " foi deletado com sucesso!"); // Retorna sucesso
        } else {
            return ResponseEntity.notFound().build(); // Retorna erro 404 se o usuário não for encontrado
        }
    }
}