<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>日历</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/calendar.js"></script>
    <!--日期控件-->
    <link type="text/css" rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>日历<button onclick="logOut()">退出</button></h1>
        <div id="date"></div>
        <div>
            <table width="80%" border="1">
                <tr>
                    <td width="70%" align="center">
                        提醒
                    </td>
                    <td width="30%" align="center">
                        详情
                    </td>
                </tr>
                <tr>
                    <td>
                        <table id="remind_table" border="1" width="100%"></table>
                    </td>
                    <td align="center">
                        <table id="create_remind_table" border="1" style="display: none;" width="100%">
                            <tr>
                                <td>日期:</td><td><input type="text" id="create_date"></td>
                            </tr>
                            <tr>
                                <td>内容:</td><td><input type="text" id="create_content"></td>
                            </tr>
                            <tr>
                                <td>提醒类型:</td>
                                <td>
                                    <select id="create_remind_type">
                                        <option value="1">不提醒</option>
                                        <option value="2">消息提醒</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>提醒时间:</td><td><input type="text" id="create_remind_date_time"></td>
                            </tr>
                            <tr>
                                <td colspan="4" align="center"><button onclick="createRemind()">提交</button></td>
                            </tr>
                        </table>

                        <table id="show_remind_table" border="1" style="display: none;" width="100%">
                            <tr>
                                <td>日期:</td><td id="show_date"></td>
                            </tr>
                            <tr>
                                <td>内容:</td><td id="show_content"></td>
                            </tr>
                            <tr>
                                <td>提醒类型:</td>
                                <td id="show_remind_type"></td>
                            </tr>
                            <tr>
                                <td>提醒时间:</td><td id="show_remind_date_time"></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
<%}%>