package com.jerry.manager;

import com.jerry.model.Comment;

import java.util.List;

/**
 * @author jerrychien
 * @create 2016-06-24 18:48
 */
public interface UserManager {

    void doPrint();

    List<Comment> getAll();
}
