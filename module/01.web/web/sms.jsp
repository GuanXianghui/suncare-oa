<%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
<%@ page import="com.gxx.oa.dao.SMSDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<%
    String date = request.getParameter("date");
    date = StringUtils.trimToEmpty(date);
%>
<html>
<head>
    <title>短信</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/sms.js"></script>
    <!--日期控件-->
    <link type="text/css" rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"/>
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <script type="text/javascript">
        //日期
        var date = "<%=date%>";
        /**
         * 短信Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var smsJsonStr = "<%=BaseUtil.getJsonArrayFromSMS(SMSDao.querySMSByUserIdAndStateAndDate(user.getId(),
                        0, date)).replaceAll("\\\"", "\\\\\\\"").replaceAll(SymbolInterface.SYMBOL_NEW_LINE2,
                        PropertyUtil.getInstance().getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //根据用户id，状态和日期查短信量
        var smsCount = <%=SMSDao.countSMSByUserIdAndStateAndDate(user.getId(), 0, date)%>;
    </script>
</head>
<body>
    <div align="center">
        <h1><button onclick="jump2Main()">主页</button>短信<button onclick="logOut()">退出</button></h1>
        <div id="date"></div>
        <button onclick="beforeSendSMS()">发送短信</button>
        <table id="sms_table" border="1" width="80%" style="text-align: center"></table>
        <table id="sendSmsDiv" style="display: none; text-align: center; border: 1px solid gray;">
            <tr><td>手机号：</td><td><input type="text" id="phone"></td></tr>
            <tr><td>内容：</td><td><textarea id="content" cols="80" rows="3"></textarea></td></tr>
            <tr><td colspan="2" align="center"><button onclick="operateSMS()">发送</button></td></tr>
        </table>
    </div>
</body>
</html>
<%}%>