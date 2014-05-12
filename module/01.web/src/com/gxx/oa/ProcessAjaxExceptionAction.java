package com.gxx.oa;

import com.gxx.oa.utils.TokenUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * ajax异常捕获
 *
 * @author Gxx
 * @module oa
 * @datetime 14-5-12 18:54
 */
public class ProcessAjaxExceptionAction extends BaseAction {
    /**
     * 处理异常
     * @return
     */
    public String execute() throws Exception {
        Exception e = (Exception) ActionContext.getContext().getValueStack().findValue("exception");
        logger.error("异常发生~", e);
        String resp = "{isSuccess:false,message:'" + e.getMessage() + "',hasNewToken:true,token:'" +
                TokenUtil.createToken(request) + "'}";
        write(resp);
        return null;
    }
}
