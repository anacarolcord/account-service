package com.anadev.account_service.service;

import com.anadev.account_service.dto.AccountRequest;
import com.anadev.account_service.dto.AccountResponse;
import com.anadev.account_service.dto.UserRequestDto;
import com.anadev.account_service.dto.UserResponseDto;
import com.anadev.account_service.entity.Account;
import com.anadev.account_service.entity.User;
import com.anadev.account_service.exepcions.AccountNottFoundException;
import com.anadev.account_service.exepcions.UserNotFoundException;
import com.anadev.account_service.repository.AccountRepository;
import com.anadev.account_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
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

    @Transactional
    public AccountResponse addNewAccount (AccountRequest data, Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException());

        Account account = new Account();
        account.setName(data.name());
        account.setCurrency(data.currency());
        account.setCurrentBalance(data.currentBalance());
        account.setTypeAccount(data.typeAccount());
        account.setMonthlyLimit(data.monthlyLimit());

        accountRepository.save(account);
        user.getAccounts().add(account);
        userRepository.save(user);

        return AccountResponse.fromEntity(account);
    }

    public List<AccountResponse> findAllAccountsFromUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException());

        List<Account> accounts = user.getAccounts();

        return accounts.stream().map(account -> AccountResponse.fromEntity(account))
                .collect(Collectors.toList());
    }

    public AccountResponse updateAccount(AccountRequest data, Long idUser, Long idAccount){
        User user = userRepository.findById(idUser)
                .orElseThrow(()-> new UserNotFoundException());

        List<Account> accounts = user.getAccounts();





    }



}
