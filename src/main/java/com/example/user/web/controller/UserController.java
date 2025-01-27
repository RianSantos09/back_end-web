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
import com.example.user.domain.models.LoginDTO; // Certifique-se que o LoginDTO tenha nome, salario e experiencia
import com.example.user.domain.models.User;
import com.example.user.domain.models.UserCreateDTO;
import com.example.user.domain.models.UserGetResponseDTO;
import com.example.user.domain.services.UserService;
import com.example.user.infrastructure.mappers.UserMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    // Mapeia a requisição GET para obter um usuário pelo seu ID.
    @GetMapping("/{id}")
    public UserGetResponseDTO findById(@PathVariable int id) {
        User user = userService.findById(id);
        return userMapper.userToUserGetResponseDTO(user);
    }

    // Mapeia a requisição GET para obter uma lista de todos os usuários.
    @GetMapping
    public List<UserGetResponseDTO> findAll() {
        List<User> allUsers = userService.findAll();
        return allUsers.stream().map(userMapper::userToUserGetResponseDTO).collect(Collectors.toList());
    }

    @GetMapping("/pages")
    public List<UserGetResponseDTO> listUsers(Pageable pageable) {
        Page<User> users = userService.listUsers(pageable);
        return users.map(userMapper::userToUserGetResponseDTO).getContent();
    }

    // Alterado: Mapeia a requisição POST para o endpoint de login com nome, salário e experiência
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO data) {
        try {
            // Alteração no método login: agora é por nome, salário e experiência
            userService.login(data.getNome(), data.getSalario(), data.getExperiencia());
            return ResponseEntity.ok("Login efetuado com sucesso");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Mapeia a requisição POST para salvar um novo usuário.
    @PostMapping
    public UserGetResponseDTO save(@RequestBody @Valid UserCreateDTO data) {
        User newUser = userService.save(data);
        return userMapper.userToUserGetResponseDTO(newUser);
    }

    // Mapeia a requisição DELETE para deletar um usuário pelo seu ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        boolean deleted = userService.delete(id);

        if (deleted) {
            return ResponseEntity.ok("Usuário de Id: " + id + " foi deletado com sucesso!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
