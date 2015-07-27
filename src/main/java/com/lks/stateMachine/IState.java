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
    public Document lock(Document document, String userId);
    public Document unlock(Document document);
    public Document hold(Document document, String comment, String userId);
    public Document resolve(Document document, String comment,String assignedTo, String userId);
    public Document complete(Document document, String userId);
    public Document approve(Document document, String userId);
    public Document reject(Document document, String comments, String assignedTo, String userId);
    public Document archive(Document document);
    public Document rescan(Document document);
    public Document reupload(Document document);
}
