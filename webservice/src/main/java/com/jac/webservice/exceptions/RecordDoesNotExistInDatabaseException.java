package com.jac.webservice.exceptions;

public class RecordDoesNotExistInDatabaseException extends RuntimeException {

    private String message;

    public RecordDoesNotExistInDatabaseException(String message){
        super(message);
    }
}
