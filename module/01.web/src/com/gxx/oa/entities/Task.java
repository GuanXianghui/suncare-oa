package com.gxx.oa.entities;

import com.gxx.oa.interfaces.TaskInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 任务实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-10 13:17
 */
public class Task {
    int id;
    int fromUserId;
    int toUserId;
    String title;
    String content;
    int state;
    String beginDate;
    String endDate;
    String createDate;
    String createTime;
    String createIp;
    String updateDate;
    String updateTime;
    String updateIp;

    /**
     * 新增时使用
     * @param fromUserId
     * @param toUserId
     * @param title
     * @param content
     * @param state
     * @param beginDate
     * @param endDate
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public Task(int fromUserId, int toUserId, String title, String content, int state, String beginDate,
                String endDate, String createDate, String createTime, String createIp, String updateDate,
                String updateTime, String updateIp) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.title = title;
        this.content = content;
        this.state = state;
        this.beginDate = beginDate;
        this.endDate = endDate;
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
     * @param fromUserId
     * @param toUserId
     * @param title
     * @param content
     * @param state
     * @param beginDate
     * @param endDate
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public Task(int id, int fromUserId, int toUserId, String title, String content, int state, String beginDate,
                String endDate, String createDate, String createTime, String createIp, String updateDate,
                String updateTime, String updateIp) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.title = title;
        this.content = content;
        this.state = state;
        this.beginDate = beginDate;
        this.endDate = endDate;
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

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
     * 返回状态中文描述
     * @return
     */
    public String getStateDesc(){
        String stateDesc = StringUtils.EMPTY;
        if(state == TaskInterface.STATE_NEW){
            stateDesc = "新建";
        } else if(state == TaskInterface.STATE_ING){
            stateDesc = "进行中";
        } else if(state == TaskInterface.STATE_DONE){
            stateDesc = "完成";
        } else if(state == TaskInterface.STATE_DROP){
            stateDesc = "废除";
        }
        return stateDesc;
    }
}
