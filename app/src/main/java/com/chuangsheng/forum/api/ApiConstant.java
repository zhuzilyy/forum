package com.chuangsheng.forum.api;

public class ApiConstant {
    public static final String BASE_URL="http://192.168.0.102:8000/";
    public static final int SUCCESS_CODE = 0;
    public static final int LIMIT_CODE = 1;
    public static final int PAGE_SIZE = 10;
    public static String SEND_CODE = BASE_URL + "send_code";
    //登录
    public static String LOGIN = BASE_URL + "phone_login";
    //绑定email
    public static String BIND_EMAIL = BASE_URL + "bind_email";
    //设置昵称
    public static String SET_NICKNAME = BASE_URL + "change_user_attribute";
    //获取轮播图片
    public static String BANNER = BASE_URL + "get_img_list";
    //获取首页帖子列表
    public static String HOME_FROUM_LIST = BASE_URL + "get_discussion_list";
    //获取社区
    public static String COMMUNITY_LIST = BASE_URL + "get_community_list";
    //获取贷款列表
    public static String LOAN_LIST = BASE_URL + "get_loan_list";
    //获取我的帖子列表
    public static String MY_FROUM_LIST = BASE_URL + "get_user_discussion";
    //获取我的回帖列表
    public static String MY_REPLY_FROUM_LIST = BASE_URL + "get_user_comment";
    //请求个人信息
    public static String GET_USER_INFO = BASE_URL + "get_user_info";
    //修改个人信息
    public static String CHANGE_USER_INFO = BASE_URL + "change_user_info";
    //发布帖子
    public static String POST_FORUM = BASE_URL + "publish_discussion";
    //帖子详情
    public static String FORUM_DETAIL = BASE_URL + "get_discussion_detail_and_comment";
    //发布评论
    public static String PUBLISH_COMMENT = BASE_URL + "publish_comment";
    //评论点赞
    public static String LIKE_COMMENT = BASE_URL + "user_likes";
}
