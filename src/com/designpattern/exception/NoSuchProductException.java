package com.designpattern.exception;

public class NoSuchProductException extends RuntimeException{
    public NoSuchProductException() {
    }

    public NoSuchProductException(Throwable cause) {
        super(cause);
    }
}
