package com.lks.security;

import com.lks.core.model.UserModelDO;

/**
 * Created by lokkur on 8/10/2015.
 */
public interface IUserService {

    String createNewUser(UserModelDO userModelDO);
    int resetPassword(UserModelDO userModelDO);
    void deleteUser(UserModelDO userModelDO);
}
