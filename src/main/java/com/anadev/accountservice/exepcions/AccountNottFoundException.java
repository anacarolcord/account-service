package com.anadev.accountservice.exepcions;

public class AccountNottFoundException extends RuntimeException {
    public AccountNottFoundException() {
        super("Account not found, please check the Id");
    }
}
