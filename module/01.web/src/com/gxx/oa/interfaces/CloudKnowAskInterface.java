package com.gxx.oa.interfaces;

/**
 * 申成知道提问接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-8 14:21
 */
public interface CloudKnowAskInterface extends BaseInterface {
    /**
     * 是否紧急 1 不紧急 2 紧急
     */
    public static final int URGENT_NO = 1;
    public static final int URGENT_YES = 2;

    /**
     * 状态 1 正常 2 删除
     */
    public static final int STATE_NORMAL = 1;
    public static final int STATE_DELETE = 2;
}
