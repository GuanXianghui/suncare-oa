<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>组织架构管理</title>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/orgStructureManage.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>/css/orgStructureManage.css"/>
    <script type="text/javascript">
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
    </script>
</head>
<body>
<div align="center">
    <h1>组织架构管理<button onclick="logOut()">退出</button></h1>
    <div>
        <table id="structure_table" width="80%"></table>
    </div>
    <div align="center">
        <button onclick="move2Left()"><</button>
        <button onclick="move2Right()">></button><br>
        <select id="type1">
            <option value="1">公司</option>
            <option value="2">部门</option>
            <option value="3">职位</option>
        </select>
        节点名称：<input id="name1" type="text">
        <button onclick="addNode()">新增</button><br>
        <select id="type2">
            <option value="1">公司</option>
            <option value="2">部门</option>
            <option value="3">职位</option>
        </select>
        节点名称：<input id="name2" type="text">
        <button onclick="updateNode()">修改</button><br>
        <button onclick="deleteNode()">删除</button><br>
    </div>
</div>
</body>
</html>
<%}%>