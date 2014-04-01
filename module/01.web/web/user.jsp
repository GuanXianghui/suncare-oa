<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page import="com.gxx.oa.entities.Structure" %>
<%@ page import="com.gxx.oa.utils.DateUtil" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
    <%
        int id;
        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            response.sendRedirect(baseUrl + "main.jsp");
            return;
        }
        User user2 = UserDao.getUserById(id);
        if(null == user2) {
            response.sendRedirect(baseUrl + "main.jsp");
            return;
        }
        Structure company = StructureDao.getStructureById(user2.getCompany());
        Structure dept = StructureDao.getStructureById(user2.getDept());
        Structure position = StructureDao.getStructureById(user2.getPosition());
    %>
<html>
<head>
    <title>用户管理</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>css/userManage.css"/>
</head>
<body>
<div align="center">
    <h1><button onclick="jump2Main()">主页</button>个人展示<button onclick="logOut()">退出</button></h1>
</div>
<div align="center" style="border: 1px solid green;">
    <table width="80%" style="border: 1px solid green;">
        <tr>
            <td width="50%" style="border: 1px solid green;">
                <table>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td colspan="2">
                                        <img src="<%=baseUrl%>images/temp1.jpg"><b>个人信息</b>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        姓名：
                                    </td>
                                    <td>
                                        <%=user2.getName()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        拼音缩写：
                                    </td>
                                    <td id="letter">
                                        <%=user2.getLetter()%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td colspan="3">
                                        <img src="<%=baseUrl%>images/temp1.jpg"><b>工作信息</b>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        公司：
                                    </td>
                                    <td id="company_td">
                                        <%=null==company?"无":company.getName()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        部门：
                                    </td>
                                    <td id="dept_td">
                                        <%=null==dept?"无":dept.getName()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        职位：
                                    </td>
                                    <td id="position_td">
                                        <%=null==position?"无":position.getName()%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
            <td width="50%" style="border: 1px solid green;">
                <table>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td colspan="2">
                                        <img src="<%=baseUrl%>images/temp1.jpg"><b>头像信息</b>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        头像：
                                    </td>
                                    <td id="before_upload_head_photo_td">
                                        <img src="<%=baseUrl + user2.getHeadPhoto()%>" width="54px"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td colspan="2">
                                        <img src="<%=baseUrl%>images/temp1.jpg"><b>用户信息</b>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        性别：
                                    </td>
                                    <td id="sex_td_1">
                                        <%=BaseUtil.translateUserSex(user2.getSex())%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        生日：
                                    </td>
                                    <td id="birthday_td_1">
                                        <%=DateUtil.getCNDate(DateUtil.getDate(user2.getBirthday()))%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        办公电话：
                                    </td>
                                    <td id="office_tel_td_1">
                                        <%=user2.getOfficeTel()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        移动电话：
                                    </td>
                                    <td id="mobile_tel_td_1">
                                        <%=user2.getMobileTel()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        工位：
                                    </td>
                                    <td id="desk_td_1">
                                        <%=user2.getDesk()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        邮件：
                                    </td>
                                    <td id="email_td_1">
                                        <%=user2.getEmail()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        qq：
                                    </td>
                                    <td id="qq_td_1">
                                        <%=user2.getQq()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        msn：
                                    </td>
                                    <td id="msn_td_1">
                                        <%=user2.getMsn()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        地址：
                                    </td>
                                    <td id="address_td_1">
                                        <%=user2.getAddress()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        个人网站：
                                    </td>
                                    <td id="website_td_1">
                                        <a href="<%=user2.getWebsite()%>" target="_blank"><%=user2.getWebsite()%></a>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
<%}%>