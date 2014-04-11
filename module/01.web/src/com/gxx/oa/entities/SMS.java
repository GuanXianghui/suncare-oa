package com.gxx.oa.entities;

import com.gxx.oa.interfaces.SMSInterface;
import org.apache.commons.lang.StringUtils;

/**
 * 短信实体
 *
 * @author Gxx
 * @module oa
 * @datetime 14-4-11 10:17
 */
public class SMS {
    int id;
    int userId;
    String phone;
    String content;
    int state;
    String date;
    String time;
    String ip;

    /**
     * 新增时使用
     * @param userId
     * @param phone
     * @param content
     * @param state
     * @param date
     * @param time
     * @param ip
     */
    public SMS(int userId, String phone, String content, int state, String date, String time, String ip) {
        this.userId = userId;
        this.phone = phone;
        this.content = content;
        this.state = state;
        this.date = date;
        this.time = time;
        this.ip = ip;
    }

    /**
     * 查询时使用
     * @param id
     * @param userId
     * @param phone
     * @param content
     * @param state
     * @param date
     * @param time
     * @param ip
     */
    public SMS(int id, int userId, String phone, String content, int state, String date, String time, String ip) {
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.content = content;
        this.state = state;
        this.date = date;
        this.time = time;
        this.ip = ip;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 返回状态中文描述
     * @return
     */
    public String getStateDesc(){
        String stateDesc = StringUtils.EMPTY;
        if(state == SMSInterface.STATE_SUCCESS){
            stateDesc = "成功";
        }
        if(state == SMSInterface.STATE_FAIL){
            stateDesc = "失败";
        }
        return stateDesc;
    }
}
