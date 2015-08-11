package com.lks.test;

import com.lks.core.UserUtils;
import com.lks.core.model.UserModelDO;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by lokkur on 6/20/2015.
 */

public class UserTest extends AbstractTest {

    //@Test
    public void testCreateUser(){
        UserModelDO userModelDO = new UserModelDO();
        userModelDO.setUsername("test1");
        userModelDO.setPassword("123456");
        userModelDO.setUserRole("ROLE_DO");
        userModelDO.setBranchCode("Jayanagar");
        String newUser = userService.createNewUser(userModelDO);
        Assert.assertEquals(newUser, userModelDO.getUsername());


    }

    @Test
    public void createHashPassword(){
        for(int i = 0; i< 5; i++){
            System.out.println(UserUtils.createHashedPassword("123456"));
        }

    }






}
