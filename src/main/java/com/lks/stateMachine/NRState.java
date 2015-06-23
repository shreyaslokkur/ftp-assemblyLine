package com.lks.stateMachine;

import com.lks.orm.dao.DataUploadDao;
import com.lks.orm.entities.Document;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
@Configurable
public class NRState extends AbstractState {

    @Resource(name = "dataUploadDao")
    DataUploadDao dataUploadDao;

    @Override
    public int create(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, int bookletNo, int applicationNo, int numOfCustomers) {
        return dataUploadDao.fileUploaded(fileName, fileLocation, createdBy, branchName, placeOfMeeting, bookletNo, applicationNo, numOfCustomers);
    }

    @Override
    public void lock(int documentId, String userId) {

        /**
         * 1. Retrieve the document from the database
         * 2. Update the lock flag and lockedBy field
         * 3. Insert it back to database
         */


    }
}
