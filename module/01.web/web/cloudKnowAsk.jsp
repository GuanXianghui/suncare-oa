<%@ page import="com.gxx.oa.interfaces.CloudKnowAskInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //外层
    outLayer = "申成云";
    //内层
    inLayer = "申成知道";
%>
<html>
<head>
    <title>申成文库</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudKnowBase.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudKnowAsk.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <%--<script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
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
                <h3>我要提问</h3>
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
                    <form action="<%=baseUrl%>cloudKnowAsk.do?token=<%=token%>" name="cloudKnowAskFrom" method="post">
                        <fieldset>
                            <p>
                                <span><span style="color:red;">*</span>问题</span>&nbsp;&nbsp;
                                <input class="text-input medium-input" name="question" type="text" id="question"
                                       onfocus="focusQuestion(this)" onblur="blurQuestion(this)"/>
                            </p>

                            <p>
                                <textarea style="display: none;" id="description" name="description"></textarea>
                                <span>&nbsp;补充</span>&nbsp;&nbsp;&nbsp;&nbsp;可以填写问题补充，让你的问题看起来不会太奇葩~
                                <input class="button" type="button" onclick="writeDescription(this)" value="展开"/>
                                <span id="editor_div" style="display: none">
                                    <script id="editor" type="text/plain"></script>
                                </span>
                            </p>
                            <p>
                                <span><span style="color:red;">*</span>分类</span>&nbsp;&nbsp;
                                <span id="show_type"></span>
                                <input name="type" type="hidden" id="type"/>
                            </p>

                            <p>
                                <span><span style="color:red;">*</span>紧急</span>&nbsp;&nbsp;
                                <select name="urgent">
                                    <option value="<%=CloudKnowAskInterface.URGENT_NO%>">不紧急</option>
                                    <option value="<%=CloudKnowAskInterface.URGENT_YES%>">紧急</option>
                                </select>
                            </p>
                            <p>
                                <input class="button" type="button" onclick="cloudKnowAsk()" value="提问"/>
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