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
<p>树洞</p>
	<form action="TreeHoleAction" method="post">
		<br /> treeHoleContent<input type="text" name="treeHoleContent" /><br />
		SessionID<input type="text" name="SessionID" /><br /> campus<input
			type="text" name="campus" /><br /> action<input type="text"
			name="action" value="发布树洞" /><br /> <input type="submit" value="提交">
	</form>
</body>
</html>