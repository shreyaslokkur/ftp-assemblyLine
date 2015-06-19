package com.lks.stateMachine;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class NARState extends AbstractState {

    @Override
    public void approve(int documentId, String userId) {
        super.approve(documentId, userId);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void reject(int documentId, String userId) {
        super.reject(documentId, userId);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
