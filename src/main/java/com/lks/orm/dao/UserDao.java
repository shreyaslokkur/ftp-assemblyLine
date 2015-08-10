package com.lks.orm.dao;

import com.lks.orm.entities.User;

import java.util.List;

public interface UserDao {

	User findByUserName(String username);
	String createNewUser(String username, String password, String branchName, String role);
	boolean editExisitingUser(User user);
	boolean deleteExistingUser(String username);
	List<User> retrieveAllUsers();
	List<String> retrieveAllUsersInRole(String role);
}