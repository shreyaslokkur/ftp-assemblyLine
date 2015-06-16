package com.lks.stateMachine;

/**
 * Created with IntelliJ IDEA.
 * User: shylu
 * Date: 07/07/13, 1:48 PM
 */
public class InvalidStateTransitionException extends RuntimeException {

    public InvalidStateTransitionException(String message) {
        super(message);
    }
}
