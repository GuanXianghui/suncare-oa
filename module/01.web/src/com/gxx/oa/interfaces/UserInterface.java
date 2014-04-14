package com.gxx.oa.interfaces;

/**
 * 用户基础接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface UserInterface extends BaseInterface {
    /**
     * 状态: 1正常，2锁定，3离职
     */
    public static final int STATE_NORMAL = 1;
    public static final int STATE_LOCKED = 2;
    public static final int STATE_QUIT = 3;
    /**
     * 性别: 1男，0女
     */
    public static final int SEX_X = 1;
    public static final int SEX_O = 0;
    /**
     * 用户类型 1:普通用户 2:公众账号
     */
    public static final int USER_TYPE_NORMAL = 1;
    public static final int USER_TYPE_PUBLIC = 2;
}
