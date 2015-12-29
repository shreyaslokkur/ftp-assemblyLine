package com.lks.stateMachine;

import com.lks.core.enums.RecStatus;
import com.lks.orm.entities.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;


/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class RNState extends AbstractState {

    private static final Logger logger = LoggerFactory.getLogger(RNState.class);

    @Override
    public Document reupload(Document document) {
        logger.info("Reset state to NR, rescanNeededFlag to false");
        document.setState(RecStatus.NR);
        document.setRescanNeeded(false);
        document.setLocked(false);
        document.setLockedBy(null);
        document.setUploadState(document.getUploadState());
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
