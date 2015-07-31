package com.lks.stateMachine;

import com.lks.core.enums.RecStatus;
import com.lks.orm.entities.Document;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class RJState extends AbstractState {

    public static final Logger logger = Logger.getLogger(RJState.class.getName());

    @Override
    public Document complete(Document document, String userId) {

        logger.info("Set the recStatus to complete");
        document.setState(RecStatus.NAR);
        logger.info("Set completedBy, remove locked flag");
        document.setCompletedBy(userId);
        document.setLocked(false);
        document.setLockedBy(null);
        document.setAssignedTo(null);
        logger.info("Update the document into table");
        documentUploadDao.updateDocument(document);
        return document;
    }


}
