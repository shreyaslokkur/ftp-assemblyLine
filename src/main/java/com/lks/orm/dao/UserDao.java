package com.lks.orm.dao;

import com.lks.orm.entities.User;

public interface UserDao {

	User findByUserName(String username);

}