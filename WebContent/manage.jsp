<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width = device-width,inital-scale=1">
<link rel="stylesheet" type="text/css" href="css/css.css" />
<title>接口测试页面</title>


</head>

<style type="text/css">
#abc a:link {
	color: #CC0000;
	text-decoration: none;
}

#abc a:visited {
	color: #CC0000;
	text-decoration: none;
}

#abc a:hover {
	color: white;
	text-decoration: none;
}

#abc a:active {
	text-decoration: none;
	color: #9494c5;
	text-decoration: none;
}

body {
	text-align: center;
	font-size: 18px;
	background: url(music/6.jpg) no-repeat;
	background-size: 100% 100%;
}

.div-b {
	width: 80%;
	height: 99%;
	margin-left: 10%;
	background: rgba(93, 93, 93, 0.5) !important;
	color: white;
	filter: Alpha(opacity = 60);
	background: #fff; /*　使用IE专属滤镜实现IE背景透明*/
}

.div-b p {
	position: relative;
}
</style>
<body>
	<div id="abc">
		<div class="top" style="text-align: left">


			<div class="dropdown">
				<button type="button" class="dropbtn">其他</button>
				<div class="dropdown-content">
					<a href="welcome.jsp" target="main1">首页</a>&nbsp; <a
						href="adminlogin.jsp">管理员</a>&nbsp; <a
						href="fileDownloadAndUpload/upload.jsp" target="main1">上传</a>&nbsp;
					<a href="fileDownloadAndUpload/download.jsp" target="main1">下载</a>&nbsp;

				</div>
			</div>
			<div class="dropdown">
				<button type="button" class="dropbtn">用户模块</button>
				<div class="dropdown-content">
					<a href="persionalCenter/user/user_login.jsp" target="main1">登录</a>
					<a href="persionalCenter/user/user_register.jsp" target="main1">注册</a>
					<a href="persionalCenter/user/uinfo_change.jsp" target="main1">更新用户信息</a>
					<a href="persionalCenter/user/uinfo_query.jsp" target="main1">查询用户信息</a>
				</div>
			</div>

			<div class="dropdown">
				<button type="button" class="dropbtn">淘渔模块</button>
				<div class="dropdown-content">
					<a href="life/taoyu/publishgoods.jsp" target="main1">发布商品</a>&nbsp;
					<a href="life/taoyu/querygoods.jsp" target="main1">查询商品</a>&nbsp; <a
						href="life/taoyu/comments.jsp" target="main1">商品评论</a>&nbsp; <a
						href="life/taoyu/cart.jsp" target="main1">购物车</a>&nbsp;
				</div>
			</div>
			<div class="dropdown">
				<button type="button" class="dropbtn">话题圈模块</button>
				<div class="dropdown-content">
					<a href="life/myTopicCircle/theme.jsp" target="main1">主题</a>&nbsp;
					<a href="life/myTopicCircle/Topic.jsp" target="main1">话题题</a>&nbsp;
					<a href="life/myTopicCircle/TopicComment.jsp" target="main1">话题评论</a>&nbsp;
				</div>
			</div>
			
			<div class="dropdown">
				<button type="button" class="dropbtn">兼职模块</button>
				<div class="dropdown-content">
					<a href="life/PartTime/partTime.jsp" target="main1">兼职</a>&nbsp;
				</div>
			</div>
			
			<div class="dropdown">
				<button type="button" class="dropbtn">树洞模块</button>
				<div class="dropdown-content">
					<a href="life/treeHole/Treehole.jsp" target="main1">树洞</a>&nbsp;
					<a href="life/treeHole/TreeholeComment.jsp" target="main1">树洞评论</a>&nbsp;
				</div>
			</div>
			<div class="dropdown">
				<button type="button" class="dropbtn">表白墙模块</button>
				<div class="dropdown-content">
					<a href="persionalCenter/confessionWall/loveCard.jsp" target="main1">表白卡</a>&nbsp;
					<a href="persionalCenter/confessionWall/loveCardComment.jsp" target="main1">表白卡评论</a>&nbsp;
					
				</div>
			</div>
			<div class="dropdown">
				<button type="button" class="dropbtn">一起玩模块</button>
				<div class="dropdown-content">
					<a href="life/playTogether/Group.jsp" target="main1">群组</a>&nbsp;
					<a href="life/playTogether/GroupDynamic.jsp" target="main1">群组动态</a>&nbsp;
					<a href="life/playTogether/GroupDynamicComment.jsp" target="main1">群组动态评论</a>&nbsp;
					
				</div>
			</div>
			
		</div>





		<div class="main">

			<div class="div-b">
				<iframe src="welcome.jsp" width="100%" height="99%" name="main1"
					id="main1" scrolling="auto" frameborder="0"></iframe>
			</div>
		</div>
	</div>


	<!--  
<div class="footing">
<center>
<p>郑州大学Rndroid团队开发</p>
</center>
</div>
-->



</body>
</html>