package com.gxx.oa.entities;

/**
 * 申成知道提问实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-22 10:17
 */
public class CloudKnowAsk {
    int id;
    int userId;
    String question;
    String description;
    String type;
    int urgent;
    int state;
    String createDate;
    String createTime;
    String createIp;
    String updateDate;
    String updateTime;
    String updateIp;

    /**
     * 新增时使用
     * @param userId
     * @param question
     * @param description
     * @param type
     * @param urgent
     * @param state
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public CloudKnowAsk(int userId, String question, String description, String type, int urgent, int state,
                        String createDate, String createTime, String createIp, String updateDate, String updateTime, String updateIp) {
        this.userId = userId;
        this.question = question;
        this.description = description;
        this.type = type;
        this.urgent = urgent;
        this.state = state;
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
     * @param question
     * @param description
     * @param type
     * @param urgent
     * @param state
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public CloudKnowAsk(int id, int userId, String question, String description, String type, int urgent, int state,
                        String createDate, String createTime, String createIp, String updateDate, String updateTime, String updateIp) {
        this.id = id;
        this.userId = userId;
        this.question = question;
        this.description = description;
        this.type = type;
        this.urgent = urgent;
        this.state = state;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
