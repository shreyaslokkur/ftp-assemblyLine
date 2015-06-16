package com.lks.uploader;

import com.lks.core.model.FileOperationDO;
import com.lks.core.model.FileReceivedForUploadDO;

/**
 * Created with IntelliJ IDEA.
 * User: shreyas
 * Date: 13/6/15
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IDataUploadService {

    int createNewDocument(FileReceivedForUploadDO fileReceivedForUploadDO);
    String performOperationOnDocument(FileOperationDO fileOperationDO);
}
