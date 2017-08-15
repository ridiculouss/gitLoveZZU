<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>话题圈</title>
<style type="text/css">
body {
	text-align: center;
	
}
</style>
</head>
<body>

	<br/>
	<form action="TopicCommentAction" method="post">
	TopicId:<input type="text" name="TopicId" /><br/>
	TopicComment:<input type="text" name="TopicComment" /><br/>
	SessionID:<input type="text" name="SessionID" /><br/>
	ThumbNum:<input type="text" name="ThumbNum" /><br/>
	action:<input type="text" name="action" value="发布话题评论"/>话题点赞<br/>
	<input type="submit" value="提交"/>
	
	</form>

</body>
</html>