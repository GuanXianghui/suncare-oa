package com.gxx.oa.interfaces;

/**
 * �����ӿ�
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface BaseInterface {
    /**
     * mysql���ݿ�����
     */
    public static final String MYSQL_CONNECTION = "mysql_connection";
    /**
     * md5 key
     */
    public static final String MD5_KEY = "md5_key";
    /**
     * session�����е�token����
     */
    public static final String SESSION_TOKEN_LIST = "session_token_list";
    /**
     * TOKEN��
     */
    public static final String TOKEN_KEY = "token";
    /**
     * �û���
     */
    public static final String USER_KEY = "user";
    /**
     * ����ҳ���С
     */
    public static final String NOTICE_PAGE_SIZE = "notice_page_size";
    /**
     * uuid����
     * UE�Ĵ��빦�ܣ�ÿ�ж��ǻ��еģ�д��json��������⣬���԰ѻ�����ת����һ��uuid����չʾ֮ǰ��ת����
     * ע�⣺�ñ�־Ҫ��ͻ���base.js������һ��
     */
    public static final String GXX_OA_NEW_LINE_UUID = "gxx_oa_new_line_uuid";
}
