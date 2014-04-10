<%@ page import="com.gxx.oa.dao.TaskDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<%
    //如果fromUserId大于0带上该条件
    int fromUserId;//任务来源用户id
    try{
        fromUserId = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("fromUserId")));
    } catch (Exception e){
        fromUserId = 0;
    }
    //如果toUserId大于0带上该条件
    int toUserId;//任务接受用户id
    try{
        toUserId = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("toUserId")));
    } catch (Exception e){
        toUserId = 0;
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
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript">
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
    </script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>任务<button onclick="logOut()">退出</button></h1>
        <div>
            <input type="button" value="分配任务" onclick="location.href='<%=baseUrl%>writeTask.jsp'">
        </div>
        <div>
            任务来源用户id:<input type="text" id="fromUserId" value="<%=fromUserId>0?fromUserId:""%>">
            任务接受用户id:<input type="text" id="toUserId" value="<%=toUserId>0?toUserId:""%>">
            状态:<input type="text" id="state" value="<%=state>0?state:""%>">
            <input type="button" value="选择" onclick="selectTask()">
        </div>
        <table id="task_table" border="1" width="80%" style="text-align: center"></table>
    </div>
    <div align="center">
        <div id="nextPageDiv" style="display: none; width: 80%; text-align: center; border: 1px solid gray;">
            <button onclick="showNextPageTasks()">加载下一页</button>
        </div>
    </div>
</body>
</html>
<%}%>