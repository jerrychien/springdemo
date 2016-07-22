package com.jerry.manager.impl;

import com.jerry.manager.UserManager;
import com.jerry.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * impl
 *
 * @author jerrychien
 * @create 2016-06-24 18:49
 */
@Repository
public class UserManagerImpl implements UserManager {

    private static final Logger logger = LoggerFactory.getLogger(UserManagerImpl.class);

    public void doPrint() {
        logger.info("----doPrint()----");
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }

}
