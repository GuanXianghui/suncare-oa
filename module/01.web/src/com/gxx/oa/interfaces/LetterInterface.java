package com.gxx.oa.interfaces;

/**
 * 站内信接口
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-5 18:10
 */
public interface LetterInterface extends BaseInterface {
    /**
     * 发送还是接受 1:发送 2:接受
     */
    public static final int SEND = 1;
    public static final int RECEIVE = 2;

    /**
     * 未读还是已读 1:未读 2:已读
     */
    public static final int READ_STATE_NOT_READED = 1;
    public static final int READ_STATE_READED = 2;

    /**
     * 未删除还是删除 1:未删除 2:删除
     */
    public static final int DELETE_STATE_NOT_DELETED = 1;
    public static final int DELETE_STATE_DELETED = 2;

    /**
     * 类型 1:已接受 2:已发送 3:已删除
     */
    public static final int TYPE_RECEIVED = 1;
    public static final int TYPE_SENT = 2;
    public static final int TYPE_DELETED = 3;

    /**
     * 站内信信箱 received:收件箱 sent:已发送 deleted:已删除
     */
    public static final String BOX_RECEIVED = "received";
    public static final String BOX_SENT = "sent";
    public static final String BOX_DELETED = "deleted";

    /**
     * 写信类型 write:写信 reply:回复 replyAll:回复全部 transmit:转发
     */
    public static final String WRITE_TYPE_WRITE = "write";
    public static final String WRITE_TYPE_REPLY = "reply";
    public static final String WRITE_TYPE_REPLY_ALL = "replyAll";
    public static final String WRITE_TYPE_TRANSMIT = "transmit";
}
