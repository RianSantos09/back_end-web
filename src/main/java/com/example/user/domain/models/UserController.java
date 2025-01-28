package com.example.user.domain.models;

import com.example.user.domain.models.User;
import com.example.user.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    // Endpoint GET para login de usuário com base no nome, salário e experiência
    @GetMapping("/login")
    public User loginUser(@RequestParam String nome, @RequestParam Double salario, @RequestParam String experiencia) {
         List<User> userOptional = findByNomeAndSalarioAndExperiencia(nome, salario, experiencia);
         
        if (userOptional.isPresent()) {
            // Retorna o usuário encontrado
            return userOptional.get(0);
        } else {
            throw new RuntimeException("Usuário não encontrado com essas credenciais");
        }
    }
}
