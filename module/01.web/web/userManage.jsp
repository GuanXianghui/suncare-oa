<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>用户管理</title>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/md5.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/userManage.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/base.js"></script>
    <style type="text/css">
        .leftTd{
            width: 100px;
        }
    </style>
</head>
<body>
<div align="center">
    <h1>用户管理<button onclick="logOut()">退出</button></h1>
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
                                        <%=user.getName()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        拼音缩写：
                                    </td>
                                    <td id="letter">
                                        <%=user.getLetter()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        密码：
                                    </td>
                                    <td id="before_update_password_td">
                                        <button onclick="beforeUpdatePassword();">修改密码</button>
                                    </td>
                                    <td id="update_password_td" style="display: none;">
                                        <input type="password" id="password">
                                        <button onclick="updatePassword();">修改</button>
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
                                        <img src="<%=baseUrl%>images/temp1.jpg"><b>工作信息</b>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        公司：
                                    </td>
                                    <td>
                                        <%=user.getCompany()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        部门：
                                    </td>
                                    <td>
                                        <%=user.getDept()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        职位：
                                    </td>
                                    <td>
                                        <%=user.getPosition()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        工位：
                                    </td>
                                    <td>
                                        <%=user.getDesk()%>
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
                                    <td>
                                        <%=user.getHeadPhoto()%>
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
                                    <td>
                                        <%=user.getSex()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        生日：
                                    </td>
                                    <td>
                                        <%=user.getBirthday()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        办公电话：
                                    </td>
                                    <td>
                                        <%=user.getOfficeTel()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        移动电话：
                                    </td>
                                    <td>
                                        <%=user.getMobileTel()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        邮件：
                                    </td>
                                    <td>
                                        <%=user.getEmail()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        qq：
                                    </td>
                                    <td>
                                        <%=user.getQq()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        msn：
                                    </td>
                                    <td>
                                        <%=user.getMsn()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        地址：
                                    </td>
                                    <td>
                                        <%=user.getAddress()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        个人网站：
                                    </td>
                                    <td>
                                        <%=user.getWebsite()%>
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