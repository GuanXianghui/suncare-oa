package com.gxx.oa.interfaces;

/**
 * 短信接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-11 10:20
 */
public interface SMSInterface extends BaseInterface {
    /**
     * 状态 1:成功 2:失败
     */
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_FAIL = 2;
}
