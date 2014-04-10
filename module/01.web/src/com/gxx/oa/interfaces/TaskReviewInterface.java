package com.gxx.oa.interfaces;

/**
 * 任务评论接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 15:46
 */
public interface TaskReviewInterface extends BaseInterface {
    /**
     * 类型 1:催 2:留言 3:回复
     */
    public static final int TYPE_CUI = 1;
    public static final int TYPE_MESSAGE = 2;
    public static final int TYPE_REPLY = 3;
}
