<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新闻</title>
<style type="text/css">
body{text-align: center;}
</style>
</head>
<body>



<s:iterator value="imglist">
<img alt="" src="<s:property value="img" />">
</s:iterator>
<br/>
<s:iterator value="titlelist">
<strong><s:property value="title" /><br/></strong>
</s:iterator>
<br/>
<s:iterator value="textlist">
<s:property value="text" /><br/>
</s:iterator>
</body>
</html>