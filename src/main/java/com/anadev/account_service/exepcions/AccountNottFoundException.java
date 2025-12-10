package com.anadev.account_service.exepcions;

public class AccountNottFoundException extends RuntimeException {
    public AccountNottFoundException() {
        super("Account not found, please check the Id");
    }
}
