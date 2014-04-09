package com.gxx.oa.entities;

import com.gxx.oa.interfaces.RemindInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 提醒实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-9 15:07
 */
public class Remind {
    int id;
    int userId;
    String date;
    String content;
    int remindType;
    String remindDateTime;
    String remindTarget;
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
     * @param remindType
     * @param remindDateTime
     * @param remindTarget
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public Remind(int userId, String date, String content, int remindType, String remindDateTime, String remindTarget,
                  String createDate, String createTime, String createIp, String updateDate, String updateTime,
                  String updateIp) {
        this.userId = userId;
        this.date = date;
        this.content = content;
        this.remindType = remindType;
        this.remindDateTime = remindDateTime;
        this.remindTarget = remindTarget;
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
     * @param remindType
     * @param remindDateTime
     * @param remindTarget
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public Remind(int id, int userId, String date, String content, int remindType, String remindDateTime,
                  String remindTarget, String createDate, String createTime, String createIp, String updateDate,
                  String updateTime, String updateIp) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.content = content;
        this.remindType = remindType;
        this.remindDateTime = remindDateTime;
        this.remindTarget = remindTarget;
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

    public int getRemindType() {
        return remindType;
    }

    public void setRemindType(int remindType) {
        this.remindType = remindType;
    }

    public String getRemindDateTime() {
        return remindDateTime;
    }

    public void setRemindDateTime(String remindDateTime) {
        this.remindDateTime = remindDateTime;
    }

    public String getRemindTarget() {
        return remindTarget;
    }

    public void setRemindTarget(String remindTarget) {
        this.remindTarget = remindTarget;
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

    /**
     * 得到提醒类型描述
     * @return
     */
    public String getRemindTypeDesc(){
        String cnStr = StringUtils.EMPTY;
        if(RemindInterface.REMIND_TYPE_NO_REMIND == remindType){
            cnStr =  "不提醒";
        } else if(RemindInterface.REMIND_TYPE_MESSAGE == remindType){
            cnStr =  "信息提醒";
        }
        return cnStr;
    }
}
