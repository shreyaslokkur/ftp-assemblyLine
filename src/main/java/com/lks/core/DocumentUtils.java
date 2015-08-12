package com.lks.core;

import com.lks.orm.entities.Comments;
import com.lks.orm.entities.CommentsArchive;
import com.lks.orm.entities.Document;
import com.lks.orm.entities.DocumentArchive;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lokkur on 7/1/2015.
 */
public class DocumentUtils {

    private String tatTime;

    private int tatTimeInMinutes;

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
        documentArchive.setBranchCode(document.getBranchCode());
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
        documentArchive.setRecCreatedOn(document.getRecCreatedOn());
        documentArchive.setRecApprovedOn(document.getRecApprovedOn());
        documentArchive.setRecCompletedOn(document.getRecCompletedOn());
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
            commentsArchive.setDocumentId(comments.getDocumentId());
            commentsArchive.setState(comments.getState());
            commentsArchives.add(commentsArchive);

        }
        return commentsArchives;
    }

    public String getTatTime() {
        return tatTime;
    }

    public void setTatTime(String tatTime) {
        this.tatTime = tatTime;
        setTatTimeInMinutes(tatTime);
    }

    public int getTatTimeInMinutes(){

        return tatTimeInMinutes;
    }

    public void setTatTimeInMinutes(String tatTime) {
        String[] split = tatTime.split(":");
        int tatHours = Integer.parseInt(split[0]);
        int tatMinutes = Integer.parseInt(split[1]);
        int totalTatTimeInMinutes = (tatHours * 60) + tatMinutes;
        tatTimeInMinutes = totalTatTimeInMinutes;
    }

    public static Date convertStringToDate(String dateInString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;

        try {

            date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));

        } catch (ParseException e) {
            throw new FALException("Unable to covert string to date: "+ dateInString);
        }
        return date;
    }
}
