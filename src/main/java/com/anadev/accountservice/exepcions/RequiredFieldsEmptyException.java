package com.anadev.accountservice.exepcions;

public class RequiredFieldsEmptyException extends RuntimeException {
    public RequiredFieldsEmptyException() {
        super("Requeired field is empty, try again");
    }
}
