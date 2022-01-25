package com.shop.exception;

public class CartProductNotFoundException extends RuntimeException{

    public CartProductNotFoundException(String msg) {
        super(msg);
    }
}
