<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>测试消息</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>测试消息<button onclick="logOut()">退出</button></h1>
        <table id="message_table" border="1" width="80%" style="text-align: center">
            <form action="<%=baseUrl%>testMessage.do" method="post">
                <input type="hidden" name="token" value="<%=token%>">
                <tr><td>来源用户id</td><td><input type="text" name="fromUserId" value=""></td></tr>
                <tr><td>来源用户类型</td><td><input type="text" name="fromUserType" value=""></td></tr>
                <tr><td>接受用户id</td><td><input type="text" name="toUserId" value=""></td></tr>
                <tr>
                    <td>内容</td>
                    <td>
                        <textarea rows="10" cols="10" name="content"></textarea>
                    </td>
                </tr>
                <tr><td colspan="2"><input type="submit" value="提交"></td></tr>
            </form>
        </table>
    </div>
</body>
</html>
<%}%>