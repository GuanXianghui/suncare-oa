package com.gxx.oa.interfaces;

/**
 * 提醒接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-9 15:10
 */
public interface RemindInterface extends BaseInterface {
    /**
     * 提醒类型 1:不提醒 2:消息提醒 3:短信提醒 4:邮件提醒(目前只能1或者2)
     */
    public static final int REMIND_TYPE_NO_REMIND = 1;
    public static final int REMIND_TYPE_MESSAGE = 2;
    public static final int REMIND_TYPE_SMS = 3;
    public static final int REMIND_TYPE_MAIL = 4;
}
