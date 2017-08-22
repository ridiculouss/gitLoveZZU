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
<p>一起玩群组动态评论</p>
	<form  method="post" action="DynamicCommentAction">
		comment:<input type="text" name="comment" /><br>
		dynamicId：<input type="text" name="dynamicId" /><br>
		
		action：<input type="text" name="action" value="发表动态评论"/><br>
		SessionID：<input type="text" name="SessionID" value="f3dbf132-63d6-4263-90b4-85f5c4adfa94"/><br>  
		
        <input type="submit" value="提交" />
	</form>
	<br /><br />
<p>查询一起玩群组动态评论</p>
	<form  method="post" action="DynamicCommentAction">
		
		dynamicId：<input type="text" name="dynamicId" /><br>
		
		action：<input type="text" name="action" value="查询动态评论"/><br>
		
        <input type="submit" value="提交" />
	</form>

	
</body>
</html>