package com.anadev.accountservice.service.strategy;

import com.anadev.accountservice.dto.AccountResponse;
import com.anadev.accountservice.entity.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("entradaStrategy")
public class EntradaStrategy implements TransactionStrategy {

    @Override
    public AccountResponse execute(Account account, BigDecimal value){

        BigDecimal newBalance = account.getCurrentBalance().add(value);
        account.setCurrentBalance(newBalance);
        return AccountResponse.fromEntity(account);
    }
}
