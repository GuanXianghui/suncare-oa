package com.gxx.oa.interfaces;

/**
 * 消息接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-4 19:41
 */
public interface MessageInterface extends BaseInterface {
    /**
     * 状态 1:未读 2:已读 删就直接删掉记录了
     */
    public static final int STATE_NOT_READED = 1;
    public static final int STATE_READED = 2;
}
