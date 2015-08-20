package com.lks.orm.entities;

import com.lks.core.enums.RecStatus;

/**
 * Created by lokkur on 6/19/2015.
 */
public class Comments {

    private int commentId;
    private int documentId;
    private String commentedBy;
    private String comments;
    private RecStatus state;
    private String recCreatedOn;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public RecStatus getState() {
        return state;
    }

    public void setState(RecStatus state) {
        this.state = state;
    }

    public String getRecCreatedOn() {
        return recCreatedOn;
    }

    public void setRecCreatedOn(String recCreatedOn) {
        this.recCreatedOn = recCreatedOn;
    }
}
