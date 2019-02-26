package com.chuangsheng.forum.api;

public class ApiConstant {
    public static final String BASE_URL="http://192.168.0.105:8000/";
    public static final int SUCCESS_CODE = 0;
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
}
