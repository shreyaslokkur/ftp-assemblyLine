package com.lks.core.model;

import com.lks.core.enums.RecStatus;
import com.lks.orm.entities.Comments;

import java.util.List;

/**
 * Created by lokkur on 7/6/2015.
 */
public class DocumentDO extends AbstractDO {

    private int documentId;
    private RecStatus state;
    private String fileName;
    private String fileLocation;
    private String documentUrl = new String("/all/view?documentId=");
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
    private boolean approved;
    private boolean rescanNeeded;
    private List<Comments> comments;
    private String recCreatedOn;
    private String recCompletedOn;
    private String recApprovedOn;

    public DocumentDO(int documentId,RecStatus state, String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, int bookletNo, int applicationNo, int numOfCustomers, String lockedBy, String completedBy, String approvedBy, String assignedTo, int queryLevel, boolean onHold, boolean locked, boolean approved, boolean rescanNeeded, List<Comments> comments, String recCreatedOn, String recCompletedOn, String recApprovedOn) {
        this.documentId = documentId;
        this.state = state;
        this.fileName = fileName;
        this.fileLocation = fileLocation;
        this.createdBy = createdBy;
        this.branchName = branchName;
        this.placeOfMeeting = placeOfMeeting;
        this.bookletNo = bookletNo;
        this.applicationNo = applicationNo;
        this.numOfCustomers = numOfCustomers;
        this.lockedBy = lockedBy;
        this.completedBy = completedBy;
        this.approvedBy = approvedBy;
        this.assignedTo = assignedTo;
        this.queryLevel = queryLevel;
        this.onHold = onHold;
        this.locked = locked;
        this.approved = approved;
        this.rescanNeeded = rescanNeeded;
        this.comments = comments;
        this.recCreatedOn = recCreatedOn;
        this.recCompletedOn = recCompletedOn;
        this.recApprovedOn = recApprovedOn;
        this.documentUrl = this.documentUrl.concat(String.valueOf(documentId));
    }

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

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
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

    public void setOnHold(boolean onHold) {
        this.onHold = onHold;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRescanNeeded() {
        return rescanNeeded;
    }

    public void setRescanNeeded(boolean rescanNeeded) {
        this.rescanNeeded = rescanNeeded;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public String getRecCreatedOn() {
        return recCreatedOn;
    }

    public void setRecCreatedOn(String recCreatedOn) {
        this.recCreatedOn = recCreatedOn;
    }

    public String getRecCompletedOn() {
        return recCompletedOn;
    }

    public void setRecCompletedOn(String recCompletedOn) {
        this.recCompletedOn = recCompletedOn;
    }

    public String getRecApprovedOn() {
        return recApprovedOn;
    }

    public void setRecApprovedOn(String recApprovedOn) {
        this.recApprovedOn = recApprovedOn;
    }
}
