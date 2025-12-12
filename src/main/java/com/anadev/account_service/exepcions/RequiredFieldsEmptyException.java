package com.anadev.account_service.exepcions;

public class RequiredFieldsEmptyException extends RuntimeException {
    public RequiredFieldsEmptyException() {
        super("Requeired field is empty, try again");
    }
}
