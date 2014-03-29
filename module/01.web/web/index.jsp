<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>登陆页面</title>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/md5.js"></script>
    <script type="text/javascript">
        //登陆
        function login() {
            var name = document.getElementById("name").value;
            var password = document.getElementById("password").value;
            if(name==""){
                alert("请输入用户名!");
                return;
            }
            if(password==""){
                alert("请输入密码!");
                return;
            }
            var md5Pwd = MD5(password + md5Key);
            document.getElementById("password").value = md5Pwd;

            var SUCCESS_STR = "success";//成功编码
            $.ajax({
                type: "post",
                async: false,
                url: "<%=baseUrl%>ajax/login.jsp",
                data: "name=" + name + "&password=" + md5Pwd + "&token=" + token,
                success: function(data, textStatus){
                    if((SUCCESS_STR == textStatus) && (null != data))
                    {
                        data = eval("(" + data + ")");
                        //判登陆是否成功
                        if(false == data["isSuccess"]){
                            alert(data["message"]);
                            document.getElementById("password").value = "";
                            //判是否有新token
                            if(data["hasNewToken"]){
                                token = data["token"];
                            }
                            return;
                        } else {
                            //登陆成功
                            alert(data["message"]);
                        }
                        //是否跳转页面
                        if(data["isRedirect"]){
                            var redirectUrl = data["redirectUrl"];
                            location.href = redirectUrl;
                        }
                    } else
                    {
                        alert("Connection failed,please try again later!");
                    }
                },
                error: function(data, textStatus){
                    alert("Connection failed,please try again later!");
                }
            });
        }
    </script>
</head>
<body>
<div align="center">
    <h1>登陆页面</h1>
</div>
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
</body>
</html>