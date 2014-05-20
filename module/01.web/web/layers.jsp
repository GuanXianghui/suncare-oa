<%@ page import="com.gxx.oa.interfaces.UserRightInterface" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul id="main-nav">
    <%
        if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0001_USER_MANAGE) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0002_USER) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0003_USER_OPERATE) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0004_CONTACTS) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0005_ORG_STRUCTURE_MANAGE)){
    %>
    <li><a href="#" class="nav-top-item<%=outLayer.equals("用户模块")?" current":""%>"> 用户模块 </a>
        <ul>
            <%
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0001_USER_MANAGE)){
            %>
            <li><a href="<%=baseUrl%>userManage.jsp"<%=inLayer.equals("用户管理")?" class=\"current\"":""%>>用户管理</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0002_USER)){
            %>
            <li><a href="<%=baseUrl%>user.jsp?id=<%=user.getId()%>"<%=inLayer.equals("个人展示")?" class=\"current\"":""%>>个人展示</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0003_USER_OPERATE)){
            %>
            <li><a href="<%=baseUrl%>userOperate.jsp"<%=inLayer.equals("后台用户管理")?" class=\"current\"":""%>>后台用户管理</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0004_CONTACTS)){
            %>
            <li><a href="<%=baseUrl%>contacts.jsp"<%=inLayer.equals("通讯录")?" class=\"current\"":""%>>通讯录</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0005_ORG_STRUCTURE_MANAGE)){
            %>
            <li><a href="<%=baseUrl%>orgStructureManage.jsp"<%=inLayer.equals("组织架构管理")?" class=\"current\"":""%>>组织架构管理</a></li>
            <%
                }
            %>
        </ul>
    </li>
    <%
        }
    %>
    <%
        if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0006_NOTICE) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0007_CONFIG_NOTICE) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0008_MESSAGE) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0009_LETTER)){
    %>
    <li><a href="#" class="nav-top-item<%=outLayer.equals("消息模块")?" current":""%>"> 消息模块 </a>
        <ul>
            <%
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0006_NOTICE)){
            %>
            <li><a href="<%=baseUrl%>notice.jsp"<%=inLayer.equals("公告")?" class=\"current\"":""%>>公告</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0007_CONFIG_NOTICE)){
            %>
            <li><a href="<%=baseUrl%>configNotice.jsp"<%=inLayer.equals("公告管理")?" class=\"current\"":""%>>公告管理</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0008_MESSAGE)){
            %>
            <li><a href="<%=baseUrl%>message.jsp"<%=inLayer.equals("消息")?" class=\"current\"":""%>>消息</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0009_LETTER)){
            %>
            <li><a href="<%=baseUrl%>letter.jsp"<%=inLayer.equals("站内信")?" class=\"current\"":""%>>站内信</a></li>
            <%
                }
            %>
        </ul>
    </li>
    <%
        }
    %>
    <%
        if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0010_DIARY) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0011_CALENDAR) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0012_TASK)){
    %>
    <li><a href="#" class="nav-top-item<%=outLayer.equals("工作模块")?" current":""%>"> 工作模块 </a>
        <ul>
            <%
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0010_DIARY)){
            %>
            <li><a href="<%=baseUrl%>diary.jsp"<%=inLayer.equals("工作日志")?" class=\"current\"":""%>>工作日志</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0011_CALENDAR)){
            %>
            <li><a href="<%=baseUrl%>calendar.jsp"<%=inLayer.equals("日历")?" class=\"current\"":""%>>日历</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0012_TASK)){
            %>
            <li><a href="<%=baseUrl%>task.jsp"<%=inLayer.equals("任务")?" class=\"current\"":""%>>任务</a></li>
            <%
                }
            %>
        </ul>
    </li>
    <%
        }
    %>
    <%
        if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0013_SMS)){
    %>
    <li><a href="#" class="nav-top-item<%=outLayer.equals("工具模块")?" current":""%>"> 工具模块 </a>
        <ul>
            <%
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0013_SMS)){
            %>
            <li><a href="<%=baseUrl%>sms.jsp"<%=inLayer.equals("短信")?" class=\"current\"":""%>>短信</a></li>
            <%
                }
            %>
        </ul>
    </li>
    <%
        }
    %>
    <%
        if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0014_DEFAULT_RIGHT) ||
                BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0015_USER_RIGHT)){
    %>
    <li><a href="#" class="nav-top-item<%=outLayer.equals("权限模块")?" current":""%>"> 权限模块 </a>
        <ul>
            <%
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0014_DEFAULT_RIGHT)){
            %>
            <li><a href="<%=baseUrl%>defaultRight.jsp"<%=inLayer.equals("默认权限")?" class=\"current\"":""%>>默认权限</a></li>
            <%
                }
                if(BaseUtil.haveRight(userRight, UserRightInterface.RIGHT_0015_USER_RIGHT)){
            %>
            <li><a href="<%=baseUrl%>userRight.jsp"<%=inLayer.equals("权限管理")?" class=\"current\"":""%>>权限管理</a></li>
            <%
                }
            %>
        </ul>
    </li>
    <%
        }
    %>
    <li><a href="#" class="nav-top-item<%=outLayer.equals("申成云")?" current":""%>"> 申成云 </a>
        <ul>
            <li><a href="<%=baseUrl%>cloud.jsp"<%=inLayer.equals("申成网盘")?" class=\"current\"":""%>>申成网盘</a></li>
        </ul>
    </li>
</ul>