package com.anadev.accountservice.dto;

import com.anadev.accountservice.entity.User;
import com.anadev.accountservice.entity.enums.TypeUser;

import java.time.LocalDateTime;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        TypeUser typeUser,
        LocalDateTime createdAt)
{
    public static UserResponseDto fromEntity(User user){
        return new UserResponseDto(
                user.getIdUser(),
                user.getName(),
                user.getEmail(),
                user.getTypeUser(),
                user.getCreatedAt());
    }
}
