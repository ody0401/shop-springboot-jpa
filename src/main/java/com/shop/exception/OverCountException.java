package com.shop.exception;

public class OverCountException extends RuntimeException{

    public OverCountException(String msg) {
        super(msg);
    }
}
