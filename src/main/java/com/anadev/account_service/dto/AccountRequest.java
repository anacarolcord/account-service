package com.anadev.account_service.dto;

import com.anadev.account_service.entity.enums.TypeAccount;
import com.anadev.account_service.entity.enums.TypeCurrency;
import lombok.Builder;

import java.text.DecimalFormat;

@Builder
public record AccountRequest(
        String name,
        TypeAccount typeAccount,
        TypeCurrency currency,
        DecimalFormat currentBalance,
        DecimalFormat monthlyLimit
){}