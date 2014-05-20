<%@ page import="com.gxx.oa.dao.CloudDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //外层
    outLayer = "申成云";
    //内层
    inLayer = "申成网盘";
%>
<html>
<head>
    <title>申成云</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudRecycle.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>css/cloudRecycle.css"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="scripts/facebox.js"></script>
    <script type="text/javascript">
        /**
         * 回收站文件Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         */
        var filesJsonStr = "<%=BaseUtil.getJsonArrayFromClouds(CloudDao.queryRecycleClouds(user.getId()))%>";
        //回收站里的对象
        var files = new Array();
        //选择对象
        var chooseClouds = new Array();
        //ctrl键是否按下
        var isCtrlDown = false;
    </script>
</head>
<body onkeydown="keyDown(event)" onkeyup="keyUp(event)">
<div id="body-wrapper">
    <div id="sidebar">
        <div id="sidebar-wrapper">
            <h1 id="sidebar-title"><a href="#">申成-OA系统</a></h1>
            <img id="logo" src="images/suncare-files-logo.png" alt="Simpla Admin logo"/>
            <div id="profile-links">
                Hello, [<%=user.getName()%>],
                <a href="http://www.suncarechina.com" target="_blank">申成</a>欢迎您！
                <br/>
                <br/>
                <a href="javascript: logOut()" title="Sign Out">退出</a>
            </div>
            <%@ include file="layers.jsp" %>
        </div>
    </div>

    <div id="main-content">

        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>

            <div id="message_id_content"> 提示信息！</div>
        </div>

        <input class="button" type="button" onclick="recover();" value="还原">
        <input class="button" type="button" onclick="ctrlDelete();" value="彻底删除">
        <input class="button" type="button" onclick="clearRecycle();" value="清空回收站">
        <input class="button" type="button" onclick="location.href='cloud.jsp'" value="<申成网盘">

        <div class="clear"></div>

        <ul class="shortcut-buttons-set" id="files">
            <li>
                <span class="shortcut-button">//background: #fff
                    <span>
                        <img src="images/file.jpg" alt="icon" width="48" height="48"/>
                        <br/>写信
                    </span>
                </span>
            </li>
        </ul>

        <div class="clear"></div>
        <div id="footer">
            <small>
                &#169; Copyright 2014 Suncare | Powered by 关向辉
            </small>
        </div>
    </div>
</div>
</body>
</html>