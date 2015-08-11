package com.lks.test;

import com.lks.core.enums.DocOperations;
import com.lks.core.enums.RecStatus;
import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;
import com.lks.orm.entities.Document;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by lokkur on 6/20/2015.
 */

public class StateMachineTest extends AbstractTest {

    static int documentId;
    @Test
    public void testUpload(){
        FileReceivedForUploadDO fileReceivedForUploadDO = new FileReceivedForUploadDO();
        fileReceivedForUploadDO.setFileName("test.txt");
        fileReceivedForUploadDO.setFileLocation("/src/test/resources/test.txt");
        fileReceivedForUploadDO.setBranchCode("abc");
        fileReceivedForUploadDO.setCreatedBy("scanner");
        fileReceivedForUploadDO.setPlaceOfMeeting("bangalore");
        fileReceivedForUploadDO.setApplicationNo(123);
        fileReceivedForUploadDO.setBookletNo(12);
        fileReceivedForUploadDO.setNumOfCustomers(1);

        documentId = documentUploadService.createNewDocument(fileReceivedForUploadDO);

        Document document = documentUploadService.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.getFileName(),"test.txt");
        Assert.assertEquals(document.getCreatedBy(),"scanner");

    }

    @Test(dependsOnMethods = { "testUpload" })
    public void testLock(){
        //lock the document
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.LOCK);
        fileOperationDO.setUserId("Data Operator locker");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.getState(), RecStatus.NR);
        Assert.assertEquals(document.isLocked(), true);
        Assert.assertEquals(document.getLockedBy(),"Data Operator locker");

    }

    @Test(dependsOnMethods = { "testLock" })
    public void testRescan(){
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.RESCAN);
        fileOperationDO.setComment("Document corrupted");
        fileOperationDO.setUserId("data Operator");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.getState(), RecStatus.RN);
        Assert.assertEquals(document.isRescanNeeded(), true);
       Assert.assertEquals(document.getComments().get(0).getComments(), fileOperationDO.getComment());

    }

    @Test(dependsOnMethods = { "testRescan" })
    public void testLockForRescan(){
        //lock the document
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.LOCK);
        fileOperationDO.setUserId("Scanner locker");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.isLocked(), true);
        Assert.assertEquals(document.getState(), RecStatus.RN);
        Assert.assertEquals(document.getLockedBy(),"Scanner locker");

    }

    @Test(dependsOnMethods = { "testLockForRescan" })
    public void testReupload(){
        FileReceivedForUploadDO fileReceivedForUploadDO = new FileReceivedForUploadDO();
        fileReceivedForUploadDO.setFileName("test.txt");
        fileReceivedForUploadDO.setFileLocation("/src/test/resources/test.txt");
        fileReceivedForUploadDO.setBranchCode("abcReupload");
        fileReceivedForUploadDO.setCreatedBy("scanner");
        fileReceivedForUploadDO.setPlaceOfMeeting("bangalore");
        fileReceivedForUploadDO.setApplicationNo(123);
        fileReceivedForUploadDO.setBookletNo(12);
        fileReceivedForUploadDO.setNumOfCustomers(1);
        fileReceivedForUploadDO.setDocumentId(documentId);

        int reuploadDocumentId = documentUploadService.reuploadDocument(fileReceivedForUploadDO);

        Assert.assertEquals(documentId, reuploadDocumentId);

        Document document = documentUploadService.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.getFileName(),"test.txt");
        Assert.assertEquals(document.getCreatedBy(),"scanner");

    }

    @Test(dependsOnMethods = { "testReupload" })
    public void testLockAgain(){
        //lock the document
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.LOCK);
        fileOperationDO.setUserId("locker");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.isLocked(), true);
        Assert.assertEquals(document.getState(), RecStatus.NR);
        Assert.assertEquals(document.getLockedBy(),"locker");

    }

    @Test(dependsOnMethods = { "testLockAgain" })
    public void testHold(){
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.HOLD);
        fileOperationDO.setComment("I am not sure, what needs to be done");
        fileOperationDO.setUserId("holder");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.getState(), RecStatus.HR);
        Assert.assertEquals(document.isOnHold(), true);
        Assert.assertEquals(document.getComments().get(1).getComments(), fileOperationDO.getComment());

    }

    @Test(dependsOnMethods = { "testHold" })
    public void testLockHold(){
        //lock the document
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.LOCK);
        fileOperationDO.setUserId("Holder locker");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.isLocked(), true);
        Assert.assertEquals(document.getState(), RecStatus.HR);
        Assert.assertEquals(document.getLockedBy(),"Holder locker");

    }

    @Test(dependsOnMethods = { "testLockHold" })
    public void testResolve(){

        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.RESOLVE);
        fileOperationDO.setComment("Do this macha");
        fileOperationDO.setUserId("resolver");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.getState(), RecStatus.NR);
        Assert.assertEquals(document.isOnHold(), false);
        Assert.assertEquals(document.getComments().get(2).getComments(), fileOperationDO.getComment());

    }

    @Test(dependsOnMethods = { "testResolve" })
    public void testRelock(){

        //lock the document
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.LOCK);
        fileOperationDO.setUserId("locker");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.isLocked(), true);
        Assert.assertEquals(document.getState(), RecStatus.NR);
        Assert.assertEquals(document.getLockedBy(),"locker");


    }

    @Test(dependsOnMethods = { "testRelock" })
    public void testComplete(){

        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.COMPLETE);
        fileOperationDO.setUserId("finisher");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.getState(), RecStatus.NAR);
        Assert.assertEquals(document.getCompletedBy(),"finisher");


    }

    @Test(dependsOnMethods = { "testComplete" })
    public void testLockNotApproved(){
        //lock the document
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.LOCK);
        fileOperationDO.setUserId("Approver locker");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.isLocked(), true);
        Assert.assertEquals(document.getState(), RecStatus.NAR);
        Assert.assertEquals(document.getLockedBy(),"Approver locker");

    }



    @Test(dependsOnMethods = { "testLockNotApproved" })
    public void testReject(){

        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.REJECT);
        fileOperationDO.setUserId("approver");
        fileOperationDO.setComment("I am rejecting this");
        fileOperationDO.setAssignedTo("locker");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.getState(), RecStatus.RJ);
        Assert.assertEquals(document.getApprovedBy(),"approver");
        Assert.assertEquals(document.getAssignedTo(), "locker");

    }

    @Test(dependsOnMethods = { "testReject" })
    public void testRelockAgain(){

        //lock the document
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.LOCK);
        fileOperationDO.setUserId("locker");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.isLocked(), true);
        Assert.assertEquals(document.getState(), RecStatus.RJ);
        Assert.assertEquals(document.getLockedBy(),"locker");


    }

    @Test(dependsOnMethods = { "testRelockAgain" })
    public void testCompleteAgain(){

        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.COMPLETE);
        fileOperationDO.setUserId("finisher");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.getState(), RecStatus.NAR);
        Assert.assertEquals(document.getCompletedBy(),"finisher");


    }

    @Test(dependsOnMethods = { "testCompleteAgain" })
    public void testLockNotApprovedAgain(){
        //lock the document
        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.LOCK);
        fileOperationDO.setUserId("Approver locker");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.isLocked(), true);
        Assert.assertEquals(document.getState(), RecStatus.NAR);
        Assert.assertEquals(document.getLockedBy(),"Approver locker");

    }

    @Test(dependsOnMethods = { "testLockNotApprovedAgain" })
    public void testApprove(){

        FileOperationDO fileOperationDO = new FileOperationDO();
        fileOperationDO.setDocumentId(documentId);
        fileOperationDO.setDocOperations(DocOperations.APPROVE);
        fileOperationDO.setUserId("approver");
        documentUploadService.performOperationOnDocument(fileOperationDO);

        /*Document document = documentUploadDao.retrieveDocument(documentId);

        Assert.assertNotNull(document);
        Assert.assertEquals(document.getState(), RecStatus.AR);
        Assert.assertEquals(document.getApprovedBy(),"approver");*/

    }


}
