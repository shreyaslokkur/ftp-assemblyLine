package com.lks.orm.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lks.core.FALException;
import com.lks.core.UserUtils;
import com.lks.orm.entities.UserRole;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.lks.orm.entities.User;

public class UserDaoImpl implements UserDao {

	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = getSessionFactory().getCurrentSession().createQuery("from User where username=?")
				.setParameter(0, username).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}

	}


	@Override
	public String createNewUser(String username, String password, int branchCode, String role) {
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		String key = null;
		try{
			String hashedPassword = UserUtils.createHashedPassword(password);
			User user = new User(username, hashedPassword, branchCode, true);
			UserRole userRole = new UserRole(username, role);
			key = (String) session.save(user);
			userRole.setUser(user);
			session.save(userRole);
		}catch (HibernateException e) {
			throw new FALException("Unable to create new user with user name"+ username, e);
		}finally {
			session.close();
		}
		return key;
	}

	@Override
	public boolean editExisitingUser(User user) {
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			session.merge(user);
			return true;
		}catch (HibernateException e) {
			throw new FALException("Unable to update user with username"+ user.getUsername(), e);
		}finally {
			session.flush();
			session.close();
		}
	}

	@Override
	public boolean deleteExistingUser(String username) {
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		try{

			return deleteById(User.class, username,session);

		}catch (HibernateException e) {
			throw new FALException("Unable to delete user with username"+ username, e);
		}finally {
			session.flush();
			session.close();
		}

	}

	@Override
	public List<User> retrieveAllUsers() {
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		List<User> userList = new ArrayList<User>();
		try{
			userList = session.createQuery("from User u")
					.list();

		}catch (HibernateException e){
			throw new FALException("Unable to retrieve all branches", e);
		}finally {
			session.close();
		}

		return userList;
	}

	@Override
	public List<String> retrieveAllUsersInRole(String role){
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		List<String> userList = new ArrayList<String>();
		try{
			userList = session.createQuery("from User_Roles u where u.role=?")
					.setParameter("role", role)
					.list();

		}catch (HibernateException e){
			throw new FALException("Unable to retrieve all branches", e);
		}finally {
			session.close();
		}

		return userList;
	}

	@Override
	public List<String> getAllRoles() {
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.getCurrentSession();
		List<String> userRoleList = new ArrayList<String>();
		try{
			userRoleList = session.createQuery("Select u.role from User_Roles u group by u.role")
					.list();

		}catch (HibernateException e){
			throw new FALException("Unable to retrieve all branches", e);
		}finally {
			session.close();
		}

		return userRoleList;
	}

	private boolean deleteById(Class<?> type, Serializable id, Session session) {
		Object persistentInstance = session.load(type, id);
		if (persistentInstance != null) {
			session.delete(persistentInstance);
			return true;
		}
		return false;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}