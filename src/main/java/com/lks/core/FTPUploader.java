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
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

            }
            try(InputStream input = new FileInputStream(new File(localFileFullName))){
                String fileLocation = hostDir + "/" + fileName;
                boolean b = ftp.storeFile(fileLocation, input);
                if(b){
                    return fileLocation;
                }
                else {
                    return null;
                }
            } catch (IOException e) {
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to add file: "+ fileName+" to directory: "+ hostDir, e);
            }finally {
                disconnect(ftp);
            }

        }else {
            disconnect(ftp);
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Not connected to FTP server");
        }



    }


    private void disconnect(FTPClient ftpClient){
        if (ftpClient.isConnected()) {
            try {
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
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

            }
            ftp.changeWorkingDirectory(dirPath);

        returnCode = ftp.getReplyCode();
        if (returnCode == 550) {
            return false;
        }
        ftp.changeToParentDirectory();
        } catch (IOException e) {
            System.out.println(e);
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to check if directory exists in the ftp server",e);
        }
        return true;
    }

    public boolean createDirectory(FTPClient ftp,String dirPath){
        try {
            if(!checkIfConnectionExists(ftp)){
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
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

        }
        try{
            tmpFile = File.createTempFile(FilenameUtils.getBaseName(fileName), "." + FilenameUtils.getExtension(fileName));
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(tmpFile));
            boolean success = ftp.retrieveFile(fileLocation, outputStream1);
            outputStream1.close();

            if (success) {
                return tmpFile;
            }
            else {
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to download file: "+ fileLocation+" from server");
            }
        }catch (IOException e){
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Encountered exception when tryin to download file: "+ fileLocation, e);
        }finally {
            disconnect(ftp);
        }

    }



    public FTPClient connect(){
        FTPClient ftp = new FTPClient();
        try{
            ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
            int reply;
            ftp.connect(host);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new FALException(ExceptionCode.SYSTEM_ERROR,"Exception in connecting to FTP Server");
            }
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

    /*private boolean checkIfConnectionExists(FTPClient ftp){

        try
        {
            // Sends a NOOP command to the FTP server.
            boolean answer = ftp.sendNoOp();
            if(answer)
                return true;
            else
            {
                logger.info("Server connection failed!");
                boolean success = connect();
                if(success)
                {
                    logger.info("Reconnect attempt have succeeded!");
                    return true;
                }
                else
                {
                    logger.info("Reconnect attempt failed!");
                    disconnect(ftp);
                    return false;
                }
            }
        }
        catch (FTPConnectionClosedException e)
        {
            logger.info("Server connection is closed!");
            boolean recon = connect();
            if(recon)
            {
                logger.info("Reconnect attempt have succeeded!");
                return true;
            }
            else
            {
                logger.info("Reconnect attempt have failed!");
                disconnect(ftp);
                return false;
            }

        }
        catch (IOException e)
        {
            logger.info("Server connection failed!");
            boolean recon = connect();
            if(recon)
            {
                logger.info("Reconnect attempt have succeeded!");
                return true;
            }
            else
            {
                logger.info("Reconnect attempt have failed!");
                disconnect(ftp);
                return false;
            }
        }
        catch (NullPointerException e)
        {
            logger.info("Server connection is closed!");
            boolean recon = connect();
            if(recon)
            {
                logger.info("Reconnect attempt have succeeded!");
                return true;
            }
            else
            {
                logger.info("Reconnect attempt have failed!");
                disconnect(ftp);
                return false;
            }
        }
    }*/


}
