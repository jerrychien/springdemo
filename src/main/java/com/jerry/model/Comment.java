package com.jerry.model;

import java.util.Date;

/**
 * comment
 *
 * @author jerrychien
 * @create 2016-07-15 14:08
 */
public class Comment extends BaseObject {

    private static final long serialVersionUID = 8142628080006751740L;

    private long id;

    private String title;

    private String content;

    private Date createTime;

    private Date updateTime;

    public Comment() {

    }

    public Comment(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
