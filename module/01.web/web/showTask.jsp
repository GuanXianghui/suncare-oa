<%@ page import="java.util.List" %>
<%@ page import="com.gxx.oa.dao.*" %>
<%@ page import="com.gxx.oa.entities.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.gxx.oa.interfaces.TaskReviewInterface" %>
<%@ page import="com.gxx.oa.interfaces.TaskInterface" %>
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
    <%--<script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript">
        //任务id
        var taskId = <%=id%>;
        //任务接受用户id
        var toUserId = <%=task.getToUserId()%>;
        <%
            //有权限看的下级用户 用逗号隔开
            String rightUserWithComma = BaseUtil.getLowerLevelPositionUserIdWithComma(user.getPosition());
        %>
        //有权限看的下级用户 用逗号隔开
        var rightUserWithComma = "<%=rightUserWithComma%>";
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
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

    <ul class="shortcut-buttons-set">
        <%
            //任务来源和接受任务的人可以修改任务
            if(task.getFromUserId() == user.getId() || task.getToUserId() == user.getId()){
        %>
        <li>
            <a class="shortcut-button" href="javascript: beforeUpdateTask()">
                    <span>
                        <img src="images/icons/paper_content_pencil_48.png" alt="icon"/>
                        <br/>修改
                    </span>
            </a>
        </li>
        <%
            }
        %>
        <%
            //只有创建的人可以删除任务
            if(task.getFromUserId() == user.getId()){
        %>
        <li>
            <a class="shortcut-button" href="javascript: deleteTask()">
                    <span>
                        <img src="images/icons/image_add_48.png" alt="icon"/>
                        <br/>删除
                    </span>
            </a>
        </li>
        <%
            }
        %>

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
            <h3>查看任务</h3>
            <ul class="content-box-tabs">
                <li><a href="#tab2" class="default-tab">Forms</a></li>
            </ul>
            <div class="clear"></div>
        </div>
        <div class="content-box-content">
            <div class="tab-content default-tab" id="tab2">
                <div>
                    <div id="showDiv">
                        <form>
                            <table>
                                <tr><td width="10%">任务名称：</td><td><%=task.getTitle()%></td></tr>
                                <tr><td>任务来源用户：</td><td><a href="<%=baseUrl%>user.jsp?id=<%=task.getFromUserId()%>" target="_blank"><%=fromUser.getName()%></a></td></tr>
                                <tr><td>任务接受用户：</td><td><a href="<%=baseUrl%>user.jsp?id=<%=task.getToUserId()%>" target="_blank"><%=toUser.getName()%></a></td></tr>
                                <tr>
                                    <td>状态：</td>
                                    <td>
                                        <b><%=task.getStateDesc()%></b>
                                        <%
                                            //只有
                                            if(task.getFromUserId() == user.getId() || task.getToUserId() == user.getId()){
                                                if(task.getState() == TaskInterface.STATE_NEW){
                                        %>
                                        <input class="button" type="button" onclick="updateTaskState(<%=TaskInterface.STATE_ING%>)" value="开始任务" />
                                        <%
                                            } if(task.getState() == TaskInterface.STATE_ING){
                                        %>
                                        <input class="button" type="button" onclick="updateTaskState(<%=TaskInterface.STATE_DONE%>)" value="完成任务" />
                                        <input class="button" type="button" onclick="updateTaskState(<%=TaskInterface.STATE_DROP%>)" value="废除任务" />
                                        <%
                                            } if(task.getState() == TaskInterface.STATE_DONE){
                                        %>
                                        <input class="button" type="button" onclick="updateTaskState(<%=TaskInterface.STATE_ING%>)" value="重新开始任务" />
                                        <%
                                            } if(task.getState() == TaskInterface.STATE_DROP){
                                        %>
                                        <input class="button" type="button" onclick="updateTaskState(<%=TaskInterface.STATE_ING%>)" value="重新开始任务" />
                                        <%
                                                }
                                            }
                                        %>
                                    </td>
                                </tr>
                                <tr><td>开始日期：</td><td><b><%=task.getBeginDate()%></b></td></tr>
                                <tr><td>结束日期：</td><td><b><%=task.getEndDate()%></b></td></tr>
                                <tr><td>创建日期：</td><td><b><%=task.getCreateDate()%></b></td></tr>
                                <%
                                    if(StringUtils.isNotBlank(task.getUpdateDate())){
                                %>
                                <tr><td>修改日期：</td><td><b><%=task.getUpdateDate()%></b></td></tr>
                                <%
                                    }
                                %>

                                <tr><td>内容：</td><td>
                                    <div id="showContent" style="overflow: auto;">
                                        <%=task.getContent()%>
                                    </div>
                                    <div id="initContent" style="display: none;">
                                        <%=task.getContent()%>
                                    </div>
                                </td></tr>
                                <tr>
                                    <td>催：</td>
                                    <td>
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
                                        <input class="button" type="button" onclick="cui()" value="催" />
                                    </td>
                                </tr>
                                <tr>
                                    <td>评论：</td>
                                    <td>
                                        <input class="button" type="button" onclick="beforeReview()" value="评论" />
                                    </td>
                                </tr>
                                <tr <%//=diaryReviews.size()==countZan?"style='display: none;'":""%>>
                                    <td colspan="2">
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
                                                <td width="10%">
                                                    <a target="_blank" href="<%=baseUrl%>user.jsp?id=<%=taskReview.getUserId()%>">
                                                        <img width="54px" src="<%=tempUser.getHeadPhoto()%>"></a>
                                                </td>
                                                <td width="10%" id="review_desc_<%=taskReview.getId()%>"><b><%=tempUser.getName()%>：</b><%=isReply?"回复<b>" + repliedUser.getName() + "</b>：":""%></td>
                                                <td width="50%" id="review_content_<%=taskReview.getId()%>"><%=taskReview.getContent()%></td>
                                                <td width="10%"><%=taskReview.getCreateDate()%></td>
                                                <td width="20%">
                                                    <input class="button" type="button" onclick="beforeReplyTaskReview(<%=taskReview.getId()%>)" value="回复" />
                                                    <%
                                                        if(tempUser.getId() == user.getId()){
                                                    %>
                                                    <input class="button" type="button" onclick="beforeUpdateTaskReview(<%=taskReview.getId()%>)" value="修改" />
                                                    <input class="button" type="button" onclick="deleteTaskReview(<%=taskReview.getId()%>)" value="删除" />
                                                    <%
                                                        }
                                                    %>
                                                </td>
                                            </tr>
                                            <%
                                                }
                                            %>
                                        </table>
                                    </td>
                                </tr>
                                <tr id="review_div" style="display: none;">
                                    <td colspan="2">
                                        <span id="review_desc">你的评语：</span><input class="text-input small-input" type="text" id="review_content">
                                        <input class="button" type="button" onclick="review()" value="提交" />
                                        <input class="button" type="button" onclick="cancelReview()" value="取消" />
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>

                <div id="updateDiv" style="display:none;">
                    <form name="updateTaskForm" action="<%=baseUrl%>updateTask.do" method="post">
                        <table>
                            <input type="hidden" id="token" name="token" value="<%=token%>">
                            <input type="hidden" id="taskId" name="taskId" value="<%=task.getId()%>">
                            <tr>
                                <td width="15%">任务接受用户:</td>
                                <td>
                                    <select id="toUserId" name="toUserId" class="medium-input">
                                        <option value="">选择用户</option>
                                    </select>
                                </td>
                            </tr>
                            <tr><td>任务名称:</td><td><input class="text-input medium-input" type="text" id="title" name="title" value="<%=task.getTitle()%>"/></td></tr>
                            <tr><td>开始日期:</td><td><input class="text-input medium-input" type="text" id="beginDate" name="beginDate" value="<%=task.getBeginDate()%>"/></td></tr>
                            <tr><td>结束日期:</td><td><input class="text-input medium-input" type="text" id="endDate" name="endDate" value="<%=task.getEndDate()%>"/></td></tr>
                            <textarea style="display: none;" id="content" name="content"></textarea>
                        </table>
                    </form>
                    <script id="editor" type="text/plain"></script>
                    <input class="button" type="button" onclick="updateTask()" value="修改" />
                    <input class="button" type="button" onclick="cancelUpdateTask()" value="取消" />
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