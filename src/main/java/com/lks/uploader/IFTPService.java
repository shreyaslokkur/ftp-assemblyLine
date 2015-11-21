package com.lks.uploader;

import java.io.File;

/**
 * Created by lokkur on 8/18/2015.
 */
public interface IFTPService {

    String uploadFile(String localFileFullName, String fileName, String branchCode, String date);
    boolean deleteFile(String fileLocation);
    File downloadFile(String fileLocation);
}
