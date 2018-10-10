<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%--声明我要使用C标签--%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% 
	String path = request.getContextPath() + "/uiface";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<style>
	.td-manage .ml-5 .Hui-iconfont {
		font-size: 18px;
	}
</style>
<title>视频管理</title>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页
	 <span class="c-gray en">&gt;</span> 视频管理
	 <a class="btn btn-success radius r btn-refresh" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
	 	<i class="Hui-iconfont">&#xe68f;</i>
	 </a>
</nav>
<div class="page-container">
	<div class="text-c"> 
		日期范围：
		<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;">
		- <input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;">
		<button type="submit" class="btn btn-success radius" id="searchbtn" name=""><i class="Hui-iconfont"></i>搜用户</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> 
		<span class="r">共有数据：<strong>${pageNo[1]}</strong> 条</span>
	</div>
	<div class="mt-20">
	<div class="dataTables_wrapper">
	<table class="table table-border table-bordered table-hover table-bg">
		<thead>
			<tr class="text-c">
				<th width=20>序号</th>
				<th width=20>ID</th>
				<th width="20">会员ID</th>
				<th width="60">视频首图</th>
				<th width="40">时间</th>
				<th width="100">视频状态</th>
			</tr>
		</thead>
		<tbody id="list-content">
			<c:forEach var="map" items="${reList}" varStatus="status">
				<tr class="text-c">
					<td>${status.count}</td><!-- 序号 -->
					<td>${map['id']}</td>
					<td>${map['userid']}</td>
					<td>${map['shoturl']}</td>
					<td>${map['time']}</td>
					<td class="td-manage">
						<input  type="button" href="javascript:;" class="btn btn-danger radius" onclick="client_del(this,${map['id']})" value="确定封禁">
					</td>		
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="dataTables_info" id="DataTables_Table_0_info" role="status" aria-live="polite">显示 <span id="pagefirst">${pageNo[2]+1}</span> 到 <span id="pagelast">${pageNo[3]}</span> ，共 <span id="total">${pageNo[1]}</span>条</div>
	<div class="dataTables_paginate paging_simple_numbers" id="DataTables_Table_0_paginate"><a class="paginate_button previous disabled" aria-controls="DataTables_Table_0" data-dt-idx="0" tabindex="0" id="DataTables_Table_0_previous">上一页</a><span><a class="paginate_button current" aria-controls="DataTables_Table_0" data-dt-idx="1" tabindex="0"><span id="currentpage">${pageNo[4]}</span></a></span><a class="paginate_button next disabled" aria-controls="DataTables_Table_0" data-dt-idx="2" tabindex="0" id="DataTables_Table_0_next">下一页</a></div>
	</div>
	</div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path%>1/boss/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path%>1/boss/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path%>1/boss/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path%>1/boss/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path%>1/boss/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="<%=path%>/boss/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  -->
<script type="text/javascript" src="<%=path%>1/boss/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
var totalpage = Number('${pageNo[0]}');
$(function(){
	// 上一页
	$("#DataTables_Table_0_previous").click(function() {
		var currentpage = Number($("#currentpage").html());
		
		if(currentpage <= 1) {
			alert('当前已经是第一页');
			return;
		}
		
		fresh_page(currentpage - 1);
	});
	
	// 下一页
	$("#DataTables_Table_0_next").click(function() {
		var currentpage = Number($("#currentpage").html());
		
		if(currentpage >= totalpage) {
			alert('当前已经是最后一页');
			return;
		}
		fresh_page(currentpage + 1);
	});
	//搜索符合条件的所有的记录显示出来，用ajax
	$("#searchbtn").click(function(){
		var startdate = $("#datemin").val();
		var enddate = $("#datemax").val();
		
		/* 1:获取输入的性别 */
		/* 2:获取输入的起始时间和结束时间 */
		/* 固定死判断条件，如性别，时间段：开始时间---结束时间 */
		//2种搜索条件,第一种满足就用第一种刷新；第二种满足就用第二种刷新
		if(startdate != '' && enddate == ''){
			alert("结束时间不能为空");
		}else if(startdate == '' && enddate != ''){
			alert("开始时间不能为空");
		}else if(startdate != '' && enddate != ''){
			fresh_page(1);
		} 
	});
});

/*刷新列表*/
function fresh_page(pageIndex) {
	
	var time1 = $("#datemin").val();
	var time2 = $("#datemax").val();
	/* <input  type="button" href="javascript:;" class="btn btn-danger radius" onclick="client_del(this,${map['userid']})" value="确定封禁"> */
	$.ajax({
		cache: true,
		type: "POST",
		url:"<%=path%>/rp?p0=A-boss-search&p1=all_video&p2="+pageIndex+"&p3=tojson&&p4="+time1+"&p5="+time2,
		async: true,
		error: function(request) {
			alert("提交失败 ");
		},
		success: function(data) {
			var json=eval("("+data+")");
			
			var content = '';
			for(var i = 0; i < json.length-1; i++) {
				  var a ='<input  type="button" href="javascript:;" class="btn btn-danger radius" onclick="client_del(this,'+json[i].id+')" value="确定封禁">';	
				content +='<tr class="text-c">'
					+'<td>'+(Number(json[json.length-1].current)+1+i)+'</td>'
					+'<td>'+json[i].id+'</td>'
					+'<td>'+json[i].userid+'</td>'
					+'<td>'+json[i].shoturl+'</td>'
					+'<td>'+json[i].time+'</td>'
					+'<td class="td-manage">'
					+a
					+'</td>';
			}
			$("#list-content").html(content);
			totalpage = Number(json[json.length-1].totlePage);
			$("#pagefirst").html(Number(json[json.length-1].current)+1);
			$("#pagelast").html(json[json.length-1].lastcount);
			$("#total").html(json[json.length-1].totle);
			$("#currentpage").html(json[json.length-1].pagenum);
		}
	});
}

//点击封禁，将其视频状态改为封禁
function client_del(obj,id){
	layer.confirm('确认要禁封此视频？',function(index){

		$.ajax({
			type: 'POST',
			url: '<%=path%>/rp?p0=A-boss-mod&p1=closed&p2='+id,
			success: function(data){
				/*$(obj).parents("tr").remove();*/
				layer.msg('已禁封!',{icon:1,time:1000});
				setTimeout(function () { 
					javascript:location.replace(location.href);
			    }, 1000);
			},
			error:function(data) {
				alert('提交失败');
			},
		});		
	});
}


</script> 
</body>
</html>