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
                {"test.txt","/src/test/resources/test.txt", 3004, "manohar","bangalore",1004,"52ABC",5},
                {"test.txt","/src/test/resources/test.txt", 3000, "manohar","bangalore",1000,"12ABC",6},
                {"test.txt","/src/test/resources/test.txt", 3001, "manohar","bangalore",1001,"22ABC",7},
                {"test.txt","/src/test/resources/test.txt", 3002, "manohar","bangalore",1002,"32ABC",8},
                {"test.txt","/src/test/resources/test.txt", 3003, "manohar","bangalore",1003,"42ABC",9},
                {"test.txt","/src/test/resources/test.txt", 3004, "manohar","bangalore",1004,"52ABC",10},
                {"test.txt","/src/test/resources/test.txt", 3000, "manohar","bangalore",1000,"12ABC",11},
                {"test.txt","/src/test/resources/test.txt", 3001, "manohar","bangalore",1001,"22ABC",12},
                {"test.txt","/src/test/resources/test.txt", 3002, "manohar","bangalore",1002,"32ABC",13},
                {"test.txt","/src/test/resources/test.txt", 3003, "manohar","bangalore",1003,"42ABC",14},
                {"test.txt","/src/test/resources/test.txt", 3004, "manohar","bangalore",1004,"52ABC",15},
                {"test.txt","/src/test/resources/test.txt", 3000, "manohar","bangalore",1000,"12ABC",16},
                {"test.txt","/src/test/resources/test.txt", 3001, "manohar","bangalore",1001,"22ABC",17},
                {"test.txt","/src/test/resources/test.txt", 3002, "manohar","bangalore",1002,"32ABC",18},
                {"test.txt","/src/test/resources/test.txt", 3003, "manohar","bangalore",1003,"42ABC",19},
                {"test.txt","/src/test/resources/test.txt", 3004, "manohar","bangalore",1004,"52ABC",20},
                {"test.txt","/src/test/resources/test.txt", 3000, "manohar","bangalore",1000,"12ABC",21},
                {"test.txt","/src/test/resources/test.txt", 3001, "manohar","bangalore",1001,"22ABC",22},
                {"test.txt","/src/test/resources/test.txt", 3002, "manohar","bangalore",1002,"32ABC",23},
                {"test.txt","/src/test/resources/test.txt", 3003, "manohar","bangalore",1003,"42ABC",24},
                {"test.txt","/src/test/resources/test.txt", 3004, "manohar","bangalore",1004,"52ABC",25},
                {"test.txt","/src/test/resources/test.txt", 3000, "manohar","bangalore",1000,"12ABC",26},
                {"test.txt","/src/test/resources/test.txt", 3001, "manohar","bangalore",1001,"22ABC",27},
                {"test.txt","/src/test/resources/test.txt", 3002, "manohar","bangalore",1002,"32ABC",28},
                {"test.txt","/src/test/resources/test.txt", 3003, "manohar","bangalore",1003,"42ABC",29},
                {"test.txt","/src/test/resources/test.txt", 3004, "manohar","bangalore",1004,"52ABC",30},
                {"test.txt","/src/test/resources/test.txt", 3000, "manohar","bangalore",1000,"12ABC",31},
                {"test.txt","/src/test/resources/test.txt", 3001, "manohar","bangalore",1001,"22ABC",32},
                {"test.txt","/src/test/resources/test.txt", 3002, "manohar","bangalore",1002,"32ABC",33},
                {"test.txt","/src/test/resources/test.txt", 3003, "manohar","bangalore",1003,"42ABC",34},
                {"test.txt","/src/test/resources/test.txt", 3004, "manohar","bangalore",1004,"52ABC",35},




        };
    }

    //@Test(dependsOnMethods = { "testMultipleUpload" })
    public void testAllRecords(){
        List<DocumentDO> documentDOList = documentUploadService.retrieveAllNewDocuments(0);
    }




}
