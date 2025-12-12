package com.anadev.account_service.dto;

import com.anadev.account_service.entity.Account;
import com.anadev.account_service.entity.enums.TypeAccount;
import com.anadev.account_service.entity.enums.TypeCurrency;

import java.text.DecimalFormat;


public record AccountResponse(
        Long idAccount,
        String name,
        TypeAccount typeAccount,
        TypeCurrency currency,
        Double currentBalance,
        Double monthlyLimit)
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
