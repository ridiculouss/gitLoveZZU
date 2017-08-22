<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>兼职详情</title>
<style type="text/css">

body{text-align: center;}
td{text-align: center; /*设置水平居中*/
	vertical-align: middle; /*设置垂直居中*/
	}
</style>

</head>
<body>
<center>

         <s:actionmessage/>
		<s:iterator value="partTime">
			<table border="1">
				<tr>
					<td>待审核兼职信息</td>
					<td><a href="adminAction_QueryPartTime?action=approved&partTimeId=<s:property value="partTimeId" />">通过</a>&nbsp;
					<a href="adminAction_QueryPartTime?action=notapproved&partTimeId=<s:property value="partTimeId" />">不通过</a></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">兼职ID:</font></td>
					<td><s:property value="partTimeId" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">兼职标题:</font></td>
					<td><s:property value="title" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">兼职详情:</font></td>
					<td style="width:400px;"><s:property value="content" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">兼职薪资:</font></td>
					<td><s:property value="salary" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">开始日期:</font></td>
					<td><s:property value="startDate" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">结束日期:</font></td>
					<td><s:property value="endDate" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">上班时间:</font></td>
					<td><s:property value="startTime" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">下班时间:</font></td>
					<td><s:property value="endTime" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">工作地点:</font></td>
					<td><s:property value="workPlace" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">联系方式:</font></td>
					<td><s:property value="contactWay" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">校内/校外:</font></td>
					<td><s:property value="campus" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">发布时期:</font></td>
					<td><s:property value="publishDate" /></td>
				</tr>
				<tr>
					<td><font color="#1C86EE;">审核状态:</font></td>
					<td><font color="red;"><s:property value="status" /></font></td>
				</tr>


			</table>
			
			<P style="width: 80%;">✈----------✈-----------✈--------✈-------✈---------✈</P>
		</s:iterator>
	

</center>
</body>
</html>