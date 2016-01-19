package com.lks.orm.dao;

import com.lks.core.DateUtils;
import com.lks.core.DocumentUtils;
import com.lks.core.FALException;
import com.lks.core.enums.RecStatus;
import com.lks.orm.entities.Comments;
import com.lks.orm.entities.Document;
import com.lks.orm.entities.DocumentArchive;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocumentUploadDaoImpl implements DocumentUploadDao {

	private SessionFactory sessionFactory;

    @Resource(name = "documentUtils")
    DocumentUtils documentUtils;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


    @Override
    public int fileUploaded(String fileName, String fileLocation, String createdBy, int branchCode, String placeOfMeeting, String bookletNo, int applicationNo, int numOfCustomers) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Integer documentId = null;
        try{
            String currentDate = DateUtils.getCurrentDate();
            Document document = new Document(RecStatus.NR, fileName, fileLocation, createdBy, branchCode, placeOfMeeting, bookletNo, applicationNo, numOfCustomers, currentDate);
            documentId = (Integer) session.save(document);
        }catch (HibernateException e) {
            throw new FALException("Unable to create new document with file name"+ fileName, e);
        }finally {
            session.flush();
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

            deleteById(Document.class, document.getDocumentId(),session);

        }catch (HibernateException e) {
            throw new FALException("Unable to delete document with file name"+ document.getFileName(), e);
        }finally {
            session.flush();
            session.close();
        }

    }

    @Override
    public List<Document> getAllNewRecords(int offset) {
        List<Document> documentList;
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            String hql = "from Document d where d.state in ('NR') and d.assignedTo is null ORDER BY d.recCreatedOn DESC ";
            documentList = session.createQuery(hql).setFirstResult(offset).setMaxResults(documentUtils.getOffset()).list();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve new and locked records", e);
        }finally {
            session.close();
        }

        return documentList;
    }

    @Override
    public List<Document> getAllRecordsAssignedToUser(String userId) {

        //Need to check by that time if the user exists in the list at all
        List<Document> documentList;
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            String hql = "from Document d where d.assignedTo= :userId";
            documentList = session.createQuery(hql).
                    setParameter("userId", userId).list();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve records assigned to: "+ userId, e);
        }finally {
            session.close();
        }

        return documentList;
    }

    @Override
    public List<Document> getAllBranchRecordsWhichNeedRescan(int branchCode) {
        List<Document> documentList;
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            String hql = "from Document d where d.rescanNeeded = :rescanNeeded and d.branchCode = :branchCode";
            documentList = session.createQuery(hql).setParameter("rescanNeeded", true).
                    setParameter("branchCode", branchCode).list();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve records assigned to: "+ branchCode, e);
        }finally {
            session.close();
        }

        return documentList;
    }

    @Override
    public List<Document> getAllRecordsWhichNeedApproval(int offset) {
        List<Document> documentList;
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            String hql = "from Document d where d.state in ('NAR') and d.assignedTo is null";
            documentList = session.createQuery(hql).setFirstResult(offset).setMaxResults(documentUtils.getOffset()).list();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve records which need approval", e);
        }finally {
            session.close();
        }

        return documentList;
    }

    @Override
    public List<Document> getAllBranchRecordsWhichNeedRescan() {
        List<Document> documentList;
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            String hql = "from Document d where d.state in ('RN')";
            documentList = session.createQuery(hql).list();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve records which need rescan", e);
        }finally {
            session.close();
        }

        return documentList;


    }

    @Override
    public List<Document> getAllRecordsWhichAreInHold(int offset) {
        List<Document> documentList;
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            String hql = "from Document d where d.state in ('HR')";
            documentList = session.createQuery(hql).setFirstResult(offset).setMaxResults(documentUtils.getOffset()).list();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve records which are in hold", e);
        }finally {
            session.close();
        }

        return documentList;
    }

    @Override
    public List<Document> getAllRecords() {
        List<Document> documentList;
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            String hql = "from Document d ";
            documentList = session.createQuery(hql).list();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve records ", e);
        }finally {
            session.close();
        }

        return documentList;
    }

    @Override
    public String retrieveDocumentUrl(int documentId) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        String documentUrl = null;
        try{
            String hql = "Select d.fileLocation from Document d where d.documentId= :documentId";
            documentUrl = (String) session.createQuery(hql)
                    .setParameter("documentId", documentId).uniqueResult();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve documentUrl from table with document id: "+ documentId, e);
        }finally {
            session.close();
        }

        return documentUrl;

    }

    @Override
    public Long retrieveCountOfNewDocuments() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Long count = null;
        try{
            String hql = "Select count(*) from Document d where d.state in ('NR') and d.assignedTo is null";
            count = (Long) session.createQuery(hql)
                    .uniqueResult();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve count of new documents", e);
        }finally {
            session.close();
        }

        return count;
    }

    @Override
    public Long retrieveCountOfHoldDocuments() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Long count =null;
        try{
            String hql = "Select count(*) from Document d where d.state in ('HR')";
            count = (Long) session.createQuery(hql)
                    .uniqueResult();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve count of new documents", e);
        }finally {
            session.close();
        }

        return count;
    }

    @Override
    public Long retrieveCountOfApprovalDocuments() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Long count = null ;
        try{
            String hql = "Select count(*) from Document d where d.state in ('NAR') and d.assignedTo is null";
            count = (Long) session.createQuery(hql)
                    .uniqueResult();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve count of new documents", e);
        }finally {
            session.close();
        }

        return count;
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