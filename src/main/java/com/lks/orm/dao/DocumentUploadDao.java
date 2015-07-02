package com.lks.orm.dao;

import com.lks.orm.entities.Document;
import com.lks.orm.entities.DocumentArchive;
import com.lks.orm.entities.User;

public interface DocumentUploadDao {

	int fileUploaded(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, int bookletNo, int applicationNo, int numOfCustomers);
    Document retrieveDocument(int documentId);
    boolean lockDocument(int documentId);
    void updateDocument(Document document);
    int archiveDocument(DocumentArchive documentArchive);
    void deleteDocument(Document document);

}