package com.chuangsheng.forum.api;

public class ApiConstant {
    public static final String BASE_URL="http://192.168.0.105:8000/";
    public static final int SUCCESS_CODE = 0;
    public static String SEND_CODE = BASE_URL + "send_code";
    public static String LOAN_LIST = BASE_URL + "get_loan_list";
    //登录
    public static String LOGIN = BASE_URL + "phone_login";
    //绑定email
    public static String BIND_EMAIL = BASE_URL + "bind_email";
    //设置昵称
    public static String SET_NICKNAME = BASE_URL + "change_user_attribute";
}
