package com.anadev.account_service.dto;

import com.anadev.account_service.entity.User;
import com.anadev.account_service.entity.enums.TypeUser;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;

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
