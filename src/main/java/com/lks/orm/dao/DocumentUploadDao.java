package com.lks.orm.dao;

import com.lks.core.enums.UploadState;
import com.lks.orm.entities.Document;
import com.lks.orm.entities.DocumentArchive;
import com.lks.orm.entities.User;

import java.util.List;

public interface DocumentUploadDao {

	int fileUploaded(String fileName, String fileLocation, String createdBy, int branchCode, String placeOfMeeting, String bookletNo, int applicationNo, int numOfCustomers, UploadState uploadState);
    Document retrieveDocument(int documentId);
    void updateDocument(Document document);
    int archiveDocument(DocumentArchive documentArchive);
    void deleteDocument(Document document);
    List<Document> getAllNewRecords(int offset);
    List<Document> getAllRecordsAssignedToUser(String userId);
    List<Document> getAllBranchRecordsWhichNeedRescan(int branchCode);
    List<Document> getAllRecordsWhichNeedApproval(int offset);
    List<Document> getAllBranchRecordsWhichNeedRescan();
    List<Document> getAllRecordsWhichAreInHold(int offset);
    List<Document> getAllRecords();
    String retrieveDocumentUrl(int documentId);
    Long retrieveCountOfNewDocuments();
    Long retrieveCountOfHoldDocuments();
    Long retrieveCountOfApprovalDocuments();
}