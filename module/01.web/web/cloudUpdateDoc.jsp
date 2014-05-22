<%@ page import="com.gxx.oa.entities.CloudDoc" %>
<%@ page import="com.gxx.oa.dao.CloudDocDao" %>
<%@ page import="com.gxx.oa.interfaces.CloudDocInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //外层
    outLayer = "申成云";
    //内层
    inLayer = "申成文库";
    int id;
    CloudDoc cloudDoc;
    try{
        id = Integer.parseInt(request.getParameter("id"));
        cloudDoc = CloudDocDao.getCloudDocById(id);
        if(cloudDoc == null || cloudDoc.getUserId() != user.getId() || cloudDoc.getState() != CloudDocInterface.STATE_NORMAL){
            throw new RuntimeException("文档不存在 或者 文档不是你的！");
        }
    } catch (Exception e){
        response.sendRedirect("cloudDoc.jsp");
        return;
    }
%>
<html>
<head>
    <title>申成文库</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudDocBase.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudUpdateDoc.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="scripts/facebox.js"></script>
    <script type="text/javascript">
        var initTitle = '<%=cloudDoc.getTitle()%>';
        var initDescription = '<%=cloudDoc.getDescription()%>';
        var initType = '<%=cloudDoc.getType()%>';
        var initTags = '<%=cloudDoc.getTags()%>';
    </script>
</head>
<body>
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

        <form name="cloudQueryDocForm" action="cloudDoc.jsp" method="post">
            <input type="hidden" name="doc" id="cloudQueryDocName">
        </form>

        <form onsubmit="return false;">
            <fieldset>
                <p>
                    <span>申成文库</span>&nbsp;&nbsp;
                    <input class="text-input small-input" type="text" id="doc_name"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="queryDoc($('#doc_name').val());" value="查询" />&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudUploadDoc.jsp'" value="贡献我的文档" />&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudMyDoc.jsp'" value="我的文档" />
                </p>
                <table id="doc_list"></table>
            </fieldset>
            <div class="clear"></div>
        </form>

        <div class="content-box">
            <div class="content-box-header">
                <h3>修改我的文档</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab" class="default-tab">文档</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab">
                    <div id="message_id" class="notification information png_bg" style="display: none;">
                        <a href="#" class="close">
                            <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
                        </a>

                        <div id="message_id_content"> 提示信息！</div>
                    </div>
                    <form action="<%=baseUrl%>cloudUpdateDoc.do" name="cloudUpdateDocFrom" method="post">
                        <input type="hidden" name="token" value="<%=token%>">
                        <input type="hidden" name="docId" value="<%=cloudDoc.getId()%>">
                        <fieldset>
                            <p>
                                <span><span style="color:red;">*</span>标题</span>&nbsp;&nbsp;
                                <input class="text-input medium-input" name="title" type="text" id="title"
                                        onfocus="focusTitle(this)" onblur="blurTitle(this)" />
                            </p>
                            <p>
                                <span>&nbsp;简介</span>&nbsp;&nbsp;
                                <textarea class="text-input medium-input" name="description" id="description"
                                          type="text" onfocus="focusDescription(this)" onblur="blurDescription(this)"></textarea>
                            </p>
                            <p>
                                <span><span style="color:red;">*</span>分类</span>&nbsp;&nbsp;
                                <span id="show_type"></span>
                                <input name="type" type="hidden" id="type"/>
                            </p>
                            <p>
                                <span>&nbsp;标签</span>&nbsp;&nbsp;
                                <input class="text-input medium-input" name="tags" type="text" id="tags"
                                       onfocus="focusTags(this)" onblur="blurTags(this)"/>
                            </p>
                            <p>
                                <input class="button" type="button" onclick="cloudUpdateDoc()" value="修改"/>
                            </p>
                        </fieldset>
                        <div class="clear"></div>
                    </form>
                </div>
            </div>
        </div>

        <div id="footer">
            <small>
                &#169; Copyright 2014 Suncare | Powered by 关向辉
            </small>
        </div>
    </div>
</div>
</body>
</html>