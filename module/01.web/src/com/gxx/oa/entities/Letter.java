package com.gxx.oa.entities;

/**
 * 站内信实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-5 13:47
 */
public class Letter {
    int id;
    int userId;
    int userType;
    int sendOrReceive;
    int fromUserId;
    int fromUserType;
    String toUserIds;
    String toUserTypes;
    String ccUserIds;
    String ccUserTypes;
    int readState;
    int deleteState;
    String title;
    String content;
    String createDate;
    String createTime;
    String createIp;
    String operateDate;
    String operateTime;
    String operateIp;

    /**
     * 新增时使用
     * @param userId
     * @param userType
     * @param sendOrReceive
     * @param fromUserId
     * @param fromUserType
     * @param toUserIds
     * @param toUserTypes
     * @param ccUserIds
     * @param ccUserTypes
     * @param readState
     * @param deleteState
     * @param title
     * @param content
     * @param createDate
     * @param createTime
     * @param createIp
     * @param operateDate
     * @param operateTime
     * @param operateIp
     */
    public Letter(int userId, int userType, int sendOrReceive, int fromUserId, int fromUserType, String toUserIds,
                  String toUserTypes, String ccUserIds, String ccUserTypes, int readState, int deleteState,
                  String title, String content, String createDate, String createTime, String createIp,
                  String operateDate, String operateTime, String operateIp) {
        this.userId = userId;
        this.userType = userType;
        this.sendOrReceive = sendOrReceive;
        this.fromUserId = fromUserId;
        this.fromUserType = fromUserType;
        this.toUserIds = toUserIds;
        this.toUserTypes = toUserTypes;
        this.ccUserIds = ccUserIds;
        this.ccUserTypes = ccUserTypes;
        this.readState = readState;
        this.deleteState = deleteState;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createIp = createIp;
        this.operateDate = operateDate;
        this.operateTime = operateTime;
        this.operateIp = operateIp;
    }

    /**
     * 查询时使用
     * @param id
     * @param userId
     * @param userType
     * @param sendOrReceive
     * @param fromUserId
     * @param fromUserType
     * @param toUserIds
     * @param toUserTypes
     * @param ccUserIds
     * @param ccUserTypes
     * @param readState
     * @param deleteState
     * @param title
     * @param content
     * @param createDate
     * @param createTime
     * @param createIp
     * @param operateDate
     * @param operateTime
     * @param operateIp
     */
    public Letter(int id, int userId, int userType, int sendOrReceive, int fromUserId, int fromUserType,
                  String toUserIds, String toUserTypes, String ccUserIds, String ccUserTypes, int readState,
                  int deleteState, String title, String content, String createDate, String createTime,
                  String createIp, String operateDate, String operateTime, String operateIp) {
        this.id = id;
        this.userId = userId;
        this.userType = userType;
        this.sendOrReceive = sendOrReceive;
        this.fromUserId = fromUserId;
        this.fromUserType = fromUserType;
        this.toUserIds = toUserIds;
        this.toUserTypes = toUserTypes;
        this.ccUserIds = ccUserIds;
        this.ccUserTypes = ccUserTypes;
        this.readState = readState;
        this.deleteState = deleteState;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createIp = createIp;
        this.operateDate = operateDate;
        this.operateTime = operateTime;
        this.operateIp = operateIp;
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

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getSendOrReceive() {
        return sendOrReceive;
    }

    public void setSendOrReceive(int sendOrReceive) {
        this.sendOrReceive = sendOrReceive;
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

    public String getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(String toUserIds) {
        this.toUserIds = toUserIds;
    }

    public String getToUserTypes() {
        return toUserTypes;
    }

    public void setToUserTypes(String toUserTypes) {
        this.toUserTypes = toUserTypes;
    }

    public String getCcUserIds() {
        return ccUserIds;
    }

    public void setCcUserIds(String ccUserIds) {
        this.ccUserIds = ccUserIds;
    }

    public String getCcUserTypes() {
        return ccUserTypes;
    }

    public void setCcUserTypes(String ccUserTypes) {
        this.ccUserTypes = ccUserTypes;
    }

    public int getReadState() {
        return readState;
    }

    public void setReadState(int readState) {
        this.readState = readState;
    }

    public int getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(int deleteState) {
        this.deleteState = deleteState;
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

    public String getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(String operateDate) {
        this.operateDate = operateDate;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }
}
