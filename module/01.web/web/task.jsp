<%@ page import="com.gxx.oa.dao.TaskDao" %>
<%@ page import="com.gxx.oa.interfaces.TaskInterface" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //权限校验
    if(!BaseUtil.checkRight(user.getId(), UserRightInterface.RIGHT_0012_TASK)){
        //域名链接
        response.sendRedirect(baseUrl + "index.jsp");
        return;
    }
    //外层
    outLayer = "工作模块";
    //内层
    inLayer = "任务";
    //类型
    int type;
    try{
        type = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("type")));
    } catch (Exception e){
        type = TaskInterface.TYPE_TO_ME;//分配给我的
    }

    //用户id
    int userId;
    try{
        userId = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("userId")));
    } catch (Exception e){
        userId = 0;
    }

    int fromUserId;
    int toUserId;
    if(type == TaskInterface.TYPE_TO_ME){
        fromUserId = userId;
        toUserId = user.getId();
    } else if(type == TaskInterface.TYPE_FROM_ME){
        fromUserId = user.getId();
        toUserId = userId;
    } else {
        response.sendRedirect(baseUrl + "task.jsp");
        return;
    }

    //如果state大于0带上该条件
    int state;//状态
    try{
        state = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("state")));
    } catch (Exception e){
        state = 0;
    }
%>
<html>
<head>
    <title>任务</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/task.js"></script>
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
        //选择用户id
        var userId = <%=userId%>;
        //任务来源用户id
        var fromUserId = <%=fromUserId%>;
        //任务接受用户id
        var toUserId = <%=toUserId%>;
        //状态
        var state = <%=state%>;
        /**
         * 任务Json串
         */
        var taskJsonStr = "<%=BaseUtil.getJsonArrayFromTasks(TaskDao.queryTasksByFromTo(fromUserId, toUserId, state, 0,
                        Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.TASK_PAGE_SIZE))))%>";
        //任务总数
        var taskCount = <%=TaskDao.countTasksByUserIdAndState(fromUserId, toUserId, state)%>;
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
        <%@ include file="layers.jsp" %>
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
            <h3>任务</h3>
            <ul class="content-box-tabs">
                <li><a href="#tab2" class="default-tab">Forms</a></li>
            </ul>
            <div class="clear"></div>
        </div>
        <div class="content-box-content">
            <div class="tab-content default-tab" id="tab2">
                <form>
                    类型:
                    <select id="type" class="small-input">
                        <option value="<%=TaskInterface.TYPE_TO_ME%>"<%=type == TaskInterface.TYPE_TO_ME?" selected":""%>>分配给我的</option>
                        <option value="<%=TaskInterface.TYPE_FROM_ME%>"<%=type == TaskInterface.TYPE_FROM_ME?" selected":""%>>我分配的</option>
                    </select>
                    用户:
                    <select name="userId" id="userId" class="small-input">
                        <option value="">全部用户</option>
                    </select>
                    状态:
                    <select id="state" class="small-input">
                        <option value="">全部</option>
                        <option value="<%=TaskInterface.STATE_NEW%>"<%=state == TaskInterface.STATE_NEW?" selected":""%>>新建</option>
                        <option value="<%=TaskInterface.STATE_ING%>"<%=state == TaskInterface.STATE_ING?" selected":""%>>进行中</option>
                        <option value="<%=TaskInterface.STATE_DONE%>"<%=state == TaskInterface.STATE_DONE?" selected":""%>>完成</option>
                        <option value="<%=TaskInterface.STATE_DROP%>"<%=state == TaskInterface.STATE_DROP?" selected":""%>>废除</option>
                    </select>
                    <input class="button" type="button" onclick="selectTask()" value="选择" />
                    <table id="task_table"></table>
                    <div align="center">
                        <div id="nextPageDiv" style="display: none;">
                            <input class="button" type="button" onclick="showNextPageTasks()" value="加载下一页" />
                        </div>
                    </div>
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