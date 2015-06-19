package com.lks.stateMachine;

import com.lks.core.enums.RecStatus;
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
            if(RecStatus.NR.equals(document.getState()))
                return new NRState();
            else if(RecStatus.AR.equals(document.getState()))
                return new ARState();
            else if(RecStatus.HR.equals(document.getState()))
                return new HRState();
            else if(RecStatus.LR.equals(document.getState()))
                return new LRState();
            else if(RecStatus.NAR.equals(document.getState()))
                return new NARState();
        }



        return null;
    }
}
