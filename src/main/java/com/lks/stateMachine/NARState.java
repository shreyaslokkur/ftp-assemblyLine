package com.lks.stateMachine;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 11/6/15
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class NARState extends AbstractState {

    @Override
    public void approve(int documentId, String userId) {
        /**
         * 1. Retrieve the document from the database
         * 2. Update the approved by field
         * 3. Retrieve all the comments from the comments table
         * 4. Insert into documentsArchive table
         * 5. Delete all comments from comment table
         * 6. Move the file to the fileArchiver
         * 7. Delete this row from the documents table
         */
    }

    @Override
    public void reject(int documentId, String comments, String assignedTo, String userId) {
        /**
         * 1. Retrieve the record from the database
         * 2. Create a new comment in the Comment table
         * 3. Update the assignedTo field
         * 4. Move the record to NR State
         * 5. Update the database
         */
    }
}
