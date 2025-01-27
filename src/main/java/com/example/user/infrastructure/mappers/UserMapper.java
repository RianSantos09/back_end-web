package com.example.user.infrastructure.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.user.domain.models.User;
import com.example.user.domain.models.UserGetResponseDTO;

@Mapper
public interface UserMapper {
    // Cria uma instância do UserMapper usando o Mappers, que é uma fábrica para mappers gerados automaticamente.
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Método que define a conversão de um objeto User para um UserGetResponseDTO.
    UserGetResponseDTO userToUserGetResponseDTO(User user);
}
