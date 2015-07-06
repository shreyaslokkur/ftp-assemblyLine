package com.lks.stateMachine;

import com.lks.orm.entities.Document;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IState {

    public int create(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, int bookletNo, int applicationNo, int numOfCustomers);
    public void lock(Document document, String userId);
    public void unlock(Document document);
    public void hold(Document document, String comment, String userId);
    public void resolve(Document document, String comment,String assignedTo, String userId);
    public void complete(Document document, String userId);
    public void approve(Document document, String userId);
    public void reject(Document document, String comments, String assignedTo, String userId);
    public void archive(Document document);
    public void rescan(Document document);
    public void reupload(Document document);
}
