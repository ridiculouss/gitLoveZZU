<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
body {
	 font-size:18px;
    color: white;
	text-align: center;
}
</style>
</head>
<body>
<p>表白卡评论</p>
	<form action="LoveCardCommentAction" method="post">
		 SessionID<input type="text" name="SessionID" /><br />
		loveCardId<input type="text" name="loveCardId" /><br />
		 commentContent<input type="text" name="commentContent" /><br />
		
		  action<input type="text" name="action" value="发布表白评论" /><br /> 
		  <input type="submit" value="提交">
	</form>
</body>
</html>