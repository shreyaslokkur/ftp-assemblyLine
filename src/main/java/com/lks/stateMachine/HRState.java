package com.lks.stateMachine;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class HRState extends AbstractState {

    @Override
    public void resolve(int documentId, String comment,String assignedTo, String userId) {
        /**
         * 1. Retrieve the document
         * 2. Create a new comment in the Comment table
         * 3. set the assignedTo field value
         * 4. change the state of the record to NR
         * 5. Update the db
         */
    }
}
