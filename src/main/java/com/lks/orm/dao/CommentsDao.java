package com.lks.orm.dao;

import com.lks.orm.entities.Comments;
import com.lks.orm.entities.Document;

import java.util.List;

public interface CommentsDao {

	int createComment(Comments comments);
    List<Comments> retrieveComments(int documentId);

}