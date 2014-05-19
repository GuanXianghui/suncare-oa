<%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //外层
    outLayer = "申成云";
    //内层
    inLayer = "申成云";
    //查看Action传出来的dir
    String dir = (String)request.getAttribute("dir");
%>
<html>
<head>
    <title>申成云</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloud.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>css/cloud.css"/>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="scripts/facebox.js"></script>
    <script type="text/javascript">
        //当前目录 首页为/
        var dir = '<%=StringUtils.isBlank(dir)?SymbolInterface.SYMBOL_SLASH:dir%>';
        //当前目录中的文件
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

    <div id="new_dir_div" align="center" style="width: 100%; display: none;">
        <form>
            文件夹名字：<input class="text-input medium-input" type="text" id="new_dir_name"><input class="button" type="button" onclick="newDir(this);" value="新建">
        </form>
    </div>

    <div id="main-content">

        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>

            <div id="message_id_content"> 提示信息！</div>
        </div>

        <input class="button" type="button" onclick="beforeUpload();" value="上传">
        <input class="button" type="button" onclick="download();" value="下载">
        <input class="button" type="button" onclick="beforeNewDir();" value="新建文件夹">
        <div style="display: none;">
            <a id="showNewDirDiv" class="shortcut-button" href="#new_dir_div" rel="modal"></a>
        </div>
        <input class="button" type="button" onclick="deleteFile();" value="删除">
        <input class="button" type="button" onclick="lastDir();" value="返回上一层">

        <div class="clear"></div>

        <table>
            <tr>
                <td style="display: none;">
                    <form name="uploadForm" method="post" autocomplete="off"
                          action="" enctype="multipart/form-data">
                        <input type="text" name="dir" id="uploadDir">
                        <input type="file" name="file" id="uploadFile" onchange="upload(this)">
                    </form>
                </td>
            </tr>
            <tr style="border: 1px solid gray">
                <td id="dir_route"><span class="dir">我的网盘/</span></td>
            </tr>
        </table>

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