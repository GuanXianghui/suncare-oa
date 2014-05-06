<%@ page import="com.gxx.oa.interfaces.LetterInterface" %>
<%@ page import="com.gxx.oa.dao.LetterDao" %>
<%@ page import="com.gxx.oa.entities.Letter" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page import="com.gxx.oa.utils.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%if(isLogin){%>
<%
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
    if(null == letter || letter.getUserId() != user.getId()){
        response.sendRedirect(baseUrl + "letter.jsp");
        return;
    }
    //如果未读，置成已读
    if(letter.getReadState() == LetterInterface.READ_STATE_NOT_READED){
        letter.setReadState(LetterInterface.READ_STATE_READED);
        letter.setOperateDate(DateUtil.getNowDate());
        letter.setOperateTime(DateUtil.getNowTime());
        letter.setOperateIp(IPAddressUtil.getIPAddress(request));
        LetterDao.updateLetter(letter);
    }
    User fromUser = UserDao.getUserById(letter.getFromUserId());
%>
<html>
<head>
    <title>查看站内信</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/showLetter.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"> </script>
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
        //站内信id
        var letterId = <%=id%>;
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
            <ul id="main-nav">
                <li><a href="#" class="nav-top-item"> 用户模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>userManage.jsp">用户管理</a></li>
                        <li><a href="<%=baseUrl%>user.jsp?id=<%=user.getId()%>">个人展示</a></li>
                        <li><a href="<%=baseUrl%>userOperate.jsp">后台用户管理</a></li>
                        <li><a href="<%=baseUrl%>contacts.jsp">通讯录</a></li>
                        <li><a href="<%=baseUrl%>orgStructureManage.jsp">组织架构管理</a></li>
                    </ul>
                </li>
                <li><a href="#" class="nav-top-item current"> 消息模块 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>notice.jsp">公告</a></li>
                        <li><a href="<%=baseUrl%>configNotice.jsp">公告管理</a></li>
                        <li><a href="<%=baseUrl%>message.jsp">消息</a></li>
                        <li><a href="<%=baseUrl%>letter.jsp" class="current">站内信</a></li>
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
                        <li><a href="<%=baseUrl%>sms.jsp">短信</a></li>
                    </ul>
                </li>
            </ul>
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
                <h3>查看站内信</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab2" class="default-tab">Forms</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab2">
                    <div>
                        <input class="button" type="button" onclick="reply();" value="回复" />
                        <input class="button" type="button" onclick="replyAll();" value="回复全部" />
                        <input class="button" type="button" onclick="transmit();" value="转发" />
                        <input class="button" type="button" onclick="deleteLetter();" value="删除" />
                        <input class="button" type="button" onclick="ctrlDeleteLetter();" value="彻底删除" />
                    </div>
                    <div>
                        <table>
                            <tr>
                                <td>
                                    <div align="center">
                                        标题：<b><%=letter.getTitle()%></b>
                                        <input id="detail_button" class="button" type="button" onclick="showDetail();" value="+" />
                                    </div>
                                </td>
                            </tr>
                            <tr style="display: none;" class="detail_tr">
                                <td>
                                    发件人：<a href="<%=baseUrl%>user.jsp?id=<%=fromUser.getId()%>" target="_blank"><%=fromUser.getName()%></a>
                                </td>
                            </tr>
                            <tr style="display: none;" class="detail_tr">
                                <td>
                                    收件人：<%=BaseUtil.displayUsersByIds(request,letter.getToUserIds())%>
                                </td>
                            </tr>
                                <%
                                    if(StringUtils.isNotBlank(letter.getCcUserIds())){
                                %>
                            <tr style="display: none;" class="detail_tr">
                                <td>
                                    抄送人：<%=BaseUtil.displayUsersByIds(request,letter.getCcUserIds())%>
                                </td>
                            </tr>
                                <%
                                    }
                                %>
                            <tr style="display: none;" class="detail_tr">
                                <td>
                                    时间：<%=letter.getCreateDate() + " " + letter.getCreateTime()%>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div id="editor">
                        <%=letter.getContent()%>
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