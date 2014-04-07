<%@ page import="com.gxx.oa.interfaces.LetterInterface" %>
<%@ page import="com.gxx.oa.dao.LetterDao" %>
<%@ page import="com.gxx.oa.entities.Letter" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page import="com.gxx.oa.utils.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<%
    //判入参id合法性
    int id = 0;
    try{
        id = Integer.parseInt(request.getParameter("id"));
    } catch (Exception e){
        response.sendRedirect(baseUrl + "letter.jsp");
        return;
    }
    //判站内信是否属于该登陆用户
    Letter letter = LetterDao.getLetterById(id);
    if(letter.getUserId() != user.getId()){
        response.sendRedirect(baseUrl + "letter.jsp");
        return;
    }
    //如果未读，置成已读
    if(letter.getReadState() == LetterInterface.READ_STATE_NOT_READED){
        letter.setReadState(LetterInterface.READ_STATE_READED);
        letter.setOperateDate(DateUtil.getNowDate());
        letter.setOperateTime(DateUtil.getNowTime());
        letter.setOperateIp(IPAddressUtil.getIPAddress(request));
        LetterDao.updateLetter(letter);
    }
    User fromUser = UserDao.getUserById(letter.getFromUserId());
%>
<html>
<head>
    <title>查看站内信</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/showLetter.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript">
        //站内信id
        var letterId = <%=id%>;
    </script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>查看站内信<button onclick="logOut()">退出</button></h1>
        <div>
            <a href="<%=baseUrl%>writeLetter.jsp">写信</a>
            <a href="<%=baseUrl%>letter.jsp">收件箱</a>
            <a href="<%=baseUrl%>letter.jsp?box=<%=LetterInterface.BOX_SENT%>">已发送</a>
            <a href="<%=baseUrl%>letter.jsp?box=<%=LetterInterface.BOX_DELETED%>">已删除</a>
        </div>
        <div>
            <button onclick="reply()">回复</button>
            <button onclick="replyAll()">回复全部</button>
            <button onclick="transmit()">转发</button>
            <button onclick="deleteLetter()">删除</button>
            <button onclick="ctrlDeleteLetter()">彻底删除</button>
        </div>
        <div>
            <div><b><%=letter.getTitle()%></b></div>
            <div>发件人：<a href="<%=baseUrl%>user.jsp?id=<%=fromUser.getId()%>" target="_blank"><%=fromUser.getName()%></a></div>
            <div>时间：<%=letter.getCreateDate()%></div>
            <div>收件人：<%=letter.getToUserIds()%></div>
            <%
                if(StringUtils.isNotBlank(letter.getCcUserIds())){
            %>
                <div>抄送人：<%=letter.getCcUserIds()%></div>
            <%
                }
            %>
            <div id="editor">
                <%=letter.getContent()%>
            </div>
        </div>
    </div>
</body>
</html>
<%}%>