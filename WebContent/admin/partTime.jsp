<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html >
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>Insert title here</title>
<style type="text/css">
body{
text-align: center;
}
</style>


</head>
<body>
	
		<p>兼职审核页面</p>
		<form action="adminAction_QueryPartTime" method="post" >
		<input type="hidden" name="action" value="搜索兼职"/>
			<font size="5"> 搜索:<input type="text" name="SearchGoods"
				placeholder="请输入兼职标题关键语句，空则查询所有兼职" style="width: 250px; height: 40px;" />
				<input type="submit" value="提交" style="width: 50px; height: 30px;" />
			</font>
		</form>
		<br/>
		
		<s:actionmessage cssStyle="color:green"/>
			<s:action name="adminAction_QueryPartTime" executeResult="true"
			namespace="/">
			<s:param name="action">查询待审核兼职</s:param>
		</s:action>


	

</body>
</html>