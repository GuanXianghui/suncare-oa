<%@ page import="org.apache.commons.lang.StringUtils"
        %><%@ page import="com.gxx.oa.dao.UserDao"
        %><%@ page import="com.gxx.oa.entities.User"
        %><%@ page import="com.gxx.oa.interfaces.BaseInterface"
        %><%@ page import="com.gxx.oa.utils.DateUtil"
        %><%@ page import="com.gxx.oa.utils.IPAddressUtil"
        %><%@ page import="com.gxx.oa.utils.TokenUtil"
        %><%@ page contentType="text/html;charset=UTF-8" language="java"
        %><%
    String resp;
    String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
    String name = StringUtils.trimToEmpty(request.getParameter("name"));
    String password = StringUtils.trimToEmpty(request.getParameter("password"));
    String token = StringUtils.trimToEmpty(request.getParameter("token"));
    System.out.println("name=" + name + ",password=" + password + ",token=" + token);
    if(!TokenUtil.checkToken(request, token)){
        resp = "{isSuccess:false,message:'您的提交失败，token已失效！',hasNewToken:true,token:'" +
                TokenUtil.createToken(request) + "'}";
    } else {
        if(!UserDao.isNameExist(name)){
            resp = "{isSuccess:false,message:'你输入的用户名不存在！',hasNewToken:true,token:'" +
                    TokenUtil.createToken(request) + "'}";
        } else {
            User user = UserDao.getUserByName(name);
            if(!user.getPassword().equals(password)){
                resp = "{isSuccess:false,message:'你输入的密码错误！',hasNewToken:true,token:'" +
                        TokenUtil.createToken(request) + "'}";
            } else {
                user.setVisitDate(DateUtil.getNowDate());
                user.setVisitTime(DateUtil.getNowTime());
                user.setVisitIp(IPAddressUtil.getIPAddress(request));
                UserDao.updateUserVisitInfo(user);
                request.getSession().setAttribute(BaseInterface.USER_KEY, user);
                resp = "{isSuccess:true,message:'登陆成功！',isRedirect:true,redirectUrl:'" + baseUrl + "userManage.jsp'}";
            }
        }
    }
    response.getWriter().write(resp);
%>