<%@ page import="com.gxx.oa.interfaces.CloudKnowAskInterface" %>
<%@ page import="com.gxx.oa.entities.CloudKnowAsk" %>
<%@ page import="com.gxx.oa.dao.CloudKnowAskDao" %>
<%@ page import="com.gxx.oa.dao.UserDao" %>
<%@ page import="com.gxx.oa.entities.CloudKnowAnswer" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gxx.oa.dao.CloudKnowAnswerDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //外层
    outLayer = "申成云";
    //内层
    inLayer = "申成知道";
    //申成知道提问
    CloudKnowAsk cloudKnowAsk;
    //提问人
    User cloudUser;
    //是否自己的问题
    boolean isMine;
    //回答集合
    List<CloudKnowAnswer> cloudKnowAnswers;
    try{
        int id = Integer.parseInt(request.getParameter("id"));
        cloudKnowAsk = CloudKnowAskDao.getCloudKnowAskById(id);
        if(cloudKnowAsk == null || cloudKnowAsk.getState() != CloudKnowAskInterface.STATE_NORMAL){
            throw new RuntimeException("找不到该申成知道提问！");
        }
        isMine = cloudKnowAsk.getUserId() == user.getId();
        cloudUser = UserDao.getUserById(cloudKnowAsk.getUserId());
        cloudKnowAnswers = CloudKnowAnswerDao.queryCloudKnowAnswersByAskId(cloudKnowAsk.getId());
    } catch (Exception e){
        response.sendRedirect("cloudKnow.jsp");
        return;
    }
%>
<html>
<head>
    <title>申成文库</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudKnowBase.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudViewKnow.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <%--<script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/lang/zh-cn/zh-cn.js"></script>--%>
    <script type="text/javascript" charset="utf-8" src="<%=baseUrl%>ueditor/ueditor.parse.min.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="scripts/facebox.js"></script>
    <script type="text/javascript">
        //问题id
        var askId = <%=cloudKnowAsk.getId()%>;
        //初始类型
        var initType = "<%=cloudKnowAsk.getType()%>";
        /**
         * 初始问题补充
         *
         * 注意这里ueditor提交的内容不会有单引号，只有双引号
         * 这里如果用双引号括起来会有问题，所以改成单引号
         */
        var initDescription = '<%=cloudKnowAsk.getDescription()%>';
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

        <form name="cloudQueryAskForm" action="cloudKnow.jsp" method="post">
            <input type="hidden" name="ask" id="cloudQueryAsk">
        </form>

        <form name="cloudKnowDeleteAskForm" action="cloudKnowDeleteAsk.do" method="post">
            <input type="hidden" name="token" value="<%=token%>">
            <input type="hidden" name="askId" value="<%=cloudKnowAsk.getId()%>">
        </form>

        <form name="cloudKnowDeleteAnswerForm" action="cloudKnowDeleteAnswer.do" method="post">
            <input type="hidden" name="token" value="<%=token%>">
            <input type="hidden" name="answerId" id="answerId">
        </form>

        <form name="cloudKnowZanAnswerForm" action="cloudKnowZanAnswer.do" method="post">
            <input type="hidden" name="token" value="<%=token%>">
            <input type="hidden" name="answerId" id="cloudKnowZanAnswerId">
        </form>

        <form onsubmit="return false;">
            <fieldset>
                <p>
                    <span>申成知道</span>&nbsp;&nbsp;
                    <input class="text-input small-input" type="text" id="ask"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="queryAsk($('#ask').val())" value="搜索答案"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudKnowAsk.jsp'" value="我要提问"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudKnowMyAsk.jsp'" value="我的提问"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudKnowMyAnswer.jsp'" value="我的回答"/>
                </p>
            </fieldset>
            <div class="clear"></div>
        </form>

        <form style="display: none;" action="<%=baseUrl%>cloudKnowAnswer.do" name="cloudKnowAnswerFrom" method="post">
            <input type="hidden" name="token" value="<%=token%>">
            <input type="hidden" name="askId" value="<%=cloudKnowAsk.getId()%>">
            <textarea id="answer" name="answer"></textarea>

            <input type="hidden" name="type" id="cloudKnowAnswerType" value="insert">
            <input type="hidden" name="answerId" id="cloudKnowAnswerAnswerId">
        </form>

        <div class="content-box">
            <div class="content-box-header">
                <h3>查看提问</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab" class="default-tab">提问</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab">
                    <div id="message_id" class="notification information png_bg" style="display: none;">
                        <a href="#" class="close">
                            <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
                        </a>

                        <div id="message_id_content"> 提示信息！</div>
                    </div>
                    <form>
                        <fieldset>
                            <p>
                                <span>提问人:</span>&nbsp;&nbsp;
                                <a href="user.jsp?id=<%=cloudUser.getId()%>" target="_blank"><%=cloudUser.getName()%></a>
                            </p>

                            <p>
                                <span>问题:</span>&nbsp;&nbsp;<%=cloudKnowAsk.getQuestion()%>
                                <%
                                    if(isMine){
                                %>
                                <input class="button" type="button" onclick="location.href='cloudKnowUpdateAsk.jsp?id=<%=cloudKnowAsk.getId()%>'" value="修改"/>
                                <input class="button" type="button" onclick="cloudKnowDeleteAsk()" value="删除"/>
                                <%
                                    }
                                %>
                            </p>

                            <p id="description" style="display: <%=StringUtils.isBlank(cloudKnowAsk.getDescription())?"none":""%>;">
                                <span>补充:</span>
                                <div id="description_content" style="overflow: auto;">
                                    <%=cloudKnowAsk.getDescription()%>
                                </div>
                            </p>

                            <p>
                                <span>分类:</span>&nbsp;&nbsp;
                                <span id="show_type"></span>
                                <input name="type" type="hidden" id="type"/>
                            </p>

                            <p>
                                <span>紧急:</span>&nbsp;&nbsp;<%=cloudKnowAsk.getUrgent()==CloudKnowAskInterface.URGENT_NO?"不紧急":"紧急"%>
                            </p>

                            <p>
                                <span>提问时间:</span>&nbsp;&nbsp;<%=cloudKnowAsk.getCreateDate() + " " + cloudKnowAsk.getCreateTime()%>
                            </p>

                            <p>
                                <a name="writeAnswerButtonA"></a>
                                <input id="writeAnswerButton" class="button" type="button" onclick="showAnswer(this)" value="展开回答"/>
                                <span id="editor_div" style="display: none">
                                    <script id="editor" type="text/plain"></script>
                                    <input class="button" type="button" onclick="cloudKnowAnswer()" value="回答"/>
                                </span>
                            </p>

                            <p>
                                共<%=cloudKnowAnswers.size()%>个回答
                            </p>

                            <table id="cloud_list">
                                <%
                                    if(cloudKnowAnswers.size() == 0){
                                %>
                                <tr><td>暂时还没有人回答过！</td></tr>
                                <%
                                    }
                                    for(CloudKnowAnswer cloudKnowAnswer : cloudKnowAnswers){
                                        User cloudKnowAnswerUser = UserDao.getUserById(cloudKnowAnswer.getUserId());
                                %>
                                <tr id="answer_tr_<%=cloudKnowAnswer.getId()%>">
                                    <td id="answer_td_<%=cloudKnowAnswer.getId()%>">
                                        <div id="answer_<%=cloudKnowAnswer.getId()%>" class="answer_content" style="overflow: auto;">
                                            <%=cloudKnowAnswer.getAnswer()%>
                                        </div>
                                        <span style="color: gray">
                                            <%=cloudKnowAnswer.getCreateDate()%>
                                            <%=cloudKnowAnswer.getCreateTime()%>
                                            | 回答者：<a href="user.jsp?id=<%=cloudKnowAnswerUser.getId()%>" target="_blank"><%=cloudKnowAnswerUser.getName()%></a>
                                            | 该回答获得<%=cloudKnowAnswer.getZan()%>个赞
                                            <input class="button" type="button" onclick="cloudKnowZanAnswer(<%=cloudKnowAnswer.getId()%>)" value="赞"/>
                                            <%
                                                if(user.getId() == cloudKnowAnswerUser.getId()){
                                            %>
                                            <input class="button" type="button" onclick="showCloudKnowUpdateAnswer(<%=cloudKnowAnswer.getId()%>)" value="修改"/>
                                            <input class="button" type="button" onclick="cloudKnowDeleteAnswer(<%=cloudKnowAnswer.getId()%>)" value="删除"/>
                                            <%
                                                }
                                            %>
                                        </span>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                            </table>
                        </fieldset>
                        <div class="clear"></div>
                    </form>
                </div>
            </div>
        </div>

        <div id="footer">
            <small>
                &#169; Copyright 2014 Suncare | Powered by 关向辉
            </small>
        </div>
    </div>
</div>
</body>
</html>