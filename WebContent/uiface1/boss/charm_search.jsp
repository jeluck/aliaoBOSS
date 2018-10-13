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
<title>会员管理</title>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 
	<span class="c-gray en">&gt;</span> 充值管理
	<span class="c-gray en">&gt;</span> 充值记录表
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
		<i class="Hui-iconfont">&#xe68f;</i>
	</a>
</nav>
<div class="page-container">
<div class="text-c">
	<div class="mt-20">
	                日期范围：
		<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;" name="">
		
		- <input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;" name="">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
		<button type="submit" class="btn btn-success radius" id="searchbtn" name=""><i class="Hui-iconfont"></i>搜索充值记录</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l">
		
	</div>
	<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper ">
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="65">ID</th>
				<th width="65">用户id</th>
				<th width="65">魅力值增长类型</th>
				<th width="65">数量</th>
				<!-- <th width="65">送礼人id</th> -->
				<th width="65">时间</th>
			</tr>
		</thead>
		<tbody id="list-content" >
		<c:forEach var="map" items="${reList}">
			<tr class="text-c">				
				<td>${map['id']}</td>
				<td>${map['user_id']}</td>       
				<td>${map['type']}</td>
				<td>${map['money']}</td>
				<%-- <td>${map['send_id']}</td> --%>
				<td>${map['time']}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
<div class="dataTables_info" id="DataTables_Table_0_info" role="status" aria-live="polite">显示 <span id="pagefirst">${pageNo[2]+1}</span> 到 <span id="pagelast">${pageNo[3]}</span> ，共 <span id="total">${pageNo[1]}</span>条</div>
	
	<div class="dataTables_paginate paging_simple_numbers" id="DataTables_Table_0_paginate">
		<a class="paginate_button previous disabled" aria-controls="DataTables_Table_0" data-dt-idx="0" tabindex="0" id="DataTables_Table_0_previous">上一页</a>
		<span>
			<a class="paginate_button current" aria-controls="DataTables_Table_0" data-dt-idx="1" tabindex="0">   
				<span id="currentpage">${pageNo[4]}</span>
			</a>
		</span>
		<a class="paginate_button next disabled" aria-controls="DataTables_Table_0" data-dt-idx="2" tabindex="0" id="DataTables_Table_0_next">下一页</a>
	</div>
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
<!-- <script type="text/javascript" src="<%=path%>/static/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path%>/static/lib/jquery.validation/1.14.0/validate-methods.js"></script>  -->
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
	$("#searchbtn").click(function() {
		var startdate = $("#datemin").val();
		var enddate = $("#datemax").val();
		var userid = $(".userid").val();
		/* if(datemin=="") {
			alert('请输入开始时间');
			return;
		}else if(date==""){
			alert('请输入结束时间时间');
			return;
		} */
		
		fresh_page(1);
	});
/* 	$("#all").click(function() {
		$("#datemin").html("");
		$("#datemax").html("");
		fresh_page(0);
	}); */
	
});
function fresh_page(pageIndex) {
	var startdate = $("#datemin").val();
	var enddate = $("#datemax").val();
	$.ajax({
		cache: true,
		type: "POST",
		//p2开始时间 p3当前页数 p4结束时间 
		url:"<%=path%>/rp?p0=A-boss-search&p1=charm_search&p2=${param.p2}&p3="+pageIndex+"&p4="+startdate+"&p5="+enddate+"&p6=tojson",
		async: true,
		error: function(request) {
			alert("提交失败 ");
		},
		success: function(data) {
			var json=eval("("+data+")");
			var content = '';
			for(var i = 0; i < json.length-1; i++) {
				content +='<tr class="text-c">'
					+'<td>'+json[i].id+'</td>'
					+'<td>'+json[i].user_id+'</td>'
					+'<td>'+json[i].type+'</td>'
					+'<td>'+json[i].money+'</td>'
					/* +'<td>'+json[i].send_id+'</td>' */
					+'<td>'+json[i].time+'</td>';
			}
			$("#list-content").html(content);
			totalpage = Number(json[json.length-1].totlePage);
			$("#pagefirst").html(Number(json[json.length-1].current)+1);
			$("#pagelast").html(json[json.length-1].lastcount);
			$("#total").html(json[json.length-1].totle);
			$("#currentpage").html(json[json.length-1].pagenum);
		},
	});
}
/* <td>${map['id']}</td>
<td>${map['user_id']}</td>
<td>${map['gift']}</td>
<td>${map['num']}</td>
<td>${map['send_id']}</td>
<td>${map['time']}</td> */
</script>
</body>
</html>