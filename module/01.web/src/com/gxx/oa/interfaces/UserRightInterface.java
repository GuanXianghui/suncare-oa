package com.gxx.oa.interfaces;

/**
 * 用户基础接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface UserRightInterface extends BaseInterface {
    /**
     * 用户管理	0001
     * 个人展示	0002
     * 后台用户管理	0003
     * 通讯录	0004
     * 组织架构管理	0005
     * 公告	0006
     * 公告管理	0007
     * 消息	0008
     * 站内信	0009
     * 工作日志	0010
     * 日历	0011
     * 任务	0012
     * 短信	0013
     * 默认权限	0014
     * 权限管理	0015
     */
    public static final String RIGHT_0001_USER_MANAGE = "0001";
    public static final String RIGHT_0002_USER = "0002";
    public static final String RIGHT_0003_USER_OPERATE = "0003";
    public static final String RIGHT_0004_CONTACTS = "0004";
    public static final String RIGHT_0005_ORG_STRUCTURE_MANAGE = "0005";
    public static final String RIGHT_0006_NOTICE = "0006";
    public static final String RIGHT_0007_CONFIG_NOTICE = "0007";
    public static final String RIGHT_0008_MESSAGE = "0008";
    public static final String RIGHT_0009_LETTER = "0009";
    public static final String RIGHT_0010_DIARY = "0010";
    public static final String RIGHT_0011_CALENDAR = "0011";
    public static final String RIGHT_0012_TASK = "0012";
    public static final String RIGHT_0013_SMS = "0013";
    public static final String RIGHT_0014_DEFAULT_RIGHT = "0014";
    public static final String RIGHT_0015_USER_RIGHT = "0015";
}
