package com.lks.stateMachine;

import com.lks.core.enums.RecStatus;
import com.lks.orm.dao.DocumentUploadDao;
import com.lks.orm.entities.Document;
import org.springframework.beans.factory.annotation.Configurable;

import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class NRState extends AbstractState {

    public static final Logger logger = Logger.getLogger(NRState.class.getName());

    @Override
    public int create(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, int bookletNo, int applicationNo, int numOfCustomers) {
        return documentUploadDao.fileUploaded(fileName, fileLocation, createdBy, branchName, placeOfMeeting, bookletNo, applicationNo, numOfCustomers);
    }

    @Override
    public void lock(Document document, String userId) {

        logger.info("Update the lock flag to true and set the locked by field");
        document.setLocked(true);
        document.setLockedBy(userId);
        document.setState(RecStatus.LR);

        logger.info("Update the document: "+document.getDocumentId()+ " into the table");
        documentUploadDao.updateDocument(document);

    }
}
