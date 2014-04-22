<%@ page import="java.util.List" %>
<%@ page import="com.gxx.file.dao.FilesDao" %>
<%@ page import="com.gxx.file.entities.Files" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="header.jsp" %>
<%
    //文档名字
    String name = StringUtils.trimToEmpty(request.getParameter("name"));

    //当前页数
    String pageStr = StringUtils.trimToEmpty(request.getParameter("pageNum"));
    //当前页数
    int pageNum;
    try {
        pageNum = Integer.parseInt(pageStr);
    } catch (Exception e) {
        pageNum = 1;
    }

    String fileNum = StringUtils.trimToEmpty(request.getParameter("fileNum"));
    String projectName = StringUtils.trimToEmpty(request.getParameter("projectName"));
    String doorSeries = StringUtils.trimToEmpty(request.getParameter("doorSeries"));
    String glassType = StringUtils.trimToEmpty(request.getParameter("glassType"));
    String wind = StringUtils.trimToEmpty(request.getParameter("wind"));
    String air = StringUtils.trimToEmpty(request.getParameter("air"));
    String water = StringUtils.trimToEmpty(request.getParameter("water"));
    String temperature = StringUtils.trimToEmpty(request.getParameter("temperature"));
    String voice = StringUtils.trimToEmpty(request.getParameter("voice"));
    String sun = StringUtils.trimToEmpty(request.getParameter("sun"));
    String perspective = StringUtils.trimToEmpty(request.getParameter("perspective"));
    String dewPoint = StringUtils.trimToEmpty(request.getParameter("dewPoint"));


    //文档列表每页大小
    int pageSize = Integer.parseInt(PropertyUtil.getInstance().getProperty(BaseInterface.FILES_PAGE_SIZE));
    //文档总数
    int count = FilesDao.countFilesByLikeColumns(name, fileNum, projectName, doorSeries, glassType, wind, air, water,
            temperature, voice, sun, perspective, dewPoint);
    //是否为空
    boolean isEmpty = count == 0;
    //总页数
    int pageCount = (count - 1) / pageSize + 1;
    //删除最后一条，可能会少掉一页
    if(pageNum > pageCount){
        pageNum = pageCount;
    }
    //文档列表
    List<Files> files = FilesDao.queryFilesByLikeColumns(name, fileNum, projectName, doorSeries, glassType, wind, air,
            water, temperature, voice, sun, perspective, dewPoint, pageNum);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
    <title>申成-文件系统</title>
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/style.css" type="text/css" media="screen"/>
    <link rel="stylesheet" href="css/invalid.css" type="text/css" media="screen"/>
    <script type="text/javascript" src="scripts/jquery-1.3.2.min.js"></script>
    <script type="text/javascript" src="scripts/simpla.jquery.configuration.js"></script>
    <script type="text/javascript" src="scripts/facebox.js"></script>
    <script type="text/javascript" src="scripts/jquery.wysiwyg.js"></script>
    <script type="text/javascript" src="scripts/jquery.datePicker.js"></script>
    <script type="text/javascript" src="scripts/jquery.date.js"></script>
    <script type="text/javascript" src="scripts/base.js"></script>
    <script type="text/javascript" src="scripts/files.js"></script>
    <script type="text/javascript">
        //当前页数
        var pageNum = <%=pageNum%>;
    </script>
</head>
<body>
<div id="body-wrapper">
    <div id="sidebar">
        <div id="sidebar-wrapper">
            <h1 id="sidebar-title"><a href="#">申成-文件系统</a></h1>
            <img id="logo" src="images/suncare-files-logo.png" alt="Simpla Admin logo"/>

            <div id="profile-links"> Hello, [<%=user.getName()%>], <a href="http://www.suncarechina.com"
                                                                      target="_blank">申成</a>欢迎您！<br/>
                <br/>
                <a href="javascript: logOut()" title="Sign Out">退出</a></div>
            <ul id="main-nav">
                <li><a href="#" class="nav-top-item"> 用户管理 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>main.jsp">用户查询</a></li>
                        <%if(isSuperMan){%>
                        <li><a href="<%=baseUrl%>createUser.jsp">新增用户</a></li>
                        <%}%>
                        <li><a href="<%=baseUrl%>updatePassword.jsp">修改密码</a></li>
                    </ul>
                </li>
                <li><a href="#" class="nav-top-item current"> 文档管理 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>files.jsp" class="current">查看文档</a></li>
                        <%if(isSuperMan){%>
                        <li><a href="<%=baseUrl%>createFiles.jsp">新建文档</a></li>
                        <%}%>
                    </ul>
                </li>
                <li><a href="#" class="nav-top-item"> 日志 </a>
                    <ul>
                        <li><a href="<%=baseUrl%>operateLog.jsp">查看日志</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    <div id="main-content">
        <form action="<%=baseUrl%>files.jsp" name="filesForm" method="post" style="display: none;">
            <input type="hidden" name="name" id="filesName" value="<%=name%>">
            <input type="hidden" name="fileNum" id="filesFileNum" value="<%=fileNum%>">
            <input type="hidden" name="projectName" id="filesProjectName" value="<%=projectName%>">
            <input type="hidden" name="doorSeries" id="filesDoorSeries" value="<%=doorSeries%>">
            <input type="hidden" name="glassType" id="filesGlassType" value="<%=glassType%>">
            <input type="hidden" name="wind" id="filesWind" value="<%=wind%>">
            <input type="hidden" name="air" id="filesAir" value="<%=air%>">
            <input type="hidden" name="water" id="filesWater" value="<%=water%>">
            <input type="hidden" name="temperature" id="filesTemperature" value="<%=temperature%>">
            <input type="hidden" name="voice" id="filesVoice" value="<%=voice%>">
            <input type="hidden" name="sun" id="filesSun" value="<%=sun%>">
            <input type="hidden" name="perspective" id="filesPerspective" value="<%=perspective%>">
            <input type="hidden" name="dewPoint" id="filesDewPoint" value="<%=dewPoint%>">
            <input type="hidden" name="pageNum" id="pageNum" value="1">
        </form>
        <form>
            <table>
                <tr style="background-color: white;">
                    <td>文档名称</td>
                    <td><input class="text-input" type="text" id="name"/></td>
                    <td>报告编号</td>
                    <td><input class="text-input" type="text" id="fileNum"/></td>
                    <td>工程名称</td>
                    <td><input class="text-input" type="text" id="projectName"/></td>
                </tr>
                <tr>
                    <td>门窗系列</td>
                    <td><input class="text-input" type="text" id="doorSeries"/></td>
                    <td>玻璃规格</td>
                    <td><input class="text-input" type="text" id="glassType"/></td>
                    <td>抗风压性能</td>
                    <td><input class="text-input" type="text" id="wind"/></td>
                </tr>
                <tr style="background-color: white;">
                    <td>气密性</td>
                    <td><input class="text-input" type="text" id="air"/></td>
                    <td>水密性</td>
                    <td><input class="text-input" type="text" id="water"/></td>
                    <td>保温性</td>
                    <td><input class="text-input" type="text" id="temperature"/></td>
                </tr>
                <tr>
                    <td>隔声性</td>
                    <td><input class="text-input" type="text" id="voice"/></td>
                    <td>遮阳系数</td>
                    <td><input class="text-input" type="text" id="sun"/></td>
                    <td>可见光透射比</td>
                    <td><input class="text-input" type="text" id="perspective"/></td>
                </tr>
                <tr style="background-color: white;">
                    <td>露点测试</td>
                    <td><input class="text-input" type="text" id="dewPoint"/></td>
                    <td><input class="button" type="button" onclick="queryFiles();" value="查询"/></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </form>
        <div class="content-box">
            <div class="content-box-header">
                <h3>文档列表(这些文档每天日终备份到百度云)</h3>
                <ul class="content-box-tabs">
                    <li><a href="#tab1" class="default-tab">Table</a></li>
                </ul>
                <div class="clear"></div>
            </div>
            <div class="content-box-content">
                <div class="tab-content default-tab" id="tab1">
                    <div id="message_id" class="notification information png_bg" style="display: none;">
                        <a href="#" class="close">
                            <img src="images/icons/cross_grey_small.png" title="关闭" alt="关闭"/>
                        </a>

                        <div id="message_id_content"> 提示信息！</div>
                    </div>
                    <table>
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>文档名称</th>
                            <th>报告编号</th>
                            <th>工程名称</th>
                            <th>门窗系列</th>
                            <th>玻璃规格</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tfoot>
                        <tr>
                            <td colspan="7">
                                <div class="pagination">
                                    <a href="javascript: jump2page(1)" title="首页">&laquo; 首页</a>
                                    <%
                                        if(pageNum > 1){
                                    %>
                                    <a href="javascript: jump2page(<%=pageNum-1%>)" title="上一页">&laquo; 上一页</a>
                                    <%
                                        }
                                    %>
                                    <%
                                        //显示前2页，本页，后2页
                                        for(int i=pageNum-2;i<pageNum+3;i++){
                                            if(i >= 1 && i <= pageCount){
                                    %>
                                    <a href="javascript: jump2page(<%=i%>)" class="number<%=(i==pageNum)?" current":""%>" title="<%=i%>"><%=i%></a>
                                    <%
                                            }
                                        }
                                    %>
                                    <%
                                        if(pageNum < pageCount){
                                    %>
                                    <a href="javascript: jump2page(<%=pageNum+1%>)" title="下一页">下一页 &raquo;</a>
                                    <%
                                        }
                                    %>
                                    <a href="javascript: jump2page(<%=pageCount%>)" title="尾页">尾页 &raquo;</a>
                                </div>
                                <div class="clear"></div>
                            </td>
                        </tr>
                        </tfoot>
                        <tbody>
                        <%
                            //判是否为空
                            if (isEmpty) {
                        %>
                        <tr>
                            <td colspan="7">
                                没找到符合条件的文档
                            </td>
                        </tr>
                        <%
                        } else {//非空
                            for (Files tempFiles : files) {
                        %>
                        <tr>
                            <td><%=tempFiles.getId()%></td>
                            <td><%=tempFiles.getName()%></td>
                            <td><%=tempFiles.getFileNum()%></td>
                            <td><%=tempFiles.getProjectName()%></td>
                            <td><%=tempFiles.getDoorSeries()%></td>
                            <td><%=tempFiles.getGlassType()%></td>
                            <td>
                                <a href="<%=baseUrl%>showFiles.jsp?filesId=<%=tempFiles.getId()%>" title="查看文档"><img
                                        src="images/icons/watch.png" width="16px" alt="查看文档"/></a>
                                <%if(isSuperMan){%>
                                <a href="<%=baseUrl%>updateFiles.jsp?filesId=<%=tempFiles.getId()%>" alt="修改文档"><img
                                        src="images/icons/hammer_screwdriver.png" alt="修改文档"/></a>
                                <a href="javascript: deleteFiles(<%=tempFiles.getId()%>);" title="删除"><img
                                        src="images/icons/cross.png" alt="删除"/></a>
                                <%}%>
                            </td>
                        </tr>
                        <%
                                }
                            }
                        %>

                        </tbody>
                    </table>
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
