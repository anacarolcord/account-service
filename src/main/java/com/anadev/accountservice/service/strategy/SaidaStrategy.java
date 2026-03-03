package com.anadev.accountservice.service.strategy;

import com.anadev.accountservice.dto.AccountResponse;
import com.anadev.accountservice.entity.Account;
import com.anadev.accountservice.exepcions.InsufficientBalanceException;
import com.anadev.accountservice.exepcions.InvalidValueException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("saidaStrategy")
public class SaidaStrategy implements TransactionStrategy{

    @Override
    public AccountResponse execute(Account account, BigDecimal value) {

        //valida o valor que vem pra sacar
        if(value.compareTo(BigDecimal.ZERO) <=0){
            throw new InvalidValueException();
        }

        BigDecimal newBalance = account.getCurrentBalance().subtract(value);

        //valida o saldo apos o saque
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException();
        }

        account.setCurrentBalance(newBalance);
        return AccountResponse.fromEntity(account);
    }
}
