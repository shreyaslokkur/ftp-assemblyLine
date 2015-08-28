package com.lks.test;

import com.lks.core.model.DocumentDO;
import com.lks.core.model.FileReceivedForUploadDO;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by lokkur on 6/20/2015.
 */

public class AllRecordsTest extends AbstractTest {

    @Test(dataProvider = "recordsDataProvider")
    public void testMultipleUpload(String fileName, String fileLocation, int branchCode, String createdBy, String placeOfMeeting, int applicationNo, String bookletNo, int noOfCustomers){
        FileReceivedForUploadDO fileReceivedForUploadDO = null;

        fileReceivedForUploadDO = new FileReceivedForUploadDO();
        fileReceivedForUploadDO.setFileName(fileName);
        fileReceivedForUploadDO.setFileLocation(fileLocation);
        fileReceivedForUploadDO.setBranchCode(branchCode);
        fileReceivedForUploadDO.setCreatedBy(createdBy);
        fileReceivedForUploadDO.setPlaceOfMeeting(placeOfMeeting);
        fileReceivedForUploadDO.setApplicationNo(applicationNo);
        fileReceivedForUploadDO.setBookletNo(bookletNo);
        fileReceivedForUploadDO.setNumOfCustomers(noOfCustomers);

        documentUploadService.createNewDocument(fileReceivedForUploadDO);




    }

    @DataProvider(name = "recordsDataProvider")
    public static Object[][] recordsData() {
        return new Object[][] {
                {"test.txt","/src/test/resources/test.txt", 3000, "manohar","bangalore",1000,"12ABC",1},
                {"test.txt","/src/test/resources/test.txt", 3001, "manohar","bangalore",1001,"22ABC",2},
                {"test.txt","/src/test/resources/test.txt", 3002, "manohar","bangalore",1002,"32ABC",3},
                {"test.txt","/src/test/resources/test.txt", 3003, "manohar","bangalore",1003,"42ABC",4},
                {"test.txt","/src/test/resources/test.txt", 3004, "manohar","bangalore",1004,"52ABC",5}


        };
    }

    //@Test(dependsOnMethods = { "testMultipleUpload" })
    public void testAllRecords(){
        List<DocumentDO> documentDOList = documentUploadService.retrieveAllNewAndLockedDocuments();
    }




}
