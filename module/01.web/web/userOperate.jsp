<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>后台管理用户</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/md5.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/pinyin.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/userOperate.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>css/userOperate.css"/>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="scripts/facebox.js"></script>
    <script type="text/javascript">
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
        //名字拼音缩写
        var letter = EMPTY;
        //职位id
        var positionId = 0;
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
                <li><a href="#" class="nav-top-item current"> 用户模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>userManage.jsp">用户管理</a></li>
                        <li><a href="<%=baseUrl%>user.jsp?id=<%=user.getId()%>">个人展示</a></li>
                        <li><a href="<%=baseUrl%>userOperate.jsp" class="current">后台用户管理</a></li>
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
                <li><a href="#" class="nav-top-item"> 工具模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>sms.jsp">短信</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>

    <div id="structure_div" align="center" style="width: 100%; display: none;">
        <h1>组织架构</h1>
        <div>
            <table id="structure_table" width="100%"></table>
        </div>
    </div>

    <div id="main-content">
        <ul class="shortcut-buttons-set">
            <li>
                <a class="shortcut-button" href="contacts.jsp">
                    <span>
                        <img src="images/icons/image_add_48.png" alt="icon"/>
                        <br/>查看用户
                    </span>
                </a>
            </li>
            <li>
                <a class="shortcut-button" href="javascript: chooseCreateUser();">
                    <span>
                        <img src="images/icons/paper_content_pencil_48.png" alt="icon"/>
                        <br/>创建用户
                    </span>
                </a>
            </li>
            <li>
                <a class="shortcut-button" href="javascript: chooseUpdateUser();">
                    <span>
                        <img src="images/icons/pencil_48.png" alt="icon"/>
                        <br/>修改用户
                    </span>
                </a>
            </li>
            <li style="display: none;">
                <a id="showStructureDiv" class="shortcut-button" href="#structure_div" rel="modal"></a>
            </li>
        </ul>

        <div class="clear"></div>

        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>

            <div id="message_id_content"> 提示信息！</div>
        </div>

        <div class="content-box" id="create_user_div" style="display: none;">
            <div class="content-box-header">
                <h3>创建用户</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab1" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab1">
                    <form onsubmit="return false;">
                        <fieldset>
                            <p>
                                <span>姓名</span>&nbsp;&nbsp;
                                <input class="text-input small-input" type="text" id="name"
                                       onchange="changeName()" onkeyup="changeName()"/>&nbsp;&nbsp;
                                <span>拼音缩写</span>&nbsp;&nbsp;
                                <input id="letter" class="text-input small-input" type="text" disabled="disabled"/>&nbsp;&nbsp;
                                <span>初始密码</span>&nbsp;&nbsp;
                                <input class="text-input small-input" type="text" disabled="disabled"
                                       value="<%=PropertyUtil.getInstance().getProperty(BaseInterface.DEFAULT_PASSWORD)%>"/><br>
                                <span>职位</span>&nbsp;&nbsp;
                                <input id="position_input" class="text-input small-input" type="text" disabled="disabled"/>&nbsp;&nbsp;
                                <input class="button" type="button" onclick="createOrUpdate='create';choosePosition();" value="选择职位" />
                                <input class="button" type="button" onclick="createUser();" value="创建" />
                            </p>
                        </fieldset>
                        <div class="clear"></div>
                    </form>
                </div>
            </div>
        </div>


        <div class="content-box" id="update_user_div" style="display: block;">
            <div class="content-box-header">
                <h3>修改用户</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab2" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab2">
                    <form onsubmit="return false;">
                        <fieldset>
                            <p>
                                <span>姓名</span>&nbsp;&nbsp;
                                <input class="text-input small-input" type="text" id="user_name"/>&nbsp;&nbsp;
                                <input class="button" type="button" onclick="queryUser();" value="查询" />(可输入缩写如：严明皓->ymh)
                            </p>
                            <table id="user_list"></table>
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
<%}%>