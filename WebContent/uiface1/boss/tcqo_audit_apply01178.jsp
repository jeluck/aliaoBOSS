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

<title>消费记录</title>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 
	<span class="c-gray en">&gt;</span> 消息管理
	<span class="c-gray en">&gt;</span> 消息表
	<!-- <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
		<i class="Hui-iconfont">&#xe68f;</i>
	</a> -->
</nav>
<div class="page-container">
	<div class="text-c">
		<div class="mt-20">
			 <div class="text-c">	
				<span>日期范围：</span>
				<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;" name="">
				
				- <input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;" name="">
				
				<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;选择类型：</span>
				<select class="input-text" style="width:150px" id="searchname" name="">
					<option value=""></option>
					<option value="自动回复">自动回复</option>
					<option value="打招呼">打招呼</option>
					<option value="待审核">待审核</option>
					<option value="已通过">已通过</option>
					<option value="未通过">未通过</option>
				</select>
				<button type="submit" class="btn btn-success radius" id="searchbtn" name=""><i class="Hui-iconfont"></i>搜用户</button>
			 </div>	
		     <div class="text-c" style="margin-top:10px;">
					
			</div>
		</div>
	</div>
	<div class="mt-20">
	<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper ">
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="20">序号</th>
				<th width="20">ID</th>
				<th width="30">用户ID</th>
				<th width="100">消息内容</th>
				<th width="100">提交审核时间</th>
				<th width="100">审核状态</th>
				<th width="100">原因</th>
				<th width="100">审核类型</th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody id="list-content" >
			<c:forEach var="map" items="${reList}" varStatus="status">
				<tr class="text-c">
					<td>${status.count}</td>
					<td>${map['id']}</td>
					<td>${map['user_id']}</td>
					<td>${map['audit_content']}</td>
					<td>${map['time']}</td>
					<td>${map['status']}</td>
					<td>${map['un_reason']}</td>
					<td>${map['type']}</td>
					<td>
						<input  type="button" href="javascript:;" class="btn btn-success radius" onclick="client_update_pass(this,${map['id']})" value="已通过">
						<input  type="button" href="javascript:;" class="btn btn-danger radius" onclick="client_update_nopass(this,${map['id']})" value="未通过">
					</td>
				</tr>
			</c:forEach>	
			</tbody>
	</table>
	<%-- <a title="编辑" href="javascript:;" 
						onclick="client_edit('编辑','<%=path%>/room?p0=A-boss-search&p1=room_id_search&p2=${map['id']}','','','510')" 
						class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a> --%>
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
	
	//搜索符合条件的所有的记录显示出来，用ajax
	$("#searchbtn").click(function(){
		
		var startdate = $("#datemin").val();
		var enddate = $("#datemax").val();
		var sele_condition = $("#searchname").val();
		
		/* 1:获取输入的条件 */
		/* 2:获取输入的起始时间和结束时间 */
		//2种搜索条件,第一种满足就用第一种刷新；第二种满足就用第二种刷新
		if(sele_condition != ''){
			fresh_page(1);
		}else if(startdate != '' && enddate == ''){
			alert("结束时间不能为空");
		}else if(startdate == '' && enddate != ''){
			alert("开始时间不能为空");
		}else if(startdate != '' && enddate != '' && sele_condition=='type'){
			fresh_page(1);
		}else if(sele_condition==''){
			fresh_page(1);
		} 
	});
	
	
	<%-- function client_update_pass(obj,id){
			$.ajax({
				type: 'POST',
				url: '<%=path%>/TCQOServlet?p0=A-boss-mod&p1=auditapplystatus_mod&p2='+id,
				async: true,
				success: function(data){
					/* var json=eval("("+data+")");
					if(json == '1'){
						alert("已修改");
					} */
				},
				error:function(data) {
					alert('修改失败');
				},
			});		
	}
	

	function client_update_nopass(obj,id){
		alert('请在un_reason栏输入原因');
		var reason = 0;
		$.ajax({
			type: 'POST',
			url: '<%=path%>/TCQOServlet?p0=A-boss-mod&p1=auditapplyreason&p2='+id+'&p3='+reason+'&p4=tojson',
			async: true,
			success: function(data){
				var json=eval("("+data+")");
				if(json == 'success'){
					alert("审核未通过");
				}
			},
			error:function(data) {
				alert('未通过失败');
			},
		});	
	} --%>
	
});

/*刷新列表*/
function fresh_page(pageIndex){
	var startdate = $("#datemin").val();
	var enddate = $("#datemax").val();
	var sele_condition = $("#searchname").val();
	<%-- <%=path%>/TCQOServlet?p0=A-boss-search&p1=auditapply&p2=1&p3=&p4=&p5=&p6=tojsp --%>
	
	/* 获取输入的起始时间和结束时间 */
	/* 固定死判断条件，如性别，时间段：开始时间---结束时间 */
	$.ajax({
		cache: true,
		type: "POST",
		url: "<%=path%>/TCQOServlet?p0=A-boss-search&p1=auditapply&p2="+pageIndex+"&p3="+startdate+"&p4="+enddate+"&p5="+sele_condition+"&p6=tojson",
		async: true,
		error: function(request) {
			alert("提交失败 ");
		},
		success: function(data){
			var json=eval("("+data+")");
			var content = '';
			
			for(var i = 0; i < json.length-1; i++) {
				 content +='<tr class="text-c">'
					+'<td>'+(Number(json[json.length-1].current)+1+i)+'</td>'
					+'<td>'+json[i].id+'</td>'
					+'<td>'+json[i].user_id+'</td>'
					+'<td>'+json[i].audit_content+'</td>'
					+'<td>'+json[i].time+'</td>'
					+'<td>'+json[i].status+'</td>'
					+'<td>'+json[i].un_reason+'</td>'
					+'<td>'+json[i].type+'</td>'
					+'<td>'
					+'<input  type="button" href="javascript:;" class="btn btn-success radius" onclick="client_update_pass(this,'+json[i].id+')" value="已通过">'
					+'&nbsp;'
					+'<input  type="button" href="javascript:;" class="btn btn-danger radius" onclick="client_update_nopass(this,'+json[i].id+')" value="未通过">'
					+'</td>'
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
function client_update_pass(obj,id){
	layer.confirm('确认通过？',function(index){

		$.ajax({
			type: 'POST',
			url: '<%=path%>/TCQOServlet?p0=A-boss-mod&p1=auditapplystatus_mod&p2='+id,
			success: function(data){
				/*$(obj).parents("tr").remove();*/
				layer.msg('已通过!',{icon:1,time:1000});
				setTimeout(function () { 
					javascript:location.replace(location.href);
			    }, 1000);
			},
			error:function(data) {
				alert('通过失败');
			},
		});		
	});
}

function client_update_nopass(obj,id){
	layer.confirm('不能通过吗？',function(index){

		$.ajax({
			type: 'POST',
			url: '<%=path%>/TCQOServlet?p0=A-boss-mod&p1=auditapplyreason_mod&p2='+id,
			success: function(data){
				/*$(obj).parents("tr").remove();*/
				layer.msg('已拒绝!',{icon:1,time:1000});
				setTimeout(function () { 
					javascript:location.replace(location.href);
			    }, 1000);
			},
			error:function(data) {
				alert('未通过失败');
			},
		});		
	});
}

</script>
</body>
</html>