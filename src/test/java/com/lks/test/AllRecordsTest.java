package com.lks.test;

import com.lks.core.enums.DocOperations;
import com.lks.core.enums.RecStatus;
import com.lks.core.model.DocumentDO;
import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;
import com.lks.orm.entities.Document;
import com.lks.stateMachine.InvalidStateTransitionException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by lokkur on 6/20/2015.
 */

public class AllRecordsTest extends AbstractTest {

    @Test
    public void testMultipleUpload(){
        FileReceivedForUploadDO fileReceivedForUploadDO = null;
        for(int i = 0; i< 5; i++){
            fileReceivedForUploadDO = new FileReceivedForUploadDO();
            fileReceivedForUploadDO.setFileName("test.txt");
            fileReceivedForUploadDO.setFileLocation("/src/test/resources/test.txt");
            fileReceivedForUploadDO.setBranchName("abc");
            fileReceivedForUploadDO.setCreatedBy("scanner");
            fileReceivedForUploadDO.setPlaceOfMeeting("bangalore");
            fileReceivedForUploadDO.setApplicationNo(123);
            fileReceivedForUploadDO.setBookletNo(12);
            fileReceivedForUploadDO.setNumOfCustomers(i);

            documentUploadService.createNewDocument(fileReceivedForUploadDO);

        }


    }

    @Test(dependsOnMethods = { "testMultipleUpload" })
    public void testAllRecords(){
        List<DocumentDO> documentDOList = documentUploadService.retrieveAllNewAndLockedDocuments();
    }




}
