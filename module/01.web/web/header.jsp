<%@ page import="com.gxx.oa.utils.PropertyUtil" %>
<%@ page import="com.gxx.oa.interfaces.BaseInterface" %>
<%@ page import="com.gxx.oa.utils.TokenUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //域名链接
    String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
    //md5 key
    String md5Key = PropertyUtil.getInstance().getProperty(BaseInterface.MD5_KEY);
    //token串
    String token = TokenUtil.createToken(request);
%>
<script type="text/javascript">
    //域名链接
    var baseUrl = "<%=baseUrl%>";
    //md5 key
    var md5Key = "<%=md5Key%>";
    //token穿
    var token = "<%=token%>";
</script>