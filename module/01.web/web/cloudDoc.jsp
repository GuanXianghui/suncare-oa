<%@ page import="com.gxx.oa.dao.CloudDocDao" %>
<%@ page import="com.gxx.oa.entities.CloudDoc" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //外层
    outLayer = "申成云";
    //内层
    inLayer = "申成文库";
    //搜索文档
    String doc = StringUtils.trimToEmpty(request.getParameter("doc"));
    //是否执行了查询
    boolean isQuery = false;
    //搜索结果
    List<CloudDoc> cloudDocs = new ArrayList<CloudDoc>();
    if(StringUtils.isNotBlank(doc)){
        isQuery = true;
        cloudDocs = BaseUtil.queryCloudDocs(doc);
    }
%>
<html>
<head>
    <title>申成文库</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudDoc.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="scripts/facebox.js"></script>
    <script type="text/javascript">

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

        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>

            <div id="message_id_content"> 提示信息！</div>
        </div>

        <form name="cloudQueryDocForm" action="cloudDoc.jsp" method="post">
            <input type="hidden" name="doc" id="cloudQueryDocName">
        </form>

        <form onsubmit="return false;">
            <fieldset>
                <p>
                    <span>申成文库</span>&nbsp;&nbsp;
                    <input class="text-input small-input" type="text" id="doc_name" value="<%=StringUtils.trimToEmpty(doc)%>"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="queryDoc($('#doc_name').val())" value="查询" />&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudUploadDoc.jsp'" value="贡献我的文档" />&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudMyDoc.jsp'" value="我的文档" />
                </p>

                <%
                    if(cloudDocs.size() == 0){
                        List<String> tags = CloudDocDao.queryDistinctTags();
                %>
                热门标签：
                <%
                        for(String tag : tags){
                %>
                <a href="javascript: queryDoc('<%=tag%>')"><%=tag%></a> &nbsp;
                <%
                        }
                %>
                <br>
                <br>
                <%
                    }
                %>
                <table id="cloud_list">
                    <%
                            if(isQuery && cloudDocs.size() == 0){
                    %>
                    <tr><td>尼玛，没有符合条件[<%=doc%>]的文档！</td></tr>
                    <%
                            }
                        for(CloudDoc cloudDoc : cloudDocs){
                            User cloudUser = UserDao.getUserById(cloudDoc.getUserId());
                    %>
                    <tr>
                        <td>
                            <img src="images/cloud_doc/<%=BaseUtil.getFileType(cloudDoc.getRoute())%>.jpg" width="19px">
                            <a href="cloudViewDoc.jsp?id=<%=cloudDoc.getId()%>" target="_blank">
                                <%=BaseUtil.displayCloudDocTitle(cloudDoc.getTitle(), doc)%>
                            </a><br>
                            <%=BaseUtil.displayCloudDocDescription(cloudDoc.getDescription(), doc)%>
                            <%=BaseUtil.displayCloudDocTags(cloudDoc.getTags(), doc)%>
                            <span style="color: gray">
                                <%=cloudDoc.getCreateDate()%> | 贡献者：<a href="user.jsp?id=<%=cloudUser.getId()%>" target="_blank"><%=cloudUser.getName()%></a>
                            </span>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                </table>
            </fieldset>
            <div class="clear"></div>
        </form>

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