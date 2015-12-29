package com.lks.stateMachine;

import com.lks.core.enums.UploadState;
import com.lks.orm.dao.CommentsDao;
import com.lks.orm.dao.DocumentUploadDao;
import com.lks.orm.entities.Document;
import org.springframework.beans.factory.annotation.Configurable;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:23 PM
 * To change this template use File | Settings | File Templates.
 */


@Configurable
public class AbstractState implements IState {

    @Resource(name = "documentUploadDao")
    protected DocumentUploadDao documentUploadDao;



    @Override
    public int create(String fileName, String fileLocation, String createdBy, int branchCode, String placeOfMeeting, String bookletNo, int applicationNo, int numOfCustomers, UploadState uploadState) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public Document lock(Document document, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public Document unlock(Document document) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public Document hold(Document document, String comment, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public Document resolve(Document document, String comment,String assignedTo, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public Document complete(Document document, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public Document approve(Document document, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public Document reject(Document document, String comments, String assignedTo, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public Document archive(Document document) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public Document rescan(Document document, String comment, String userId) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }

    @Override
    public Document reupload(Document document) {
        throw new InvalidStateTransitionException("Transition to this state is not supported");
    }
}
