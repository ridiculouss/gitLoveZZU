
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin.css"/>
<title>管理员</title>
</head>
<script type="text/javascript">
function out() {
	 window.location.href="adminloginAction_loginout";
}

</script>
<body>
<% String s=null;
   s=(String)session.getAttribute("admin");
if(s!=null && !s.equals("已登录")){
	System.out.print("页面检测到已退出");
	response.sendRedirect("adminlogin.jsp");
}
%>
<div class="top"> 
top

<div class="top-right"><br/>

<%=session.getAttribute("admin")%>
&nbsp;&nbsp;
<a  style=background-color:#cf6d77; href="javascript:out();">退出</a>
</div>
</div>
<div class="left">left</div>
<div class="right">right</div>
<div class="bottom" >bottom</div>
</body>
</html>