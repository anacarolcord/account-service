package com.anadev.account_service.service;

import com.anadev.account_service.client.dto.enums.TypeTransaction;
import com.anadev.account_service.dto.AccountRequest;
import com.anadev.account_service.dto.AccountResponse;
import com.anadev.account_service.dto.AccountUpdateValues;
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
                .orElseThrow(AccountNottFoundException::new);

        return AccountResponse.fromEntity(account);
    }

    @Transactional
    public List<AccountResponse> findAllAccountsFromUser(Long id){
        User user = getUser(id);

        List<Account> accounts = user.getAccounts();

        return accounts.stream().map(AccountResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public AccountResponse updateValues(AccountUpdateValues data, Long idAccount){
        Account account = accountRepository.findById(idAccount)
                .orElseThrow(AccountNottFoundException::new);

        TypeAccount typeAccount = account.getTypeAccount();

        AccountResponse response;

        if (typeAccount.equals(TypeAccount.CARTAO_CREDITO) ) {
            response = updateMonthlyLimitAccount(data,account);
        }else{
            response = updateCurrentBalanceAccount(data,account);
        }

        return response;
    }

    @Transactional
    public AccountResponse updateMonthlyLimitAccount(AccountUpdateValues data, Account accountUser) {
        // 1. Validar se é saída (compra no cartão)
        if (!data.typeTransaction().equals(TypeTransaction.SAIDA)) {
            // Se for "ENTRADA" no cartão, seria como pagar a fatura (diminuir a dívida)
            //processCreditCardPayment(data, accountUser);
        }

        BigDecimal valorCompra = data.value();
        BigDecimal limiteTotal = accountUser.getMonthlyLimit();
        BigDecimal saldoDevedorAtual = accountUser.getCurrentBalance();

        // 2. Verificar se a compra estoura o limite
        // Regra: (Saldo Devedor + Nova Compra) não pode ser maior que o Limite Total
        if (saldoDevedorAtual.add(valorCompra).compareTo(limiteTotal) > 0) {
            // TODO evento para o RabbitMQ avisando o estouro
            throw new IllegalArgumentException("Limite excedido! Disponível: " + limiteTotal.subtract(saldoDevedorAtual));
        }

        accountUser.setCurrentBalance(saldoDevedorAtual.add(valorCompra));

        accountRepository.save(accountUser);
        return AccountResponse.fromEntity(accountUser);
    }

    @Transactional
    public AccountResponse updateCurrentBalanceAccount(AccountUpdateValues data, Account accountUser){

        if(data.typeTransaction().equals(TypeTransaction.SAIDA)){
            return subtract(accountUser,data.value());
        }
        if(data.typeTransaction().equals(TypeTransaction.ENTRADA)){
            return sum(accountUser,data.value());
        }

        throw new RuntimeException("Erro ");
    }

    @Transactional
    public User getUser(Long idUser){
        return userRepository.findById(idUser)
                .orElseThrow(UserNotFoundException::new);
    }
    
    public BigDecimal getMonthlyLimit(Long idAccount){
        Account accountUser = accountRepository.findById(idAccount)
                .orElseThrow(AccountNottFoundException::new);

        return accountUser.getMonthlyLimit();
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