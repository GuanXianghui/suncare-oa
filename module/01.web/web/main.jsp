<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
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
                    <div align="center">
                        <div style="background-color: green; width: 50px; height: 50px;"></div>
                    </div>
                    <div align="center">用户管理</div>
                </td>
                <td>
                    <div align="center">
                        <div style="background-color: green; width: 50px; height: 50px;"></div>
                    </div>
                    <div align="center">用户管理</div>
                </td>
                <td>
                    <div align="center">
                        <div style="background-color: green; width: 50px; height: 50px;"></div>
                    </div>
                    <div align="center">用户管理</div>
                </td>
                <td>
                    <div align="center">
                        <div style="background-color: green; width: 50px; height: 50px;"></div>
                    </div>
                    <div align="center">用户管理</div>
                </td>
            </tr>
        </table>
    </div>
</body>
</html>