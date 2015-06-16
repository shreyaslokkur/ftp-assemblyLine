package com.lks.stateMachine;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class AbstractState implements IState {
    @Override
    public void create() {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void lock() {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void hold() {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void resolved() {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void completed() {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void approved() {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void rejected() {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }
}
