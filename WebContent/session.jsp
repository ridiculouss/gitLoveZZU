<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<% int i=0; %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
body {
	text-align: center;
}
</style>
</head>
<body>
<s:form action="sessionaction_setsession" namespace="/">
<s:textfield name="content">输入内容</s:textfield>
<s:submit>发送</s:submit>
</s:form>
<s:form action="sessionaction_getsession" namespace="/">
<s:textfield name="content">输入内容</s:textfield>
<s:submit>发送</s:submit>
</s:form>





</body>
</html>