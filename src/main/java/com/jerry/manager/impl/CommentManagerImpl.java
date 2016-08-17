package com.jerry.manager.impl;

import com.jerry.dao.CommentDao;
import com.jerry.manager.CommentManager;
import com.jerry.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * CommentManagerImpl
 *
 * @author jerrychien
 * @create 2016-08-17 23:37
 */
@Component
public class CommentManagerImpl implements CommentManager {

    @Autowired
    private CommentDao commentDao;

    public int save(Comment comment) {
        return commentDao.save(comment);
    }
}
