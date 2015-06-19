package com.lks.orm.dao;

/**
 * Created by lokkur on 6/18/2015.
 */
public class UserDaoFactory {

    private static UserDao userDao;

    public static UserDao getUserDao() {
        return UserDaoFactory.userDao;
    }

    public void setUserDao(UserDao userDao) {
        UserDaoFactory.userDao = userDao;
    }
}
