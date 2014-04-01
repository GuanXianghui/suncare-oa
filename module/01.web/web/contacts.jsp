<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>通讯录</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/contacts.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>css/contacts.css"/>
    <script type="text/javascript">
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
    </script>
</head>
<body>
<div align="center">
    <h1><button onclick="jump2Main()">主页</button>通讯录<button onclick="logOut()">退出</button></h1>
    <div>
        <table id="contacts_table" width="80%"></table>
    </div>
</div>
</body>
</html>
<%}%>