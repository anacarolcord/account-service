package com.anadev.accountservice.dto;

import com.anadev.accountservice.entity.Account;
import com.anadev.accountservice.entity.User;
import com.anadev.accountservice.entity.enums.TypeAccount;
import com.anadev.accountservice.entity.enums.TypeCurrency;

import java.math.BigDecimal;



public record AccountResponse(
        Long idAccount,
        String name,
        User user,
        TypeAccount typeAccount,
        TypeCurrency currency,
        BigDecimal currentBalance,
        BigDecimal monthlyLimit)
{
    public static AccountResponse fromEntity(Account account){
        return new AccountResponse(
                account.getIdAccount(),
                account.getName(),
                account.getUser(),
                account.getTypeAccount(),
                account.getCurrency(),
                account.getCurrentBalance(),
                account.getMonthlyLimit()
                );
    }
}
