<%@ page import="java.util.List" %>
<%@ page import="com.gxx.oa.entities.CloudKnowAsk" %>
<%@ page import="com.gxx.oa.dao.CloudKnowAskDao" %>
<%@ page import="com.gxx.oa.dao.CloudKnowAnswerDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //外层
    outLayer = "申成云";
    //内层
    inLayer = "申成知道";
    //根据 用户id 查 申成知道提问
    List<CloudKnowAsk> cloudKnowAsks = CloudKnowAskDao.queryCloudKnowAsksByUserId(user.getId());
%>
<html>
<head>
    <title>申成文库</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudKnowMyAsk.js"></script>
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

        <form name="cloudQueryAskForm" action="cloudKnow.jsp" method="post">
            <input type="hidden" name="ask" id="cloudQueryAsk">
        </form>

        <form onsubmit="return false;">
            <fieldset>
                <p>
                    <span>申成知道</span>&nbsp;&nbsp;
                    <input class="text-input small-input" type="text" id="ask"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="queryAsk($('#ask').val())" value="搜索答案"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudKnowAsk.jsp'" value="我要提问"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudKnowMyAsk.jsp'" value="我的提问"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudKnowMyAnswer.jsp'" value="我的回答"/>
                </p>
            </fieldset>
            <div class="clear"></div>
        </form>

        <div class="content-box">
            <div class="content-box-header">
                <h3>我的提问</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab" class="default-tab">提问</a></li>
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
                    <table id="know_list">
                        <%
                            if(cloudKnowAsks.size() == 0){
                        %>
                        <tr>
                            <td>
                            你还没有提问过哦！
                            <input class="button" type="button" onclick="location.href='cloudKnowAsk.jsp'" value="我要提问"/>
                            </td>
                        </tr>
                        <%
                            }
                            for(CloudKnowAsk cloudKnowAsk : cloudKnowAsks){
                                int answerCount = CloudKnowAnswerDao.countCloudKnowAnswersByAskId(cloudKnowAsk.getId());
                        %>
                        <tr>
                            <td>
                                <a href="cloudViewKnow.jsp?id=<%=cloudKnowAsk.getId()%>" target="_blank" style="color: blue">
                                    <%=cloudKnowAsk.getQuestion()%>
                                </a><br>
                            <span style="color: gray">
                                <%=cloudKnowAsk.getCreateDate()%>
                                <%=cloudKnowAsk.getCreateTime()%>
                                | 提问者：
                                <a href="user.jsp?id=<%=user.getId()%>" target="_blank"><%=user.getName()%></a>
                                | <%=answerCount%>个回答
                            </span>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
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