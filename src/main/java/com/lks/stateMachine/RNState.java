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
public class RNState extends AbstractState {

    public static final Logger logger = Logger.getLogger(RNState.class.getName());

    @Override
    public Document reupload(Document document) {
        logger.info("Reset state to NR, rescanNeededFlag to false");
        document.setState(RecStatus.NR);
        document.setRescanNeeded(false);
        document.setLocked(false);
        document.setLockedBy(null);
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
}
