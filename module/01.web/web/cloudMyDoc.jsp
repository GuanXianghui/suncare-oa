<%@ page import="com.gxx.oa.entities.CloudDoc" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gxx.oa.dao.CloudDocDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //外层
    outLayer = "申成云";
    //内层
    inLayer = "申成文库";
%>
<html>
<head>
    <title>申成文库</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudMyDoc.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="scripts/facebox.js"></script>
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

        <div class="clear"></div>

        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>

            <div id="message_id_content"> 提示信息！</div>
        </div>

        <div class="clear"></div>

        <div class="content-box">
            <div class="content-box-header">
                <h3>我的文档</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab" class="default-tab">文档</a></li>
                </ul>
                <div class="clear"></div>
            </div>

            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab">
                    <table>
                        <thead><tr><th>文档名称</th><th>上传时间</th><th>操作</th></tr></thead>
                        <%
                            List<CloudDoc> cloudDocs = CloudDocDao.queryCloudDocsByUserId(user.getId());
                            if(cloudDocs.size() == 0){
                        %>
                        <tr><td colspan="3">暂无文档ToT</td></tr>
                        <%
                            } else {
                                for(CloudDoc cloudDoc : cloudDocs){
                        %>
                        <tr>
                            <td><%=cloudDoc.getTitle()%></td>
                            <td><%=cloudDoc.getCreateDate()%></td>
                            <td>
                                <input class="button" type="button" onclick="window.open('cloudViewDoc.jsp?id=<%=cloudDoc.getId()%>')" value="查看"/>
                                <input class="button" type="button" onclick="window.open('<%=cloudDoc.getRoute()%>')" value="下载"/>
                                <input class="button" type="button" onclick="location.href='cloudUpdateDoc.jsp?id=<%=cloudDoc.getId()%>'" value="修改"/>
                                <input class="button" type="button" onclick="cloudDeleteDoc(<%=cloudDoc.getId()%>)" value="删除"/>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>
                    </table>
                    <form name="cloudDeleteDocForm" action="<%=baseUrl%>cloudDeleteDoc.do" method="post">
                        <input type="hidden" name="token" value="<%=token%>">
                        <input type="hidden" name="docId" id="cloudDeleteDocId">
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