package com.prepfortech.exception;

public class InvalidCredentailsException extends RuntimeException{

    private String message;

    public InvalidCredentailsException(final String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return this.message = message;
    }
}
