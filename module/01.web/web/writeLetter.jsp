<%@ page import="com.gxx.oa.interfaces.LetterInterface" %>
<%@ page import="com.gxx.oa.entities.Letter" %>
<%@ page import="com.gxx.oa.dao.LetterDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<%
    String toUserIds = StringUtils.EMPTY;//收件人
    String ccUserIds = StringUtils.EMPTY;//抄送人
    String title = StringUtils.EMPTY;//标题
    String content = StringUtils.EMPTY;//内容

    String type = request.getParameter("type");//写信类型type
    if(StringUtils.isNotBlank(type) && !StringUtils.equals(type, LetterInterface.WRITE_TYPE_WRITE)){//写信类型type不为空而且不是写信
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
        if(StringUtils.equals(type, LetterInterface.WRITE_TYPE_REPLY)){//回复
            toUserIds = StringUtils.EMPTY + letter.getFromUserId();
            ccUserIds = StringUtils.EMPTY;
            title = "回复：" + letter.getTitle();
        } else if(StringUtils.equals(type, LetterInterface.WRITE_TYPE_REPLY_ALL)){//回复全部
            toUserIds = letter.getToUserIds();
            ccUserIds = letter.getCcUserIds();
            title = "回复：" + letter.getTitle();
        } else if(StringUtils.equals(type, LetterInterface.WRITE_TYPE_TRANSMIT)){//转发
            toUserIds = StringUtils.EMPTY;
            ccUserIds = StringUtils.EMPTY;
            title = "转发：" + letter.getTitle();
        }
        if(!StringUtils.EMPTY.equals(letter.getContent())){
            content = "<div class='quote'><blockquote>" + letter.getContent() + "</blockquote></div><p></p>";
        }
    }
%>
<html>
<head>
    <title>写信</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <script type="text/javascript">
        //ueditor编辑器
        var editor;

        /**
         * 初始化
         */
        $(document).ready(function() {
            //实例化编辑器
            //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
            editor = UE.getEditor('editor');
            //一定要加载页面后隔上1秒钟(时间可能可以短一点)设置编辑器的内容
            setTimeout("setContent()", 1000);
        });

        /**
         * 一定要加载页面后隔上1秒钟(时间可能可以短一点)设置编辑器的内容*
         */
        function setContent(){
            //设置编辑器的内容
            editor.setContent(document.getElementById("content").value);
        }

        /**
         * 写信
         */
        function writeLetter(){
            var toUserIds = document.getElementById("toUserIds").value;
            if(toUserIds == EMPTY){
                alert("请选择收件人");
                return false;
            }
            var ccUserIds = document.getElementById("ccUserIds").value;
            var title = document.getElementById("title").value;
            if(title == EMPTY){
                alert("标题不能为空");
                return false;
            }
            //判断字符串是否含有非法字符
            var result = checkStr(title, SYMBOL_ARRAY_1);
            if (result["isSuccess"] == false) {
                alert("标题包含非法字符:" + result["symbol"]);
                return false;
            }
            var content = editor.getContent();
            if(content.length > LETTER_CONTENT_LENGTH) {
                alert("站内信内容大于" + LETTER_CONTENT_LENGTH + "个字符");
                return false;
            }
            document.getElementById("content").value = content;
            //提交表格
            document.forms["writeLetterForm"].submit();
        }
    </script>
</head>
<body onclick="cc()">
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>写信<button onclick="logOut()">退出</button></h1>
        <div>
            <a href="<%=baseUrl%>writeLetter.jsp">写信</a>
            <a href="<%=baseUrl%>letter.jsp">收件箱</a>
            <a href="<%=baseUrl%>letter.jsp?box=<%=LetterInterface.BOX_SENT%>">已发送</a>
            <a href="<%=baseUrl%>letter.jsp?box=<%=LetterInterface.BOX_DELETED%>">已删除</a>
        </div>
        <div style="border: 1px solid gray; width: 80%;">
            <form name="writeLetterForm" action="<%=baseUrl%>writeLetter.do" method="post">
                <table>
                    <input type="hidden" id="token" name="token" value="<%=token%>">
                    <textarea style="display: none;" id="content" name="content"><%=content%></textarea>
                    <!--目前默认都是普通用户类型-->
                    <tr><td>收件人:</td><td><input type="text" id="toUserIds" name="toUserIds" value="<%=toUserIds%>"></td></tr>
                    <tr><td>抄送:</td><td><input type="text" id="ccUserIds" name="ccUserIds" value="<%=ccUserIds%>"></td></tr>
                    <tr><td>标题:</td><td><input type="text" id="title" name="title" value="<%=title%>"></td></tr>
                </table>
            </form>
            <script id="editor" type="text/plain" style="width:1024px;height:300px;"></script>
            <input type="button" value="发送" onclick="writeLetter()">
        </div>
    </div>
</body>
</html>
<%}%>