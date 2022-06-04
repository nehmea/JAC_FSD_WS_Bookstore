package com.jac.webservice.exceptions;

public class DatabaseException extends RuntimeException {

    private Throwable cause;
    private String message;

    public DatabaseException(Throwable message){
        super(message);
    }
    public DatabaseException(String message){
        super(message);
    }
}
