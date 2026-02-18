package com.anadev.accountservice.dto;

import com.anadev.accountservice.client.dto.enums.TypeTransaction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Builder
public record AccountUpdateValues(
        BigDecimal value,
        TypeTransaction type) {
}
