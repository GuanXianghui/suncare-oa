<%@ page import="com.gxx.oa.dao.NoticeDao" %>
<%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>公告管理</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/configNotice.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript">
        /**
         * 公告Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var noticeJsonStr = "<%=BaseUtil.getJsonArrayFromNotices(NoticeDao.queryNoticesByPage(1,
                        Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.NOTICE_PAGE_SIZE)))).
                        replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE,
                        PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //公告总数
        var noticeCount = <%=NoticeDao.countAllNotices()%>;
    </script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>公告管理<button onclick="logOut()">退出</button></h1>
        <table id="notice_table" border="1" width="80%" style="text-align: center"></table>
    </div>
    <div align="center">
        <div id="nextPageDiv" style="display: none; width: 80%; text-align: center; border: 1px solid gray;">
            <button onclick="showNextPageNotices()">加载下一页</button>
        </div>
        <div id="configNoticeDiv" style="display: none; border: 1px solid gray; width: 80%;">
            <form name="configNoticeForm" action="<%=baseUrl%>configNotice.do" method="post">
                <table>
                    <input type="hidden" id="token" name="token" value="<%=token%>">
                    <input id="type" type="hidden" name="type">
                    <input id="noticeId" type="hidden" name="noticeId">
                    <textarea style="display: none;" id="content" name="content"></textarea>
                    <tr><td>标题:</td><td><input type="text" id="title" name="title"></td></tr>
                </table>
            </form>
            <script id="editor" type="text/plain" style="width:1024px;height:300px;"></script>
            <input id="configButton" type="button" value="新增公告" onclick="createNotice()">
        </div>
        <div id="showNoticeDiv" style="display: none; width: 80%; text-align: center; border: 1px solid gray;">
            <div id="showNoticeTitleDiv"></div>
            <div id="showNoticeContentDiv"></div>
        </div>
    </div>
</body>
</html>
<%}%>