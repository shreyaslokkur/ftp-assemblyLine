package com.lks.orm.entities;

import com.lks.core.enums.RecStatus;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 8:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class Document implements IEntity {

    private int documentId;
    private RecStatus state;
    private String fileName;
    private String fileLocation;
    private String createdBy;
    private String branchName;
    private String placeOfMeeting;
    private int bookletNo;
    private int applicationNo;
    private int numOfCustomers;
    private String lockedBy;
    private String completedBy;
    private String approvedBy;
    private String assignedTo;
    private int queryLevel;
    private boolean onHold;
    private boolean locked;


    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public RecStatus getState() {
        return state;
    }

    public void setState(RecStatus state) {
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

    public int getBookletNo() {
        return bookletNo;
    }

    public void setBookletNo(int bookletNo) {
        this.bookletNo = bookletNo;
    }

    public int getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(int applicationNo) {
        this.applicationNo = applicationNo;
    }

    public int getNumOfCustomers() {
        return numOfCustomers;
    }

    public void setNumOfCustomers(int numOfCustomers) {
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


    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public int getQueryLevel() {
        return queryLevel;
    }

    public void setQueryLevel(int queryLevel) {
        this.queryLevel = queryLevel;
    }

    public boolean isOnHold() {
        return onHold;
    }

    public void setIsOnHold(boolean isOnHold) {
        this.onHold = isOnHold;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setIsLocked(boolean isLocked) {
        this.locked = isLocked;
    }
}
