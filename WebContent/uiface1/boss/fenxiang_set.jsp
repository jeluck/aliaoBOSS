<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName();
int webPort = request.getServerPort();
if(webPort != 80) {
	basePath = basePath+":"+webPort;
}
String path = basePath+"/uiface";
%>
<%!
Boolean bool = true;
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/uiface1/boss/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/uiface1/boss/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/uiface1/boss/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/uiface1/boss/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/uiface1/boss/static/h-ui.admin/css/style.css" />

<title>分销比例设置</title>
</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>
		设置
		<!-- <span class="c-gray en">&gt;</span> 新提交 -->
		<a class="btn btn-success radius r btn-refresh"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新"> <i
			class="Hui-iconfont">&#xe68f;</i>
		</a>
	</nav>
	<div class="page-container">
		
		
		<div class="mt-20">
			<div class="dataTables_wrapper">
				<table
					class="table table-border table-bordered table-hover table-bg">
					<thead>
					<tr class="text-c">
					<th width="30">序号</th>
					<th width="30">类型</th>
					<th width="40">男</th>
					<th width="40">女</th>
					<th width="40">操作</th>
					</tr>
					</thead>
					<tbody id="list-content">
						<tr class="text-c">
								<td>1</td>
								<td>(周邀请)0-20人</td>
								<td>${reList[0]['male_percent']}</td>
								<td>${reList[0]['female_percent']}</td>
								<td class="td-manage"><a title="编辑" href="javascript:;"
									onclick="proj_edit('编辑','<%=path%>/rp?p0=A-boss-search&p1=fenxiang_info&p2=1','600','510')"
									class="ml-5" style="text-decoration: none"><i
										class="Hui-iconfont">&#xe6df;</i></a>
								</td>
							</tr>
							<tr class="text-c">
								<td>2</td>
								<td>(周邀请)21-50人</td>
								<td>${reList[1]['male_percent']}</td>
								<td>${reList[1]['female_percent']}</td>
								<td class="td-manage"><a title="编辑" href="javascript:;"
									onclick="proj_edit('编辑','<%=path%>/rp?p0=A-boss-search&p1=fenxiang_info&p2=2','600','510')"
									class="ml-5" style="text-decoration: none"><i
										class="Hui-iconfont">&#xe6df;</i></a>
								</td>
							</tr>
							<tr class="text-c">
								<td>3</td>
								<td>(周邀请)50人以上</td>
								<td>${reList[2]['male_percent']}</td>
								<td>${reList[2]['female_percent']}</td>
								<td class="td-manage"><a title="编辑" href="javascript:;"
									onclick="proj_edit('编辑','<%=path%>/rp?p0=A-boss-search&p1=fenxiang_info&p2=3','600','510')"
									class="ml-5" style="text-decoration: none"><i
										class="Hui-iconfont">&#xe6df;</i></a>
								</td>
							</tr>
							<tr class="text-c">
								<td>4</td>
								<td>注册90-180天</td>
								<td>${reList[3]['male_percent']}</td>
								<td>${reList[3]['female_percent']}</td>
								<td class="td-manage"><a title="编辑" href="javascript:;"
									onclick="proj_edit('编辑','<%=path%>/rp?p0=A-boss-search&p1=fenxiang_info&p2=4','600','510')"
									class="ml-5" style="text-decoration: none"><i
										class="Hui-iconfont">&#xe6df;</i></a>
								</td>
							</tr>
							<tr class="text-c">
								<td>5</td>
								<td>注册180天以上</td>
								<td>${reList[4]['male_percent']}</td>
								<td>${reList[4]['female_percent']}</td>
								<td class="td-manage"><a title="编辑" href="javascript:;"
									onclick="proj_edit('编辑','<%=path%>/rp?p0=A-boss-search&p1=fenxiang_info&p2=5','600','510')"
									class="ml-5" style="text-decoration: none"><i
										class="Hui-iconfont">&#xe6df;</i></a>
								</td>
							</tr>
					</tbody>
				</table>
				<div class="dataTables_info" id="DataTables_Table_0_info"
					role="status" aria-live="polite">
					显示 <span id="pagefirst">${pageNo[2]+1}</span> 到 <span id="pagelast">${pageNo[3]}</span>
					，共 <span id="total">${pageNo[1]}</span>条
				</div>
				<div class="dataTables_paginate paging_simple_numbers"
					id="DataTables_Table_0_paginate">
					<a class="paginate_button previous disabled"
						aria-controls="DataTables_Table_0" data-dt-idx="0" tabindex="0"
						id="DataTables_Table_0_previous">上一页 </a><span><a
						class="paginate_button current" aria-controls="DataTables_Table_0"
						data-dt-idx="1" tabindex="0"><span id="currentpage">${pageNo[4]}</span></a></span>
					<a class="paginate_button next disabled"
						aria-controls="DataTables_Table_0" data-dt-idx="2" tabindex="0"
						id="DataTables_Table_0_next">下一页</a>
				</div>
			</div>
		</div>
	</div>
	<div></div>
	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/boss/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/uiface1/boss/lib/layer/2.4/layer.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/boss/static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/boss/static/h-ui.admin/js/H-ui.admin.js"></script>
	<!--/_footer 作为公共模版分离出去-->

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/boss/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/boss/lib/My97DatePicker/4.8/WdatePicker.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/lib/laypage/1.2/laypage.js"></script>
	<script type="text/javascript">
//var totalpage = ${pageNo[0]};
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
		var currentpage = Number($("#currentpage").html());
		var a = $("#searchtxt").val();
		if(a=="") {
			alert('请输入会员id');
			return;
		}
		fresh_page(1);
	});
});
function fresh_page(pageIndex) {
	var a = $("#searchtxt").val();
	
	$.ajax({
		cache: true,
		type: "POST",
		//arg[2]会员id arg[3]当前页数
		url:"<%=path%>/rp?p0=A-boss-search&p1=vip_table_search&p2="+a+"&p3="+pageIndex+"&p4=tojson",
		async: true,
		error: function(request) {
			alert("提交失败 ");
		},
		success: function(data) {
			var json=eval("("+data+")");
			var content = '';
			for(var i = 0; i < json.length-1; i++) {
				content +='<tr class="text-c">'
					+'<td>'+(Number(json[json.length-1].current)+1+i)+'</td>'
					+'<td>'+json[i].id+'</td>'
					+'<td>'+json[i].user_name+'</td>'
					+'<td>'+json[i].user_pwd+'</td>'
					+'<td>'+json[i].photo+'</td>'
					+'<td>'+json[i].gender+'</td>'
					+'<td>'+json[i].phonenum+'</td>'
					+'<td>'+json[i].invite_num+'</td>'
					+'<td>'+json[i].integral_num+'</td>'
					+'<td><a href="javascript:;" onclick="proj_edit(\'个人娃娃币详情\',\'<%=path %>/rp?p0=A-boss-search&p1=coin_search&p2='+json[i].id+'&p3=0\',\'600\',\'510\')" style="text-decoration:none;color:blue" title="娃娃币详情">'+json[i].play_coin+'</a></td>'
					+'<td class="td-manage">'
					+'<a href="javascript:;" onclick="client_search(\'个人娃娃记录详情\',\'<%=path%>/rp?p0=A-boss-search&p1=wawa_details&p2='+json[i].id+'&p3=0\',\'600\',\'510\')" ><i class="Hui-iconfont">'+json[i].tt+'</i></a>'
					+'</td>';
					+'</tr>';
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
function proj_edit(title,url,w,h){
	layer_show(title,url,w,h);
}
/*查询娃娃详情*/
function client_search(title,url,w,h){
	layer_show(title,url,w,h);
}
/*用户-删除*/
function client_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '<%=path%>/rp?p0=A-boss-del&p1=photo_del&p2='+id,
			success: function(data){
				/*$(obj).parents("tr").remove();*/
				layer.msg('已删除!',{icon:1,time:1000});
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