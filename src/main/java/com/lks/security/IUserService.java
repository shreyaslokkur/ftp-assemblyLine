package com.lks.security;

import com.lks.core.model.UserModelDO;
import com.lks.orm.entities.User;

import java.util.List;

/**
 * Created by lokkur on 8/10/2015.
 */
public interface IUserService {

    String createNewUser(UserModelDO userModelDO);
    boolean resetPassword(UserModelDO userModelDO);
    boolean deleteUser(UserModelDO userModelDO);
    UserModelDO findUser(String username);
    List<String> findUsersByRole(String role);
    List<UserModelDO> findAllUsers();
}
