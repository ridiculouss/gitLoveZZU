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
<p>一起玩群组动态</p>
	<form  method="post" action="GroupDynamicAction">
		talk:<input type="text" name="talk" /><br>
		groupId：<input type="text" name="groupId" /><br>
		
		action：<input type="text" name="action" value="发表说说"/><br>
		SessionID：<input type="text" name="SessionID" value="f988fdc1-135f-4ae7-901e-def8d2c913e2"/><br>  
		
        <input type="submit" value="提交" />
	</form>
	<br /><br />
<p>查询一起玩群组动态</p>
	<form  method="post" action="GroupDynamicAction">
		num:<input type="text" name="num" /><br>
		groupId：<input type="text" name="groupId" /><br>
		SessionID：<input type="text" name="SessionID" /><br>
		
		action：<input type="text" name="action" value="查询群动态"/><br>
		
        <input type="submit" value="提交" />
	</form>
	<br /><br />
	<s:form action="imagesupload" namespace="/"
		enctype="multipart/form-data" method="post" theme="simple">

		
file:<s:file name="images" lable="选择文件"></s:file>
		<br />
		<s:file name="images" label="选择文件" />
		<br />
dynamicId <s:textfield name="dynamicId "></s:textfield>
		<br />
action指令<s:textfield name="action" value="上传群组动态图片"></s:textfield>
		<br />
		<br />
		<s:submit name="submit" value="上传文件"></s:submit>
	</s:form>
	
	
</body>
</html>