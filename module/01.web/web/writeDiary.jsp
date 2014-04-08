<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>写工作日志</title>
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
        });

        /**
         * 提交
         */
        function writeDiary(){
            var date = document.getElementById("date").value;
            if(date == EMPTY){
                alert("请输入日期");
                return false;
            }
            var content = editor.getContent();
            if(content.length > DIARY_CONTENT_LENGTH) {
                alert("工作日志内容大于" + DIARY_CONTENT_LENGTH + "个字符");
                return false;
            }
            document.getElementById("content").value = content;
            //提交表格
            document.forms["writeDiaryForm"].submit();
        }
    </script>
</head>
<body onclick="cc()">
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>写工作日志<button onclick="logOut()">退出</button></h1>
        <div style="border: 1px solid gray; width: 80%;">
            <form name="writeDiaryForm" action="<%=baseUrl%>writeDiary.do" method="post">
                <table>
                    <input type="hidden" id="token" name="token" value="<%=token%>">
                    <tr><td>日期:</td><td><input type="text" id="date" name="date" value=""></td></tr>
                    <textarea style="display: none;" id="content" name="content"></textarea>
                </table>
            </form>
            <script id="editor" type="text/plain" style="width:1024px;height:300px;"></script>
            <input type="button" value="提交" onclick="writeDiary()">
        </div>
    </div>
</body>
</html>
<%}%>