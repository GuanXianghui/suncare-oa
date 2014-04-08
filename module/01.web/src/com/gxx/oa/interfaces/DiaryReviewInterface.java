package com.gxx.oa.interfaces;

/**
 * 工作日志评论接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-8 14:21
 */
public interface DiaryReviewInterface extends BaseInterface {
    /**
     * 类型 1:点赞 2:留言 3:回复
     */
    public static final int TYPE_ZAN = 1;
    public static final int TYPE_MESSAGE = 2;
    public static final int TYPE_REPLY = 3;
}
