<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="headerWithOutCheckLogin.jsp" %>
<html>
<head>
    <title>登陆页面</title>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/md5.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/index.js"></script>
</head>
<body>
<div align="center">
    <h1>登陆页面</h1>
</div>
<%
    if (!isLogin) {
%>
        <div align="center">
            <table border="1">
                <tr>
                    <td>用户名：</td>
                    <td><input type="text" id="name"></td>
                </tr>
                <tr>
                    <td>密码：</td>
                    <td><input type="password" id="password"></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button onclick="login()">登录</button>
                    </td>
                </tr>
            </table>
        </div>
<%
    } else {
%>
        <div align="center">
            <table border="1">
                <tr>
                    <td colspan="2" align="right"><button onclick="logOut()">退出</button></td>
                </tr>
                <tr>
                    <td colspan="2">欢迎使用suncare-oa！</td>
                </tr>
                <tr>
                    <td>您当前使用的账户是：</td>
                    <td><%=user.getName()%></td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <button onclick="location.href='<%=baseUrl%>main.jsp'">直接进入主页</button>
                    </td>
                </tr>
            </table>
        </div>
<%
    }
%>
</body>
</html>