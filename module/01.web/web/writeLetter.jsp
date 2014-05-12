<%@ page import="com.gxx.oa.interfaces.LetterInterface" %>
<%@ page import="com.gxx.oa.entities.Letter" %>
<%@ page import="com.gxx.oa.dao.LetterDao" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page import="com.gxx.oa.dao.StructureDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //权限校验
    if(!BaseUtil.checkRight(user.getId(), UserRightInterface.RIGHT_0009_LETTER)){
        //域名链接
        response.sendRedirect(baseUrl + "index.jsp");
        return;
    }
    //外层
    outLayer = "消息模块";
    //内层
    inLayer = "站内信";
    String toUserIds = StringUtils.EMPTY;//收件人
    String ccUserIds = StringUtils.EMPTY;//抄送人
    String title = StringUtils.EMPTY;//标题
    String content = StringUtils.EMPTY;//内容

    String type = request.getParameter("type");//写信类型type
    if(StringUtils.isNotBlank(type) && !StringUtils.equals(type, LetterInterface.WRITE_TYPE_WRITE)){//写信类型type不为空而且不是写信
        //判入参id合法性
        int id = 0;
        try{
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e){
            response.sendRedirect(baseUrl + "letter.jsp");
            return;
        }
        //判站内信是否属于该登陆用户
        Letter letter = LetterDao.getLetterById(id);
        if(letter.getUserId() != user.getId()){
            response.sendRedirect(baseUrl + "letter.jsp");
            return;
        }
        if(StringUtils.equals(type, LetterInterface.WRITE_TYPE_REPLY)){//回复
            toUserIds = StringUtils.EMPTY + letter.getFromUserId();
            ccUserIds = StringUtils.EMPTY;
            title = "回复：" + letter.getTitle();
        } else if(StringUtils.equals(type, LetterInterface.WRITE_TYPE_REPLY_ALL)){//回复全部
            toUserIds = letter.getToUserIds();
            ccUserIds = letter.getCcUserIds();
            title = "回复：" + letter.getTitle();
        } else if(StringUtils.equals(type, LetterInterface.WRITE_TYPE_TRANSMIT)){//转发
            toUserIds = StringUtils.EMPTY;
            ccUserIds = StringUtils.EMPTY;
            title = "转发：" + letter.getTitle();
        }
        if(!StringUtils.EMPTY.equals(letter.getContent())){
            content = "<div class='quote'><blockquote>" + letter.getContent() + "</blockquote></div><p></p>";
        }
    }
%>
<html>
<head>
    <title>写信</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/writeLetter.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=baseUrl%>css/writeLetter.css"/>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <!--这一行显示ie会有问题xxxxxx-->
    <%--<script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript">
        //所有员工json串
        var userJsonStr = "<%=BaseUtil.getJsonArrayFromUsers(UserDao.queryAllUsers())%>";
        //所有公司结构json串
        var structureJsonStr = "<%=BaseUtil.getJsonArrayFromStructures(StructureDao.queryAllStructures())%>";
        //初始接受用户id
        var initToUserIds = "<%=toUserIds%>";
        //初始抄送用户id
        var initCcUserIds = "<%=ccUserIds%>";
    </script>
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
            <%@ include file="layers.jsp" %>
        </div>
    </div>

    <div id="main-content">
        <ul class="shortcut-buttons-set">
            <li>
                <a class="shortcut-button" href="<%=baseUrl%>writeLetter.jsp">
                    <span>
                        <img src="images/icons/paper_content_pencil_48.png" alt="icon"/>
                        <br/>写信
                    </span>
                </a>
            </li>
            <li>
                <a class="shortcut-button" href="<%=baseUrl%>letter.jsp">
                    <span>
                        <img src="images/icons/image_add_48.png" alt="icon"/>
                        <br/>收件箱
                    </span>
                </a>
            </li>
            <li>
                <a class="shortcut-button" href="<%=baseUrl%>letter.jsp?box=<%=LetterInterface.BOX_SENT%>">
                    <span>
                        <img src="images/icons/image_add_48.png" alt="icon"/>
                        <br/>已发送
                    </span>
                </a>
            </li>
            <li>
                <a class="shortcut-button" href="<%=baseUrl%>letter.jsp?box=<%=LetterInterface.BOX_DELETED%>">
                    <span>
                        <img src="images/icons/image_add_48.png" alt="icon"/>
                        <br/>已删除
                    </span>
                </a>
            </li>
        </ul>

        <div class="clear"></div>

        <div id="message_id" class="notification information png_bg" style="display: none;">
            <a href="#" class="close">
                <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
            </a>

            <div id="message_id_content"> 提示信息！</div>
        </div>

        <div class="content-box friends-column-left">
            <div class="content-box-header">
                <h3>写信</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab">
                    <form name="writeLetterForm" action="<%=baseUrl%>writeLetter.do" method="post">
                        <input name="toUserIds" id="toUserIdsValue" type="hidden">
                        <input name="ccUserIds" id="ccUserIdsValue" type="hidden">
                        <table>
                            <input type="hidden" id="token" name="token" value="<%=token%>">
                            <textarea style="display: none;" id="content" name="content"><%=content%></textarea>
                            <!--目前默认都是普通用户类型-->
                            <tr id="toUserIdsTr" onclick="changeUserType(USER_TYPE_RECEIVE, this)" style="border: 1px solid gray">
                                <td id="toUserIds"><b>收件人:</b></td>
                            </tr>
                            <tr id="ccUserIdsTr" onclick="changeUserType(USER_TYPE_CC, this)" style="border: 0px solid gray">
                                <td id="ccUserIds"><b>抄送人:</b></td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;<b>标题:</b>
                                    <input id="title" class="text-input medium-input" type="text"
                                           name="title" value="<%=title%>"/>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <script id="editor" type="text/plain" style="width: 100%;"></script>
                    <input class="button" type="button" onclick="writeLetter();" value="发送" />
                </div>
            </div>
        </div>
        <div class="content-box friends-column-right">
            <div class="content-box-header">
                <h3>联系人</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab2" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab2">
                    <table id="friends">
                    </table>
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