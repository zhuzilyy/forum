package com.chuangsheng.forum.ui.forum.bean;

import java.util.List;
import java.util.PriorityQueue;

public class DetailForumInfo {
    private String content;
    private String created;
    private String user_img;
    private String user_username;
    private String user_points;
    private String id;
    private String likes;
    private String like_status;
    private List<String> attachment;
    private String adImg;
    private String user_id;
    private ForumParent parent;
    public ForumParent getParent() {
        return parent;
    }

    public void setParent(ForumParent parent) {
        this.parent = parent;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAdImg() {
        return adImg;
    }

    public void setAdImg(String adImg) {
        this.adImg = adImg;
    }

    public String getLike_status() {
        return like_status;
    }
    public void setLike_status(String like_status) {
        this.like_status = like_status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public List<String> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<String> attachment) {
        this.attachment = attachment;
    }
}
