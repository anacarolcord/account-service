package com.anadev.accountservice.dto;

import com.anadev.accountservice.entity.User;
import com.anadev.accountservice.entity.enums.TypeUser;

public record UserRequestDto(
    String name,
    String email,
    TypeUser typeUser
){
    public User toEntity(){
        User user = new User();

        user.setName(this.name);
        user.setEmail(this.email);
        user.setTypeUser(this.typeUser);

        return user;
    }
}
