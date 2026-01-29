package com.anadev.accountservice.dto;

import com.anadev.accountservice.client.dto.enums.TypeTransaction;

import java.math.BigDecimal;

public record AccountUpdateValues(
        BigDecimal value,
        TypeTransaction typeTransaction) {
}
