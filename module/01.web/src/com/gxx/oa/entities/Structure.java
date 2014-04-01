package com.gxx.oa.entities;

/**
 * 组织结构实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 14:25
 */
public class Structure {
    int id;
    int type;
    String name;
    int pid;
    int indexId;

    /**
     * 查询时使用
     * @param id
     * @param type
     * @param name
     * @param pid
     * @param indexId
     */
    public Structure(int id, int type, String name, int pid, int indexId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.pid = pid;
        this.indexId = indexId;
    }

    /**
     * 新增时使用
     * @param type
     * @param name
     * @param pid
     * @param indexId
     */
    public Structure(int type, String name, int pid, int indexId) {
        this.type = type;
        this.name = name;
        this.pid = pid;
        this.indexId = indexId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getIndexId() {
        return indexId;
    }

    public void setIndexId(int indexId) {
        this.indexId = indexId;
    }
}
