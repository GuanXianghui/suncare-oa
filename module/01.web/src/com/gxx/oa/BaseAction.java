package com.gxx.oa;

import com.gxx.oa.utils.DateUtil;
import com.gxx.oa.utils.IPAddressUtil;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * ����Action
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 12:44
 */
public class BaseAction extends ActionSupport implements ServletRequestAware {
    /**
     * ��־������
     */
    Logger logger = Logger.getLogger(BaseAction.class);

    /**
     * ip��ַ
     */
    String ip;

    /**
     * ��ǰʱ��
     */
    String date;
    String time;
    String defaultDateTime;

    /**
     * token��
     */
    String token;

    /**
     * request��session��application
     */
    HttpServletRequest request;
    HttpSession session;
    ServletContext application;

    /**
     * ���캯��
     */
    public BaseAction() {
        this.ip = IPAddressUtil.getIPAddress(request);
        this.date = DateUtil.getNowDate();
        this.time = DateUtil.getNowTime();
        this.defaultDateTime = DateUtil.getDefaultDateTime(new Date());
        this.session = request.getSession();
        this.application = session.getServletContext();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}