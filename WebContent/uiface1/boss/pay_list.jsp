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
<div class="page-container">
	<div class="mt-20">
		<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper ">
		<table class="table table-border table-bordered table-hover table-bg table-sort">
			<thead>
				<tr class="text-c">
					<th width=20>序号</th>
					<th width="100">ID</th>
					<th width="100">用户ID</th>
					<th width="100">付费类型</th>
					<th width="100">付费价格</th>
					<th width="100">A币数量</th>
					<th width="100">支付状态</th>
					<th width="100">时间</th>
				</tr>
			</thead>
			<tbody id="list-content">
				<c:forEach var="map" items="${reList}" varStatus="status">
					<tr class="text-c">
						<td>${status.count}</td>
						<td>${map['id']}</td>
						<td>${map['user_id']}</td>
						<td>${map['pay_type']}</td>
						<td>${map['pay_price']}元</td>
						<td>${map['pay_value']}A币</td>
						<td>
						<c:choose>
						     <c:when test="${map['pay_status']=='已付款'}"><span style="color:green">${map['pay_status']}</span></c:when>
						     <c:when test="${map['pay_status']=='未付款'}"><span style="color:red">${map['pay_status']}</span></c:when>
						</c:choose>
						</td>
						<td>${map['pay_time']}</td>
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
	
	
	
	
});
/*刷新列表*/
function fresh_page(pageIndex){
	var startdate = $("#datemin").val();
	var enddate = $("#datemax").val();
	var sele_condition = $("#searchname").val();
	
	<%-- <%=path%>/rz?p0=A-boss-search&p1=pay_list&p2=1&p3=${map['user_id']}&p4=tojsp --%>
	/* 获取输入的性别 */
	/* 获取输入的起始时间和结束时间 */
	/* 固定死判断条件，如性别，时间段：开始时间---结束时间 */
	$.ajax({
		cache: true,
		type: "POST",
		url: "<%=path%>/rp?p0=A-boss-search&p1=pay_list&p2="+pageIndex+"&p3=${param.p3}&p4=tojson",
		async: true,
		error: function(request) {
			alert("提交失败 ");
		},
		success: function(data){
			var json=eval("("+data+")");
			var content = '';
			
			for(var i = 0; i < json.length-1; i++) {
				var zhi;
				if(json[i].pay_status=='已付款'){
					zhi='<td style="color:green">'+json[i].pay_status+'</td>';
				}else{
					zhi='<td style="color:red">'+json[i].pay_status+'</td>';
				}
				
				 content +='<tr class="text-c">'
					+'<td>'+(Number(json[json.length-1].current)+1+i)+'</td>'
					+'<td>'+json[i].id+'</td>'
					+'<td>'+json[i].user_id+'</td>'
					+'<td>'+json[i].pay_type+'</td>'
					+'<td>'+json[i].pay_price+'元</td>'
					+'<td>'+json[i].pay_value+'A币</td>'
					+zhi
					+'<td>'+json[i].pay_time+'</td>'; 
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
</script>
</body>
</html>