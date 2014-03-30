<%@ page import="com.gxx.oa.interfaces.BaseInterface"
        %><%@ page contentType="text/html;charset=UTF-8" language="java"
        %><%
    String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
    request.getSession().setAttribute(BaseInterface.USER_KEY, null);
    String resp = "{isSuccess:true,message:'退出成功！',isRedirect:true,redirectUrl:'" + baseUrl + "index.jsp'}";
    response.getWriter().write(resp);
%>