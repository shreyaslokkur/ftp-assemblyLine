package com.lks.core;

import com.lks.orm.entities.Comments;
import com.lks.orm.entities.CommentsArchive;
import com.lks.orm.entities.Document;
import com.lks.orm.entities.DocumentArchive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lokkur on 7/1/2015.
 */
public class DocumentUtils {

    public static DocumentArchive archiveDocument(Document document){
        DocumentArchive documentArchive = new DocumentArchive();
        documentArchive.setApproved(document.isApproved());
        documentArchive.setState(document.getState());
        documentArchive.setLocked(document.isLocked());
        documentArchive.setDocumentId(document.getDocumentId());
        documentArchive.setBookletNo(document.getBookletNo());
        documentArchive.setApplicationNo(document.getApplicationNo());
        documentArchive.setApprovedBy(document.getApprovedBy());
        documentArchive.setAssignedTo(document.getAssignedTo());
        documentArchive.setBranchName(document.getBranchName());
        documentArchive.setComments(archiveComments(document.getComments()));
        documentArchive.setCompletedBy(document.getCompletedBy());
        documentArchive.setCreatedBy(document.getCreatedBy());
        documentArchive.setFileLocation(document.getFileLocation());
        documentArchive.setFileName(document.getFileName());
        documentArchive.setNumOfCustomers(document.getNumOfCustomers());
        documentArchive.setPlaceOfMeeting(document.getPlaceOfMeeting());
        documentArchive.setQueryLevel(document.getQueryLevel());
        documentArchive.setLockedBy(document.getLockedBy());
        documentArchive.setOnHold(document.isOnHold());
        return documentArchive;
    }

    public static List<CommentsArchive> archiveComments(List<Comments> commentsList){
        List<CommentsArchive> commentsArchives = new ArrayList<CommentsArchive>();
        CommentsArchive commentsArchive;
        for(Comments comments: commentsList){
            commentsArchive = new CommentsArchive();
            commentsArchive.setCommentId(comments.getCommentId());
            commentsArchive.setCommentedBy(comments.getCommentedBy());
            commentsArchive.setComments(comments.getComments());
            commentsArchive.setDocumentArchiveId(comments.getDocument().getDocumentId());
            commentsArchive.setState(comments.getState());
            commentsArchives.add(commentsArchive);

        }
        return commentsArchives;
    }
}
