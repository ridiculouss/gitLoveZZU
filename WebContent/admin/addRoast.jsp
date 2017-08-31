<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<center>
 <s:actionerror cssStyle="color:red"/><br/>
<s:form action="RoastingAction" method="post"  namespace="/" >
<s:textfield name="imageUrl" label="图片链接,多个链接使用ZZU分隔拼接"/>
<s:textfield name="newsUrl" label="新闻链接，同上"/>
<s:textfield name="moduleIdentifier" label="模块轮播标识:首页/生活"/>
<s:hidden name="action" value="设置轮播"></s:hidden>
<s:submit value="更新轮播"></s:submit>
</s:form>
<br/><br/>
<a href="RoastingAction?action=查询">查看轮播已有信息</a>
<br/>
<s:iterator value="list">
图片url:<s:property value="imageUrl"/><br/>
新闻url:<s:property value="newsUrl"/><br/>
模块:<s:property value="moduleIdentifier"/>
<br/><br/><br/>
</s:iterator>
</center>
</body>
</html>