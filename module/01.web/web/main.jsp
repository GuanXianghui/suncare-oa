<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>用户主页</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
</head>
<body>
    <div align="center"><h1>用户主页<button onclick="logOut()">退出</button></h1></div>
    <div>
        <table width="100%">
            <tr>
                <td>
                    <a href="<%=baseUrl%>userManage.jsp">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">用户管理</div>
                    </a>
                </td>
                <td>
                    <a href="<%=baseUrl%>user.jsp?id=<%=user.getId()%>">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">个人展示</div>
                    </a>
                </td>
                <td>
                    <a href="<%=baseUrl%>orgStructureManage.jsp">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">组织架构管理</div>
                    </a>
                </td>
                <td>
                    <a href="<%=baseUrl%>contacts.jsp">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">通讯录</div>
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="<%=baseUrl%>configNotice.jsp">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">公告管理</div>
                    </a>
                </td>
                <td>
                    <a href="<%=baseUrl%>notice.jsp">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">公告</div>
                    </a>
                </td>
                <td>
                    <a href="<%=baseUrl%>testMessage.jsp">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">测试消息</div>
                    </a>
                </td>
                <td>
                    <a href="<%=baseUrl%>message.jsp">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">消息</div>
                    </a>
                </td>
            </tr>
            <tr>
                <td>
                    <a href="<%=baseUrl%>letter.jsp">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">站内信</div>
                    </a>
                </td>
                <td>
                    <a href="<%=baseUrl%>diary.jsp">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">工作日志</div>
                    </a>
                </td>
                <td>
                    <a href="<%=baseUrl%>calendar.jsp">
                        <div align="center">
                            <div style="background-color: green; width: 50px; height: 50px;"></div>
                        </div>
                        <div align="center">日历</div>
                    </a>
                </td>
                <td>
                    <div align="center">
                        <div style="background-color: green; width: 50px; height: 50px;"></div>
                    </div>
                    <div align="center">xxx</div>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>
<%}%>