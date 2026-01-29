package com.anadev.accountservice.exepcions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("User not found, please check id");
    }
}
