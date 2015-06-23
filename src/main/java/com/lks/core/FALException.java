package com.lks.core;

/**
 * Created by lokkur on 6/23/2015.
 */
public class FALException extends RuntimeException {
    public FALException(){
        super();
    }

    public FALException(String message) {
        super(message);
    }

    public FALException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getMessage() {
        return super.getMessage() ;
    }


}
