package com.gxx.oa.interfaces;

/**
 * 基础接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:16
 */
public interface UserInterface extends BaseInterface {
    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "default_password";
    /**
     * 默认头像
     */
    public static final String DEFAULT_HEAD_PHOTO = "default_head_photo";
    /**
     * 状态: 1正常，2锁定
     */
    public static final int STATE_NORMAL = 1;
    public static final int STATE_LOCKED = 2;
    /**
     * 性别: 1男，0女
     */
    public static final int SEX_X = 1;
    public static final int SEX_O = 0;
}
