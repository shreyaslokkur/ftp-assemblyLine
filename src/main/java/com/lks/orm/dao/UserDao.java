package com.lks.orm.dao;

import com.lks.orm.entities.User;
import com.lks.orm.entities.UserRole;

import java.util.List;

public interface UserDao {

	User findByUserName(String username);
	int createNewUser(String username, String password, int branchCode, String role);
	boolean editExisitingUser(User user);
	boolean deleteExistingUser(String username);
	List<User> retrieveAllUsers();
	List<UserRole> retrieveAllUsersInRole(String role);
	List<String> getAllRoles();
}