package com.lks.uploader;

import com.lks.core.enums.DocOperations;
import com.lks.core.model.DocumentDO;
import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;
import com.lks.orm.dao.DocumentUploadDao;
import com.lks.orm.entities.Document;
import com.lks.stateMachine.IState;
import com.lks.stateMachine.NRState;
import com.lks.stateMachine.StateMachineFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class DocumentUploadService implements IDocumentUploadService {

    public static final Logger logger = Logger.getLogger(DocumentUploadService.class.getName());


    @Resource(name = "documentUploadDao")
    DocumentUploadDao documentUploadDao;

    @Resource(name = "stateMachineFactory")
    StateMachineFactory stateMachineFactory;

    @Transactional
    public int createNewDocument(FileReceivedForUploadDO fileReceivedForUploadDO){

        logger.info("Entered the creation of new document method in data upload service");
        try{
            IState currentState = new NRState();
            return currentState.create(fileReceivedForUploadDO.getFileName(), fileReceivedForUploadDO.getFileLocation(), fileReceivedForUploadDO.getCreatedBy(), fileReceivedForUploadDO.getBranchName(), fileReceivedForUploadDO.getPlaceOfMeeting(), fileReceivedForUploadDO.getBookletNo(), fileReceivedForUploadDO.getApplicationNo(), fileReceivedForUploadDO.getNumOfCustomers());

        }catch(Exception e){
            logger.severe("Encountered exception in the method create New document: "+ e.getMessage());
            System.out.println(e.getMessage());
        }
        return -1;

    }

    @Transactional
    public void reuploadDocument(FileReceivedForUploadDO fileReceivedForUploadDO){
        logger.info("Entered the reupload of document in service class");
        try{
            Document document = documentUploadDao.retrieveDocument(fileReceivedForUploadDO.getDocumentId());
            String oldFileLocation = document.getFileLocation();
            IState currentState = stateMachineFactory.getCurrentState(document.getState());
            document.setFileName(fileReceivedForUploadDO.getFileName());
            document.setFileLocation(fileReceivedForUploadDO.getFileLocation());
            document.setApplicationNo(fileReceivedForUploadDO.getApplicationNo());
            document.setBookletNo(fileReceivedForUploadDO.getBookletNo());
            document.setBranchName(fileReceivedForUploadDO.getBranchName());
            document.setCreatedBy(fileReceivedForUploadDO.getCreatedBy());
            document.setNumOfCustomers(fileReceivedForUploadDO.getNumOfCustomers());
            document.setPlaceOfMeeting(fileReceivedForUploadDO.getPlaceOfMeeting());
            currentState.reupload(document);

            //delete old fileLocation
            File file = new File(oldFileLocation);
            file.delete();

        }catch(Exception e){
            logger.severe("Encountered exception in the method reupload document: "+ e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public synchronized DocumentDO performOperationOnDocument(FileOperationDO fileOperationDO){

        DocOperations docOperations = fileOperationDO.getDocOperations();
        Document document = documentUploadDao.retrieveDocument(fileOperationDO.getDocumentId());
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
            updatedDocument = currentState.rescan(document);
        }

        DocumentDO documentDO = setDocumentDo(updatedDocument);

        return documentDO;

    }




    @Override
    public Document retrieveDocument(int documentId) {
        logger.info("Entered the method in document upload service to retrieve the document with id: "+ documentId);
        try{
            return documentUploadDao.retrieveDocument(documentId);
        }catch(Exception e){
            logger.severe("Encountered exception in the method retrieve document: "+ e.getMessage());
            System.out.println(e.getMessage());
        }

        return null;
    }

    @Override
    public List<DocumentDO> retrieveAllNewAndLockedDocuments() {
        logger.info("Entered the method in document upload service to retrieve all new and locked records ");
        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allNewAndLockedRecords = documentUploadDao.getAllNewAndLockedRecords();
            DocumentDO documentDO = null;
            for(Document document : allNewAndLockedRecords){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.severe("Encountered exception in the method retrieve document: "+ e.getMessage());
            System.out.println(e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public List<DocumentDO> retrieveAllRejectedDocumentsAssignedTo(String userId) {
        logger.info("Entered the method in document upload service to retrieve all rejected documents assigned to: "+ userId);

        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allRecordsAssignedToUser = documentUploadDao.getAllRejectedRecordsAssignedToUser(userId);
            DocumentDO documentDO = null;
            for(Document document : allRecordsAssignedToUser){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.severe("Encountered exception in the method retrieve rejected documents: "+ e.getMessage());
            System.out.println(e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public List<DocumentDO> retrieveAllDocumentsWhichNeedRescan(String branchName) {
        logger.info("Entered the method in document upload service to retrieve all documents which need rescan for the branch: "+branchName);

        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allRecordsWhichNeedRescan = documentUploadDao.getAllRecordsWhichNeedRescan(branchName);
            DocumentDO documentDO = null;
            for(Document document : allRecordsWhichNeedRescan){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.severe("Encountered exception in the method retrieve document: "+ e.getMessage());
            System.out.println(e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public List<DocumentDO> retrieveAllDocumentsWhichNeedApproval() {
        logger.info("Entered the method in document upload service to retrieve all documents which need approval");

        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allRecordsWhichNeedApproval = documentUploadDao.getAllRecordsWhichNeedApproval();
            DocumentDO documentDO = null;
            for(Document document : allRecordsWhichNeedApproval){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.severe("Encountered exception in the method retrieve document: "+ e.getMessage());
            System.out.println(e.getMessage());
        }

        return documentDOList;
    }

    @Override
    public List<DocumentDO> retrieveAllRescanDocuments() {
        logger.info("Entered the method in document upload service to retrieve all documents which are in hold");

        List<DocumentDO> documentDOList = new ArrayList<DocumentDO>();
        try{
            List<Document> allRecordsWhichAreInHold = documentUploadDao.getAllRecordsWhichNeedRescan();
            DocumentDO documentDO = null;
            for(Document document : allRecordsWhichAreInHold){
                documentDO = setDocumentDo(document);
                documentDOList.add(documentDO);
            }
        }catch(Exception e){
            logger.severe("Encountered exception in the method retrieve document: "+ e.getMessage());
            System.out.println(e.getMessage());
        }

        return documentDOList;
    }

    private DocumentDO setDocumentDo(Document document) {
        DocumentDO documentDO = new DocumentDO(document.getDocumentId(),document.getState(), document.getFileName(), document.getFileLocation(),document.getCreatedBy(), document.getBranchName(), document.getPlaceOfMeeting(),document.getBookletNo(), document.getApplicationNo(), document.getNumOfCustomers(), document.getLockedBy(), document.getCompletedBy(), document.getApprovedBy(), document.getAssignedTo(), document.getQueryLevel(), document.isOnHold(), document.isLocked(), document.isApproved(), document.isRescanNeeded(), document.getComments(), document.getRecCreatedOn(), document.getRecCompletedOn(), document.getRecApprovedOn());
        return documentDO;
    }

}
