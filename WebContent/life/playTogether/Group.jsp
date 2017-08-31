<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html ">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
body{text-align: center;}
</style>
</head>
<body>
<p>一起玩群组</p>
	<form  method="post" action="GroupAction">
		name:<input type="text" name="name" /><br>
		introduce：<input type="text" name="introduce" /><br>
		label：<input type="text" name="label" /><br>  
		action：<input type="text" name="action" /><br>
		SessionID：<input type="text" name="SessionID" /><br>  
		groupId：<input type="text" name="groupId" /><br>  
		
        <input type="submit" value="提交" />
	</form>
	<br /><br />
<p>一起玩查询群组</p>
	<form  method="post" action="GroupAction">
		num:<input type="text" name="num" /><br>
	
		action：<input type="text" name="action" value="查询群组"/><br>
		 
		
        <input type="submit" value="提交" />
	</form>
	<br /><br />
	<s:form action="imagesupload" namespace="/"
		enctype="multipart/form-data" method="post" theme="simple">

		
file:<s:file name="images" lable="选择文件"></s:file>
		<br />
		<s:file name="images" label="选择文件" />
		<br />
		<br />
groupId <s:textfield name="groupId "></s:textfield>
		

		<br />
action指令<s:textfield name="action" value="上传群组图片"></s:textfield>
		<br />

		<br />
		<s:submit name="submit" value="上传文件"></s:submit>
	</s:form>
	
	
</body>
</html>