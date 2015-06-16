package com.lks.uploader;

import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;
import com.lks.orm.dao.DataUploadDao;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 6:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataUploadService implements IDataUploadService {


    @Resource(name = "dataUploadDao")
    DataUploadDao dataUploadDao;

    public int createNewDocument(FileReceivedForUploadDO fileReceivedForUploadDO){

        //send it to state machine to create a new record

        return dataUploadDao.fileUploaded(fileReceivedForUploadDO.getFileName(), fileReceivedForUploadDO.getFileLocation(), fileReceivedForUploadDO.getCreatedBy(), fileReceivedForUploadDO.getBranchName(), fileReceivedForUploadDO.getPlaceOfMeeting(), fileReceivedForUploadDO.getBookletNo(), fileReceivedForUploadDO.getApplicationNo(), fileReceivedForUploadDO.getNumOfCustomers());

    }

    public String performOperationOnDocument(FileOperationDO fileOperationDO){

        //send it to state machine
        return null;

    }


}
