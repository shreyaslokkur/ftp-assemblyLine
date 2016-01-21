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

    private Object downloadLock = new Object();
    private Object uploadLock = new Object();

    @Resource(name = "ftpUploader")
    FTPUploader ftpUploader;

    @Override
    public String uploadFile(String localFileFullName, String fileName, String branchCode, String date) {
        synchronized (uploadLock){
            logger.info("Server got a request to upload the file: "+ fileName+ " in branch: "+ branchCode+" with date: "+ date);
            FTPClient ftp = ftpUploader.connect();
            if(!checkIfDirectoryWithBranchCodeExists(ftp,branchCode)){
                createBranchCodeDirectory(ftp,branchCode);
            }
            if(!checkIfDirectoryWithDateExists(ftp,branchCode,date)){
                createDateDirectory(ftp,branchCode, date);
            }
            String dirPath = getDirectoryForBranchAndDate(ftp,branchCode,date);
            long startTime = System.currentTimeMillis();
            String uploadFile = ftpUploader.uploadFile(ftp, localFileFullName, fileName, dirPath);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            logger.info("Time taken to upload the file: "+ fileName+" is: "+ elapsedTime+ " ms");
            return uploadFile;

        }

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
    public File downloadFile(String fileLocation) {
        synchronized (downloadLock){
            logger.info("Server is requested to download the file: "+ fileLocation);
            FTPClient ftp = ftpUploader.connect();
            long startTime = System.currentTimeMillis();
            File downloadFile = ftpUploader.downloadFile(ftp, fileLocation);
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            logger.info("Time taken to download the file: "+ fileLocation+" is: "+ elapsedTime+ " ms");
            return downloadFile;
        }
    }
}
