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
public class NARState extends AbstractState {

    public static final Logger logger = Logger.getLogger(NARState.class.getName());

    @Override
    public Document approve(Document document, String userId) {
        /**
         * 1. Retrieve the document from the database
         * 2. Update the approved by field
         * 3. Retrieve all the comments from the comments table
         * 4. Insert into documentsArchive table
         * 5. Delete all comments from comment table
         * 6. Move the file to the fileArchiver
         * 7. Delete this row from the documents table
         */
        logger.info("Update the assigned to field and recStatus. Save it into database");
        document.setState(RecStatus.AR);
        document.setApprovedBy(userId);
        document.setApproved(true);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        document.setRecApprovedOn(simpleDateFormat.format(date));
        documentUploadDao.updateDocument(document);

        logger.info("Archive the document");
        IState state = new ARState();
        state.archive(document);
        return document;
    }

    @Override
    public Document reject(Document document, String comment, String assignedTo, String userId) {

        logger.info("Creating a new comment object");
        Comments comments = new Comments();
        comments.setComments(comment);
        comments.setCommentedBy(userId);
        comments.setState(RecStatus.RJ);
        comments.setDocumentId(document.getDocumentId());

        logger.info("Add the comments into the docuemnt object");
        if(document.getComments() == null){
            List<Comments> commentsList = new ArrayList<Comments>();
            commentsList.add(comments);
            document.setComments(commentsList);
        }else {
            document.getComments().add(comments);
        }

        logger.info("Update the assigned to field and recStatus. Save it into database");
        document.setState(RecStatus.RJ);
        if(assignedTo == null || assignedTo.length() == 0){
            assignedTo = document.getCompletedBy();
        }
        document.setAssignedTo(assignedTo);
        document.setApprovedBy(userId);
        document.setApproved(false);
        documentUploadDao.updateDocument(document);
        return document;

    }
}
