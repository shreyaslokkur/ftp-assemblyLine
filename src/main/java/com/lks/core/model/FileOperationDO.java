package com.lks.core.model;

import com.lks.core.enums.DocOperations;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileOperationDO extends AbstractDO {

    private int documentId;
    private DocOperations docOperations;
    private String userId;
    private String assignedTo;
    private String comment;

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public DocOperations getDocOperations() {
        return docOperations;
    }

    public void setDocOperations(DocOperations docOperations) {
        this.docOperations = docOperations;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
}
