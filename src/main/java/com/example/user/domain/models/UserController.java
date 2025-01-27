package com.example.user.controllers;

import com.example.user.domain.models.User;
import com.example.user.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Endpoint POST para criar um novo usuário
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody UserCreateDTO userCreateDTO) {
        // Cria o usuário com a senha criptografada
        User user = User.fromDTOWithEncryptedPassword(userCreateDTO);
        // Salva no banco de dados
        return userRepository.save(user);
    }

    // Endpoint GET para obter informações básicas do usuário (nome e idade)
    @GetMapping("/{id}")
    public UserGetResponseDTO getUser(@PathVariable int id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Retorna um DTO com os dados básicos do usuário
            return new UserGetResponseDTO(user.getNome(), user.getNome());
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }
}
