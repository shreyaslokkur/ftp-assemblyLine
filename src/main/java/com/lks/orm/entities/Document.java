package com.lks.orm.entities;

import com.lks.stateMachine.IState;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 8:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Document implements IEntity {

    private IState state;
    private String fileName;
    private String fileLocation;
    private String createdBy;
    private String branchName;
    private String placeOfMeeting;
    private String bookletNo;
    private String applicationNo;
    private String numOfCustomers;
    private String lockedBy;
    private String completedBy;
    private String approvedBy;
    private int queryLevel;

    public IState getState() {
        return state;
    }

    public void setState(IState state) {
        this.state = state;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getPlaceOfMeeting() {
        return placeOfMeeting;
    }

    public void setPlaceOfMeeting(String placeOfMeeting) {
        this.placeOfMeeting = placeOfMeeting;
    }

    public String getBookletNo() {
        return bookletNo;
    }

    public void setBookletNo(String bookletNo) {
        this.bookletNo = bookletNo;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getNumOfCustomers() {
        return numOfCustomers;
    }

    public void setNumOfCustomers(String numOfCustomers) {
        this.numOfCustomers = numOfCustomers;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public int getQueryLevel() {
        return queryLevel;
    }

    public void setQueryLevel(int queryLevel) {
        this.queryLevel = queryLevel;
    }
}
