package com.lks.test;

import com.lks.core.UserUtils;
import com.lks.core.model.UserModelDO;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Created by lokkur on 6/20/2015.
 */

public class UserTest extends AbstractTest {

    @Test(dataProvider = "userDataProvider")
    public void testCreateUser(String username, String password, String role, int branchCode){
        UserModelDO userModelDO = new UserModelDO();
        userModelDO.setUsername(username);
        userModelDO.setPassword(password);
        userModelDO.setUserRole(role);
        if(branchCode != 0)
            userModelDO.setBranchCode(branchCode);
        String newUser = userService.createNewUser(userModelDO);
        Assert.assertEquals(newUser, userModelDO.getUsername());


    }

    @DataProvider(name = "userDataProvider")
    public static Object[][] userData() {
        return new Object[][] {
                {"shreyas","123456","ROLE_DO",0},
                {"manohar","123456","ROLE_SCANNER",3000},
                {"ashok","123456","ROLE_APPROVER",0},
                {"vindhya","123456","ROLE_ADMIN",0},
                {"dravid","123456","ROLE_RESOLVER",0},

        };
    }


    /*@Test
    public void createHashPassword(){
        for(int i = 0; i< 5; i++){
            System.out.println(UserUtils.createHashedPassword("123456"));
        }

    }*/






}
