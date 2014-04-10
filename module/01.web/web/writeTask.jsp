<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>写任务</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"></script>
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
        function writeTask(){
            var fromUserId = document.getElementById("fromUserId").value;
            if(fromUserId == EMPTY){
                alert("请选择任务来源用户");
                return false;
            }
            var toUserId = document.getElementById("toUserId").value;
            if(toUserId == EMPTY){
                alert("请选择任务接受用户");
                return false;
            }
            var title = document.getElementById("title").value;
            if(title == EMPTY){
                alert("请输入任务名称");
                return false;
            }
            if(title.length > TASK_TITLE_LENGTH) {
                alert("任务名称大于" + TASK_TITLE_LENGTH + "个字符");
                return false;
            }
            //判断字符串是否含有非法字符
            var result = checkStr(title, SYMBOL_ARRAY_1);
            if(result["isSuccess"] == false){
                alert("标题包含非法字符:" + result["symbol"]);
                return false;
            }
            var content = editor.getContent();
            if(content == EMPTY){
                alert("请输入任务内容");
                return false;
            }
            if(content.length > TASK_CONTENT_LENGTH) {
                alert("任务内容大于" + TASK_CONTENT_LENGTH + "个字符");
                return false;
            }
            var beginDate = document.getElementById("beginDate").value;
            if(beginDate == EMPTY){
                alert("请输入开始日期");
                return false;
            }
            var endDate = document.getElementById("endDate").value;
            if(endDate == EMPTY){
                alert("请输入结束日期");
                return false;
            }
            document.getElementById("content").value = content;
            //提交表格
            document.forms["writeTaskForm"].submit();
        }
    </script>
</head>
<body onclick="cc()">
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>写任务<button onclick="logOut()">退出</button></h1>
        <div style="border: 1px solid gray; width: 80%;">
            <form name="writeTaskForm" action="<%=baseUrl%>writeTask.do" method="post">
                <table>
                    <input type="hidden" id="token" name="token" value="<%=token%>">
                    <input type="hidden" id="fromUserId" name="fromUserId" value="<%=user.getId()%>">
                    <tr><td>任务接受用户id:</td><td><input type="text" id="toUserId" name="toUserId" value=""></td></tr>
                    <tr><td>任务名称:</td><td><input type="text" id="title" name="title" value=""></td></tr>
                    <tr><td>开始日期:</td><td><input type="text" id="beginDate" name="beginDate" value=""></td></tr>
                    <tr><td>结束日期:</td><td><input type="text" id="endDate" name="endDate" value=""></td></tr>
                    <textarea style="display: none;" id="content" name="content"></textarea>
                </table>
            </form>
            <script id="editor" type="text/plain" style="width:1024px;height:300px;"></script>
            <input type="button" value="提交" onclick="writeTask()">
        </div>
    </div>
</body>
</html>
<%}%>