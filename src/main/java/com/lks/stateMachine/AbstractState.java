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
    public int create(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, String bookletNo, String applicationNo, String numOfCustomers) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void lock(int documentId, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void hold(int documentId, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void resolve(int documentId, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void complete(int documentId, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void approve(int documentId, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public void reject(int documentId, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }
}
