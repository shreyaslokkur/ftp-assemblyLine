package com.lks.stateMachine;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IState {

    public int create(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, String bookletNo, String applicationNo, String numOfCustomers);
    public void lock(int documentId, String userId);
    public void hold(int documentId, String userId);
    public void resolve(int documentId, String userId);
    public void complete(int documentId, String userId);
    public void approve(int documentId, String userId);
    public void reject(int documentId, String userId);
}
