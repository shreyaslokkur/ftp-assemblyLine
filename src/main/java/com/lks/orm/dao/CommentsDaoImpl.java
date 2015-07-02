package com.lks.orm.dao;

import com.lks.core.FALException;
import com.lks.orm.entities.Comments;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lokkur on 6/23/2015.
 */
public class CommentsDaoImpl implements CommentsDao {

    private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


    @Override
    public int createComment(Comments comments) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();
        Integer commentsId = null;
        try{
            commentsId = (Integer) session.save(comments);
        }catch (HibernateException e) {
            throw new FALException("Unable to create new comment from user"+ comments.getCommentedBy(), e);
        }finally {
            session.close();
        }
        return commentsId;
    }

    @Override
    public List<Comments> retrieveComments(int documentId) {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        List<Comments> commentsList = new ArrayList<Comments>();
        try{
            commentsList = session.createQuery("from Comments c where c.documentId=?")
                    .setParameter(0, documentId).list();

        }catch (HibernateException e){
            throw new FALException("Unable to retrieve comments from table with document id: "+ documentId, e);
        }finally {
            session.close();
        }

        return commentsList;
    }
}
