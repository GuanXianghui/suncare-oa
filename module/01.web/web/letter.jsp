<%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
<%@ page import="com.gxx.oa.dao.LetterDao" %>
<%@ page import="com.gxx.oa.interfaces.UserInterface" %>
<%@ page import="com.gxx.oa.interfaces.LetterInterface" %>
<%@ page import="com.gxx.oa.utils.BaseUtil" %>
<%@ page import="com.gxx.oa.utils.PropertyUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){
    String box = request.getParameter("box");
    int type;
    if(StringUtils.equals(box, LetterInterface.BOX_SENT)){
        type = LetterInterface.TYPE_SENT;
    } else if(StringUtils.equals(box, LetterInterface.BOX_DELETED)){
        type = LetterInterface.TYPE_DELETED;
    } else {
        type = LetterInterface.TYPE_RECEIVED;
        box = LetterInterface.BOX_RECEIVED;
    }
%>
<html>
<head>
    <title>站内信</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/letter.js"></script>
    <script type="text/javascript">
        //标识现在是收件箱，已发送还是已删除
        var box = "<%=box%>";
        /**
         * 消息Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var letterJsonStr = "<%=BaseUtil.getJsonArrayFromLetters(LetterDao.queryLettersByTypeAndFromTo(user.getId(),
                        UserInterface.USER_TYPE_NORMAL, type, 0, Integer.parseInt(PropertyUtil.getInstance().
                        getProperty(BaseInterface.LETTER_PAGE_SIZE))), box).replaceAll("\\\"", "\\\\\\\"").
                        replaceAll(SymbolInterface.SYMBOL_NEW_LINE, PropertyUtil.getInstance().
                        getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //根据用户id和用户类型和查询类型 查总共站内信的量
        var letterCount = <%=LetterDao.countLettersByType(user.getId(), UserInterface.USER_TYPE_NORMAL, type)%>;
        //选择站内信ids
        var chooseLetterIds = EMPTY;
    </script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>站内信(<%=box%>)<button onclick="logOut()">退出</button></h1>
        <div>
            <a href="<%=baseUrl%>writeLetter.jsp">写信</a>
            <a href="<%=baseUrl%>letter.jsp">收件箱</a>
            <a href="<%=baseUrl%>letter.jsp?box=<%=LetterInterface.BOX_SENT%>">已发送</a>
            <a href="<%=baseUrl%>letter.jsp?box=<%=LetterInterface.BOX_DELETED%>">已删除</a>
        </div>
        <%
            if(type == LetterInterface.TYPE_RECEIVED){
        %>
        <div>
            <button onclick="deleteLetter()">删除</button>
            <button onclick="ctrlDeleteLetter()">彻底删除</button>
            <button onclick="transmit()">转发</button>
            <button onclick="setReaded()">标记成已读</button>
        </div>
        <%
            } else if(type == LetterInterface.TYPE_SENT) {
        %>
        <div>
            <button onclick="deleteLetter()">删除</button>
            <button onclick="ctrlDeleteLetter()">彻底删除</button>
            <button onclick="transmit()">转发</button>
        </div>
        <%
            } else if(type == LetterInterface.TYPE_DELETED) {
        %>
        <div>
            <button onclick="ctrlDeleteLetter()">彻底删除</button>
            <button onclick="transmit()">转发</button>
            <button onclick="setReaded()">标记成已读</button>
            <button onclick="restore()">还原</button>
        </div>
        <%
            }
        %>
        <%--<div>--%>
            <%--<button onclick="">回复</button>--%>
            <%--<button onclick="">删除</button>--%>
            <%--<button onclick="">彻底删除</button>--%>
            <%--<button onclick="">转发</button>--%>
            <%--<button onclick="">标记成已读</button>--%>
            <%--<button onclick="">还原</button>--%>
            <%--<button onclick="">关闭</button>--%>
        <%--</div>--%>
        <table id="letter_table" border="1" width="80%" style="text-align: center"></table>
    </div>
    <div align="center">
        <div id="nextPageDiv" style="display: none; width: 80%; text-align: center; border: 1px solid gray;">
            <button onclick="showNextPageLetters()">加载下一页</button>
        </div>
    </div>
</body>
</html>
<%}%>