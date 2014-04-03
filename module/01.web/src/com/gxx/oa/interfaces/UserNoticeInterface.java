package com.gxx.oa.interfaces;

/**
 * 用户公告接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-3 20:31
 */
public interface UserNoticeInterface extends BaseInterface {
    /**
     * 状态 1:已读 2:已删除
     */
    public static final int STATE_READED = 1;
    public static final int STATE_DELETED = 2;
}
