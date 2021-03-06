package com.lks.security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lks.core.FALException;
import com.lks.core.UserUtils;
import com.lks.core.enums.ExceptionCode;
import com.lks.core.enums.UserRoles;
import com.lks.core.model.RoleDO;
import com.lks.core.model.UserModelDO;
import com.lks.orm.dao.UserDaoFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lks.orm.dao.UserDao;
import com.lks.orm.entities.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("userDetailService")
public class AuthenticationService implements org.springframework.security.core.userdetails.UserDetailsService, IUserService {


	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		// Programmatic transaction management
		/*
		return transactionTemplate.execute(new TransactionCallback<UserDetails>() {

			public UserDetails doInTransaction(TransactionStatus status) {
				com.lks.orm.entities.User user = userDao.findByUserName(username);
				List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

				return buildUserForAuthentication(user, authorities);
			}

		});*/

		userDao = UserDaoFactory.getUserDao();
		com.lks.orm.entities.User user = userDao.findByUserName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());

		return buildUserForAuthentication(user, authorities);
		

	}

	// Converts com.lks.orm.entities.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(com.lks.orm.entities.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public int createNewUser(UserModelDO userModelDO) {
		userDao = UserDaoFactory.getUserDao();
		//check if the user exists already
		if(userDao.findByUserName(userModelDO.getUsername()) == null)
			return userDao.createNewUser(userModelDO.getUsername(), userModelDO.getPassword(), userModelDO.getBranchCode(), userModelDO.getUserRole());
		else
			throw new FALException(ExceptionCode.USER_EXISTS,"User already Exists");
	}

	@Override
	public boolean resetPassword(UserModelDO userModelDO) {
		userDao = UserDaoFactory.getUserDao();
		com.lks.orm.entities.User user = userDao.findByUserName(userModelDO.getUsername());
		user.setPassword(UserUtils.createHashedPassword(userModelDO.getPassword()));
		return userDao.editExisitingUser(user);

	}

	@Override
	public boolean deleteUser(UserModelDO userModelDO) {
		userDao = UserDaoFactory.getUserDao();
		if(userDao.findByUserName(userModelDO.getUsername()) != null){
			return userDao.deleteExistingUser(userModelDO.getUsername());
		}else
			throw new FALException("User does not exist: "+ userModelDO.getUsername());

	}

	@Override
	public UserModelDO findUser(String username) {
		userDao = UserDaoFactory.getUserDao();
		com.lks.orm.entities.User user = userDao.findByUserName(username);
		UserModelDO userModelDO = null;
		if(user != null){
			userModelDO = new UserModelDO();
			userModelDO.setUsername(user.getUsername());
			userModelDO.setBranchCode(user.getBranchCode());
			Set<UserRole> userRoleSet = user.getUserRole();
			for(UserRole userRole : userRoleSet){
				userModelDO.setUserRole(userRole.getRole());
				break;
			}
		}

		return userModelDO;
	}

	@Override
	public List<UserModelDO> findUsersByRole(String role) {
		userDao = UserDaoFactory.getUserDao();
		List<UserRole> userRoles = userDao.retrieveAllUsersInRole(role);
		List<UserModelDO> userModelDOList = new ArrayList<>();
		UserModelDO userModelDO = null;
		for(UserRole user : userRoles){
			userModelDO = new UserModelDO();
			userModelDO.setUsername(user.getUser().getUsername());
			userModelDO.setUserRole(user.getRole());

			userModelDOList.add(userModelDO);
		}
		return userModelDOList;
	}

	@Override
	public List<UserModelDO> findAllUsers() {
		userDao = UserDaoFactory.getUserDao();
		List<com.lks.orm.entities.User> users = userDao.retrieveAllUsers();
		List<UserModelDO> userModelDOList = new ArrayList<>();
		UserModelDO userModelDO = null;
		for(com.lks.orm.entities.User user : users){
			userModelDO = new UserModelDO();
			userModelDO.setUsername(user.getUsername());
			userModelDO.setBranchCode(user.getBranchCode());
			Set<UserRole> userRoleSet = user.getUserRole();
			for(UserRole userRole : userRoleSet){
				userModelDO.setUserRole(userRole.getRole());
				break;
			}
			userModelDOList.add(userModelDO);
		}
		return userModelDOList;
	}

	@Override
	public List<RoleDO> getAllRoles() {
		UserRoles[] values = UserRoles.values();
		List<RoleDO> roleDOList = new ArrayList<>();
		RoleDO roleDO = null;
		for(UserRoles role : values){
			roleDO = new RoleDO();
			roleDO.setRoleName(role.name());
			roleDO.setRoleDescription(role.getRoleDescription());
			roleDOList.add(roleDO);
		}
		return roleDOList;
	}
}