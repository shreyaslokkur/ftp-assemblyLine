package com.lks.orm.dao;

import com.lks.orm.entities.Document;
import com.lks.orm.entities.User;

public interface DataUploadDao {

	int fileUploaded(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, String bookletNo, String applicationNo, String numOfCustomers);
    Document retrieveDocument(int documentId);

}