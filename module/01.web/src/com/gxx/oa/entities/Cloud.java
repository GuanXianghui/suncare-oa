package com.gxx.oa.entities;

/**
 * 申成网盘实体 包括文件，文件夹，系统文件
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-19 17:07
 */
public class Cloud {
    int id;
    int userId;
    int type;
    int pid;
    String name;
    int state;
    String dir;
    String route;
    long size;
    String createDate;
    String createTime;
    String createIp;

    /**
     * 新增时使用
     * @param userId
     * @param type
     * @param pid
     * @param name
     * @param state
     * @param dir
     * @param route
     * @param size
     * @param createDate
     * @param createTime
     * @param createIp
     */
    public Cloud(int userId, int type, int pid, String name, int state, String dir, String route, long size,
                 String createDate, String createTime, String createIp) {
        this.userId = userId;
        this.type = type;
        this.pid = pid;
        this.name = name;
        this.state = state;
        this.dir = dir;
        this.route = route;
        this.size = size;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createIp = createIp;
    }

    /**
     * 查询时使用
     * @param id
     * @param userId
     * @param type
     * @param pid
     * @param name
     * @param state
     * @param dir
     * @param route
     * @param size
     * @param createDate
     * @param createTime
     * @param createIp
     */
    public Cloud(int id, int userId, int type, int pid, String name, int state, String dir, String route, long size,
                 String createDate, String createTime, String createIp) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.pid = pid;
        this.name = name;
        this.state = state;
        this.dir = dir;
        this.route = route;
        this.size = size;
        this.createDate = createDate;
        this.createTime = createTime;
        this.createIp = createIp;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
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
}
