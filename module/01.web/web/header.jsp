<%@ page import="com.gxx.oa.utils.BaseUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="headerWithOutCheckLogin.jsp" %>
<%
    //判登录
    BaseUtil.checkLogin(request, response);
%>