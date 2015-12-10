package com.lks.stateMachine;

import com.lks.core.DateUtils;
import com.lks.core.enums.RecStatus;
import com.lks.orm.entities.Comments;
import com.lks.orm.entities.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class HRState extends AbstractState {

    private static final Logger logger = LoggerFactory.getLogger(HRState.class);

    @Override
    public Document resolve(Document document, String comment,String assignedTo, String userId) {

        logger.info("Creating a new comment object");
        Comments comments = new Comments();
        comments.setComments(comment);
        comments.setCommentedBy(userId);
        comments.setState(RecStatus.NR);
        comments.setDocumentId(document.getDocumentId());
        comments.setRecCreatedOn(DateUtils.getCurrentDate());
        logger.info("Add the comments into the docuemnt object");
        if(document.getComments() == null){
            List<Comments> commentsList = new ArrayList<Comments>();
            commentsList.add(comments);
            document.setComments(commentsList);
        }else {
            document.getComments().add(comments);
        }

        logger.info("Update the assignedTo, onHold flag and recStatus");
        if(assignedTo == null || assignedTo.length() == 0){
            assignedTo = document.getPutOnHoldBy();
        }
        document.setAssignedTo(assignedTo);
        document.setPutOnHoldBy(null);
        document.setOnHold(false);
        document.setState(RecStatus.NR);
        document.setLocked(false);
        document.setLockedBy(null);
        logger.info("Update the document: "+document.getDocumentId()+" in the table");
        documentUploadDao.updateDocument(document);
        return document;
    }

    @Override
    public Document lock(Document document, String userId) {
        logger.info("Update the lock flag to true and set the locked by field");
        document.setLocked(true);
        document.setLockedBy(userId);
        logger.info("Update the document: "+document.getDocumentId()+ " into the table");
        documentUploadDao.updateDocument(document);
        return document;
    }

    @Override
    public Document unlock(Document document) {
        logger.info("Update the lock flag to false and set the locked by field to null");
        document.setLocked(false);
        document.setLockedBy(null);
        logger.info("Update the document: "+document.getDocumentId()+ " into the table");
        documentUploadDao.updateDocument(document);
        return document;

    }
}
