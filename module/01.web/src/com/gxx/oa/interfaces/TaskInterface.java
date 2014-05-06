package com.gxx.oa.interfaces;

/**
 * 任务接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 16:38
 */
public interface TaskInterface extends BaseInterface {
    /**
     * 状态 1:新建 2:进行中 3:完成 4:废除
     */
    public static final int STATE_NEW = 1;
    public static final int STATE_ING = 2;
    public static final int STATE_DONE = 3;
    public static final int STATE_DROP = 4;

    /**
     * 类型 1:分配给我的 2:我分配的
     */
    public static final int TYPE_TO_ME = 1;
    public static final int TYPE_FROM_ME = 2;
}
