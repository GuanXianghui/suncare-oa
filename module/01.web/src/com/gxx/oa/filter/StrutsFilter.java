package com.gxx.oa.filter;

import com.gxx.oa.interfaces.BaseInterface;
import com.gxx.oa.utils.BaseUtil;
import com.gxx.oa.utils.IPAddressUtil;
import com.gxx.oa.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * struts过滤器，绕开与ueditor的冲突
 *
 * @author Gxx
 * @module oa
 * @datetime 14-3-29 13:12
 */
public class StrutsFilter extends StrutsPrepareAndExecuteFilter implements BaseInterface {
    /**
     * 日志处理器
     */
    Logger logger = Logger.getLogger(StrutsFilter.class);

    /**
     * 过滤
     *
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        //不过滤的url
        String url = request.getRequestURI();
        if (-1 != url.indexOf("/ueditor/jsp/")) {
            chain.doFilter(req, res);
        } else {
            //.do结尾才要这些判断
            if(url.endsWith(".do")){
                String ip = IPAddressUtil.getIPAddress(request);
                String token = request.getParameter(TOKEN_KEY);
                logger.info("ip:[" + ip + "]，token:[" + token + "]");

                // 1.判token为空
                if (StringUtils.isBlank(token)) {
                    logger.error("token为空");
                    ((HttpServletResponse) res).sendRedirect(request.getContextPath() + "error.jsp");
                    return;
                }

                // 2.判token是否失效
                if (!TokenUtil.checkToken(request, token)) {
                    logger.error("token已失效");
                    ((HttpServletResponse) res).sendRedirect(request.getContextPath() + "error.jsp");
                    return;
                }

                // 3.判登录(登录为ajax请求*.jsp不过StrutsFilter)
                if (null == request.getSession().getAttribute(USER_KEY)) {
                    logger.error("用户未登录");
                    ((HttpServletResponse) res).sendRedirect(request.getContextPath() + "error.jsp");
                    return;
                }

                // 4.更新dao+session用户访问信息
                BaseUtil.refreshUserVisit(request);
            }

            super.doFilter(req, res, chain);
        }
    }
}
