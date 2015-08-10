package com.lks.orm.dao;

import com.lks.orm.entities.User;

public interface UserDao {

	User findByUserName(String username);
	String createNewUser(String username, String password, String branchName, String role);
	void editExisitingUser(User user);
	void deleteExistingUser(String username);
}