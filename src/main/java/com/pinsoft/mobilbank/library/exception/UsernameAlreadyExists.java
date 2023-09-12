package com.pinsoft.mobilbank.library.exception;

public class UsernameAlreadyExists extends RuntimeException{
    public UsernameAlreadyExists(String msg){
        super(msg);
    }
}
