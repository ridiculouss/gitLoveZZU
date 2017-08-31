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
<p>表白卡</p>
	<form action="LoveCardAction" method="post">
		 senderName<input type="text" name="senderName" /><br />
		lovedName<input type="text" name="lovedName" /><br />
		 loveContent<input type="text" name="loveContent" /><br />
		 search<input type="text" name="search" /><br />
		  action<input type="text" name="action" value="发布表白" /><br /> 
		  <input type="submit" value="提交">
	</form>
</body>
</html>