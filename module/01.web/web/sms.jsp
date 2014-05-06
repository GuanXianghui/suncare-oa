<%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
<%@ page import="com.gxx.oa.dao.SMSDao" %>
<%@ page import="com.gxx.oa.utils.DateUtil" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<%
    String date = request.getParameter("date");
    date = StringUtils.trimToEmpty(date);
    if(StringUtils.isBlank(date)){
        date = DateUtil.getNowDate();
    }
%>
<html>
<head>
    <title>短信</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/sms.js"></script>
    <link rel="stylesheet" href="css/sms.css" type="text/css" media="screen"/>
    <!--日期控件-->
    <link type="text/css" rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript">
        //日期
        var date = "<%=date%>";
        /**
         * 短信Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var smsJsonStr = "<%=BaseUtil.getJsonArrayFromSMS(SMSDao.querySMSByUserIdAndStateAndDate(user.getId(),
                        0, date)).replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE2,
                        PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //根据用户id，状态和日期查短信量
        var smsCount = <%=SMSDao.countSMSByUserIdAndStateAndDate(user.getId(), 0, date)%>;
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
    </script>
    <style type="text/css">
        #sms_table th{
            text-align: center;
        }
        #sms_table td{
            text-align: center;
        }
    </style>
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
                <li><a href="#" class="nav-top-item"> 工作模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>diary.jsp">工作日志</a></li>
                        <li><a href="<%=baseUrl%>calendar.jsp">日历</a></li>
                        <li><a href="<%=baseUrl%>task.jsp">任务</a></li>
                    </ul>
                </li>
                <li><a href="#" class="nav-top-item current"> 工具模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>sms.jsp" class="current">短信</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <div id="main-content">
        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>

            <div id="message_id_content"> 提示信息！</div>
        </div>

        <div class="content-box">
            <div class="content-box-header">
                <h3>短信记录</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab">
                    <table id="sms_table"></table>
                </div>
            </div>
        </div>

        <div class="content-box friends-column-left">
            <div class="content-box-header">
                <h3>发送短信</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab2" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab2">
                    <form>
                        <table id="sendSmsDiv">
                            <tr><td>手机号：</td><td><input class="text-input medium-input" type="text" id="phone"></td></tr>
                            <tr><td>内容：</td><td><textarea id="content" cols="80" rows="3"></textarea></td></tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <input class="button" type="button" onclick="operateSMS();" value="发送">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>

        <div class="content-box friends-column-right">
            <div class="content-box-header">
                <h3>联系人</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab3" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab3">
                    <table id="friends">
                    </table>
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