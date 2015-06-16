package com.lks.core.model;

public class FileReceivedForUploadDO extends AbstractDO {

    private static final long serialVersionUID = 1L;

    private String fileName;
    private String fileLocation;
    private String createdBy;
    private String branchName;
    private String placeOfMeeting;
    private String bookletNo;
    private String applicationNo;
    private String numOfCustomers;


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
}
