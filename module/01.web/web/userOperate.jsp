<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<html>
<head>
    <title>后台管理用户</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/md5.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/pinyin.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/userOperate.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>css/userOperate.css"/>
    <script type="text/javascript">
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
        //名字拼音缩写
        var letter = EMPTY;
        //职位id
        var positionId = 0;
    </script>
</head>
<body>
<div id="structure_div" align="center" style="position:absolute; background-color: white; width: 100%; height: 100%; display: none;">
    <h1>组织架构</h1>
    <div>
        <table id="structure_table" width="80%"></table>
    </div>
    <div>
        <button onclick="cancelChoosePosition()">取消</button>
    </div>
</div>
<div align="center">
    <h1><button onclick="jump2Main()">主页</button>后台用户管理<button onclick="logOut()">退出</button></h1>
    <div>
        <button onclick="location.href='<%=baseUrl%>contacts.jsp'">查看用户</button>
        <button onclick="chooseCreateUser()">创建用户</button>
        <button onclick="chooseUpdateUser()">修改用户</button>
    </div>
</div>
<div id="create_user_div" align="center" style="display: none;">
    <table width="40%" style="border: 1px solid green;">
        <tr>
            <td width="50%" style="border: 1px solid green;">
                <table>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td colspan="2">
                                        <img src="<%=baseUrl%>images/temp1.jpg"><b>个人信息</b>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        姓名：
                                    </td>
                                    <td>
                                        <input type="text" id="name" onchange="changeName()" onkeyup="changeName()">
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        拼音缩写：
                                    </td>
                                    <td id="letter"/>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        初始密码：
                                    </td>
                                    <td id="before_update_password_td">
                                        <%=PropertyUtil.getInstance().getProperty(BaseInterface.DEFAULT_PASSWORD)%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td colspan="3">
                                        <img src="<%=baseUrl%>images/temp1.jpg"><b>工作信息</b>
                                    </td>
                                </tr>
                                <%--<tr>--%>
                                    <%--<td class="leftTd">--%>
                                        <%--公司：--%>
                                    <%--</td>--%>
                                    <%--<td id="company_td">--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<td class="leftTd">--%>
                                        <%--部门：--%>
                                    <%--</td>--%>
                                    <%--<td id="dept_td">--%>
                                    <%--</td>--%>
                                <%--</tr>--%>
                                <tr>
                                    <td class="leftTd">
                                        职位：
                                    </td>
                                    <td id="position_td">
                                    </td>
                                    <td id="update_position_td">
                                        <button onclick="createOrUpdate='create';choosePosition();">选择职位</button>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <img src="<%=baseUrl%>images/temp1.jpg"><b>头像信息</b>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td class="leftTd">
                                        头像：
                                    </td>
                                    <td id="before_upload_head_photo_td">
                                        <img src="<%=baseUrl + PropertyUtil.getInstance().getProperty(BaseInterface.DEFAULT_HEAD_PHOTO)%>" width="54px"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <button onclick="createUser()">创建</button>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
<div id="update_user_div" align="center" style="">
    <table width="60%" style="border: 1px solid green;">
        <tr>
            <td style="border: 1px solid green;" align="center">
                姓名：<input type="text" id="user_name"><button onclick="queryUser()">查询</button>(可输入缩写如：严明皓->ymh)
            </td>
        </tr>
        <tr>
            <td id="user_list">

            </td>
        </tr>
    </table>
</div>
</body>
</html>
<%}%>