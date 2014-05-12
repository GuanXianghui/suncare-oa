<%@ page import="com.gxx.oa.interfaces.SymbolInterface" %>
<%@ page import="com.gxx.oa.dao.LetterDao" %>
<%@ page import="com.gxx.oa.interfaces.UserInterface" %>
<%@ page import="com.gxx.oa.interfaces.LetterInterface" %>
<%@ page import="com.gxx.oa.utils.BaseUtil" %>
<%@ page import="com.gxx.oa.utils.PropertyUtil" %>
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
    String box = request.getParameter("box");
    int type;
    if(StringUtils.equals(box, LetterInterface.BOX_SENT)){
        type = LetterInterface.TYPE_SENT;
    } else if(StringUtils.equals(box, LetterInterface.BOX_DELETED)){
        type = LetterInterface.TYPE_DELETED;
    } else {
        type = LetterInterface.TYPE_RECEIVED;
        box = LetterInterface.BOX_RECEIVED;
    }
%>
<html>
<head>
    <title>站内信</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/letter.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript">
        //标识现在是收件箱，已发送还是已删除
        var box = "<%=box%>";
        /**
         * 消息Json串
         * replaceAll("\\\"", "\\\\\\\"")，转换双引号
         * replaceAll("\r\n", uuid)，转换换行符成uuid
         */
        var letterJsonStr = "<%=BaseUtil.getJsonArrayFromLetters(LetterDao.queryLettersByTypeAndFromTo(user.getId(),
                        UserInterface.USER_TYPE_NORMAL, type, 0, Integer.parseInt(PropertyUtil.getInstance().
                        getProperty(BaseInterface.LETTER_PAGE_SIZE))), box).replaceAll("\\\"", "\\\\\\\"").
                        replaceAll(SymbolInterface.SYMBOL_NEW_LINE, PropertyUtil.getInstance().
                        getProperty(BaseInterface.GXX_OA_NEW_LINE_UUID))%>";
        //根据用户id和用户类型和查询类型 查总共站内信的量
        var letterCount = <%=LetterDao.countLettersByType(user.getId(), UserInterface.USER_TYPE_NORMAL, type)%>;
        //选择站内信ids
        var chooseLetterIds = EMPTY;
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

        <div class="content-box">
            <div class="content-box-header">
                <h3>站内信-<%=type==LetterInterface.TYPE_SENT?"已发送":(type==LetterInterface.TYPE_DELETED?"已删除":"收件箱")%></h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab2" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab2">
                    <%
                        if(type == LetterInterface.TYPE_RECEIVED){
                    %>
                    <div>
                        <input class="button" type="button" onclick="deleteLetter();" value="删除" />
                        <input class="button" type="button" onclick="ctrlDeleteLetter();" value="彻底删除" />
                        <input class="button" type="button" onclick="transmit();" value="转发" />
                        <input class="button" type="button" onclick="setReaded();" value="标记成已读" />
                    </div>
                    <%
                    } else if(type == LetterInterface.TYPE_SENT) {
                    %>
                    <div>
                        <input class="button" type="button" onclick="deleteLetter();" value="删除" />
                        <input class="button" type="button" onclick="ctrlDeleteLetter();" value="彻底删除" />
                        <input class="button" type="button" onclick="transmit();" value="转发" />
                    </div>
                    <%
                    } else if(type == LetterInterface.TYPE_DELETED) {
                    %>
                    <div>
                        <input class="button" type="button" onclick="ctrlDeleteLetter();" value="彻底删除" />
                        <input class="button" type="button" onclick="transmit();" value="转发" />
                        <input class="button" type="button" onclick="setReaded();" value="标记成已读" />
                        <input class="button" type="button" onclick="restore();" value="还原" />
                    </div>
                    <%
                        }
                    %>
                    <table id="letter_table"></table>
                    <div id="nextPageDiv" style="display: none; text-align: center;">
                        <input class="button" type="button" onclick="showNextPageLetters();" value="加载下一页" />
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