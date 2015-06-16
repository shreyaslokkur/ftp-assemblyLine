package com.lks.orm.dao;

import com.lks.orm.entities.Document;
import com.lks.orm.entities.User;
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
    public int fileUploaded(String fileName, String fileLocation, String createdBy, String branchName, String placeOfMeeting, String bookletNo, String applicationNo, String numOfCustomers) {
        return 0;
    }

    @Override
    public Document retrieveDocument(int documentId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}