package com.anadev.account_service.service;
import com.anadev.account_service.dto.UserRequestDto;
import com.anadev.account_service.dto.UserResponseDto;
import com.anadev.account_service.entity.User;
import com.anadev.account_service.exepcions.UserNotFoundException;
import com.anadev.account_service.repository.AccountRepository;
import com.anadev.account_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserResponseDto saveUser(UserRequestDto data){
        User user = data.toEntity();
        userRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }

    public UserResponseDto findUser(Long id){
       User user = userRepository.findById(id)
               .orElseThrow(()-> new UserNotFoundException());

       return UserResponseDto.fromEntity(user);
    }

    public List<UserResponseDto> findAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(user -> UserResponseDto.fromEntity(user))
                .collect(Collectors.toList());
    }

    public UserResponseDto updateUserName(Long id, UserRequestDto data){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException());

        user.setName(data.name());
        userRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }

    public UserResponseDto updateUserEmail(Long id, UserRequestDto data){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException());

        user.setEmail(data.email());
        userRepository.save(user);

        return UserResponseDto.fromEntity(user);
    }

    public User getUser(Long idUser){
        return userRepository.findById(idUser)
                .orElseThrow(()-> new UserNotFoundException());
    }











}
