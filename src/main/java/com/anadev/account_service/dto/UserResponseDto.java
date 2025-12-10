package com.anadev.account_service.dto;

import com.anadev.account_service.entity.Account;
import com.anadev.account_service.entity.User;
import com.anadev.account_service.entity.enums.TypeUser;

import java.time.LocalDateTime;
import java.util.List;

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
