package com.lks.stateMachine;

import com.lks.core.enums.RecStatus;
import com.lks.orm.dao.DocumentUploadDao;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class StateMachineFactory {

    @Resource(name = "documentUploadDao")
    DocumentUploadDao documentUploadDao;

    public IState getCurrentState(RecStatus recStatus){

        if(RecStatus.NR.equals(recStatus))
            return new NRState();
        else if(RecStatus.AR.equals(recStatus))
            return new ARState();
        else if(RecStatus.HR.equals(recStatus))
            return new HRState();
        else if(RecStatus.NAR.equals(recStatus))
            return new NARState();
        else if(RecStatus.RN.equals(recStatus))
            return new RNState();
        else if(RecStatus.RJ.equals(recStatus))
            return new RJState();
        else
            return null;

    }
}
