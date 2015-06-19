package com.lks.stateMachine;

import com.lks.orm.dao.DataUploadDao;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class NRState extends AbstractState {

    @Resource(name = "dataUploadDao")
    DataUploadDao dataUploadDao;

    @Override
    public int create(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, String bookletNo, String applicationNo, String numOfCustomers) {
        return dataUploadDao.fileUploaded(fileName, fileLocation, createdBy, branchName, placeOfMeeting, bookletNo, applicationNo, numOfCustomers);
    }

    @Override
    public void lock(int documentId, String userId) {
        super.lock(documentId, userId);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
