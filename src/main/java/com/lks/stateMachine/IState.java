package com.lks.stateMachine;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IState {

    public void create();
    public void lock();
    public void hold();
    public void resolved();
    public void completed();
    public void approved();
    public void rejected();
}
