package com.anadev.account_service.dto;

import com.anadev.account_service.client.dto.enums.TypeTransaction;

import java.math.BigDecimal;

public record AccountUpdateValues(
        BigDecimal value,
        TypeTransaction typeTransaction) {
}
