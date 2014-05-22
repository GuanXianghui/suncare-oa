package com.gxx.oa.entities;

/**
 * 申成知道回答实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-22 10:19
 */
public class CloudKnowAnswer {
    int id;
    int askId;
    int userId;
    String answer;
    int zan;
    int state;
    String createDate;
    String createTime;
    String createIp;
    String updateDate;
    String updateTime;
    String updateIp;

    /**
     * 新增时使用
     * @param askId
     * @param userId
     * @param answer
     * @param zan
     * @param state
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public CloudKnowAnswer(int askId, int userId, String answer, int zan, int state, String createDate,
                           String createTime, String createIp, String updateDate, String updateTime, String updateIp) {
        this.askId = askId;
        this.userId = userId;
        this.answer = answer;
        this.zan = zan;
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
     * @param askId
     * @param userId
     * @param answer
     * @param zan
     * @param state
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public CloudKnowAnswer(int id, int askId, int userId, String answer, int zan, int state, String createDate,
                           String createTime, String createIp, String updateDate, String updateTime, String updateIp) {
        this.id = id;
        this.askId = askId;
        this.userId = userId;
        this.answer = answer;
        this.zan = zan;
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

    public int getAskId() {
        return askId;
    }

    public void setAskId(int askId) {
        this.askId = askId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
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
