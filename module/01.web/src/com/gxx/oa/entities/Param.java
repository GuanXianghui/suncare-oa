package com.gxx.oa.entities;

/**
 * 启动参数实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-31 08:39
 */
public class Param {
    String name;
    String value;
    String info;

    /**
     * 查询时使用
     * @param name
     * @param value
     * @param info
     */
    public Param(String name, String value, String info) {
        this.name = name;
        this.value = value;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
