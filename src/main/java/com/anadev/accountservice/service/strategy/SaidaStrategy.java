package com.anadev.accountservice.service.strategy;

import com.anadev.accountservice.dto.AccountResponse;
import com.anadev.accountservice.entity.Account;
import com.anadev.accountservice.exepcions.InsufficientBalanceException;

import java.math.BigDecimal;

public class SaidaStrategy implements TransactionStrategy{

    @Override
    public AccountResponse execute(Account account, BigDecimal value) {
        BigDecimal newBalance = account.getCurrentBalance().subtract(value);

        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientBalanceException("Saldo insuficiente");
        }

        account.setCurrentBalance(newBalance);
        return AccountResponse.fromEntity(account);
    }
}
