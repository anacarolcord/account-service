package com.anadev.accountservice.controller;

import com.anadev.accountservice.dto.UserRequestDto;
import com.anadev.accountservice.dto.UserResponseDto;
import com.anadev.accountservice.exepcions.RequiredFieldsEmptyException;
import com.anadev.accountservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto saveUser(@RequestBody UserRequestDto data){
        if(data.name().isBlank() || data.email().isBlank() || data.typeUser().name().isBlank()){
            throw new RequiredFieldsEmptyException();
        }
        return userService.saveUser(data);
    }

    @GetMapping({"/{idUser}"})
    public UserResponseDto getUserById(@PathVariable Long idUser){
        return userService.findUser(idUser);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.findAllUsers();
    }

    @PatchMapping({"/{idUser}"})
    public UserResponseDto updateUserName(@RequestBody UserRequestDto data, @PathVariable Long idUser){
        return userService.updateUserName(idUser,data);
    }

    @PatchMapping({"/{idUser}"})
    public UserResponseDto updateUserEmail(@RequestBody UserRequestDto data, @PathVariable Long idUser){
        return userService.updateUserEmail(idUser,data);
    }
}
