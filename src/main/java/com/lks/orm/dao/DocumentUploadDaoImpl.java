package com.lks.orm.dao;

import com.lks.core.FALException;
import com.lks.core.enums.RecStatus;
import com.lks.orm.entities.Comments;
import com.lks.orm.entities.Document;
import com.lks.orm.entities.DocumentArchive;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

public class DocumentUploadDaoImpl implements DocumentUploadDao {

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
            throw new FALException("Unable to create new document with file name"+ fileName, e);
        }finally {
            session.close();
        }
        return documentId;
    }

    @Override
    public Document retrieveDocument(int documentId) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Document document = null;
        try{
            String hql = "from Document d where d.documentId= :documentId";
            document = (Document) session.createQuery(hql)
                    .setParameter("documentId", documentId).uniqueResult();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve document from table with document id: "+ documentId, e);
        }finally {
            session.close();
        }

        return document;
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


    @Override
    public void updateDocument(Document document) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            session.merge(document);
        }catch (HibernateException e) {
            throw new FALException("Unable to update document with id"+ document.getDocumentId(), e);
        }finally {
            session.flush();
            session.close();
        }

    }

    @Override
    public int archiveDocument(DocumentArchive documentArchive) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Integer documentArchiveId = null;
        try{
            documentArchiveId = (Integer) session.save(documentArchive);
        }catch (HibernateException e) {
            throw new FALException("Unable to archive document with file name"+ documentArchive.getFileName(), e);
        }finally {
            session.flush();
            session.close();
        }
        return documentArchiveId;
    }

    @Override
    public void deleteDocument(Document document) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            /*for(Comments comments: document.getComments()){
                deleteById(Comments.class, comments.getCommentId(), session);
            }*/
            deleteById(Document.class, document.getDocumentId(),session);
            /*for(Comments comments: document.getComments()){
                deleteById(Comments.class, comments.getCommentId(), session);
            }*/

        }catch (HibernateException e) {
            throw new FALException("Unable to delete document with file name"+ document.getFileName(), e);
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