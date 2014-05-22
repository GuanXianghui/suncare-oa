package com.gxx.oa.entities;

/**
 * 申成文库实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-21 08:41
 */
public class CloudDoc {
    int id;
    int userId;
    String title;
    int state;
    String description;
    String type;
    String tags;
    String route;
    long size;
    int readTimes;
    int downloadTimes;
    String createDate;
    String createTime;
    String createIp;
    String updateDate;
    String updateTime;
    String updateIp;

    /**
     * 新增时使用
     * @param userId
     * @param title
     * @param state
     * @param description
     * @param type
     * @param tags
     * @param route
     * @param size
     * @param readTimes
     * @param downloadTimes
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public CloudDoc(int userId, String title, int state, String description, String type, String tags, String route,
                    long size, int readTimes, int downloadTimes, String createDate, String createTime, String createIp,
                    String updateDate, String updateTime, String updateIp) {
        this.userId = userId;
        this.title = title;
        this.state = state;
        this.description = description;
        this.type = type;
        this.tags = tags;
        this.route = route;
        this.size = size;
        this.readTimes = readTimes;
        this.downloadTimes = downloadTimes;
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
     * @param title
     * @param state
     * @param description
     * @param type
     * @param tags
     * @param route
     * @param size
     * @param readTimes
     * @param downloadTimes
     * @param createDate
     * @param createTime
     * @param createIp
     * @param updateDate
     * @param updateTime
     * @param updateIp
     */
    public CloudDoc(int id, int userId, String title, int state, String description, String type, String tags,
                    String route, long size, int readTimes, int downloadTimes, String createDate, String createTime,
                    String createIp, String updateDate, String updateTime, String updateIp) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.state = state;
        this.description = description;
        this.type = type;
        this.tags = tags;
        this.route = route;
        this.size = size;
        this.readTimes = readTimes;
        this.downloadTimes = downloadTimes;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(int readTimes) {
        this.readTimes = readTimes;
    }

    public int getDownloadTimes() {
        return downloadTimes;
    }

    public void setDownloadTimes(int downloadTimes) {
        this.downloadTimes = downloadTimes;
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
