<%@ page import="com.gxx.oa.utils.ParamUtil" %>
<%@ page import="com.gxx.oa.interfaces.ParamInterface" %>
<%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //权限校验
    if(!BaseUtil.checkRight(user.getId(), UserRightInterface.RIGHT_0014_DEFAULT_RIGHT)){
        //域名链接
        response.sendRedirect(baseUrl + "index.jsp");
        return;
    }
    //外层
    outLayer = "权限模块";
    //内层
    inLayer = "默认权限";
    //所有权限 权限代码1=权限名称1,权限代码2=权限名称2,权限代码3=权限名称3,...
    String[] rightList = PropertyUtil.getInstance().getProperty(BaseInterface.RIGHT_LIST).split(SymbolInterface.SYMBOL_COMMA);
    //默认权限 用户创建时赋予的默认权限
    String defaultRight = ParamUtil.getInstance().getValueByName(ParamInterface.DEFAULT_RIGHT);
%>
<html>
<head>
    <title>默认权限</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/defaultRight.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript">
        //默认权限
        var defaultRight = "<%=defaultRight%>";
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

        <div class="content-box" id="update_user_div" style="display: block;">
            <div class="content-box-header">
                <h3>默认权限</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab2" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab2">
                    <table>
                        <thead><tr><th>权限代号</th><th>权限名称</th><th>选择权限</th></tr></thead>
                        <%
                            for(String right : rightList){
                                int equalIndex = right.indexOf(SymbolInterface.SYMBOL_EQUAL);
                                String rightCode = right.substring(0, equalIndex);
                                String rightName = right.substring(equalIndex + SymbolInterface.SYMBOL_EQUAL.length());
                                boolean chooseRight = defaultRight.indexOf(rightCode) > -1;
                                String checked = chooseRight ? " checked=\"checked\"" : "";
                        %>
                        <tr>
                            <td><%=rightCode%></td>
                            <td><%=rightName%></td>
                            <td><input name="<%=rightCode%>" type="checkbox"<%=checked%>></td>
                        </tr>
                        <%
                            }
                        %>
                    </table>
                    <div align="center">
                        <input class="button" type="button" onclick="updateDefaultRight();" value="修改">
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