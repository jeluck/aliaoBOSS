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

<title>提现记录</title>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页 
	<span class="c-gray en">&gt;</span> 充提管理
	<span class="c-gray en">&gt;</span> 提现明细
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
		<i class="Hui-iconfont">&#xe68f;</i>
	</a>
</nav>
<div class="page-container">
	  <div class="mt-20">
			<!-- <div class="text-c">	
			                日期范围：
				<input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;" name="">
				
				- <input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;" name="">
				<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<button type="submit" class="btn btn-success radius" id="searchbtn" name=""><i class="Hui-iconfont"></i>搜用户</button>
			</div>	 -->
			<div class="text-c">
	          <span>查询方式</span>
	          <select id="check1" name="check1" >
	            <option value="0" >时间段查询</option>
	            <option value="1" >按月查询</option>
	            <option value="2" >按年查询</option> 
	          </select>
	       </div>
			<div class="text-c" id="xx1">	
			<span>开始日期:</span><input type="text" onfocus="WdatePicker({ maxDate:'#F{$dp.$D(\'datemax\')||\'%y-%M-%d\'}' })" id="datemin" class="input-text Wdate" style="width:120px;" name="">
		    <span>结束日期:</span><input type="text" onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'datemin\')}',maxDate:'%y-%M-%d' })" id="datemax" class="input-text Wdate" style="width:120px;" name="">
			</div>
			<div class="text-c" id="xx2">	
			<input type="text" id="d243" onclick="WdatePicker({dateFmt:'yyyy-MM'})" class="input-text Wdate" style="width:120px;"  />
			</div>
			<div class="text-c" id="xx3">	
			<input type="text" id="d244" onclick="WdatePicker({dateFmt:'yyyy'})" class="input-text Wdate" style="width:120px;"  />
			</div>

		  <div class="text-c" style="margin-top:10px;">

		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ID:
		  <input type="text" class="input-text" style="width:150px"  placeholder="请输入id" id="uid" name="uid">
		  用户昵称:
		  <input type="text" class="input-text" style="width:150px"  placeholder="请输入昵称" id="nickname" name="nickname">
		  提现账号:
		  <input type="text" class="input-text" style="width:150px"  placeholder="请输入提现账号" id="txaccount" name="txaccount">
		  提现名称:
		  <input type="text" class="input-text" style="width:150px"  placeholder="请输入提现名称" id="txname" name="txname">
		  提现状态:
			  <select class="input-text" style="width:150px" id="txstatus" name="txstatus">
				  <option value=""></option>
				  <option value="拒绝提现">拒绝提现</option>
				  <option value="提现成功">提现成功</option>
				  <option value="申请中">申请中</option>
			  </select>
		  </div>
		      <div class="text-c" style="margin-top:10px;">			
				<button type="submit" class="btn btn-success radius" id="btn-search" name="search" onclick="seek()"><i class="Hui-iconfont"></i> 查询</button>
				  <button type="submit" class="btn btn-success radius" id="inputExcel" name=""><i class="Hui-iconfont"></i>导出EXCEL</button>
			</div> 
			<span id="sum1">总提现:${reList[0].sum}</span>
		</div>
		
	<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper ">
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="80">序号</th>
				<th width="80">用户id</th>
				<th width="80">用户昵称</th>
				<th width="80">提现时间</th>
				<th width="80">提现金额</th>
				<th width="80">提现账号</th>
				<th width="80">提现名称</th>
				<th width="80">提现状态</th>
				<th width="80">提现操作状态</th>
		   <!-- <th width="100">提现明细</th> -->
			</tr>
		</thead>
		<tbody id="list-content" >
		<c:set var="nodeValue" scope="page" value="0"/>
		<c:forEach var="map" items="${reList}" varStatus="status">
			<tr class="text-c">
				<td>${status.count}</td>		
				<td>${map['user_id']}</td>
				<td>${map['nickname']}</td>
				<td>${map['time']}</td>
				<td>${map['cash']}元</td>
				<td>${map['tixian_account']}</td>
				<td>${map['account_name']}</td>
				<th>
					<c:choose>
						<c:when test="${map['status']=='已提现'}"><span>提现成功</span></c:when>
						<c:when test="${map['status']=='拒绝提现'}"><span>拒绝提现</span></c:when>
						<c:when test="${map['status']=='申请中'}"><input  type="button" href="javascript:;" class="btn btn-success radius" onclick="response_money(this,${map['id']})" value="确定">
						
						<input  type="button" href="javascript:;" class="btn btn-warning radius" onclick="jujue_money(this,${map['id']})" value="拒绝">
						
						</c:when>
					</c:choose>
				</th>
				<td>${map['msg']}</td>
			</tr>
			<c:set var="nodeValue" scope="page" value="${nodeValue+map['cash']}"/>
		</c:forEach>
		<tr>
		<td colspan="9">当页提现金额:${nodeValue}</td>
		</tr>	
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
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path%>1/boss/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path%>1/boss/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path%>1/boss/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path%>1/boss/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path%>1/boss/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="<%=path%>1/static/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path%>1/static/lib/jquery.validation/1.14.0/validate-methods.js"></script>  -->
<script type="text/javascript" src="<%=path%>1/boss/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">

var totalpage = Number('${pageNo[0]}');
$(function(){
	$("#xx2").hide();
	$("#xx3").hide();
	
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
		if(startdate=="" && enddate!=""){
			alert('请选择开始时间');
		}else if(enddate=="" && startdate!=""){
			alert('请选择结束时间时间');
		}
		fresh_page(1);
	});
});

<%-- <td><a title="个人提现明细" href="javascript:;" onclick="client_geren('个人提现明细','<%=path%>/TCQOServlet?/p0=A-boss-search&p1=cashwithdrawal&p2=1&p3=${map['user_id']}&p4=tojsp','','','510')" style="color:blue">个人提现明细</a></td> --%>

function fresh_page(pageIndex) {
	var pp="";
	var startdate = $("#datemin").val();
	var enddate = $("#datemax").val();
	if($("option:selected","#check1").val() == '1'){
		pp=$("#d243").val();
		startdate="";
		enddate="";
	}else if($("option:selected","#check1").val() == '2'){
		pp=$("#d244").val();
		startdate="";
		enddate="";
	}else{
		startdate=$("#datemin").val();
		enddate=$("#datemax").val();
	}
	var nickname = $("#nickname").val();
    var uid = $("#uid").val();
    var txaccount = $("#txaccount").val();
    var txname = $("#txname").val();
    var txstatus = $("#txstatus").val();

	$.ajax({
		cache: true,
		type: "POST",
		//p2开始时间 p3当前页数 p4结束时间 p5 会员 p6积分
		/* /rz?p0=A-boss-search&p1=cash_withdrawal&p2=1&p3=&p4=&p5=tojsp */
		url:"<%=path%>/rp?p0=A-boss-search&p1=cash_withdrawal&p2="+pageIndex+"&p3="+startdate+"&p4="+enddate+"&p5=tojson&p6="+pp+"&p7=0&p8="+uid+"&p9="+nickname+"&p10="+txaccount+"&p11="+txname+"&p12="+txstatus,
		async: true,
		error: function(request) {
			alert("提交失败 ");
		},
		success: function(data) {
			var json=eval("("+data+")");
			var content = '';
			var sum = 0;
			for(var i = 0; i < json.length-1; i++) {
				
				var z = '';
				if(json[i].status=='申请中'){
					z = '<input  type="button" href="javascript:;" class="btn btn-success radius" onclick="response_money(this,'+json[i].id+')" value="确定">'+
					'<input  type="button" href="javascript:;" class="btn btn-warning radius" onclick="jujue_money(this,'+json[i].id+')" value="拒绝">';
					
				}else if(json[i].status=='已提现'){
					z = '<span>提现成功</span>';
				}else if(json[i].status=='拒绝提现'){
					z = '<span>拒绝提现</span>';
				}
				
				
				content +='<tr class="text-c">'
					+'<td>'+(Number(json[json.length-1].current)+1+i)+'</td>'
					+'<td>'+json[i].user_id+'</td>'
					+'<td>'+json[i].nickname+'</td>'
					+'<td>'+json[i].time+'</td>'
					+'<td>'+json[i].cash+'元</td>'
					+'<td>'+json[i].tixian_account+'</td>'
					+'<td>'+json[i].account_name+'</td>'
					+'<td>'+z+'</td>'
					+'<td>'+json[i].msg+'</td>'
					/* +'<td>'+b+'</td>' */
					+'</tr>';
				var a = Number(json[i].cash);
				sum = sum+a;
			}
			content +='<tr>'
					+'<td  colspan="9"> 当页收入:'+sum+'</td>'
					+'</tr>';
			$("#list-content").html(content);
			totalpage = Number(json[json.length-1].totlePage);
			$("#pagefirst").html(Number(json[json.length-1].current)+1);
			$("#pagelast").html(json[json.length-1].lastcount);
			$("#total").html(json[json.length-1].totle);
			$("#currentpage").html(json[json.length-1].pagenum);
			$("#sum1").html("总金额"+json[0].sum);
			
		}
	});
}

		function jujue_money(obj,id){
			layer.confirm('拒绝提现给用户？',function(index){

				$.ajax({
					type: 'POST',
					url: '<%=path%>/rp?p0=A-boss-mod&p1=jujue_money&p2='+id,
					success: function(data){
						/*$(obj).parents("tr").remove();*/
						layer.msg(data,{icon:1,time:1000});
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
function response_money(obj,id){
	layer.confirm('确认提现给用户？',function(index){

		$.ajax({
			type: 'POST',
			url: '<%=path%>/rp?p0=A-boss-mod&p1=response_money&p2='+id,
			success: function(data){
				/*$(obj).parents("tr").remove();*/
				layer.msg(data,{icon:1,time:1000});
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
function seek(){
	fresh_page(1);
}
function client_geren(title,url,id,w,h){
	layer_show(title,url,w,h);
}
$("#check1").on("change",function(){
	//alert('执行');
	if ($("option:selected",this).val()==2) {
		//alert('执行1');
	    //var a=$("#xx1").html();
		//alert(a);
		$("#xx1").hide();
		$("#xx2").hide();
		$("#xx3").show();
		//pp="年";
	}else if($("option:selected",this).val() == '1'){
		//alert('执行2');
		//var c='<input type="text" id="d243" onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy年MM月'})" class="Wdate"/>';
		//$("#xx1").html();
		$("#xx1").hide();
		$("#xx2").show();
		$("#xx3").hide();
		//pp=$("#d243").val();
	}else{
		//alert('执行3');
		$("#xx1").show();
		$("#xx2").hide();
		$("#xx3").hide();
	}
});
$("#inputExcel").click(function(){
    var pp="";
    var startdate = $("#datemin").val();
    var enddate = $("#datemax").val();
    if(startdate=="" && enddate!=""){
        alert('请选择开始时间');
        return;
    }else if(enddate=="" && startdate!=""){
        alert('请选择结束时间时间');
        return;
    }
    if($("option:selected","#check1").val() == '1'){
        pp=$("#d243").val();
        startdate="";
        enddate="";
    }else if($("option:selected","#check1").val() == '2'){
        pp=$("#d244").val();
        startdate="";
        enddate="";
    }else{
        startdate=$("#datemin").val();
        enddate=$("#datemax").val();
    }
    var nickname = $("#nickname").val();
    var uid = $("#uid").val();
    var txaccount = $("#txaccount").val();
    var txname = $("#txname").val();
    var txstatus = $("#txstatus").val();
    var url = "<%=path%>/rp?p0=A-boss-user-execl&p1=anchortxexecl&p2=1&p3="+startdate+"&p4="+enddate+"&p5=tojson&p6="+pp+"&p7=0&p8="+uid+"&p9="+nickname+"&p10="+txaccount+"&p11="+txname+"&p12="+txstatus;
    window.open(url);
});
</script>
</body>
</html>