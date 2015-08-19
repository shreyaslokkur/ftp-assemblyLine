package com.lks.uploader;

import com.lks.core.FTPUploader;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by lokkur on 8/18/2015.
 */
public class FTPService implements IFTPService{

    private String rootDir;

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    @Resource(name = "ftpUploader")
    FTPUploader ftpUploader;

    @Override
    public String uploadFile(String localFileFullName, String fileName, String hostDir) {
        return ftpUploader.uploadFile(localFileFullName, fileName, hostDir);

    }

    @Override
    public boolean checkIfDirectoryWithBranchCodeExists(String dirName) {
        String dirPath = rootDir.concat("/").concat(dirName);
        if(ftpUploader.checkDirectoryExists(dirPath))
            return true;
        return false;
    }

    @Override
    public boolean checkIfDirectoryWithDateExists(String branchCode,String date) {
        String dirPath = rootDir.concat("/").concat(branchCode).concat("/").concat(date);
        if(ftpUploader.checkDirectoryExists(dirPath))
            return true;
        return false;
    }

    @Override
    public String createBranchCodeDirectory(String dirName) {
        String dirPath = rootDir.concat("/").concat(dirName);
        if(ftpUploader.createDirectory(dirPath))
            return dirPath;
        return null;
    }

    @Override
    public String createDateDirectory(String branchCode,String date) {
        String dirPath = rootDir.concat("/").concat(branchCode).concat("/").concat(date);
        if(ftpUploader.createDirectory(dirPath))
            return dirPath;
        return null;
    }

    @Override
    public String getDirectoryForBranchAndDate(String branchCode, String date) {
        String dirPath = rootDir.concat("/").concat(branchCode).concat("/").concat(date);
        return dirPath;
    }

    @Override
    public void disconnect() {
        ftpUploader.disconnect();
    }

    public boolean deleteFile(String fileLocation){
        return ftpUploader.deleteFile(fileLocation);

    }

    @Override
    public File downloadFile(String fileLocation) {
        return ftpUploader.downloadFile(fileLocation);
    }
}
