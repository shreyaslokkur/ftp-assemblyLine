package com.lks.orm.dao;

import com.lks.orm.entities.Document;
import com.lks.orm.entities.DocumentArchive;
import com.lks.orm.entities.User;

import java.util.List;

public interface DocumentUploadDao {

	int fileUploaded(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, int bookletNo, int applicationNo, int numOfCustomers);
    Document retrieveDocument(int documentId);
    void updateDocument(Document document);
    int archiveDocument(DocumentArchive documentArchive);
    void deleteDocument(Document document);
    List<Document> getAllNewAndLockedAndRejectedRecords();
    List<Document> getAllRecordsAssignedToUser(String userId);
    List<Document> getAllRecordsWhichNeedRescan(String branchName);
    List<Document> getAllRecordsWhichNeedApproval();
    List<Document> getAllRecordsWhichNeedRescan();

}