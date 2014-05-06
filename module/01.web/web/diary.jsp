<%@ page import="com.gxx.oa.dao.DiaryDao" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<%
    int userId;
    try{
        userId = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("userId")));
    } catch (Exception e){
        userId = 0;
    }
    String date = StringUtils.trimToEmpty(request.getParameter("date"));
%>
<html>
<head>
    <title>工作日志</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/diary.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <%--<script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript">
        //塞选用户id
        var userId = <%=userId%>;
        //塞选日期
        var date = "<%=date%>";
        <%
            //有权限看的下级用户 用逗号隔开
            String rightUserWithComma = BaseUtil.getLowerLevelPositionUserIdWithComma(user.getPosition());
        %>
        //有权限看的下级用户 用逗号隔开
        var rightUserWithComma = "<%=rightUserWithComma%>";
        //工作日志Json串
        var diaryJsonStr = "<%=BaseUtil.getJsonArrayFromDiaries(DiaryDao.queryDiariesByFromTo(userId, date, 0,
                        Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.DIARY_PAGE_SIZE)),
                         rightUserWithComma))%>";
        //工作日志总数
        var diaryCount = <%=DiaryDao.countDiaries(userId, date, rightUserWithComma)%>;
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
    </script>
    <style type="text/css">
        td{
            text-align: center;
        }
    </style>
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
            <ul id="main-nav">
                <li><a href="#" class="nav-top-item"> 用户模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>userManage.jsp">用户管理</a></li>
                        <li><a href="<%=baseUrl%>user.jsp?id=<%=user.getId()%>">个人展示</a></li>
                        <li><a href="<%=baseUrl%>userOperate.jsp">后台用户管理</a></li>
                        <li><a href="<%=baseUrl%>contacts.jsp">通讯录</a></li>
                        <li><a href="<%=baseUrl%>orgStructureManage.jsp">组织架构管理</a></li>
                    </ul>
                </li>
                <li><a href="#" class="nav-top-item"> 消息模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>notice.jsp">公告</a></li>
                        <li><a href="<%=baseUrl%>configNotice.jsp">公告管理</a></li>
                        <li><a href="<%=baseUrl%>message.jsp">消息</a></li>
                        <li><a href="<%=baseUrl%>letter.jsp">站内信</a></li>
                    </ul>
                </li>
                <li><a href="#" class="nav-top-item current"> 工作模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>diary.jsp" class="current">工作日志</a></li>
                        <li><a href="<%=baseUrl%>calendar.jsp">日历</a></li>
                        <li><a href="<%=baseUrl%>task.jsp">任务</a></li>
                    </ul>
                </li>
                <li><a href="#" class="nav-top-item"> 工具模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>sms.jsp">短信</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>

    <div id="main-content">
        <ul class="shortcut-buttons-set">
            <li>
                <a class="shortcut-button" href="<%=baseUrl%>writeDiary.jsp">
                    <span>
                        <img src="images/icons/paper_content_pencil_48.png" alt="icon"/>
                        <br/>写日志
                    </span>
                </a>
            </li>
            <li>
                <a class="shortcut-button" href="<%=baseUrl%>diary.jsp">
                    <span>
                        <img src="images/icons/image_add_48.png" alt="icon"/>
                        <br/>看日志
                    </span>
                </a>
            </li>
        </ul>

        <div class="clear"></div>

        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>

            <div id="message_id_content"> 提示信息！</div>
        </div>

        <div class="content-box">
            <div class="content-box-header">
                <h3>工作日志</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab2" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab2">
                    <form>
                        <div align="center">
                            <div>
                                用户:<select id="userId" class="small-input">
                                <option value="">全部用户</option>
                                </select>
                                &nbsp;&nbsp;&nbsp;
                                日期:<input class="text-input small-input" type="text" id="date" value="<%=date%>"/>
                                <input class="button" type="button" onclick="selectDiary();" value="选择" />
                            </div>
                        </div>
                    </form>
                    <table id="diary_table"></table>
                    <div id="nextPageDiv" style="display: none; text-align: center;">
                        <input class="button" type="button" onclick="showNextPageDiaries();" value="加载下一页" />
                    </div>
                </div>
            </div>
        </div>
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
<%}%>