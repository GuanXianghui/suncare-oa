<%@ page import="com.gxx.oa.utils.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>日历</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/calendar.js"></script>
    <!--日期控件-->
    <link type="text/css" rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <style type="text/css">
        th{
            text-align: center;
        }
        td{
            text-align: center;
        }
    </style>
    <script type="text/javascript">
        chooseDate = "<%=DateUtil.getNowDate()%>";
    </script>
</head>
<body>
<div id="body-wrapper">

    <br>
    <br>
    <div id="date" align="center"></div>

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
                        <li><a href="<%=baseUrl%>calendar.jsp" class="current">日历</a></li>
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
                <h3>提醒</h3>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab">
                    <table id="remind_table" border="1" width="100%"></table>
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
                    <form>
                        <table id="create_remind_table" border="1" style="display: none;" width="100%">
                            <tr>
                                <td>日期:</td><td><input class="text-input between-medium-large-input" type="text" id="create_date"></td>
                            </tr>
                            <tr>
                                <td>内容:</td><td><input class="text-input between-medium-large-input" type="text" id="create_content"></td>
                            </tr>
                            <tr>
                                <td>提醒类型:</td>
                                <td>
                                    <select id="create_remind_type" class="text-input between-medium-large-input">
                                        <option value="1">不提醒</option>
                                        <option value="2">消息提醒</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>提醒时间:</td><td><input class="text-input between-medium-large-input" type="text" id="create_remind_date_time"></td>
                            </tr>
                            <tr>
                                <td colspan="4" align="center"><input class="button" type="button" onclick="createRemind()" value="提交" /></td>
                            </tr>
                        </table>
                    </form>

                    <table id="show_remind_table" border="1" style="display: none;" width="100%">
                        <tr>
                            <td>日期:</td><td id="show_date"></td>
                        </tr>
                        <tr>
                            <td>内容:</td><td id="show_content"></td>
                        </tr>
                        <tr>
                            <td>提醒类型:</td>
                            <td id="show_remind_type"></td>
                        </tr>
                        <tr>
                            <td>提醒时间:</td><td id="show_remind_date_time"></td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<%}%>