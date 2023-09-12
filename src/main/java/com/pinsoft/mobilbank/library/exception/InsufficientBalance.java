package com.pinsoft.mobilbank.library.exception;

public class InsufficientBalance extends RuntimeException{

    public InsufficientBalance(String msg){
        super(msg);
    }
}
