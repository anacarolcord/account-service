package com.anadev.accountservice.client.dto;

import com.anadev.accountservice.client.dto.enums.TypeTransaction;

public record TransactionUpdateurrentBalance(
        TypeTransaction type
) {
}
