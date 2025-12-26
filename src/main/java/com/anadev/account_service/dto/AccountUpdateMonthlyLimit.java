package com.anadev.account_service.dto;

import com.anadev.account_service.entity.Account;
import com.anadev.account_service.entity.User;

import java.math.BigDecimal;

public record AccountUpdateMonthlyLimit(
        BigDecimal monthlyLimit)
{
}
