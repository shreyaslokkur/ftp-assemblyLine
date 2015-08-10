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
	public String createNewUser(String username, String password, String branchName, String role) {
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		String key = null;
		try{
			String hashedPassword = UserUtils.createHashedPassword(password);
			User user = new User(username, hashedPassword, true);
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
	public void editExisitingUser(User user) {
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		try{
			session.merge(user);
		}catch (HibernateException e) {
			throw new FALException("Unable to update user with username"+ user.getUsername(), e);
		}finally {
			session.flush();
			session.close();
		}
	}

	@Override
	public void deleteExistingUser(String username) {
		SessionFactory sessionFactory = getSessionFactory();
		Session session = sessionFactory.openSession();
		try{

			deleteById(User.class, username,session);

		}catch (HibernateException e) {
			throw new FALException("Unable to delete user with username"+ username, e);
		}finally {
			session.flush();
			session.close();
		}

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