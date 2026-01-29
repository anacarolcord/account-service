package com.anadev.accountservice.exepcions;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException() {
        super("Valor deve ser maior que zero");
    }
}
