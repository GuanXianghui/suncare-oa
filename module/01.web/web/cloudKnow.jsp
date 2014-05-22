<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page import="com.gxx.oa.entities.CloudKnowAsk" %>
<%@ page import="com.gxx.oa.dao.CloudKnowAnswerDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //外层
    outLayer = "申成云";
    //内层
    inLayer = "申成知道";
    //搜索申成知道
    String ask = StringUtils.trimToEmpty(request.getParameter("ask"));
    //是否执行了查询
    boolean isQuery = false;
    //搜索结果
    List<CloudKnowAsk> cloudKnowAsks = new ArrayList<CloudKnowAsk>();
    if(StringUtils.isNotBlank(ask)){
        isQuery = true;
        cloudKnowAsks = BaseUtil.queryCloudKnowAsks(ask);
    }
%>
<html>
<head>
    <title>申成知道</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudKnow.js"></script>
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

        <form name="cloudQueryAskForm" action="cloudKnow.jsp" method="post">
            <input type="hidden" name="ask" id="cloudQueryAsk">
        </form>

        <form onsubmit="return false;">
            <fieldset>
                <p>
                    <span>申成知道</span>&nbsp;&nbsp;
                    <input class="text-input small-input" type="text" id="ask" value="<%=StringUtils.trimToEmpty(ask)%>"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="queryAsk($('#ask').val())" value="搜索答案" />&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudKnowAsk.jsp'" value="我要提问" />&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudKnowMyAsk.jsp'" value="我的提问" />&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudKnowMyAnswer.jsp'" value="我的回答" />
                </p>

                <table id="know_list">
                    <%
                            if(isQuery && cloudKnowAsks.size() == 0){
                    %>
                    <tr><td>尼玛，没有符合条件[<%=ask%>]的问题！</td></tr>
                    <%
                            }
                        for(CloudKnowAsk cloudKnowAsk : cloudKnowAsks){
                            User cloudUser = UserDao.getUserById(cloudKnowAsk.getUserId());
                            int answerCount = CloudKnowAnswerDao.countCloudKnowAnswersByAskId(cloudKnowAsk.getId());
                    %>
                    <tr>
                        <td>
                            <a href="cloudViewKnow.jsp?id=<%=cloudKnowAsk.getId()%>" target="_blank">
                                <%=BaseUtil.displayCloudKnowQuestion(cloudKnowAsk.getQuestion(), ask)%>
                            </a><br>
                            <span style="color: gray">
                                <%=cloudKnowAsk.getCreateDate()%>
                                <%=cloudKnowAsk.getCreateTime()%>
                                | 提问者：
                                <a href="user.jsp?id=<%=cloudUser.getId()%>" target="_blank"><%=cloudUser.getName()%></a>
                                | <%=answerCount%>个回答
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