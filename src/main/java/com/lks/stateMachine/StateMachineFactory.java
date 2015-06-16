package com.lks.stateMachine;

import com.lks.orm.dao.DataUploadDao;
import com.lks.orm.entities.Document;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateMachineFactory {

    @Resource(name = "dataUploadDao")
    DataUploadDao dataUploadDao;

    public IState getCurrentState(int documentId){
        if(documentId != 0){
            Document document = dataUploadDao.retrieveDocument(documentId);
        }
        else
            return new NRState();


        return null;
    }
}
