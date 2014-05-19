package com.gxx.oa.interfaces;

/**
 * 申成云接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-8 14:21
 */
public interface CloudInterface extends BaseInterface {
    /**
     * 首目录为左斜杠/
     */
    public static final String FRONT_DIR = SymbolInterface.SYMBOL_SLASH;
    /**
     * 首目录PID定位0
     */
    public static final int FRONT_DIR_PID = 0;

    /**
     * 状态 1 正常 2 删除 3 彻底删除
     */
    public static final int STATE_NORMAL = 1;
    public static final int STATE_DELETE = 2;
    public static final int STATE_CTRL_DELETE = 3;

    /**
     * 类型 1 文件 2 文件夹 3 系统文件
     */
    public static final int TYPE_FILE = 1;
    public static final int TYPE_DIR = 2;
    public static final int TYPE_SYSTEM_FILE = 3;
}
