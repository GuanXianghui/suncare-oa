<%@ page import="com.gxx.oa.entities.Diary" %>
<%@ page import="com.gxx.oa.dao.DiaryDao" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page import="com.gxx.oa.entities.DiaryReview" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gxx.oa.interfaces.DiaryReviewInterface" %>
<%@ page import="com.gxx.oa.dao.DiaryReviewDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<%
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
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript">
        //工作日志id
        var diaryId = <%=id%>;
    </script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>查看站工作日志<button onclick="logOut()">退出</button></h1>
        <div>
            <%
                if(diary.getUserId() == user.getId()){
            %>
            <button onclick="beforeUpdateDiary()">修改</button>
            <button onclick="deleteDiary()">删除</button>
            <%
                }
            %>
        </div>
        <div>
            <div id="showDiv">
                <div>日志日期：<b><%=diary.getDate()%></b></div>
                <div>用户：<a href="<%=baseUrl%>user.jsp?id=<%=diaryUser.getId()%>" target="_blank"><%=diaryUser.getName()%></a></div>
                <div>创建日期：<%=diary.getCreateDate()%></div>
                <%
                    if(StringUtils.isNotBlank(diary.getUpdateDate())){
                %>
                <div>修改日期：<%=diary.getUpdateDate()%></div>
                <%
                    }
                %>
                <div id="showContent">
                    <%=diary.getContent()%>
                </div>
                <div id="initContent" style="display: none"><%=diary.getContent()%></div>
                <div>
                    <div>
                        点赞：
                        <%
                            boolean hasZan = false;//没有点赞过
                            for(DiaryReview diaryReview : diaryReviews){
                                //点赞
                                if(com.gxx.oa.interfaces.DiaryReviewInterface.TYPE_ZAN != diaryReview.getType()){
                                    continue;
                                }
                                User tempUser = UserDao.getUserById(diaryReview.getUserId());
                                if(tempUser.getId() == user.getId()){
                                    hasZan = true;
                                }
                        %>
                            <a target="_blank" href="<%=baseUrl%>user.jsp?id=<%=diaryReview.getUserId()%>"><img width="54px" src="<%=tempUser.getHeadPhoto()%>"></a>
                        <%
                            }
                        %>
                        <%
                            if(hasZan){//点过赞
                        %>
                        <button onclick="cancelZan()">取消赞</button>
                        <%
                        } else {//没有点过赞
                        %>
                        <button onclick="clickZan()">点赞</button>
                        <%
                            }
                        %>
                    </div>
                    <div>
                        评论： <button onclick="beforeReview()">评论</button>
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
                                <td>
                                    <a target="_blank" href="<%=baseUrl%>user.jsp?id=<%=diaryReview.getUserId()%>">
                                    <img width="54px" src="<%=tempUser.getHeadPhoto()%>"></a>
                                </td>
                                <td id="review_desc_<%=diaryReview.getId()%>"><b><%=tempUser.getName()%>：</b><%=isReply?"回复<b>" + repliedUser.getName() + "</b>：":""%></td>
                                <td id="review_content_<%=diaryReview.getId()%>"><%=diaryReview.getContent()%></td>
                                <td><%=diaryReview.getCreateDate()%></td>
                                <td>
                                    <button onclick="beforeReplyDiaryReview(<%=diaryReview.getId()%>)">回复</button>
                                    <%
                                        if(tempUser.getId() == user.getId()){
                                    %>
                                    <button onclick="beforeUpdateDiaryReview(<%=diaryReview.getId()%>)">修改</button>
                                    <button onclick="deleteDiaryReview(<%=diaryReview.getId()%>)">删除</button>
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
                <form name="updateDiaryForm" action="<%=baseUrl%>updateDiary.do" method="post">
                    <table>
                        <input type="hidden" id="token" name="token" value="<%=token%>">
                        <input type="hidden" id="diaryId" name="diaryId" value="<%=diary.getId()%>">
                        <tr><td>日期:</td><td><input type="text" id="date" name="date" value="<%=diary.getDate()%>"></td></tr>
                        <textarea style="display: none;" id="content" name="content"></textarea>
                    </table>
                </form>
                <script id="editor" type="text/plain" style="width:1024px;height:300px;"></script>
                <input type="button" value="修改" onclick="updateDiary()">
                <input type="button" value="取消" onclick="cancelUpdateDiary()">
            </div>
        </div>
    </div>
</body>
</html>
<%}%>