<%@ page import="java.util.List" %>
<%@ page import="com.gxx.oa.dao.*" %>
<%@ page import="com.gxx.oa.entities.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.gxx.oa.interfaces.TaskReviewInterface" %>
<%@ page import="com.gxx.oa.interfaces.TaskInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<%
    //判入参id合法性
    int id;
    try{
        id = Integer.parseInt(request.getParameter("id"));
    } catch (Exception e){
        response.sendRedirect(baseUrl + "task.jsp");
        return;
    }
    Task task = TaskDao.getTaskById(id);
    if(null == task){
        response.sendRedirect(baseUrl + "task.jsp");
        return;
    }
    //任务来源和接受用户
    User fromUser = UserDao.getUserById(task.getFromUserId());
    User toUser = UserDao.getUserById(task.getToUserId());
    //根据任务id查 被催和非被催 任务评论
    List<TaskReview> cuiTaskReviews = TaskReviewDao.queryCuiTaskReviews(task.getId());
    List<TaskReview> notCuiTaskReviews = TaskReviewDao.queryNotCuiTaskReviews(task.getId());
%>
<html>
<head>
    <title>查看任务</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/showTask.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript">
        //任务id
        var taskId = <%=id%>;
    </script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>查看任务<button onclick="logOut()">退出</button></h1>
        <div>
            <%
                //任务来源和接受任务的人可以修改任务
                if(task.getFromUserId() == user.getId() || task.getToUserId() == user.getId()){
            %>
            <button onclick="beforeUpdateTask()">修改</button>
            <%
                }
            %>
            <%
                //只有创建的人可以删除任务
                if(task.getFromUserId() == user.getId()){
            %>
            <button onclick="deleteTask()">删除</button>
            <%
                }
            %>
        </div>
        <div>
            <div id="showDiv">
                <div>任务名称：<b><%=task.getTitle()%></b></div>
                <div>任务来源用户：<a href="<%=baseUrl%>user.jsp?id=<%=task.getFromUserId()%>" target="_blank"><%=fromUser.getName()%></a></div>
                <div>任务接受用户：<a href="<%=baseUrl%>user.jsp?id=<%=task.getToUserId()%>" target="_blank"><%=toUser.getName()%></a></div>
                <div>
                    状态：<b><%=task.getStateDesc()%></b>
                    <%
                        //只有
                        if(task.getFromUserId() == user.getId() || task.getToUserId() == user.getId()){
                            if(task.getState() == TaskInterface.STATE_NEW){
                    %>
                    <button onclick="updateTaskState(<%=TaskInterface.STATE_ING%>)">开始任务</button>
                    <%
                            } if(task.getState() == TaskInterface.STATE_ING){
                    %>
                    <button onclick="updateTaskState(<%=TaskInterface.STATE_DONE%>)">完成任务</button>
                    <button onclick="updateTaskState(<%=TaskInterface.STATE_DROP%>)">废除任务</button>
                    <%
                            } if(task.getState() == TaskInterface.STATE_DONE){
                    %>
                    <button onclick="updateTaskState(<%=TaskInterface.STATE_ING%>)">重新开始任务</button>
                    <%
                            } if(task.getState() == TaskInterface.STATE_DROP){
                    %>
                    <button onclick="updateTaskState(<%=TaskInterface.STATE_ING%>)">重新开始任务</button>
                    <%
                            }
                        }
                    %>
                </div>
                <div>开始日期：<b><%=task.getBeginDate()%></b></div>
                <div>结束日期：<b><%=task.getEndDate()%></b></div>
                <div>创建日期：<%=task.getCreateDate()%></div>
                <%
                    if(StringUtils.isNotBlank(task.getUpdateDate())){
                %>
                <div>修改日期：<%=task.getUpdateDate()%></div>
                <%
                    }
                %>
                <div id="showContent">
                    <%=task.getContent()%>
                </div>
                <div id="initContent" style="display: none"><%=task.getContent()%></div>
                <div>
                    <div>
                        催：
                        <%
                            //催过的用户id，已经催过的不重复显示
                            List<Integer> cuiUserIdList = new ArrayList<Integer>();
                            for(TaskReview taskReview : cuiTaskReviews){
                                if(cuiUserIdList.contains(new Integer(taskReview.getUserId()))){
                                    continue;
                                }
                                cuiUserIdList.add(new Integer(taskReview.getUserId()));
                                User tempUser = UserDao.getUserById(taskReview.getUserId());
                        %>
                            <a target="_blank" href="<%=baseUrl%>user.jsp?id=<%=taskReview.getUserId()%>"><img width="54px" src="<%=tempUser.getHeadPhoto()%>"></a>
                        <%
                            }
                        %>
                        <button onclick="cui()">催</button>
                    </div>
                    <div>
                        评论： <button onclick="beforeReview()">评论</button>
                        <table>
                            <%
                                for(TaskReview taskReview : notCuiTaskReviews){
                                    boolean isReply = false;
                                    User repliedUser = null;
                                    if(TaskReviewInterface.TYPE_REPLY == taskReview.getType()){
                                        isReply = true;
                                        repliedUser = UserDao.getUserById(taskReview.getRepliedUserId());
                                    }
                                    User tempUser = UserDao.getUserById(taskReview.getUserId());
                            %>
                            <tr>
                                <td>
                                    <a target="_blank" href="<%=baseUrl%>user.jsp?id=<%=taskReview.getUserId()%>">
                                    <img width="54px" src="<%=tempUser.getHeadPhoto()%>"></a>
                                </td>
                                <td id="review_desc_<%=taskReview.getId()%>"><b><%=tempUser.getName()%>：</b><%=isReply?"回复<b>" + repliedUser.getName() + "</b>：":""%></td>
                                <td id="review_content_<%=taskReview.getId()%>"><%=taskReview.getContent()%></td>
                                <td><%=taskReview.getCreateDate()%></td>
                                <td>
                                    <button onclick="beforeReplyTaskReview(<%=taskReview.getId()%>)">回复</button>
                                    <%
                                        if(tempUser.getId() == user.getId()){
                                    %>
                                    <button onclick="beforeUpdateTaskReview(<%=taskReview.getId()%>)">修改</button>
                                    <button onclick="deleteTaskReview(<%=taskReview.getId()%>)">删除</button>
                                    <%
                                        }
                                    %>
                                </td>
                            </tr>
                            <%
                                }
                            %>
                        </table>
                        <div id="review_div" style="display: none;">
                            <span id="review_desc">你的评语：</span><input type="text" id="review_content">
                            <button onclick="review()">提交</button><button onclick="cancelReview()">取消</button>
                        </div>
                    </div>
                </div>
            </div>

            <div id="updateDiv" style="display:none; border: 1px solid gray; width: 80%;">
                <form name="updateTaskForm" action="<%=baseUrl%>updateTask.do" method="post">
                    <table>
                        <input type="hidden" id="token" name="token" value="<%=token%>">
                        <input type="hidden" id="taskId" name="taskId" value="<%=task.getId()%>">
                        <tr><td>任务接受用户:</td><td><input type="text" id="toUserId" name="toUserId" value="<%=task.getToUserId()%>"></td></tr>
                        <tr><td>任务名称:</td><td><input type="text" id="title" name="title" value="<%=task.getTitle()%>"></td></tr>
                        <tr><td>开始日期:</td><td><input type="text" id="beginDate" name="beginDate" value="<%=task.getBeginDate()%>"></td></tr>
                        <tr><td>结束日期:</td><td><input type="text" id="endDate" name="endDate" value="<%=task.getEndDate()%>"></td></tr>
                        <textarea style="display: none;" id="content" name="content"></textarea>
                    </table>
                </form>
                <script id="editor" type="text/plain" style="width:1024px;height:300px;"></script>
                <input type="button" value="修改" onclick="updateTask()">
                <input type="button" value="取消" onclick="cancelUpdateTask()">
            </div>
        </div>
    </div>
</body>
</html>
<%}%>