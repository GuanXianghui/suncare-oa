<%@ page import="com.gxx.oa.dao.DiaryDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<%
    int userId;
    try{
        userId = Integer.parseInt(StringUtils.trimToEmpty(request.getParameter("userId")));
    } catch (Exception e){
        userId = 0;
    }
    String date = StringUtils.trimToEmpty(request.getParameter("date"));
%>
<html>
<head>
    <title>工作日志</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/diary.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript">
        //塞选用户id
        var userId = <%=userId%>;
        //塞选日期
        var date = "<%=date%>";
        <%
            //有权限看的下级用户
            String rightUserWithComma = BaseUtil.getLowerLevelPositionUserIdWithComma(user.getPosition());
        %>
        /**
         * 工作日志Json串
         */
        var diaryJsonStr = "<%=BaseUtil.getJsonArrayFromDiaries(DiaryDao.queryDiariesByFromTo(userId, date, 0,
                        Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.DIARY_PAGE_SIZE)),
                         rightUserWithComma))%>";
        //工作日志总数
        var diaryCount = <%=DiaryDao.countDiaries(userId, date, rightUserWithComma)%>;
    </script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>工作日志<button onclick="logOut()">退出</button></h1>
        <div>
            <input type="button" value="写日志" onclick="location.href='<%=baseUrl%>writeDiary.jsp'">
        </div>
        <div>
            用户id:<input type="text" id="userId" value="<%=userId>0?userId:""%>">
            日期:<input type="text" id="date" value="<%=date%>">
            <input type="button" value="选择" onclick="selectDiary()">
        </div>
        <table id="diary_table" border="1" width="80%" style="text-align: center"></table>
    </div>
    <div align="center">
        <div id="nextPageDiv" style="display: none; width: 80%; text-align: center; border: 1px solid gray;">
            <button onclick="showNextPageDiaries()">加载下一页</button>
        </div>
    </div>
</body>
</html>
<%}%>