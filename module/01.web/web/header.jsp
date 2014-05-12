<%@ page import="com.gxx.oa.utils.BaseUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="headerWithOutCheckLogin.jsp" %>
<%
    //判登录
    if(!BaseUtil.isLogin(request))
    {
        //域名链接
        response.sendRedirect(baseUrl + "index.jsp");
        return;
    }
    //外层
    String outLayer = StringUtils.EMPTY;
    //内层
    String inLayer = StringUtils.EMPTY;
%>