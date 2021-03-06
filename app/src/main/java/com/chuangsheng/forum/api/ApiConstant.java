package com.chuangsheng.forum.api;

public class ApiConstant {
    //public static final String BASE_URL="http://192.168.0.111:8000/";
    public static final String BASE_URL="http://47.92.200.176:8000/";
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
    //帖子收藏
    public static String COLLECTION_COMMENT = BASE_URL + "user_collection";
    //帖子收藏
    public static String DELETE_COLLECTION = BASE_URL + "del_discussion_collection";
    //获取我的收藏
    public static String MY_COLLECTION= BASE_URL + "get_user_discussion_collection";
    //获取浏览历史
    public static String MY_HISTORY= BASE_URL + "get_user_discussion_history";
    //意见反馈
    public static String FEEDBACK= BASE_URL + "publish_feedback";
    //获取热门搜索
    public static String HOT_SEARCH= BASE_URL + "get_search_list";
    //删除浏览历史
    public static String DELETE_HISTORY= BASE_URL + "del_discussion_history";
    //绑定新的手机号
    public static String BIND_NEW_PHONE= BASE_URL + "bind_phone_number";
    //验证邮箱
    public static String VERTIFY_EMAIL= BASE_URL + "vertify_email";
    //搜索帖子
    public static String SEARCH_FORUM= BASE_URL + "search_discussion";
    //我的普通消息
    public static String COMMON_NEWS= BASE_URL + "get_user_notification_list";
    //系统消息
    public static String SYSTEM_NEWS= BASE_URL + "get_system_notification_list";
    //获取系统变量
    public static String GET_SYSTEM_ATTRIBUTE= BASE_URL + "get_system_variables";
    //联系我们
    public static String CONTACT_US= BASE_URL + "get_contact_us_variable";
    //批量删除历史
    public static String CANCLE_HISTORY= BASE_URL + "del_discussion_history";
    //批量删除我的帖子
    public static String DELETE_MY_FORUMS= BASE_URL + "del_discussions";
    //申卡
    public static String APPLY_CARD= BASE_URL + "get_card_list";
}
