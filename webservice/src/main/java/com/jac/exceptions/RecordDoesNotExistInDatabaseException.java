package com.jac.exceptions;

public class RecordDoesNotExistInDatabaseException extends RuntimeException {

    private String message;

    public RecordDoesNotExistInDatabaseException(String message){
        super(message);
    }
}
