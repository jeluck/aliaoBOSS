<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath()+"/uiface";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/css/style.css" />

<title>礼物列表</title>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 
	<span class="c-gray en">&gt;</span> 排行榜
	<span class="c-gray en">&gt;</span> 魅力榜
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
		<i class="Hui-iconfont">&#xe68f;</i>
	</a>
</nav>
<div class="page-container">
	<div class="text-c">
		<div class="mt-20">
		   	<div class="text-c" style="margin-top:30px;">
			</div>
		</div>
	</div>
	
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<button type="submit" id="thisCharm" name="" class="btn btn-primary radius">本周魅力榜</button>
		
		<button type="submit" id="todayCharm" name="" class="btn btn-primary radius">今日魅力榜</button>
		
	</div>
	
	<div class="mt-20">
		<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper ">
		<table class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th width=20>序号</th>
					<th width="100">ID</th>
					<th width="100">账号</th>
					<th width="100">昵称</th>
					<th width="100">性别</th>
					<th width="100">魅力值</th>
				</tr>
			</thead>
			<tbody id="list-content">
				<c:forEach var="map" items="${reList}" varStatus="status">
					<tr class="text-c">
						<td>${status.count}</td>
						<td>${map['id']}</td>
						<td>${map['username']}</td>
						<td>${map['nickname']}</td>
						<td>${map['gender']}</td>
						<td>
							${map['charm_thisweek']}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</div>
	
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path%>1/boss/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path%>1/boss/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path%>1/boss/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path%>1/boss/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path%>1/boss/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="<%=path%>/static/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path%>/static/lib/jquery.validation/1.14.0/validate-methods.js"></script>  -->
<script type="text/javascript" src="<%=path%>1/boss/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
$(function(){
	$("#thisCharm").click(function(){
		var i = 0;
		fresh_page(i);
	});
	$("#todayCharm").click(function(){
		var i = 1;
		fresh_page(i);
	});
});

function fresh_page(z){
	$.ajax({
		cache: true,
		type: "POST",
		url: "<%=path%>/rp?p0=A-boss-search&p1=charm_week_search&p2="+z+"&p3=tojson",
		async: true,
		error: function(request) {
			alert("提交失败 ");
		},
		success: function(data){
			var json=eval("("+data+")");
			var content = '';
			var a="";
			for(var i = 0; i < json.length; i++) {
				if(z==0){
					a=json[i].charm_thisweek;
				}else{
					a=json[i].charm_today;
				}
				 content +='<tr class="text-c">'
					+'<td>'+(Number(1+i))+'</td>'
					+'<td>'+json[i].id+'</td>'
					+'<td>'+json[i].username+'</td>'
					+'<td>'+json[i].nickname+'</td>'
					+'<td>'+json[i].gender+'</td>'
					+'<td>'+a+'</td>'; 
			}
			$("#list-content").html(content);
		},
	});
}
</script>
</body>
</html>