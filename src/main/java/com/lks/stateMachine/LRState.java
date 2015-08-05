package com.lks.stateMachine;

import com.lks.core.enums.RecStatus;
import com.lks.orm.entities.Comments;
import com.lks.orm.entities.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class LRState extends AbstractState {

    public static final Logger logger = Logger.getLogger(LRState.class.getName());



    @Override
    public Document hold(Document document, String comment, String userId) {


        logger.info("Entered into hold method of LRState");
        int queryLevel = document.getQueryLevel();
        logger.info("Current query level is: "+queryLevel);
        queryLevel++;
        document.setQueryLevel(queryLevel);
        logger.info("Query level incremented to: "+ document.getQueryLevel());

        logger.info("Creating a new comment object");
        Comments comments = new Comments();
        comments.setComments(comment);
        comments.setCommentedBy(userId);
        comments.setState(RecStatus.LNR);
        comments.setDocumentId(document.getDocumentId());

        logger.info("Add the comments into the docuemnt object");
        if(document.getComments() == null){
            List<Comments> commentsList = new ArrayList<Comments>();
            commentsList.add(comments);
            document.setComments(commentsList);
        }else {
            document.getComments().add(comments);
        }

        logger.info("Setting the recstatus to Hold ");
        document.setState(RecStatus.HR);
        logger.info("Set the locked by to null, locked flag to false and hold flag to true");
        document.setLockedBy(null);
        document.setLocked(false);
        document.setOnHold(true);
        document.setAssignedTo(null);
        logger.info("Update the document into table");
        documentUploadDao.updateDocument(document);
        return document;

    }

    @Override
    public Document complete(Document document, String userId) {

        logger.info("Set the recStatus to complete");
        document.setState(RecStatus.NAR);
        logger.info("Set completedBy, remove locked flag");
        document.setCompletedBy(userId);
        document.setLocked(false);
        document.setLockedBy(null);
        document.setAssignedTo(null);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        document.setRecCompletedOn(simpleDateFormat.format(date));
        logger.info("Update the document into table");
        documentUploadDao.updateDocument(document);
        return document;
    }

    @Override
    public Document unlock(Document document) {
        logger.info("Update the lock flag to false and set the locked by field to null");
        document.setLocked(false);
        document.setLockedBy(null);
        document.setState(RecStatus.NR);

        logger.info("Update the document: "+document.getDocumentId()+ " into the table");
        documentUploadDao.updateDocument(document);
        return document;

    }

    @Override
    public Document rescan(Document document) {
        logger.info("Rescan the document, by settting the isRescanNeeded flag to true and remove the lock flag, locked by flag");
        document.setRescanNeeded(true);
        document.setLockedBy(null);
        document.setLocked(false);
        document.setState(RecStatus.RN);

        logger.info("Update the document: "+document.getDocumentId()+ " into the table");
        documentUploadDao.updateDocument(document);
        return document;
    }
}
