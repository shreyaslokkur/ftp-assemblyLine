package com.lks.test;

import com.lks.core.model.BranchDO;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by lokkur on 6/20/2015.
 */

public class BranchTest extends AbstractTest {

    @Test(dataProvider = "branchData")
    public void testCreateBranch(int branchCode,String branchName, String zone, String region){
        BranchDO branchDO = new BranchDO();
        branchDO.setBranchCode(branchCode);
        branchDO.setBranchName(branchName);
        branchDO.setZone(zone);
        branchDO.setRegion(region);
        int newBranchCode = branchService.createNewBranch(branchDO);
        Assert.assertEquals(newBranchCode, branchDO.getBranchCode());


    }

    @DataProvider(name = "userDataProvider")
    public static Object[][] branchData() {
        return new Object[][] {
                {3000,"BSK","SOUTH","KARNATAKA"},
                {3001,"Vijaynagar","WEST","KARNATAKA"},
                {3002,"YELAHANKA","NORTH","KARNATAKA"},
                {3003,"WHITEFIELD","EAST","KARNATAKA"}

        };
    }








}
