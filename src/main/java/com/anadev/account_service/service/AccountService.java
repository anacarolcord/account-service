package com.anadev.account_service.service;

import com.anadev.account_service.client.dto.enums.TypeTransaction;
import com.anadev.account_service.dto.AccountRequest;
import com.anadev.account_service.dto.AccountResponse;
import com.anadev.account_service.dto.AccountUpdateCurrencyBalance;
import com.anadev.account_service.dto.AccountUpdateMonthlyLimit;
import com.anadev.account_service.entity.Account;
import com.anadev.account_service.entity.User;
import com.anadev.account_service.entity.enums.TypeAccount;
import com.anadev.account_service.exepcions.AccountNottFoundException;
import com.anadev.account_service.exepcions.UserNotFoundException;
import com.anadev.account_service.repository.AccountRepository;
import com.anadev.account_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Transactional
    public AccountResponse addNewAccount (AccountRequest data, Long idUser){
        User user = getUser(idUser);

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

    public AccountResponse findById(Long idAccount){
        Account account = accountRepository.findById(idAccount)
                .orElseThrow(() -> new AccountNottFoundException());

        return AccountResponse.fromEntity(account);
    }

    @Transactional
    public List<AccountResponse> findAllAccountsFromUser(Long id){
        User user = getUser(id);

        List<Account> accounts = user.getAccounts();

        return accounts.stream().map(account -> AccountResponse.fromEntity(account))
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountResponse updateMonthlyLimitAccount(AccountUpdateMonthlyLimit data, Long idUser, Long idAccount){
        Account accountUser = getAccountUser(idAccount,idUser);

        if(!accountUser.getTypeAccount().equals(TypeAccount.CARTAO_CREDITO)){
            throw new IllegalArgumentException("The limit can only be changed if the account is a credit card");
        }

        accountUser.setMonthlyLimit(data.monthlyLimit());
        //TODO algum metodo que guarde o historico para usar para o microsserviço de relatorio
        accountRepository.save(accountUser);

        return AccountResponse.fromEntity(accountUser);
    }

    @Transactional
    public AccountResponse updateCurrentBalanceAccount(AccountUpdateCurrencyBalance data, Long idUser, Long idAccount){

        Account accountUser = getAccountUser(idAccount,idUser);

        BigDecimal value = data.currencyBalance();

        if(data.typeTransaction().equals(TypeTransaction.SAIDA)){
            subtract(accountUser,value);
        }else if(data.typeTransaction().equals(TypeTransaction.ENTRADA)){
            sum(accountUser,value);
        }
        //TODO algum metodo que guarde o historico para usar para o microsserviço de relatorio
        accountRepository.save(accountUser);

        return AccountResponse.fromEntity(accountUser);
    }

    @Transactional
    public AccountResponse updateCurrencyAccount(AccountRequest data, Long idUser, Long idAccount){

        Account accountUser = getAccountUser(idAccount, idUser);

        accountUser.setCurrency(data.currency());
        //TODO algum metodo que guarde o historico para usar para o microsserviço de relatorio
        accountRepository.save(accountUser);

        return AccountResponse.fromEntity(accountUser);
    }

    @Transactional
    public User getUser(Long idUser){
        return userRepository.findById(idUser)
                .orElseThrow(()-> new UserNotFoundException());
    }
    
    public BigDecimal getMonthlyLimit(Long idUser, Long idAccount){
        Account accountUser = getAccountUser(idAccount, idUser);
        return accountUser.getMonthlyLimit();
    }

    public Account getAccountUser(Long idAccount, Long idUser){
        User user = getUser(idUser);

        return user
                .getAccounts()
                .stream()
                .filter(account -> account.getIdAccount().equals(idAccount))
                .findAny().orElseThrow(()-> new AccountNottFoundException());
    }

    public AccountResponse subtract(Account account, BigDecimal value){
        //if type transaction == Saida
        BigDecimal currentBalance = account.getCurrentBalance();

        if(!(value.compareTo(currentBalance) <= 0)){
            throw new IllegalArgumentException("Insufficient funds");
        }
        currentBalance = currentBalance.subtract(value);

        account.setCurrentBalance(currentBalance);
        accountRepository.save(account);
        return AccountResponse.fromEntity(account);
    }

    public AccountResponse sum(Account account, BigDecimal value){

        BigDecimal currentBalance = account.getCurrentBalance();

        currentBalance = currentBalance.add(value);

        account.setCurrentBalance(currentBalance);
        accountRepository.save(account);

        return  AccountResponse.fromEntity(account);

    }





}