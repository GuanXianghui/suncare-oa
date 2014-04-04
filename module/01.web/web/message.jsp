<%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
<%@ page import="com.gxx.oa.dao.MessageDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>消息</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/message.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript">
        /**
         * 消息Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var messageJsonStr = "<%=BaseUtil.getJsonArrayFromMessages(MessageDao.queryMessagesByUserIdAndFromTo(user.getId(),
                        0, Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.MESSAGE_PAGE_SIZE)))).
                        replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                        PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //根据用户id查总共消息的量
        var messageCount = <%=MessageDao.countAllMessagesByUserId(user.getId())%>;
    </script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>消息<button onclick="logOut()">退出</button></h1>
        <table id="message_table" border="1" width="80%" style="text-align: center"></table>
    </div>
    <div align="center">
        <div id="nextPageDiv" style="display: none; width: 80%; text-align: center; border: 1px solid gray;">
            <button onclick="showNextPageMessages()">加载下一页</button>
        </div>
        <div id="showMessageDiv" style="display: none; width: 80%; text-align: center; border: 1px solid gray;">
            <div id="showMessageContentDiv"></div>
        </div>
    </div>
</body>
</html>
<%}%>