package com.lks.stateMachine;

import com.lks.core.enums.RecStatus;
import com.lks.orm.entities.Comments;
import com.lks.orm.entities.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class HRState extends AbstractState {

    public static final Logger logger = Logger.getLogger(HRState.class.getName());

    @Override
    public void resolve(Document document, String comment,String assignedTo, String userId) {

        logger.info("Creating a new comment object");
        Comments comments = new Comments();
        comments.setComments(comment);
        comments.setCommentedBy(userId);
        comments.setState(RecStatus.HR);
        comments.setDocument(document);

        logger.info("Add the comments into the docuemnt object");
        if(document.getComments() == null){
            List<Comments> commentsList = new ArrayList<Comments>();
            commentsList.add(comments);
            document.setComments(commentsList);
        }else {
            document.getComments().add(comments);
        }

        logger.info("Update the assignedTo, onHold flag and recStatus");
        document.setOnHold(false);
        document.setAssignedTo(assignedTo);
        document.setState(RecStatus.NR);

        logger.info("Update the document: "+document.getDocumentId()+" in the table");
        documentUploadDao.updateDocument(document);
    }
}
