package com.jerry.dao;

import com.jerry.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * CommentDao
 *
 * @author jerrychien
 * @create 2016-07-15 17:48
 */
@MapperScan
public interface CommentDao {

    @Select("select * from comment order by id desc limit 10")
    List<Comment> getAll();

    @Insert("insert into comment(title,content,create_time,update_time) values" +
            "(#{title}, #{content}, now(), now())")
    int save(Comment comment);
}
