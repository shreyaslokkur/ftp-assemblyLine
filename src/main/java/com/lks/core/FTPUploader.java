package com.lks.core;

import com.lks.core.enums.ExceptionCode;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;
import java.util.logging.Logger;

/**
 * Created by lokkur on 8/18/2015.
 */
public class FTPUploader {
    private static final Logger logger = Logger.getLogger(FTPUploader.class.getName());


    private String host;
    private String user;
    private String pwd;

    public FTPUploader(String host, String user, String pwd) throws Exception{
        this.host = host;
        this.user = user;
        this.pwd = pwd;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String uploadFile(FTPClient ftp,String localFileFullName, String fileName, String hostDir){
        if(ftp.isConnected()){
            if(!checkIfConnectionExists(ftp)){
                logger.info("FTP connection does not exist, when upload of file: "+fileName+" was attempted");
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

            }
            logger.info("FTP connection exists. Attempting to upload the file: "+ fileName);
            try(InputStream input = new FileInputStream(new File(localFileFullName))){
                String fileLocation = hostDir + "/" + fileName;
                boolean b = ftp.storeFile(fileLocation, input);
                if(b){
                    logger.info("Upload to FTP server successful: "+ fileLocation);
                    return fileLocation;
                }
                else {
                    logger.info("Upload to FTP server unsuccessful: "+ fileLocation);
                    return null;
                }
            } catch (IOException e) {
                logger.info("Upload unsuccessful: "+ fileName);
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to add file: "+ fileName+" to directory: "+ hostDir, e);
            }finally {
                disconnect(ftp);
            }

        }else {
            logger.info("FTP connection does not exist, when upload of file: "+fileName+" was attempted");
            disconnect(ftp);
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Not connected to FTP server");
        }



    }


    private void disconnect(FTPClient ftpClient){
        if (ftpClient.isConnected()) {
            try {
                logger.info("Disconnecting ftpClient");
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }

    public boolean checkDirectoryExists(FTPClient ftp, String dirPath) {
        int returnCode;
        try {
            if(!checkIfConnectionExists(ftp)){
                logger.info("FTP connection does not exist when system attempted to check if directory: "+ dirPath+ " exists");
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

            }
            ftp.changeWorkingDirectory(dirPath);

            returnCode = ftp.getReplyCode();
            if (returnCode == 550) {
                logger.info("Directory: "+ dirPath+" does not exist");
                return false;
            }
            ftp.changeToParentDirectory();
        } catch (IOException e) {
            logger.info("Unable to check if directory: "+ dirPath+ " exists");
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to check if directory exists in the ftp server",e);
        }
        logger.info("Directory: "+ dirPath+" exists");
        return true;
    }

    public boolean createDirectory(FTPClient ftp,String dirPath){
        try {
            if(!checkIfConnectionExists(ftp)){
                logger.info("FTP connection does not exist when system attempted to create directory: "+ dirPath);
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

            }
            return ftp.makeDirectory(dirPath);
        } catch (IOException e) {
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to create new directory "+ dirPath, e);
        }
    }


    public boolean deleteFile(FTPClient ftp,String fileLocation) {
        try {
            if(!checkIfConnectionExists(ftp)){
                logger.info("FTP connection does not exist when system attempted to delete file: "+ fileLocation);
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

            }
            return ftp.deleteFile(fileLocation);
        } catch (IOException e) {
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to delete the file: "+ fileLocation, e);
        }finally {
            disconnect(ftp);
        }
    }

    public File downloadFile(FTPClient ftp,String fileLocation){
        File tmpFile = null;
        String[] split = fileLocation.split("/");
        String fileName = split[split.length - 1];
        if(!checkIfConnectionExists(ftp)){
            logger.info("FTP connection does not exist when system attempted to download file: "+ fileLocation);
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

        }
        try{
            logger.info("Attempting to download the file: "+ fileLocation);
            tmpFile = File.createTempFile(FilenameUtils.getBaseName(fileName), "." + FilenameUtils.getExtension(fileName));
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(tmpFile));
            boolean success = ftp.retrieveFile(fileLocation, outputStream1);
            outputStream1.close();

            if (success) {
                logger.info("Download of file: "+fileLocation+" from FTP server was successful");
                return tmpFile;
            }
            else {
                logger.info("Download of file: "+fileLocation+" from FTP server was unsuccessful");
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to download file: "+ fileLocation+" from server");
            }
        }catch (IOException e){
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Encountered exception when trying to download file: "+ fileLocation, e);
        }finally {
            disconnect(ftp);
        }

    }



    public FTPClient connect(){
        FTPClient ftp = new FTPClient();
        try{
            logger.info("Attempting to connect to FTP server");
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            int reply;
            ftp.connect(host);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("Error connecting to FTP server. Did not get positive reply completion");
                ftp.disconnect();
                throw new FALException(ExceptionCode.SYSTEM_ERROR,"Exception in connecting to FTP Server");
            }
            logger.info("Successfully got FTP connection. Now setting the defaults");
            ftp.login(user, pwd);
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.setDefaultTimeout(6000);
            ftp.setConnectTimeout(3000);
            ftp.setSoTimeout(3000);
            ftp.enterLocalPassiveMode();
            return ftp;
        } catch (SocketException e) {
            disconnect(ftp);
            throw new FALException("Unable to connect to FTP server: "+ e, e);
        } catch (IOException e) {
            disconnect(ftp);
            throw new FALException("Unable to connect to FTP server: "+ e, e);
        }

    }

    private boolean checkIfConnectionExists(FTPClient ftp){

        try
        {
            // Sends a NOOP command to the FTP server.
            boolean answer = ftp.sendNoOp();
            if(answer)
                return true;
            else
            {
                return false;
            }
        }
        catch (FTPConnectionClosedException e)
        {
            return false;

        }
        catch (IOException e)
        {
            return false;
        }
        catch (NullPointerException e)
        {
            return false;
        }
    }



}
