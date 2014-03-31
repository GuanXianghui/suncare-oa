<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page import="com.gxx.oa.entities.Structure" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
    <%
        Structure company = StructureDao.getStructureById(user.getCompany());
        Structure dept = StructureDao.getStructureById(user.getDept());
        Structure position = StructureDao.getStructureById(user.getPosition());
    %>
<html>
<head>
    <title>用户管理</title>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/md5.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/userManage.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>/scripts/base.js"></script>
    <style type="text/css">
        .leftTd{
            width: 100px;
        }
        #structure_table tr td {
            text-align: center;
        }
        .top {
            background-color: gray;
        }
        .company {
            background-color: gray;
        }
        .dept {
            background-color: gray;
        }
        .position {
            cursor: pointer;
            background-color: #adff2f;
            border: 2px solid red;
        }
    </style>
    <script type="text/javascript">
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
        //所有公司结构Json数组
        var structureArray = new Array();
        /**
         * 初始化
         */
        $(document).ready(function() {
            //处理所有公司结构json串
            processWithJson();
        });

        /**
         * 处理 所有公司结构json串
         */
        function processWithJson() {
            //json串转json数组
            if(structureJsonStr != "") {
                var array = structureJsonStr.split(SYMBOL_BIT_AND);
                for(var i=0;i<array.length;i++) {
                    structureArray[structureArray.length] = eval("(" + array[i] + ")");
                }
            }
            //组织所有公司结构表格
            var html = "<tr><td onclick='chooseTd(this, 0)' class='top' colspan='" + calculateSize(0) + "'>组织架构</td></tr>";
            /**
             * pids格式:1,2,,,3
             * 空的可能是上一层某个没有子元素了
             * 全空,,,,,,则结束
             */
            var pids = "0";
            //循环每层展示
            while(true) {
                var array = pids.split(SYMBOL_COMMA);
                pids = "";
                var tempStr = "<tr>";
                for(var i=0;i<array.length;i++) {
                    if(array[i] == "") {
                        tempStr += "<td></td>";
                        pids += ",";
                        continue;
                    }
                    var hasSon = false;
                    for(var j=0;j<structureArray.length;j++) {
                        if(structureArray[j]["pid"] == array[i]) {
                            hasSon = true;
                            var className = (1== structureArray[j]["type"])?"company":((2== structureArray[j]["type"])?"dept":"position");
                            tempStr += "<td";
                            if(structureArray[j]["type"] == 3) {
                                tempStr += " onclick='chooseTd(this, " + structureArray[j]["id"] + ")'";
                            }
                            tempStr += " class='" + className + "' colspan='" +
                                    calculateSize(structureArray[j]["id"]) + "'>" +
                                    structureArray[j]["name"] + "</td>";
                            pids += structureArray[j]["id"] + "," ;
                        }
                    }
                    if(hasSon == false) {
                        tempStr += "<td></td>";
                        pids += ",";
                        continue;
                    }
                }
                tempStr += "</tr>";
                pids = pids.substr(0, pids.length-1);
                if(pids.length == containCount(pids, SYMBOL_COMMA)) {
                    break;
                }
                html += tempStr;
            }
            document.getElementById("structure_table").innerHTML = html;
        }

        /**
         * 算id所占用的空间
         * @param id
         */
        function calculateSize(id) {
            var hasSon = false;//是否有子元素
            var size = 0;
            for(var i=0;i<structureArray.length;i++) {
                if(structureArray[i]["pid"] == id) {
                    hasSon = true;
                    size += calculateSize(structureArray[i]["id"]);
                }
            }
            if(hasSon == false) {
                size = 1;
            }
            return size;
        }

        /**
         * 选择节点
         * @param t
         * @param id
         */
        function chooseTd(t, id) {
            //ajax操作
            var SUCCESS_STR = "success";//成功编码
            $.ajax({
                type:"post",
                async:false,
                url:baseUrl + "updatePosition.do",
                data:"id=" + id + "&token=" + token,
                success:function (data, textStatus) {
                    if ((SUCCESS_STR == textStatus) && (null != data)) {
                        data = eval("(" + data + ")");
                        //判修改密码是否成功
                        if (false == data["isSuccess"]) {
                            alert(data["message"]);
                            return;
                        } else {
                            //修改密码成功
                            alert(data["message"]);
                            cancelUpdatePosition();
                            document.getElementById("company_td").innerHTML = data["companyName"];
                            document.getElementById("dept_td").innerHTML = data["deptName"];
                            document.getElementById("position_td").innerHTML = data["positionName"];
                        }
                        //判是否有新token
                        if (data["hasNewToken"]) {
                            token = data["token"];
                        }
                    } else {
                        alert("Connection failed,please try again later!");
                    }
                },
                error:function (data, textStatus) {
                    alert("Connection failed,please try again later!");
                }
            });
        }

        /**
         * 修改职位
         */
        function updatePosition() {
            document.getElementById("structure_div").style.display = "";
        }

        /**
         * 取消修改职位
         */
        function cancelUpdatePosition() {
            document.getElementById("structure_div").style.display = "none";
        }
    </script>
</head>
<body>
<div id="structure_div" align="center" style="position:absolute; background-color: white; width: 100%; height: 100%; display: none;">
    <h1>组织架构</h1>
    <div>
        <table id="structure_table" width="80%"></table>
    </div>
    <div>
        <button onclick="cancelUpdatePosition()">取消</button>
    </div>
</div>
<div align="center">
    <h1>用户管理<button onclick="logOut()">退出</button></h1>
</div>
<div align="center" style="border: 1px solid green;">
    <table width="80%" style="border: 1px solid green;">
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
                                        <%=user.getName()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        拼音缩写：
                                    </td>
                                    <td id="letter">
                                        <%=user.getLetter()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        密码：
                                    </td>
                                    <td id="before_update_password_td">
                                        <button onclick="beforeUpdatePassword();">修改密码</button>
                                    </td>
                                    <td id="update_password_td" style="display: none;">
                                        <input type="password" id="password">
                                        <button onclick="updatePassword();">修改</button>
                                        <button onclick="cancelUpdatePassword();">取消</button>
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
                                <tr>
                                    <td class="leftTd">
                                        公司：
                                    </td>
                                    <td id="company_td">
                                        <%=null==company?"无":company.getName()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        部门：
                                    </td>
                                    <td id="dept_td">
                                        <%=null==dept?"无":dept.getName()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        职位：
                                    </td>
                                    <td id="position_td">
                                        <%=null==position?"无":position.getName()%>
                                    </td>
                                    <td id="update_position_td">
                                        <button onclick="updatePosition();">修改</button>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
            <td width="50%" style="border: 1px solid green;">
                <table>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td colspan="2">
                                        <img src="<%=baseUrl%>images/temp1.jpg"><b>头像信息</b>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        头像：
                                    </td>
                                    <td>
                                        <%=user.getHeadPhoto()%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td colspan="2">
                                        <img src="<%=baseUrl%>images/temp1.jpg"><b>用户信息</b>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        性别：
                                    </td>
                                    <td>
                                        <%=user.getSex()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        生日：
                                    </td>
                                    <td>
                                        <%=user.getBirthday()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        办公电话：
                                    </td>
                                    <td>
                                        <%=user.getOfficeTel()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        移动电话：
                                    </td>
                                    <td>
                                        <%=user.getMobileTel()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        工位：
                                    </td>
                                    <td>
                                        <%=user.getDesk()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        邮件：
                                    </td>
                                    <td>
                                        <%=user.getEmail()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        qq：
                                    </td>
                                    <td>
                                        <%=user.getQq()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        msn：
                                    </td>
                                    <td>
                                        <%=user.getMsn()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        地址：
                                    </td>
                                    <td>
                                        <%=user.getAddress()%>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="leftTd">
                                        个人网站：
                                    </td>
                                    <td>
                                        <%=user.getWebsite()%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
<%}%>