package com.gxx.oa;

import com.gxx.oa.dao.MessageDao;
import com.gxx.oa.entities.Message;
import com.gxx.oa.interfaces.MessageInterface;

/**
 * 测试消息action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-3 22:22
 */
public class TestMessageAction extends BaseAction {
    int fromUserId;
    int fromUserType;
    int toUserId;
    String content;


    /**
     * 入口
     * @return
     */
    public String execute() throws Exception {
        logger.info("fromUserId:" + fromUserId + ",fromUserType=" + fromUserType + ",toUserId=" +
                toUserId + ",content=" + content);
        Message message2 = new Message(fromUserId, fromUserType, toUserId, content,
                MessageInterface.STATE_NOT_READED, date, time, getIp());
        MessageDao.insertMessage(message2);
        message = "新增消息成功！";
        return SUCCESS;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getFromUserType() {
        return fromUserType;
    }

    public void setFromUserType(int fromUserType) {
        this.fromUserType = fromUserType;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
