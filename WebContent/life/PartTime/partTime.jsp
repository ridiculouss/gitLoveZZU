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
<p>兼职</p>
	<form action="PartTimeAction" method="post">
		 title<input type="text" name="title" /><br />
		content<input type="text" name="content" /><br />
		 salary<input type="text" name="salary" /><br />
		 startDate<input type="text" name="startDate" /><br />
		 endDate<input type="text" name="endDate" /><br />
		 startTime<input type="text" name="startTime" /><br />
		 endTime<input type="text" name="endTime" /><br />
		 workPlace<input type="text" name="workPlace" /><br />
		 contactWay<input type="text" name="contactWay" /><br />
		 campus<input type="text" name="campus" value="校内"/><br />
		 SessionID<input type="text" name="SessionID" value="5bf3d184-13ea-4814-ae7e-a1f9d99adef3"/><br />
		  action<input type="text" name="action" value="发布兼职" /><br /> 
		  search<input type="text" name="search"  /><br /> 
		  Id<input type="text" name="Id"  /><br /> 
		  <input type="submit" value="提交">
	</form>
</body>
</html>