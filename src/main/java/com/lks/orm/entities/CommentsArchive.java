package com.lks.orm.entities;

import com.lks.core.enums.RecStatus;

/**
 * Created by lokkur on 6/19/2015.
 */
public class CommentsArchive {

    private int commentArchiveId;
    private int commentId;
    private int documentArchiveId;
    private String commentedBy;
    private String comments;
    private RecStatus state;

    public int getCommentArchiveId() {
        return commentArchiveId;
    }

    public void setCommentArchiveId(int commentArchiveId) {
        this.commentArchiveId = commentArchiveId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getDocumentArchiveId() {
        return documentArchiveId;
    }

    public void setDocumentArchiveId(int documentArchiveId) {
        this.documentArchiveId = documentArchiveId;
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
}
