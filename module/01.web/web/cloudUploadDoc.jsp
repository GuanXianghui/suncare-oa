<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //外层
    outLayer = "申成云";
    //内层
    inLayer = "申成文库";
%>
<html>
<head>
    <title>申成文库</title>
    <script type="text/javascript" src="<%=baseUrl%>scripts/jquery-min.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/base.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudDocBase.js"></script>
    <script type="text/javascript" src="<%=baseUrl%>scripts/cloudUploadDoc.js"></script>
    <!-- 页面样式 -->
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="scripts/facebox.js"></script>
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

        <form name="cloudQueryDocForm" action="cloudDoc.jsp" method="post">
            <input type="hidden" name="doc" id="cloudQueryDocName">
        </form>

        <form onsubmit="return false;">
            <fieldset>
                <p>
                    <span>申成文库</span>&nbsp;&nbsp;
                    <input class="text-input small-input" type="text" id="doc_name"/>&nbsp;&nbsp;
                    <input class="button" type="button" onclick="queryDoc($('#doc_name').val());" value="查询" />&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudUploadDoc.jsp'" value="贡献我的文档" />&nbsp;&nbsp;
                    <input class="button" type="button" onclick="location.href='cloudMyDoc.jsp'" value="我的文档" />
                </p>
                <table id="doc_list"></table>
            </fieldset>
            <div class="clear"></div>
        </form>

        <div class="content-box">
            <div class="content-box-header">
                <h3>贡献我的文档</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab" class="default-tab">文档</a></li>
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
                    <form action="<%=baseUrl%>cloudUploadDoc.do?token=<%=token%>" name="cloudUploadDocFrom"
                          method="post" autocomplete="off" enctype="multipart/form-data">
                        <fieldset>
                            <p>
                                <span><span style="color:red;">*</span>标题</span>&nbsp;&nbsp;
                                <input class="text-input medium-input" name="title" type="text" id="title"
                                        onfocus="focusTitle(this)" onblur="blurTitle(this)" />
                            </p>
                            <p>
                                <span>&nbsp;简介</span>&nbsp;&nbsp;
                                <textarea class="text-input medium-input" name="description" id="description"
                                          type="text" onfocus="focusDescription(this)" onblur="blurDescription(this)"></textarea>
                            </p>
                            <p>
                                <span><span style="color:red;">*</span>分类</span>&nbsp;&nbsp;
                                <span id="show_type"></span>
                                <input name="type" type="hidden" id="type"/>
                            </p>
                            <p>
                                <span>&nbsp;标签</span>&nbsp;&nbsp;
                                <input class="text-input medium-input" name="tags" type="text" id="tags"
                                       onfocus="focusTags(this)" onblur="blurTags(this)"/>
                            </p>
                            <p>
                                <span>&nbsp;文档</span>&nbsp;&nbsp;
                                <input type="file" name="doc" id="doc" onchange="changeDoc()">
                                <br>
                                &nbsp;支持文件大小<=200M，文件类型：
                                <img src="images/cloud_doc/doc.jpg" width="19px">doc,docx
                                <img src="images/cloud_doc/ppt.jpg" width="19px">ppt,pptx
                                <img src="images/cloud_doc/xls.jpg" width="19px">xls,xlsx
                                <img src="images/cloud_doc/vsd.jpg" width="19px">vsd
                                <img src="images/cloud_doc/pot.jpg" width="19px">pot
                                <img src="images/cloud_doc/pps.jpg" width="19px">pps
                                <img src="images/cloud_doc/rtf.jpg" width="19px">rtf
                                <img src="images/cloud_doc/wps.jpg" width="19px">wps
                                <img src="images/cloud_doc/et.jpg" width="19px">et
                                <img src="images/cloud_doc/dps.jpg" width="19px">dps
                                <img src="images/cloud_doc/pdf.jpg" width="19px">pdf
                                <img src="images/cloud_doc/txt.jpg" width="19px">txt
                                <img src="images/cloud_doc/epub.jpg" width="19px">epub
                            </p>
                            <p>
                                <input class="button" type="button" onclick="cloudUploadDoc()" value="上传"/>
                            </p>
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