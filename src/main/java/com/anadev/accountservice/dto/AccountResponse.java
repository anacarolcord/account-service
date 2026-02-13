package com.anadev.accountservice.dto;

import com.anadev.accountservice.entity.Account;
import com.anadev.accountservice.entity.enums.TypeAccount;
import com.anadev.accountservice.entity.enums.TypeCurrency;

import java.math.BigDecimal;



public record AccountResponse(
        Long idAccount,
        String name,
        TypeAccount typeAccount,
        TypeCurrency currency,
        BigDecimal currentBalance,
        BigDecimal monthlyLimit)
{
    public static AccountResponse fromEntity(Account account){
        return new AccountResponse(
                account.getIdAccount(),
                account.getName(),
                account.getTypeAccount(),
                account.getCurrency(),
                account.getCurrentBalance(),
                account.getMonthlyLimit()
                );
    }
}
