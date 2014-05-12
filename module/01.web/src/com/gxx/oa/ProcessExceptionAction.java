package com.gxx.oa;

import com.gxx.oa.utils.TokenUtil;
import com.opensymphony.xwork2.ActionContext;

import javax.servlet.http.HttpServletResponse;

/**
 * 功能描述
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 18:54
 */
public class ProcessExceptionAction extends BaseAction {
    /**
     * 处理异常
     * @return
     */
    public String execute() throws Exception {
        Exception e = (Exception) ActionContext.getContext().getValueStack().findValue("exception");
        logger.error("异常发生~", e);
        message = e.getMessage();
        return SUCCESS;
    }
}
