package com.lks.orm.dao;

import com.lks.core.enums.RecStatus;
import com.lks.orm.entities.Document;
import com.lks.orm.entities.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class DataUploadDaoImpl implements DataUploadDao {

	private SessionFactory sessionFactory;



	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

    @Override
    public int fileUploaded(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, int bookletNo, int applicationNo, int numOfCustomers) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Integer documentId = null;
        try{
            Document document = new Document(RecStatus.NR, fileName, fileLocation, createdBy, branchName, placeOfMeeting, bookletNo, applicationNo, numOfCustomers);
            documentId = (Integer) session.save(document);
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return documentId;
    }

    @Override
    public Document retrieveDocument(int documentId) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try{
            List document = session.createQuery("from Document d where d.documentId=?")
                    .setParameter(0, documentId).list();
            System.out.println(document);

        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean lockDocument(int documentId) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            session.createQuery("");
        }catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }
}