package com.lks.stateMachine;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class LRState extends AbstractState {
    @Override
    public void hold(int documentId, String comment, String userId) {

        /**
         * 1. Retrieve the record from the db
         * 2. Increment the query level
         * 3. Create a new comment in the comment table
         * 4. Move the record to HoldState
         * 5. Update the database
         */
    }

    @Override
    public void complete(int documentId, String userId) {
        /**
         * 1. Retrieve the document
         * 2. Move the recStatus of the record to complete
         * 3. Update the completedBy field of the record
         * 4. Update the database
         */
    }
}
