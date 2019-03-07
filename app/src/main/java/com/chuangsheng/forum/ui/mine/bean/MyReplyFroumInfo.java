package com.chuangsheng.forum.ui.mine.bean;

import com.chuangsheng.forum.ui.home.bean.HomeFroumInfo;

import java.util.List;

public class MyReplyFroumInfo {
    private HomeFroumInfo discussion;
    private String user_img;
    private String user_id;
    private String user_username;
    private String user_points;
    private String id;
    private String likes;
    private String content;
    private List<String> attachment;
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HomeFroumInfo getDiscussion() {
        return discussion;
    }

    public void setDiscussion(HomeFroumInfo discussion) {
        this.discussion = discussion;
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
