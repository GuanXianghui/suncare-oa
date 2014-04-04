package com.gxx.oa.entities;

/**
 * 消息实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-4 19:32
 */
public class Message {
    int id;
    int fromUserId;
    int fromUserType;
    int toUserId;
    String content;
    int state;
    String date;
    String time;
    String ip;

    /**
     * 新增时使用
     * @param fromUserId
     * @param fromUserType
     * @param toUserId
     * @param content
     * @param state
     * @param date
     * @param time
     * @param ip
     */
    public Message(int fromUserId, int fromUserType, int toUserId, String content, int state, String date,
                   String time, String ip) {
        this.fromUserId = fromUserId;
        this.fromUserType = fromUserType;
        this.toUserId = toUserId;
        this.content = content;
        this.state = state;
        this.date = date;
        this.time = time;
        this.ip = ip;
    }

    /**
     * 查询时使用
     * @param id
     * @param fromUserId
     * @param fromUserType
     * @param toUserId
     * @param content
     * @param state
     * @param date
     * @param time
     * @param ip
     */
    public Message(int id, int fromUserId, int fromUserType, int toUserId, String content, int state, String date,
                   String time, String ip) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.fromUserType = fromUserType;
        this.toUserId = toUserId;
        this.content = content;
        this.state = state;
        this.date = date;
        this.time = time;
        this.ip = ip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
