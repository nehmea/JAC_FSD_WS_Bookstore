package com.jac.webservice.exceptions;

public class ItemExistException extends RuntimeException {

    private String message;

    public ItemExistException(String message){
        super(message);
    }
}
