<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="headerWithOutCheckLogin.jsp" %>
<html>
<head>
    <title>Suncare-OA</title>
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/md5.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/index.js"></script>
</head>

<body id="login" onkeypress="keyPress(event)">
<div id="login-wrapper" class="png_bg">
    <div id="login-top">
        <h1>Suncare-OA</h1>
        <img id="logo" src="images/suncare-files-logo.png" alt="申成-文件系统" />
    </div>
    <div id="login-content">
        <form onsubmit="return false;">
            <div id="message_id" class="notification information png_bg" style="display: none;">
                <a href="#" class="close">
                    <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
                </a>
                <div id="message_id_content"> 提示信息！ </div>
            </div>
            <%
                if(isLogin){
            %>
            <div align="center">
                <input style="width: 100%" class="button" type="button" value="您已登陆，点击直接进入主页"
                        onclick="location.href=baseUrl+'userManage.jsp';">
            </div>
            <%
                } else {
            %>
            <p>
                <label>用户名</label>
                <input class="text-input" type="text" id="name" />
            </p>
            <div class="clear"></div>
            <p>
                <label>密码</label>
                <input class="text-input" type="password" id="password" />
            </p>
            <div class="clear"></div>
            <div class="clear"></div>
            <p>
                <input class="button" type="button" onclick="login();" value="Log In" />
            </p>
            <%
                }
            %>
        </form>
    </div>
</div>
</body>
</html>