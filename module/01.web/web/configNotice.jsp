<%@ page import="com.gxx.oa.dao.NoticeDao" %>
<%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>公告管理</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/configNotice.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <!--这一行显示ie会有问题xxxxxx-->
    <%--<script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript">
        /**
         * 公告Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var noticeJsonStr = "<%=BaseUtil.getJsonArrayFromNotices(NoticeDao.queryNoticesByPage(1,
                        Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.NOTICE_PAGE_SIZE)))).
                        replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                        PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //公告总数
        var noticeCount = <%=NoticeDao.countAllNotices()%>;
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
                <li><a href="#" class="nav-top-item current"> 消息模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>notice.jsp">公告</a></li>
                        <li><a href="<%=baseUrl%>configNotice.jsp" class="current">公告管理</a></li>
                        <li><a href="<%=baseUrl%>message.jsp">消息</a></li>
                        <li><a href="<%=baseUrl%>letter.jsp">站内信</a></li>
                    </ul>
                </li>
                <li><a href="#" class="nav-top-item"> 工作模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>diary.jsp">工作日志</a></li>
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
        <div>
            <input class="button" type="button" onclick="changeMode();" value="切换模式" />
        </div>
        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>
            <div id="message_id_content"> 提示信息！</div>
        </div>
        <div id="content-box1" class="content-box column-left">
            <div class="content-box-header">
                <h3>公告</h3>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab">
                    <table id="notice_table" border="1" width="80%" style="text-align: center"></table>
                    <div class="clear"></div>
                    <div id="nextPageDiv" style="display: none; text-align: center;">
                        <input class="button" type="button" onclick="showNextPageNotices();" value="加载下一页" />
                    </div>
                </div>
            </div>
        </div>
        <div id="content-box2" class="content-box column-right">
            <div class="content-box-header">
                <h3>详细</h3>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab">
                    <div id="showNoticeDiv" style="display: none;">
                        <div id="showNoticeTitleDiv" align="center"></div>
                        <div id="showNoticeContentDiv"></div>
                    </div>
                    <div id="configNoticeDiv" style="display: none;">
                        <form name="configNoticeForm" action="<%=baseUrl%>configNotice.do" method="post">
                            <table>
                                <input type="hidden" id="token" name="token" value="<%=token%>">
                                <input id="type" type="hidden" name="type">
                                <input id="noticeId" type="hidden" name="noticeId">
                                <textarea style="display: none;" id="content" name="content"></textarea>
                                <b>标题:</b>&nbsp;&nbsp;<input class="text-input between-medium-large-input" type="text" id="title" name="title">
                            </table>
                        </form>
                        <script id="editor" type="text/plain"></script>
                        <input id="configButton" class="button" type="button" onclick="createNotice();" value="新增公告" />
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<%}%>