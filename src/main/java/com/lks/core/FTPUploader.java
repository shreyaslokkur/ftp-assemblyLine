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

/**
 * Created by lokkur on 8/18/2015.
 */
public class FTPUploader {
    FTPClient ftp = null;

    private String host;
    private String user;
    private String pwd;

    public FTPUploader(String host, String user, String pwd) throws Exception{
        ftp = new FTPClient();
        this.host = host;
        this.user = user;
        this.pwd = pwd;
        connect();
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

    public synchronized String uploadFile(String localFileFullName, String fileName, String hostDir){
        if(ftp.isConnected()){
            if(!checkIfConnectionExists()){
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

            }
            try(InputStream input = new FileInputStream(new File(localFileFullName))){
                String fileLocation = hostDir + "/" + fileName;
                boolean b = this.ftp.storeFile(fileLocation, input);
                if(b){
                    return fileLocation;
                }
                else {
                    return null;
                }
            } catch (IOException e) {
                new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to add file: "+ fileName+" to directory: "+ hostDir);
            }

        }else {
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Not connected to FTP server");
        }

        return null;

    }


    public synchronized void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }

    public synchronized boolean checkDirectoryExists(String dirPath) {
        int returnCode;
        try {
            if(!checkIfConnectionExists()){
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
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to check if directory exists in the ftp server");
        }
        return true;
    }

    public synchronized boolean createDirectory(String dirPath){
        try {
            if(!checkIfConnectionExists()){
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

            }
            return ftp.makeDirectory(dirPath);
        } catch (IOException e) {
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to create new directory "+ dirPath);
        }
    }


    public synchronized boolean deleteFile(String fileLocation) {
        try {
            if(!checkIfConnectionExists()){
                throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to connect to FTP server");

            }
            return ftp.deleteFile(fileLocation);
        } catch (IOException e) {
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Unable to delete the file: "+ fileLocation);
        }
    }

    public synchronized File downloadFile(String fileLocation){
        File tmpFile = null;
        String[] split = fileLocation.split("/");
        String fileName = split[split.length - 1];
        if(!checkIfConnectionExists()){
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
            throw new FALException(ExceptionCode.SYSTEM_ERROR, "Encountered exception when tryin to download file: "+ fileLocation);
        }

    }



    private synchronized boolean connect(){
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
            return true;
        } catch (SocketException e) {
            throw new FALException("Unable to connect to FTP server");
        } catch (IOException e) {
            throw new FALException("Unable to connect to FTP server");
        }

    }

    private boolean checkIfConnectionExists(){

        try
        {
            // Sends a NOOP command to the FTP server.
            boolean answer = ftp.sendNoOp();
            if(answer)
                return true;
            else
            {
                System.out.println("Server connection failed!");
                boolean success = connect();
                if(success)
                {
                    System.out.println("Reconnect attampt have succeeded!");
                    return true;
                }
                else
                {
                    System.out.println("Reconnect attampt failed!");
                    disconnect();
                    return false;
                }
            }
        }
        catch (FTPConnectionClosedException e)
        {
            System.out.println("Server connection is closed!");
            boolean recon = connect();
            if(recon)
            {
                System.out.println("Reconnect attampt have succeeded!");
                return true;
            }
            else
            {
                System.out.println("Reconnect attampt have failed!");
                disconnect();
                return false;
            }

        }
        catch (IOException e)
        {
            System.out.println("Server connection failed!");
            boolean recon = connect();
            if(recon)
            {
                System.out.println("Reconnect attampt have succeeded!");
                return true;
            }
            else
            {
                System.out.println("Reconnect attampt have failed!");
                disconnect();
                return false;
            }
        }
        catch (NullPointerException e)
        {
            System.out.println("Server connection is closed!");
            boolean recon = connect();
            if(recon)
            {
                System.out.println("Reconnect attampt have succeeded!");
                return true;
            }
            else
            {
                System.out.println("Reconnect attampt have failed!");
                disconnect();
                return false;
            }
        }
    }
}
