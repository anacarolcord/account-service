package com.anadev.accountservice.dto;

import com.anadev.accountservice.entity.enums.TypeAccount;
import com.anadev.accountservice.entity.enums.TypeCurrency;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountRequest(
        String name,
        TypeAccount typeAccount,
        TypeCurrency currency,
        BigDecimal currentBalance,
        BigDecimal monthlyLimit
){}