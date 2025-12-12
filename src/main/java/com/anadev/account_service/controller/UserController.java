package com.anadev.account_service.controller;

import com.anadev.account_service.dto.UserRequestDto;
import com.anadev.account_service.dto.UserResponseDto;
import com.anadev.account_service.exepcions.RequiredFieldsEmptyException;
import com.anadev.account_service.service.AccountService;
import com.anadev.account_service.service.UserService;
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

    @GetMapping({"/id"})
    public UserResponseDto getUserById(@PathVariable Long idUser){
        return userService.findUser(idUser);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers(){
        return userService.findAllUsers();
    }

    @PatchMapping({"/id"})
    public UserResponseDto updateUserName(@RequestBody UserRequestDto data, @PathVariable Long id){
        return userService.updateUserName(id,data);
    }

    @PatchMapping({"/id"})
    public UserResponseDto updateUserEmail(@RequestBody UserRequestDto data, @PathVariable Long id){
        return userService.updateUserEmail(id,data);
    }
}
