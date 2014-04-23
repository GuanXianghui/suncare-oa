<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page import="com.gxx.oa.entities.Structure" %>
<%@ page import="com.gxx.oa.utils.DateUtil" %>
<%@ page import="com.gxx.oa.interfaces.UserInterface" %>
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
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/md5.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/userManage.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>css/userManage.css"/>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
</head>
<body>
<div id="body-wrapper">
<div id="sidebar">
    <div id="sidebar-wrapper">
        <h1 id="sidebar-title"><a href="#">申成-OA系统</a></h1>
        <img id="logo" src="images/suncare-files-logo.png" alt="Simpla Admin logo"/>
        <div id="profile-links">
            Hello, [<%=user.getName()%>],
            <a href="http://www.suncarechina.com" target="_blank">申成</a>欢迎您！
            <br/>
            <br/>
            <a href="javascript: logOut()" title="Sign Out">退出</a>
        </div>
        <ul id="main-nav">
            <li><a href="#" class="nav-top-item current"> 用户模块 </a>
                <ul>
                    <li><a href="<%=baseUrl%>userManage.jsp" class="current">用户管理</a></li>
                    <li><a href="<%=baseUrl%>user.jsp?id=<%=user.getId()%>">个人展示</a></li>
                    <li><a href="<%=baseUrl%>userOperate.jsp">后台用户管理</a></li>
                    <li><a href="<%=baseUrl%>contacts.jsp">通讯录</a></li>
                    <li><a href="<%=baseUrl%>orgStructureManage.jsp">组织架构管理</a></li>
                </ul>
            </li>
            <li><a href="#" class="nav-top-item"> 消息模块 </a>
                <ul>
                    <li><a href="<%=baseUrl%>notice.jsp">公告</a></li>
                    <li><a href="<%=baseUrl%>configNotice.jsp">公告管理</a></li>
                    <li><a href="<%=baseUrl%>message.jsp">消息</a></li>
                    <li><a href="<%=baseUrl%>letter.jsp">站内信</a></li>
                </ul>
            </li>
            <li><a href="#" class="nav-top-item"> 工作模块 </a>
                <ul>
                    <li><a href="<%=baseUrl%>diary.jsp">工作日志</a></li>
                    <li><a href="<%=baseUrl%>calendar.jsp">日历</a></li>
                    <li><a href="<%=baseUrl%>task.jsp">任务</a></li>
                </ul>
            </li>
            <li><a href="#" class="nav-top-item"> 工具模块 </a>
                <ul>
                    <li><a href="<%=baseUrl%>sms.jsp">短息</a></li>
                </ul>
            </li>
        </ul>
    </div>
</div>
<div id="main-content">
    <div id="message_id" class="notification information png_bg" style="display: none;">
        <a href="#" class="close">
            <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
        </a>

        <div id="message_id_content"> 提示信息！</div>
    </div>
    <div class="column-left">
        <div class="content-box">
            <div class="content-box-header">
                <h3>头像信息</h3>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab">
                    <table>
                        <tr style="border: 1px solid white;">
                            <td id="before_upload_head_photo_td">
                                <img src="<%=baseUrl + user.getHeadPhoto()%>" width="108px"/>
                                <input class="button" type="button" onclick="beforeUploadHeadPhoto();" value="修改头像">
                            </td>
                            <td id="upload_head_photo_td" style="display: none;">
                                <form name="uploadHeadPhotoForm" method="post" autocomplete="off"
                                      action="<%=baseUrl%>uploadHeadPhoto.do" enctype="multipart/form-data">
                                    <input type="file" name="headPhoto">
                                    <input class="button" type="button" onclick="uploadHeadPhoto();" value="上传">
                                    <input class="button" type="button" onclick="cancelUploadHeadPhoto();" value="取消">
                                </form>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="content-box">
            <div class="content-box-header">
                <h3>个人信息</h3>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab">
                    <form onsubmit="return false;">
                        <table>
                            <tr>
                                <td>姓名</td>
                                <td><%=user.getName()%></td>
                            </tr>
                            <tr>
                                <td>拼音缩写</td>
                                <td><%=user.getLetter()%></td>
                            </tr>
                            <tr>
                                <td>密码</td>
                                <td id="before_update_password_td">
                                    <input class="button" type="button" onclick="beforeUpdatePassword();" value="修改密码">
                                </td>
                                <td id="update_password_td" style="display: none;">
                                    <input class="text-input medium-input" type="password" id="password">
                                    <input class="button" type="button" onclick="updatePassword();" value="修改">
                                    <input class="button" type="button" onclick="cancelUpdatePassword();" value="取消">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
        <div class="content-box">
            <div class="content-box-header">
                <h3>工作信息</h3>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab">
                    <form onsubmit="return false;">
                        <table>
                            <tr>
                                <td>公司</td>
                                <td id="company_td"><%=null==company?"无":company.getName()%></td>
                            </tr>
                            <tr>
                                <td>部门</td>
                                <td id="dept_td"><%=null==dept?"无":dept.getName()%></td>
                            </tr>
                            <tr>
                                <td>职位</td>
                                <td id="position_td"><%=null==position?"无":position.getName()%></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="column-right">
        <div class="content-box">
            <div class="content-box-header">
                <h3>用户信息</h3>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab">
                    <form onsubmit="return false;">
                        <table>
                            <tr>
                                <td>性别</td>
                                <td id="sex_td_1">
                                    <%=BaseUtil.translateUserSex(user.getSex())%>
                                </td>
                                <td id="sex_td_2" style="display: none;">
                                    <select id="sex_select">
                                        <option value="<%=UserInterface.SEX_X%>"<%=(UserInterface.SEX_X==user.getSex())?" SELECTED":""%>>男</option>
                                        <option value="<%=UserInterface.SEX_O%>"<%=(UserInterface.SEX_O==user.getSex())?" SELECTED":""%>>女</option>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>生日</td>
                                <td id="birthday_td_1">
                                    <%=DateUtil.getCNDate(DateUtil.getDate(user.getBirthday()))%>
                                </td>
                                <td id="birthday_td_2" style="display: none;">
                                    <input class="text-input between-medium-large-input" type="text" id="birthday_input" value="<%=user.getBirthday()%>">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    办公电话：
                                </td>
                                <td id="office_tel_td_1">
                                    <%=user.getOfficeTel()%>
                                </td>
                                <td id="office_tel_td_2" style="display: none;">
                                    <input class="text-input between-medium-large-input" type="text" id="office_tel_input" value="<%=user.getOfficeTel()%>">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    移动电话：
                                </td>
                                <td id="mobile_tel_td_1">
                                    <%=user.getMobileTel()%>
                                </td>
                                <td id="mobile_tel_td_2" style="display: none;">
                                    <input class="text-input between-medium-large-input" type="text" id="mobile_tel_input" value="<%=user.getMobileTel()%>">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    工位：
                                </td>
                                <td id="desk_td_1">
                                    <%=user.getDesk()%>
                                </td>
                                <td id="desk_td_2" style="display: none;">
                                    <input class="text-input between-medium-large-input" type="text" id="desk_input" value="<%=user.getDesk()%>">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    邮件：
                                </td>
                                <td id="email_td_1">
                                    <%=user.getEmail()%>
                                </td>
                                <td id="email_td_2" style="display: none;">
                                    <input class="text-input between-medium-large-input" type="text" id="email_input" value="<%=user.getEmail()%>">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    qq：
                                </td>
                                <td id="qq_td_1">
                                    <%=user.getQq()%>
                                </td>
                                <td id="qq_td_2" style="display: none;">
                                    <input class="text-input between-medium-large-input" type="text" id="qq_input" value="<%=user.getQq()%>">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    msn：
                                </td>
                                <td id="msn_td_1">
                                    <%=user.getMsn()%>
                                </td>
                                <td id="msn_td_2" style="display: none;">
                                    <input class="text-input between-medium-large-input" type="text" id="msn_input" value="<%=user.getMsn()%>">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    地址：
                                </td>
                                <td id="address_td_1">
                                    <%=user.getAddress()%>
                                </td>
                                <td id="address_td_2" style="display: none;">
                                    <input class="text-input between-medium-large-input" type="text" id="address_input" value="<%=user.getAddress()%>">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    个人网站：
                                </td>
                                <td id="website_td_1">
                                    <a href="<%=user.getWebsite()%>" target="_blank"><%=user.getWebsite()%></a>
                                </td>
                                <td id="website_td_2" style="display: none;">
                                    <input class="text-input between-medium-large-input" type="text" id="website_input" value="<%=user.getWebsite()%>">
                                </td>
                            </tr>
                            <tr>
                                <td id="before_update_info_td" colspan="2">
                                    <input class="button" type="button" onclick="beforeUpdateInfo();" value="修改用户信息">
                                </td>
                                <td id="update_info_td" style="display: none;" colspan="2">
                                    <input class="button" type="button" onclick="updateInfo();" value="修改">
                                    <input class="button" type="button" onclick="cancelUpdateInfo();" value="取消">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="clear"></div>
    <div id="footer">
        <small>
            &#169; Copyright 2014 Suncare | Powered by 关向辉
        </small>
    </div>
</div>
</div>
</body>
</html>
<%}%>