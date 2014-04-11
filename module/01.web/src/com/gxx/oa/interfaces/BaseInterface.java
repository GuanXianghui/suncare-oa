package com.gxx.oa.interfaces;

/**
 * 基础接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface BaseInterface {
    /**
     * mysql数据库链接
     */
    public static final String MYSQL_CONNECTION = "mysql_connection";
    /**
     * md5 key
     */
    public static final String MD5_KEY = "md5_key";
    /**
     * session缓存中的token集合
     */
    public static final String SESSION_TOKEN_LIST = "session_token_list";
    /**
     * TOKEN键
     */
    public static final String TOKEN_KEY = "token";
    /**
     * 用户键
     */
    public static final String USER_KEY = "user";
    /**
     * 公告页面大小
     */
    public static final String NOTICE_PAGE_SIZE = "notice_page_size";
    /**
     * uuid配置
     * UE的代码功能，每行都是换行的，写到json里会有问题，所以把换行先转换成一个uuid串，展示之前再转回来
     * 注意：该标志要与客户端base.js中配置一致
     */
    public static final String GXX_OA_NEW_LINE_UUID = "gxx_oa_new_line_uuid";
    /**
     * 消息页面大小
     */
    public static final String MESSAGE_PAGE_SIZE = "message_page_size";
    /**
     * 站内信页面大小
     */
    public static final String LETTER_PAGE_SIZE = "letter_page_size";
    /**
     * 工作日志页面大小
     */
    public static final String DIARY_PAGE_SIZE = "diary_page_size";
    /**
     * 任务页面大小
     */
    public static final String TASK_PAGE_SIZE = "task_page_size";
    /**
     * 短信服务相关配置
     * 短信服务用户id
     */
    public static final String SMS_UID = "sms_uid";
    /**
     * 短信服务相关配置
     * 短信服务key
     */
    public static final String SMS_KEY = "sms_key";
    /**
     * 短信服务相关配置
     * 短信日数量限制
     */
    public static final String SMS_DAY_LIMIT = "sms_day_limit";
}
