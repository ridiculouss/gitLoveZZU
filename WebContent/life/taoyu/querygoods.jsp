<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<style type="text/css">

body{
    font-size:18px;
    color: white;
}

</style>
<body>
<center>
<p>分类查询和关键字查询商品</p>
<form action="querygoodsAction" method="post">

action:<input type="text"name="action"/><br/><br/>
num:<input type="text"name="num"/><br/><br/>
<input type="submit" value="提交">
</form>
<br/>

</center>
</body>
</html>