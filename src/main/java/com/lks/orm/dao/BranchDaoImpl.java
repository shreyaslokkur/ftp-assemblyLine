package com.lks.orm.dao;

import com.lks.core.FALException;
import com.lks.orm.entities.Branch;
import com.lks.orm.entities.Comments;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lokkur on 6/23/2015.
 */
public class BranchDaoImpl implements BranchDao {

    private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


    @Override
    public String createBranch(int branchCode, String branchName,String zone, String region) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Branch branch = new Branch(branchCode,branchName, zone, region, true);
        String key = null;
        try{
            key = (String) session.save(branch);
        }catch (HibernateException e) {
            throw new FALException("Unable to create new branch : "+ branch.getBranchName(), e);
        }finally {
            session.close();
        }
        return key;
    }

    @Override
    public List<Branch> retrieveAllBranches() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        List<Branch> branchList = new ArrayList<Branch>();
        try{
            branchList = session.createQuery("from Branch b")
                    .list();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve all branches", e);
        }finally {
            session.close();
        }

        return branchList;
    }

    @Override
    public Branch retrieveBranch(int branchCode) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Branch branch = null;
        try{
            branch = (Branch) session.createQuery("from Branch b where b.branchCode=?")
                    .setParameter(0, branchCode).uniqueResult();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve branch from table with branch code: "+ branchCode, e);
        }finally {
            session.close();
        }

        return branch;
    }

    @Override
    public boolean editBranch(Branch branch) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            session.merge(branch);
            return true;
        }catch (HibernateException e) {
            throw new FALException("Unable to update branch with branchName"+ branch.getBranchName(), e);
        }finally {
            session.flush();
            session.close();
        }
    }

    @Override
    public boolean deleteExistingBranch(int branchCode) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{

            return deleteById(Branch.class, branchCode,session);

        }catch (HibernateException e) {
            throw new FALException("Unable to delete branch with branchCode"+ branchCode, e);
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
}
