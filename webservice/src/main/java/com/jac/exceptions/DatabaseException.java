package com.jac.exceptions;

public class DatabaseException extends RuntimeException {

    private String message;

    public DatabaseException(String message){
        super(message);
    }
}
