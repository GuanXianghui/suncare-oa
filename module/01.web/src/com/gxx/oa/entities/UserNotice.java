package com.gxx.oa.entities;

/**
 * 用户公告实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-3 20:26
 */
public class UserNotice {
    int userId;
    int noticeId;
    int state;
    String date;
    String time;
    String ip;

    /**
     * 新增，查询时使用
     * @param userId
     * @param noticeId
     * @param state
     * @param date
     * @param time
     * @param ip
     */
    public UserNotice(int userId, int noticeId, int state, String date, String time, String ip) {
        this.userId = userId;
        this.noticeId = noticeId;
        this.state = state;
        this.date = date;
        this.time = time;
        this.ip = ip;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
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
