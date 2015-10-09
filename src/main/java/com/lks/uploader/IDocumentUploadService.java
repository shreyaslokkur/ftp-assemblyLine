package com.lks.uploader;

import com.lks.core.enums.DocOperations;
import com.lks.core.model.DocumentDO;
import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;
import com.lks.orm.entities.Document;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDocumentUploadService {

    int createNewDocument(FileReceivedForUploadDO fileReceivedForUploadDO);
    int reuploadDocument(FileReceivedForUploadDO fileReceivedForUploadDO);
    DocumentDO performOperationOnDocument(FileOperationDO fileOperationDO);
    Document retrieveDocument(int documentId);
    List<DocumentDO> retrieveAllNewDocuments(int offset);
    List<DocumentDO> retrieveAllDocumentsAssignedTo(String userId);
    List<DocumentDO> retrieveBranchDocumentsWhichNeedRescan(int branchCode);
    List<DocumentDO> retrieveAllDocumentsWhichNeedApproval(int offset);
    List<DocumentDO> retrieveAllDocumentsWhichAreInHold(int offset);
    List<DocumentDO> retrieveAllRescanDocuments();
    List<DocumentDO> retrieveAllDocuments();
    String retrieveDocumentUrl(int documentId);
    Long retrieveCountOfNewDocuments();
    Long retrieveCountOfHoldDocuments();
    Long retrieveCountOfApprovalDocuments();
}
