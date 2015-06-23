package com.lks.orm.dao;

import com.lks.orm.entities.Document;
import com.lks.orm.entities.User;

public interface DataUploadDao {

	int fileUploaded(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, int bookletNo, int applicationNo, int numOfCustomers);
    Document retrieveDocument(int documentId);
    boolean lockDocument(int documentId);

}