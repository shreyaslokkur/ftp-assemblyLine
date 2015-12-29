package com.lks.uploader;

import com.lks.core.FALException;
import com.lks.core.enums.DocOperations;
import com.lks.core.enums.ExceptionCode;
import com.lks.core.model.DocumentDO;
import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;
import com.lks.orm.dao.DocumentUploadDao;
import com.lks.orm.entities.Document;
import com.lks.stateMachine.IState;
import com.lks.stateMachine.NRState;
import com.lks.stateMachine.StateMachineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class DocumentUploadService implements IDocumentUploadService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentUploadService.class);


    @Resource(name = "documentUploadDao")
    DocumentUploadDao documentUploadDao;

    @Resource(name = "stateMachineFactory")
    StateMachineFactory stateMachineFactory;

    @Resource(name = "ftpService")
    IFTPService ftpService;

    @Transactional
    public int createNewDocument(FileReceivedForUploadDO fileReceivedForUploadDO){

        logger.info("Entered the creation of new document method in data upload service");
        try{
            IState currentState = new NRState();
            return currentState.create(fileReceivedForUploadDO.getFileName(), fileReceivedForUploadDO.getFileLocation(), fileReceivedForUploadDO.getCreatedBy(), fileReceivedForUploadDO.getBranchCode(), fileReceivedForUploadDO.getPlaceOfMeeting(), fileReceivedForUploadDO.getBookletNo(), fileReceivedForUploadDO.getApplicationNo(), fileReceivedForUploadDO.getNumOfCustomers(), fileReceivedForUploadDO.getUploadState());

        }catch(Exception e){
            logger.error("Encountered exception in the method create New document: "+ e.getMessage());
        }
        return -1;

    }

    @Transactional
    public int reuploadDocument(FileReceivedForUploadDO fileReceivedForUploadDO){
        logger.info("Entered the reupload of document in service class");
        try{
            Document document = documentUploadDao.retrieveDocument(fileReceivedForUploadDO.getDocumentId());
            String oldFileLocation = document.getFileLocation();
            IState currentState = stateMachineFactory.getCurrentState(document.getState());
            document.setFileName(fileReceivedForUploadDO.getFileName());
            document.setFileLocation(fileReceivedForUploadDO.getFileLocation());
            document.setApplicationNo(fileReceivedForUploadDO.getApplicationNo());
            document.setBookletNo(fileReceivedForUploadDO.getBookletNo());
            document.setBranchCode(fileReceivedForUploadDO.getBranchCode());
            document.setCreatedBy(fileReceivedForUploadDO.getCreatedBy());
            document.setNumOfCustomers(fileReceivedForUploadDO.getNumOfCustomers());
            document.setPlaceOfMeeting(fileReceivedForUploadDO.getPlaceOfMeeting());
            document.setUploadState(fileReceivedForUploadDO.getUploadState());
            currentState.reupload(document);

            if(ftpService.deleteFile(oldFileLocation)){
                return document.getDocumentId();
            }

            return -1;


        }catch(Exception e){
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to reupload document");

        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized DocumentDO performOperationOnDocument(FileOperationDO fileOperationDO){

        DocOperations docOperations = fileOperationDO.getDocOperations();
        Document document = documentUploadDao.retrieveDocument(fileOperationDO.getDocumentId());
        if(DocOperations.LOCK.equals(docOperations) && document.isLocked()){
            return setDocumentDo(document);
        }
        IState currentState = stateMachineFactory.getCurrentState(document.getState());
        Document updatedDocument = null;
        if(DocOperations.HOLD.equals(docOperations)){
            updatedDocument = currentState.hold(document, fileOperationDO.getComment(), fileOperationDO.getUserId());
        }else if(DocOperations.LOCK.equals(docOperations)){
            updatedDocument = currentState.lock(document, fileOperationDO.getUserId());
        }else if(DocOperations.APPROVE.equals(docOperations)){
            updatedDocument = currentState.approve(document, fileOperationDO.getUserId());
        }else if(DocOperations.REJECT.equals(docOperations)){
            updatedDocument = currentState.reject(document, fileOperationDO.getComment(), fileOperationDO.getAssignedTo(), fileOperationDO.getUserId());
        }else if(DocOperations.COMPLETE.equals(docOperations)){
            updatedDocument = currentState.complete(document, fileOperationDO.getUserId());
        }else if(DocOperations.RESOLVE.equals(docOperations)){
            updatedDocument = currentState.resolve(document, fileOperationDO.getComment(), fileOperationDO.getAssignedTo(), fileOperationDO.getUserId());
        }else if (DocOperations.UNLOCK.equals(docOperations)){
            updatedDocument = currentState.unlock(document);
        }else if(DocOperations.RESCAN.equals(docOperations)){
            updatedDocument = currentState.rescan(document,fileOperationDO.getComment(), fileOperationDO.getUserId());
        }

        DocumentDO documentDO = setDocumentDo(updatedDocument);

        return documentDO;

    }




    @Override
    public Document retrieveDocument(int documentId) {
        logger.info("Entered the method in document upload service to retrieve the document with id: "+ documentId);

            return documentUploadDao.retrieveDocument(documentId);

    }

    @Override
    public List<DocumentDO> retrieveAllNewDocuments(int offset) {
        logger.info("Entered the method in document upload service to retrieve all new and locked records ");
        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allNewAndLockedRecords = documentUploadDao.getAllNewRecords(offset);
            DocumentDO documentDO = null;
            for(Document document : allNewAndLockedRecords){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve document: "+ e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public List<DocumentDO> retrieveAllDocumentsAssignedTo(String userId) {
        logger.info("Entered the method in document upload service to retrieve all documents assigned to: "+ userId);

        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allRecordsAssignedToUser = documentUploadDao.getAllRecordsAssignedToUser(userId);
            DocumentDO documentDO = null;
            for(Document document : allRecordsAssignedToUser){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve documents: "+ e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public List<DocumentDO> retrieveBranchDocumentsWhichNeedRescan(int branchCode) {
        logger.info("Entered the method in document upload service to retrieve all documents which need rescan for the branch: "+branchCode);

        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allRecordsWhichNeedRescan = documentUploadDao.getAllBranchRecordsWhichNeedRescan(branchCode);
            DocumentDO documentDO = null;
            for(Document document : allRecordsWhichNeedRescan){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve document: "+ e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public List<DocumentDO> retrieveAllDocumentsWhichNeedApproval(int offset) {
        logger.info("Entered the method in document upload service to retrieve all documents which need approval");

        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allRecordsWhichNeedApproval = documentUploadDao.getAllRecordsWhichNeedApproval(offset);
            DocumentDO documentDO = null;
            for(Document document : allRecordsWhichNeedApproval){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve document: "+ e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public List<DocumentDO> retrieveAllDocumentsWhichAreInHold(int offset) {
        logger.info("Entered the method in document upload service to retrieve all documents which are in hold");

        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allRecordsWhichAreInHold = documentUploadDao.getAllRecordsWhichAreInHold(offset);
            DocumentDO documentDO = null;
            for(Document document : allRecordsWhichAreInHold){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve document: "+ e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public List<DocumentDO> retrieveAllRescanDocuments() {
        logger.info("Entered the method in document upload service to retrieve all documents which need rescan");

        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allRecordsWhichAreInHold = documentUploadDao.getAllBranchRecordsWhichNeedRescan();
            DocumentDO documentDO = null;
            for(Document document : allRecordsWhichAreInHold){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve document: "+ e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public List<DocumentDO> retrieveAllDocuments() {
        logger.info("Entered the method in document upload service to retrieve all documents ");

        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allRecords = documentUploadDao.getAllRecords();
            DocumentDO documentDO = null;
            for(Document document : allRecords){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve document: "+ e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public String retrieveDocumentUrl(int documentId) {
        logger.info("Entered the method in document upload service to retrieve document url");

        String documentUrl = null;
        try{
            documentUrl = documentUploadDao.retrieveDocumentUrl(documentId);

        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve document: "+ e.getMessage());
        }

        return documentUrl;

    }

    @Override
    public Long retrieveCountOfNewDocuments() {
        logger.info("Entered the method in document upload service to retrieve count of new documents");

        Long count = null;
        try{
            count = documentUploadDao.retrieveCountOfNewDocuments();

        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve document: "+ e.getMessage());
        }

        return count;
    }

    @Override
    public Long retrieveCountOfHoldDocuments() {
        logger.info("Entered the method in document upload service to retrieve count of hold documents");

        Long count = null;
        try{
            count = documentUploadDao.retrieveCountOfHoldDocuments();

        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve count of hold documents: "+ e.getMessage());
        }

        return count;
    }

    @Override
    public Long retrieveCountOfApprovalDocuments() {
        logger.info("Entered the method in document upload service to retrieve count of approval documents");

        Long count = null;
        try{
            count = documentUploadDao.retrieveCountOfApprovalDocuments();

        }catch(Exception e){
            logger.error("Encountered exception in the method retrieve count of approval documents:"+ e.getMessage());
        }

        return count;
    }

    private DocumentDO setDocumentDo(Document document) {
        DocumentDO documentDO = new DocumentDO(document.getDocumentId(),document.getState(), document.getFileName(), document.getFileLocation(),document.getCreatedBy(), document.getBranchCode(), document.getPlaceOfMeeting(),document.getBookletNo(), document.getApplicationNo(), document.getNumOfCustomers(), document.getLockedBy(), document.getCompletedBy(), document.getApprovedBy(), document.getAssignedTo(), document.getQueryLevel(), document.isOnHold(), document.isLocked(), document.isApproved(), document.isRescanNeeded(), document.getComments(), document.getRecCreatedOn(), document.getRecCompletedOn(), document.getRecApprovedOn(), document.getPutOnHoldBy());
        return documentDO;
    }

}
