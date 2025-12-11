package com.anadev.account_service.service;

import com.anadev.account_service.dto.AccountRequest;
import com.anadev.account_service.dto.AccountResponse;
import com.anadev.account_service.entity.Account;
import com.anadev.account_service.entity.User;
import com.anadev.account_service.exepcions.AccountNottFoundException;
import com.anadev.account_service.exepcions.UserNotFoundException;
import com.anadev.account_service.repository.AccountRepository;
import com.anadev.account_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public List<AccountResponse> findAllAccountsFromUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException());

        List<Account> accounts = user.getAccounts();

        return accounts.stream().map(account -> AccountResponse.fromEntity(account))
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountResponse updateMonthlyLimitAccount(AccountRequest data, Long idUser, Long idAccount){
        User user = userRepository.findById(idUser)
                .orElseThrow(()-> new UserNotFoundException());

        Account accountUser = user
                .getAccounts()
                .stream()
                .filter(account -> account.getIdAccount().equals(idAccount))
                .findAny().orElseThrow(()-> new AccountNottFoundException());

        accountUser.setMonthlyLimit(data.monthlyLimit());
        //TODO algum metodo que guarde o historico para usar para o microsserviço de relatorio
        accountRepository.save(accountUser);

        return AccountResponse.fromEntity(accountUser);
    }

    @Transactional
    public AccountResponse updateCurrentBalanceAccount(AccountRequest data, Long idUser, Long idAccount){
        User user = userRepository.findById(idUser)
                .orElseThrow(()-> new UserNotFoundException());

        Account accountUser = user
                .getAccounts()
                .stream()
                .filter(account -> account.getIdAccount().equals(idAccount))
                .findAny().orElseThrow(()-> new AccountNottFoundException());

        accountUser.setCurrentBalance(data.currentBalance());
        //TODO algum metodo que guarde o historico para usar para o microsserviço de relatorio
        accountRepository.save(accountUser);

        return AccountResponse.fromEntity(accountUser);
    }

    @Transactional
    public AccountResponse updateCurrencyAccount(AccountRequest data, Long idUser, Long idAccount){
        User user = userRepository.findById(idUser)
                .orElseThrow(()-> new UserNotFoundException());

        Account accountUser = user
                .getAccounts()
                .stream()
                .filter(account -> account.getIdAccount().equals(idAccount))
                .findAny().orElseThrow(()-> new AccountNottFoundException());

        accountUser.setCurrency(data.currency());
        //TODO algum metodo que guarde o historico para usar para o microsserviço de relatorio
        accountRepository.save(accountUser);

        return AccountResponse.fromEntity(accountUser);
    }


}