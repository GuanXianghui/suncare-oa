<%@ page import="com.gxx.oa.entities.Diary" %>
<%@ page import="com.gxx.oa.dao.DiaryDao" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page import="com.gxx.oa.entities.DiaryReview" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gxx.oa.interfaces.DiaryReviewInterface" %>
<%@ page import="com.gxx.oa.dao.DiaryReviewDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //权限校验
    if(!BaseUtil.checkRight(user.getId(), UserRightInterface.RIGHT_0010_DIARY)){
        //域名链接
        response.sendRedirect(baseUrl + "index.jsp");
        return;
    }
    //外层
    outLayer = "工作模块";
    //内层
    inLayer = "工作日志";
    //判入参id合法性
    int id;
    try{
        id = Integer.parseInt(request.getParameter("id"));
    } catch (Exception e){
        response.sendRedirect(baseUrl + "diary.jsp");
        return;
    }
    Diary diary = DiaryDao.getDiaryById(id);
    if(null == diary){
        response.sendRedirect(baseUrl + "diary.jsp");
        return;
    }
    //工作日志所属用户
    User diaryUser = UserDao.getUserById(diary.getUserId());
    //工作日志回复 包括点赞和留言
    List<DiaryReview> diaryReviews = DiaryReviewDao.queryDiaryReviews(diary.getId());
%>
<html>
<head>
    <title>查看站内信</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/showDiary.js"></script>
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
        //工作日志id
        var diaryId = <%=id%>;
    </script>
    <style type="text/css">
        td{
            vertical-align: middle;
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

        <%
            if(diary.getUserId() == user.getId()){
        %>
        <ul class="shortcut-buttons-set">
            <li>
                <a class="shortcut-button" href="javascript: beforeUpdateDiary()">
                    <span>
                        <img src="images/icons/paper_content_pencil_48.png" alt="icon"/>
                        <br/>修改
                    </span>
                </a>
            </li>
            <li>
                <a class="shortcut-button" href="javascript: deleteDiary()">
                    <span>
                        <img src="images/icons/image_add_48.png" alt="icon"/>
                        <br/>删除
                    </span>
                </a>
            </li>
        </ul>
        <%
            }
        %>

        <div class="clear"></div>

        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>

            <div id="message_id_content"> 提示信息！</div>
        </div>

        <div class="content-box">
            <div class="content-box-header">
                <h3>查看日志</h3>
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
                                    <tr><td width="10%">日志日期：</td><td><b><input class="text-input small-input" type="text" value="<%=diary.getDate()%>" disabled="disabled"/></b></td></tr>
                                    <tr><td>用户：</td><td><a href="<%=baseUrl%>user.jsp?id=<%=diaryUser.getId()%>" target="_blank"><%=diaryUser.getName()%></a></td></tr>
                                    <tr><td>创建时间：</td><td><input class="text-input small-input" disabled="disabled" type="text" value="<%=diary.getCreateDate()%> <%=diary.getCreateTime()%>"/></td></tr>
                                    <%
                                        if(StringUtils.isNotBlank(diary.getUpdateDate())){
                                    %>
                                    <tr><td>修改日期：</td><td><input class="text-input small-input" type="text" value="<%=diary.getUpdateDate()%>"/></td></tr>
                                    <%
                                        }
                                    %>
                                    <tr><td>内容：</td><td>
                                        <div id="showContent" style="overflow: auto;">
                                            <%=diary.getContent()%>
                                        </div>
                                        <div id="initContent" style="display: none;">
                                            <%=diary.getContent()%>
                                        </div>
                                    </td></tr>
                                    <tr>
                                        <td>点赞：</td>
                                        <td>
                                            <%
                                                int countZan = 0;//赞数量
                                                boolean hasZan = false;//没有点赞过
                                                for(DiaryReview diaryReview : diaryReviews){
                                                    //点赞
                                                    if(com.gxx.oa.interfaces.DiaryReviewInterface.TYPE_ZAN != diaryReview.getType()){
                                                        continue;
                                                    }
                                                    countZan ++;
                                                    User tempUser = UserDao.getUserById(diaryReview.getUserId());
                                                    if(tempUser.getId() == user.getId()){
                                                        hasZan = true;
                                                    }
                                            %>
                                            <a target="_blank" href="<%=baseUrl%>user.jsp?id=<%=diaryReview.getUserId()%>">
                                                <img width="54px" src="<%=tempUser.getHeadPhoto()%>"></a>
                                            <%
                                                }
                                            %>
                                            <%
                                                if(hasZan){//点过赞
                                            %>
                                            <input class="button" type="button" onclick="cancelZan()" value="取消赞" />
                                            <%
                                            } else {//没有点过赞
                                            %>
                                            <input class="button" type="button" onclick="clickZan()" value="点赞" />
                                            <%
                                                }
                                            %>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>评论：</td>
                                        <td>
                                            <input class="button" type="button" onclick="beforeReview()" value="评论" />
                                        </td>
                                    </tr>
                                    <tr <%=diaryReviews.size()==countZan?"style='display: none;'":""%>>
                                        <td colspan="2">
                                            <table>
                                                <%
                                                    for(DiaryReview diaryReview : diaryReviews){
                                                        //不是点赞，而是留言或者回复
                                                        if(com.gxx.oa.interfaces.DiaryReviewInterface.TYPE_ZAN == diaryReview.getType()){
                                                            continue;
                                                        }
                                                        boolean isReply = false;
                                                        User repliedUser = null;
                                                        if(DiaryReviewInterface.TYPE_REPLY == diaryReview.getType()){
                                                            isReply = true;
                                                            repliedUser = UserDao.getUserById(diaryReview.getRepliedUserId());
                                                        }
                                                        User tempUser = UserDao.getUserById(diaryReview.getUserId());
                                                %>
                                                <tr>
                                                    <td width="10%">
                                                        <a target="_blank" href="<%=baseUrl%>user.jsp?id=<%=diaryReview.getUserId()%>">
                                                            <img width="54px" src="<%=tempUser.getHeadPhoto()%>"></a>
                                                    </td>
                                                    <td width="10%" id="review_desc_<%=diaryReview.getId()%>"><b><%=tempUser.getName()%>：</b><%=isReply?"回复<b>" + repliedUser.getName() + "</b>：":""%></td>
                                                    <td width="50%" id="review_content_<%=diaryReview.getId()%>"><%=diaryReview.getContent()%></td>
                                                    <td width="10%"><%=diaryReview.getCreateDate()%></td>
                                                    <td width="20%">
                                                        <input class="button" type="button" onclick="beforeReplyDiaryReview(<%=diaryReview.getId()%>)" value="回复" />
                                                        <%
                                                            if(tempUser.getId() == user.getId()){
                                                        %>
                                                        <input class="button" type="button" onclick="beforeUpdateDiaryReview(<%=diaryReview.getId()%>)" value="修改" />
                                                        <input class="button" type="button" onclick="deleteDiaryReview(<%=diaryReview.getId()%>)" value="删除" />
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
                                            <span id="review_desc">你的评语：</span><input class="text-input small-input" type="text" id="review_content"/>
                                            <input class="button" type="button" onclick="review()" value="提交" />
                                            <input class="button" type="button" onclick="cancelReview()" value="取消" />
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>

                        <div id="updateDiv" style="display:none;">
                            <form name="updateDiaryForm" action="<%=baseUrl%>updateDiary.do" method="post">
                                <table>
                                    <input type="hidden" id="token" name="token" value="<%=token%>">
                                    <input type="hidden" id="diaryId" name="diaryId" value="<%=diary.getId()%>">
                                    <tr><td>日期:</td><td><input class="text-input small-input" type="text" id="date" name="date" value="<%=diary.getDate()%>"/></td></tr>
                                    <textarea style="display: none;" id="content" name="content"></textarea>
                                </table>
                            </form>
                            <script id="editor" type="text/plain"></script>
                            <input class="button" type="button" onclick="updateDiary()" value="修改" />
                            <input class="button" type="button" onclick="cancelUpdateDiary()" value="取消" />
                        </div>
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