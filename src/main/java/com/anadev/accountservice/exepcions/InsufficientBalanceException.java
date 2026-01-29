package com.anadev.accountservice.exepcions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super("Saldo insuficiente");
    }
}
