<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>树洞评论</title>
<style type="text/css">
body {
	 font-size:18px;
    color: white;
	text-align: center;
}
</style>
</head>
<body>
<p>树洞评论</p>
	<form action="TreeHoleCommentAction" method="post">
		SessionID<input type="text" name="SessionID" /><br /> 
		<br /> treeHoleId<input type="text" name="treeHoleId" /><br />
		commentContent<input type="text" name="commentContent" /><br /> 
		action<input type="text" name="action" value="发布树洞评论" /><br />
		 <input type="submit" value="提交">
	</form>
</body>
</html>