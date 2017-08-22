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
	 font-size:18px;
    color: white;
}
</style>
</head>
<body>

	<form action="ThemeAction" method="post">
		ThemeTitle:<input type="text" name="ThemeTitle" /><br /> SessionID:<input
			type="text" name="SessionID" />9217a753-8148-4552-89e8-86e39adad713<br />
		action<input type="text" name="action" /><br /> <input type="submit"
			value="发布主题" />
	</form>
	<br />
	<br />
	<s:form action="imagesupload" namespace="/"
		enctype="multipart/form-data" method="post" theme="simple">
		<s:token></s:token>


		<s:fielderror name="image"></s:fielderror>

		<br />
file:<s:file name="images" lable="选择文件"></s:file>
		<br />
		<s:file name="images" label="选择文件" />
		<br />
		<br />
ThemeId <s:textfield name="ThemeId "></s:textfield>
		<br />
TopicId<s:textfield name="TopicId"></s:textfield>
		<br />
action指令<s:textfield name="action" value="上传主题图片"></s:textfield>
		<br />

		<br />
		<s:submit name="submit" value="上传文件"></s:submit>
	</s:form>
	<br/>
	<form action="ThemeAction" method="post">
	action:<input type="text" name="action" value="查询所有主题"/><br/>
	<input type="submit" value="查询所有主题"/>
	
	</form>

</body>
</html>