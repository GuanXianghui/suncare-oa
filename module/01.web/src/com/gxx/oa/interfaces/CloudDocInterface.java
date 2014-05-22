package com.gxx.oa.interfaces;

/**
 * 申成文库接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-8 14:21
 */
public interface CloudDocInterface extends BaseInterface {
    /**
     * 状态 1 正常 2 删除
     */
    public static final int STATE_NORMAL = 1;
    public static final int STATE_DELETE = 2;
}
