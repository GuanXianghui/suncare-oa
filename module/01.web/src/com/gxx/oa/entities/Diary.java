package com.gxx.oa.entities;

/**
 * 工作日志实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-7 17:20
 */
public class Diary {
    int id;
    int userId;
    String date;
    String content;
    String createDate;
    String createTime;
    String createIp;
    String updateDate;
    String updateTime;
    String updateIp;

    /**
     * 新增时使用
     * @param userId
     * @param date
     * @param content
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public Diary(int userId, String date, String content, String createDate, String createTime, String createIp,
                 String updateDate, String updateTime, String updateIp) {
        this.userId = userId;
        this.date = date;
        this.content = content;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createIp = createIp;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
        this.updateIp = updateIp;
    }

    /**
     * 查询时使用
     * @param id
     * @param userId
     * @param date
     * @param content
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public Diary(int id, int userId, String date, String content, String createDate, String createTime,
                 String createIp, String updateDate, String updateTime, String updateIp) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.content = content;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createIp = createIp;
        this.updateDate = updateDate;
        this.updateTime = updateTime;
        this.updateIp = updateIp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateIp() {
        return updateIp;
    }

    public void setUpdateIp(String updateIp) {
        this.updateIp = updateIp;
    }
}
