package com.shopp.exceptions;

public class CheckoutStateException extends RuntimeException {
    public CheckoutStateException(String message){
        super(message);
    }
}
