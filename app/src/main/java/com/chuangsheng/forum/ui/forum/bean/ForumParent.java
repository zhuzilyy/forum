package com.chuangsheng.forum.ui.forum.bean;

/**
 * Created by Administrator on 2019/3/21.
 */

public class ForumParent {
    private String  user_id;
    private String  user_img;
    private String  created;
    private String  content;
    private String  user_username;
    private String  user_points;
    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser_username() {
        return user_username;
    }

    public void setUser_username(String user_username) {
        this.user_username = user_username;
    }

    public String getUser_points() {
        return user_points;
    }

    public void setUser_points(String user_points) {
        this.user_points = user_points;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
