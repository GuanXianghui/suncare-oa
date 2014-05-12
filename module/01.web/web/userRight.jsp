<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //权限校验
    if(!BaseUtil.checkRight(user.getId(), UserRightInterface.RIGHT_0015_USER_RIGHT)){
        //域名链接
        response.sendRedirect(baseUrl + "index.jsp");
        return;
    }
    //外层
    outLayer = "权限模块";
    //内层
    inLayer = "权限管理";
    //所有权限 权限代码1=权限名称1,权限代码2=权限名称2,权限代码3=权限名称3,...
    String[] rightList = PropertyUtil.getInstance().getProperty(BaseInterface.RIGHT_LIST).split(SymbolInterface.SYMBOL_COMMA);
%>
<html>
<head>
    <title>权限管理</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/userRight.js"></script>
    <link rel="stylesheet" href="css/sms.css" type="text/css" media="screen"/>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript">
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
        //用户id
        var chooseUserId = 0;
        //用户名字
        var chooseUserName = EMPTY;
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

        <div class="content-box friends-column-left">
            <div class="content-box-header">
                <h3>权限管理</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab2" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab2">
                    &lt;当前用户：<span id="choose_user"></span>&gt;
                    <table>
                        <thead><tr><th>权限代号</th><th>权限名称</th><th>选择权限</th></tr></thead>
                        <%
                            for(String right : rightList){
                                int equalIndex = right.indexOf(SymbolInterface.SYMBOL_EQUAL);
                                String rightCode = right.substring(0, equalIndex);
                                String rightName = right.substring(equalIndex + SymbolInterface.SYMBOL_EQUAL.length());
                        %>
                        <tr>
                            <td><%=rightCode%></td>
                            <td><%=rightName%></td>
                            <td><input name="<%=rightCode%>" type="checkbox"></td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                    <div align="center">
                        <input class="button" type="button" onclick="updateUserRight();" value="修改">
                    </div>
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