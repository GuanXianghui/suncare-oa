<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>写任务</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <%--<script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>scripts/writeTask.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript">
        <%
            //有权限看的下级用户 用逗号隔开
            String rightUserWithComma = BaseUtil.getLowerLevelPositionUserIdWithComma(user.getPosition());
        %>
        //有权限看的下级用户 用逗号隔开
        var rightUserWithComma = "<%=rightUserWithComma%>";
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
    </script>
</head>
<body onclick="cc()">
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
                        <li><a href="<%=baseUrl%>diary.jsp">工作日志</a></li>
                        <li><a href="<%=baseUrl%>calendar.jsp">日历</a></li>
                        <li><a href="<%=baseUrl%>task.jsp" class="current">任务</a></li>
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
                <a class="shortcut-button" href="javascript: location.href='<%=baseUrl%>writeTask.jsp'">
                    <span>
                        <img src="images/icons/paper_content_pencil_48.png" alt="icon"/>
                        <br/>分配任务
                    </span>
                </a>
            </li>
            <li>
                <a class="shortcut-button" href="javascript: location.href='<%=baseUrl%>task.jsp'">
                    <span>
                        <img src="images/icons/image_add_48.png" alt="icon"/>
                        <br/>查看任务
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
                <h3>分配任务</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab2" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab2">
                    <form name="writeTaskForm" action="<%=baseUrl%>writeTask.do" method="post">
                        <table>
                            <input type="hidden" id="token" name="token" value="<%=token%>">
                            <input type="hidden" id="fromUserId" name="fromUserId" value="<%=user.getId()%>">
                            <tr>
                                <td width="15%">任务接受用户:</td>
                                <td>
                                    <select id="toUserId" name="toUserId" class="medium-input">
                                        <option value="">选择用户</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>任务名称:</td>
                                <td>
                                    <input id="title" class="text-input medium-input" type="text"
                                       name="title" value=""/>
                                </td>
                            </tr>
                            <tr>
                                <td>开始日期:</td>
                                <td>
                                    <input id="beginDate" class="text-input medium-input" type="text"
                                                         name="beginDate" value=""/>
                                </td>
                            </tr>
                            <tr>
                                <td>结束日期:</td>
                                <td>
                                    <input id="endDate" class="text-input medium-input" type="text"
                                                         name="endDate" value=""/>
                                </td>
                            </tr>
                            <textarea style="display: none;" id="content" name="content"></textarea>
                        </table>
                    </form>
                    <script id="editor" type="text/plain"></script>
                    <input class="button" type="button" onclick="writeTask()" value="提交" />
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