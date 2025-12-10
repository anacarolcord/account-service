package com.anadev.account_service.exepcions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found, please check id");
    }
}
