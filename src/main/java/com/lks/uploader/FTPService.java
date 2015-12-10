package com.lks.uploader;

import com.lks.core.FTPUploader;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by lokkur on 8/18/2015.
 */
public class FTPService implements IFTPService{

    private static final Logger logger = LoggerFactory.getLogger(FTPService.class);

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
    public synchronized String uploadFile(String localFileFullName, String fileName, String branchCode, String date) {
        logger.info("Server got a request to upload the file: "+ fileName+ " in branch: "+ branchCode+" with date: "+ date);
        FTPClient ftp = ftpUploader.connect();
        if(!checkIfDirectoryWithBranchCodeExists(ftp,branchCode)){
            createBranchCodeDirectory(ftp,branchCode);
        }
        if(!checkIfDirectoryWithDateExists(ftp,branchCode,date)){
            createDateDirectory(ftp,branchCode, date);
        }
        String dirPath = getDirectoryForBranchAndDate(ftp,branchCode,date);
        return ftpUploader.uploadFile(ftp, localFileFullName, fileName, dirPath);

    }


    private boolean checkIfDirectoryWithBranchCodeExists(FTPClient ftp,String dirName) {
        String dirPath = rootDir.concat("/").concat(dirName);
        if(ftpUploader.checkDirectoryExists(ftp,dirPath))
            return true;
        return false;
    }


    private  boolean checkIfDirectoryWithDateExists(FTPClient ftp,String branchCode,String date) {
        String dirPath = rootDir.concat("/").concat(branchCode).concat("/").concat(date);
        if(ftpUploader.checkDirectoryExists(ftp,dirPath))
            return true;
        return false;
    }


    private String createBranchCodeDirectory(FTPClient ftp,String dirName) {
        String dirPath = rootDir.concat("/").concat(dirName);
        if(ftpUploader.createDirectory(ftp,dirPath))
            return dirPath;
        return null;
    }


    private String createDateDirectory(FTPClient ftp,String branchCode,String date) {
        String dirPath = rootDir.concat("/").concat(branchCode).concat("/").concat(date);
        if(ftpUploader.createDirectory(ftp,dirPath))
            return dirPath;
        return null;
    }


    private String getDirectoryForBranchAndDate(FTPClient ftp,String branchCode, String date) {
        String dirPath = rootDir.concat("/").concat(branchCode).concat("/").concat(date);
        return dirPath;
    }


    @Override
    public boolean deleteFile(String fileLocation){
        FTPClient ftp = ftpUploader.connect();
        return ftpUploader.deleteFile(ftp,fileLocation);

    }

    @Override
    public synchronized File downloadFile(String fileLocation) {
        logger.info("Server is requested to download the file: "+ fileLocation);
        FTPClient ftp = ftpUploader.connect();
        return ftpUploader.downloadFile(ftp,fileLocation);
    }
}
