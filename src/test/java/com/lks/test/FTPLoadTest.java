package com.lks.test;

import com.lks.uploader.IFTPService;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by lokkur on 11/20/2015.
 */
public class FTPLoadTest extends AbstractTest {

    //Create a test case which will do file upload and download on multiple threads
    //Create a data set which will do a mix of both
    //Invoke the method in ftp service

    @Resource(name = "ftpService")
    IFTPService ftpService;
   // @Test
    public void ftpLoadTest(){
        Object[][] ftpLoadTestData = ftpLoadTestData();


        for(final Object[] obj : ftpLoadTestData ){

            Thread t = new Thread() {
                public void run() {

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar cal = Calendar.getInstance();
                    String date = dateFormat.format(cal.getTime());
                    date = date.replaceAll("/","");
                    if(((Integer)obj[3]) == 1){
                        ftpService.uploadFile(((String) obj[0]), ((String) obj[1]), ((String) obj[2]), date);
                    }else if(((Integer)obj[3]) == 0){
                        ftpService.downloadFile(((String) obj[1]));
                    }

                }
            };
            t.start();

        }
        try {
            Thread.sleep(600000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    public Object[][] ftpLoadTestData(){
        return new Object[][]{
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument1.pdf","TestDocument1.pdf","3000",1},
                new Object[]{1,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument2.pdf","TestDocument2.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument3.pdf","TestDocument3.pdf","3000",1},
                new Object[]{2,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument4.pdf","TestDocument4.pdf","3000",1},
                new Object[]{3,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument5.pdf","TestDocument5.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument6.pdf","TestDocument6.pdf","3000",1},
                new Object[]{4,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument7.pdf","TestDocument7.pdf","3000",1},
                new Object[]{5,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument8.pdf","TestDocument8.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument9.pdf","TestDocument9.pdf","3000",1},
                new Object[]{6,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument10.pdf","TestDocument10.pdf","3000",1},
                new Object[]{7,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument11.pdf","TestDocument11.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument12.pdf","TestDocument12.pdf","3000",1},
                new Object[]{8,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument13.pdf","TestDocument13.pdf","3000",1},
                new Object[]{9,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument14.pdf","TestDocument14.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument15.pdf","TestDocument15.pdf","3000",1},
                new Object[]{10,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument16.pdf","TestDocument16.pdf","3000",1},
                new Object[]{11,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument17.pdf","TestDocument17.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument18.pdf","TestDocument18.pdf","3000",1},
                new Object[]{12,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument19.pdf","TestDocument19.pdf","3000",1},
                new Object[]{1,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument20.pdf","TestDocument20.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument21.pdf","TestDocument21.pdf","3000",1},
                new Object[]{2,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument22.pdf","TestDocument22.pdf","3000",1},
                new Object[]{3,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument23.pdf","TestDocument23.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument24.pdf","TestDocument24.pdf","3000",1},
                new Object[]{4,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument25.pdf","TestDocument25.pdf","3000",1},
                new Object[]{5,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument26.pdf","TestDocument26.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument27.pdf","TestDocument27.pdf","3000",1},
                new Object[]{6,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument28.pdf","TestDocument28.pdf","3000",1},
                new Object[]{7,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument29.pdf","TestDocument29.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument30.pdf","TestDocument30.pdf","3000",1},
                new Object[]{8,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument31.pdf","TestDocument31.pdf","3000",1},
                new Object[]{9,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument32.pdf","TestDocument32.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument33.pdf","TestDocument33.pdf","3000",1},
                new Object[]{10,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument34.pdf","TestDocument34.pdf","3000",1},
                new Object[]{11,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument35.pdf","TestDocument35.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument36.pdf","TestDocument36.pdf","3000",1},
                new Object[]{12,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument37.pdf","TestDocument37.pdf","3000",1},
                new Object[]{1,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument38.pdf","TestDocument38.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument39.pdf","TestDocument39.pdf","3000",1},
                new Object[]{2,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument40.pdf","TestDocument40.pdf","3000",1},
                new Object[]{3,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument41.pdf","TestDocument41.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument42.pdf","TestDocument42.pdf","3000",1},
                new Object[]{4,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument43.pdf","TestDocument43.pdf","3000",1},
                new Object[]{5,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument44.pdf","TestDocument44.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument45.pdf","TestDocument45.pdf","3000",1},
                new Object[]{6,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument46.pdf","TestDocument46.pdf","3000",1},
                new Object[]{7,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument47.pdf","TestDocument47.pdf","3001",1},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument48.pdf","TestDocument48.pdf","3000",1},
                new Object[]{8,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument49.pdf","TestDocument49.pdf","3000",1},
                new Object[]{9,"/NewData/LoadTest/3000/21112015/TestDocument1.pdf","3000",0},
                new Object[]{"C:\\Users\\lokkur\\Sandbox\\codebase\\Github\\ftp-assemblyLine\\src\\test\\resoures\\TestDocument50.pdf","TestDocument50.pdf","3001",1}




        };
    }

}
