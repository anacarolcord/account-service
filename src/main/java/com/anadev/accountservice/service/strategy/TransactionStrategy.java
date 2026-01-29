package com.anadev.accountservice.service.strategy;

import com.anadev.accountservice.dto.AccountResponse;
import com.anadev.accountservice.entity.Account;

import java.math.BigDecimal;

public interface TransactionStrategy {

    AccountResponse execute(Account account, BigDecimal value);
}
