package com.lks.core;

import com.lks.core.enums.ExceptionCode;

/**
 * Created by lokkur on 6/23/2015.
 */
public class FALException extends RuntimeException {

    private ExceptionCode exceptionCode;
    private String errorMessage;

    public FALException(){
        super();
    }

    public FALException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public FALException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    public FALException(ExceptionCode exceptionCode, String message, Throwable cause){
        super(message, cause);
        this.exceptionCode = exceptionCode;
        this.errorMessage = message;
    }

    public FALException(ExceptionCode exceptionCode, String message){
        super(message);
        this.exceptionCode = exceptionCode;
        this.errorMessage = message;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getMessage() {
        return super.getMessage() ;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
