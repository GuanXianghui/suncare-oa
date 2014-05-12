<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //权限校验
    if(!BaseUtil.checkRight(user.getId(), UserRightInterface.RIGHT_0005_ORG_STRUCTURE_MANAGE)){
        //域名链接
        response.sendRedirect(baseUrl + "index.jsp");
        return;
    }
    //外层
    outLayer = "用户模块";
    //内层
    inLayer = "组织架构管理";
%>
<html>
<head>
    <title>组织架构管理</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/orgStructureManage.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>css/orgStructureManage.css"/>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <style type="text/css">
        td{
            text-align: center;
            vertical-align: middle;
            border: 1px solid gray;
        }
        th{
            text-align: center;
            vertical-align: middle;
            border: 1px solid gray;
        }
    </style>
    <script type="text/javascript">
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
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
        <div class="content-box" id="create_user_div">
            <div class="content-box-header">
                <h3>组织架构</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab1" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab1">

                    <div id="message_id" class="notification information png_bg" style="display: none;">
                        <a href="#" class="close">
                            <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
                        </a>

                        <div id="message_id_content"> 提示信息！</div>
                    </div>

                    <div>
                        <table id="structure_table" width="80%"></table>
                    </div>

                    <form onsubmit="return false;">
                        <fieldset>
                            <p>
                                <input class="button" type="button" onclick="move2Left();" value="<" />
                                <input class="button" type="button" onclick="move2Right();" value=">" />
                                <input class="button" type="button" onclick="deleteNode();" value="删除" /><br>
                                <select id="type1">
                                    <option value="1">公司</option>
                                    <option value="2">部门</option>
                                    <option value="3">职位</option>
                                </select>
                                <span>节点名称</span>&nbsp;&nbsp;
                                <input class="text-input small-input" type="text" id="name1"/>&nbsp;&nbsp;
                                <input class="button" type="button" onclick="addNode();" value="新增" /><br>
                                <select id="type2">
                                    <option value="1">公司</option>
                                    <option value="2">部门</option>
                                    <option value="3">职位</option>
                                </select>
                                <span>节点名称</span>&nbsp;&nbsp;
                                <input class="text-input small-input" type="text" id="name2"/>&nbsp;&nbsp;
                                <input class="button" type="button" onclick="updateNode();" value="修改" />
                            </p>
                        </fieldset>
                        <div class="clear"></div>
                    </form>
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