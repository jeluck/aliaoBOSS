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
	<span class="c-gray en">&gt;</span> 会员管理 
	<span class="c-gray en">&gt;</span> 主播列表 
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
		<i class="Hui-iconfont">&#xe68f;</i>
	</a>
</nav>
<div class="page-container">
	<div class="text-c">
		<div class="mt-20">
			<div class="text-c">
					昵称：
				<input type="text" class="input-text" style="width:250px"  placeholder="请输入主播昵称" id="searchtxt" name="searchtxt">	
				<button type="submit" class="btn btn-success radius" id="searchbtn" name=""><i class="Hui-iconfont"></i>搜用户</button>
			</div>	
		</div>
		
	<div class="mt-20">
	<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper ">
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width=20>序号</th>
				<th width="20">ID</th>
				<th width="40">头像</th>
				<th width="40">认证图片</th>
				<th width="40">用户名</th>
				<th width="40">昵称</th>
				<th width="40">星级</th>
				<th width="40">手机号码</th>
				<th width="40">账户余额</th>
				<th width="40">身高(单位：cm)</th>
				<th width="40">体重(单位：kg)</th>
				<th width="40">星座</th>
				<th width="40">城市</th>
				
				<th width="40">M币收入</th>
				<th width="40">分享收入</th>
				
				<th width="40">是否在线</th>
				<th width="40">是否推荐</th>
				<th width="40">排序参数</th>
				<th width="40">账号状态</th>
				<th width="40">操作</th>
				
		
			</tr>
		</thead>
		<tbody id="list-content">
			<c:forEach var = "map" items="${reList }" varStatus="status">
				<tr class="text-c">
					<td>${status.count}</td><!-- 序号 -->
					<td>${map['id']}</td>
					<td><img alt="" src="${map['photo']}" style="width:80px"></td>
					<td><a href="javascript:;"
					onclick="system_category_edit('认证图片','<%=path %>/rp?p0=A-boss-search&p1=renzheng_photosearch1&p2=${map['id'] }&p3=0','600','510')"
					style="text-decoration: none;color:blue;" class="ml-5">点击查看图片</a>
					</td>
					<td>${map['username']}</td>
					<td>${map['nickname']}</td>
					<td  id="xing${map['id']}">${map['star']}星  <br>
					<a title="编辑" href="javascript:;"onclick="xingmod(${map['id']})" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a> </td>
					<td>${map['phonenum']}</td>
					<td>${map['money']}</td>
					<td>${map['height']}</td>
					<td>${map['weight']}</td>
					<td>${map['constellation']}</td>
					<td>${map['city']}</td>
					
					<td><a  style="color: blue;" href="<%=path%>/rp?p0=A-boss-search&p1=income_table_search&p2=1&p3=&p4=&p5=tojsp&p6=&p7=${map['id']}" >查看详情</a></td>
					<td><a style="color: blue;"  href="<%=path%>/rp?p0=A-boss-search&p1=income_table_search1&p2=1&p3=&p4=&p5=tojsp&p6=&p7=${map['id']}" >查看详情</a></td>
					<c:choose>
							<c:when test="${map['online']=='0' }"><td>离线</td></c:when>
							<c:when test="${map['online']=='1' }"><td>在线</td></c:when>
							<c:when test="${map['online']=='2' }"><td>忙碌</td></c:when>
					</c:choose>
					<c:choose>
							<c:when test="${map['is_tuijian']!='1' }"><td  id="tuijian${map['id']}"  ><a title="设为推荐" onclick="tuijian(${map['id']})" class="ml-5" style="text-decoration:none;    color: blue;"><i class="Hui-iconfont">设为推荐</i></a> </td></c:when>
							<c:when test="${map['is_tuijian']=='1' }"><td   id="tuijian${map['id']}" ><a title="取消推荐" onclick="tuijian(${map['id']})" class="ml-5" style="text-decoration:none;    color: red;"><i class="Hui-iconfont">取消推荐</i></a> </td></c:when>
					</c:choose>
					<td>${map['sort_id']}  <br>
					<a title="编辑" href="javascript:;"onclick="sortmod(${map['id']})" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a> </td>
					<c:choose>
							<c:when test="${map['is_banned']!='1' }"><td>正常</td></c:when>
							<c:when test="${map['is_banned']=='1' }"><td>封禁</td></c:when>
					</c:choose>
					<td class="td-manage">
						<a title="封禁" onclick="banned(${map['id']})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">封禁</i></a>  
						<a title="解封" onclick="no_banned(${map['id']})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">解封</i></a>  
					</td>
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
<!-- <script type="text/javascript" src="<%=path%>1/static/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path%>1/static/lib/jquery.validation/1.14.0/validate-methods.js"></script>  -->
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
		/* var startdate = $("#datemin").val();
		var enddate = $("#datemax").val(); */
		var sele_condition = $("#searchname").val();
		var mil_id = $("#searchtxt").val();
		
		/* 1:获取输入的性别 */
		/* 2:获取输入的起始时间和结束时间 */
		/* 固定死判断条件，如性别，时间段：开始时间---结束时间 */
		//2种搜索条件,第一种满足就用第一种刷新；第二种满足就用第二种刷新
		if(mil_id != ''){
			fresh_page(1);
		}else if(sele_condition != ''){
			fresh_page(1);
		}
		else if(sele_condition==''){
			fresh_page(1);
		} 
	});
});

/*刷新列表*/
function fresh_page(pageIndex){
	/* var startdate = $("#datemin").val();
	var enddate = $("#datemax").val(); */
	var sele_condition = $("#searchname").val();
	var mil_id = $("#searchtxt").val();
	/* 获取输入的性别 */
	/* 获取输入的起始时间和结束时间 */
	/* 固定死判断条件，如性别，时间段：开始时间---结束时间 */
	$.ajax({
		cache: true,
		type: "POST",
		url: "<%=path%>/rp?p0=A-boss-search&p1=anchor_search&p2="+pageIndex+"&p3="+mil_id+"&p4=tojson&p5=${param.p5}",
		async: true,
		error: function(request) {
			alert("提交失败 ");
		},
		success: function(data){
			var json=eval("("+data+")");
			var content = '';
			
			for(var i = 0; i < json.length-1; i++) {
				var online="";
				if(json[i].online=='1'){
					online='在线';
				   }else if(json[i].online=='2'){
				     online='忙碌';
				}else if(json[i].online=='0'){
					 online='离线';
				}
				var banned="";
				if(json[i].is_banned=='1'){
					banned='封禁';
				}else{
					banned='正常';
				}
				var tuijian="";
				if(json[i].is_tuijian=='1'){
					tuijian='<td id="tuijian'+json[i].id+'"><a title="取消推荐" onclick="tuijian('+json[i].id+')" class="ml-5" style="text-decoration:none;    color: red;"><i class="Hui-iconfont">取消推荐</i></a></td>';
				}else{
					tuijian='<td id="tuijian'+json[i].id+'"><a title="设为推荐" onclick="tuijian('+json[i].id+')" class="ml-5" style="text-decoration:none;    color: blue;"><i class="Hui-iconfont">设为推荐</i></a></td>';
				}
				 content +='<tr class="text-c">'
					+'<td>'+(Number(json[json.length-1].current)+1+i)+'</td>'
					+'<td>'+json[i].id+'</td>'
					+'<td><img alt="" src="'+json[i].photo+'" style="width:80px"></td>'
					+'<td><a href="javascript:;" onclick="system_category_edit(\'认证图片\',\'<%=path %>/rp?p0=A-boss-search&p1=renzheng_photosearch1&p2='+json[i].id+'&p3=0\',\'600\',\'510\')"  ><span style="color:blue;">点击查看图片</span></a></td>'
					+'<td>'+json[i].username+'</td>'
					+'<td>'+json[i].nickname+'</td>'
					+'<td id="xing"'+json[i].id+'>'+json[i].id+'星<br><a title="编辑" href="javascript:;"onclick="xingmod('+json[i].id+')" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a> </td>'
					+'<td>'+json[i].phonenum+'</td>' 
					+'<td>'+json[i].money+'</td>'
					+'<td>'+json[i].height+'</td>' 
					+'<td>'+json[i].weight+'</td>'
					+'<td>'+json[i].constellation+'</td>' 
					+'<td>'+json[i].city+'</td>'
					+'<td><a style="color: blue;"  href="http://47.105.33.230:8090/uiface/rp?p0=A-boss-search&p1=income_table_search&p2=1&p3=&p4=&p5=tojsp&p6=&p7='+json[i].id+'" >查看详情</a></td>'
					+'<td><a style="color: blue;"  href="http://47.105.33.230:8090/uiface/rp?p0=A-boss-search&p1=income_table_search1&p2=1&p3=&p4=&p5=tojsp&p6=&p7='+json[i].id+'" >查看详情</a></td>'
					+'<td>'+online+'</td>'
					+tuijian
					+'<td>'+json[i].id+'<br><a title="编辑" href="javascript:;"onclick="sortmod('+json[i].id+')" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a> </td>'
					+'<td>'+banned+'</td>'
					+'<td class="td-manage">'
					+'<a title="封禁" href="javascript:;" onclick="banned('+json[i].id+')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">封禁</i></a>'
					+'<a title="解封" href="javascript:;" onclick="no_banned('+json[i].id+')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">解封</i></a>'
					+'</td>'
				; 
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
function system_category_edit(title,url,w,h){
	layer_show(title,url,w,h);
}
function system_category_edit1(id,userid){
	//layer_show(title,url,w,h);
	
	$.ajax({
		type: 'POST',
		url:'<%=path%>/rp?p0=A-boss-mod&p1=zhubo_pass&p2='+id,
		success: function(data){
			/*$(obj).parents("tr").remove();*/
			layer.msg('操作成功',{icon:1,time:1000});
			setTimeout(function () { 
				javascript:location.replace(location.href);
		    }, 1000);
		},
		error:function(data) {
			alert('提交失败');
		},
	});			
}

function go(id,userid){
	/* age = prompt("请输入未通过原因","");
	layer.confirm('确认输入以上内容？',function(index){ */

		$.ajax({
			type:'POST',
			url: '<%=path%>/rp?p0=A-boss-mod&p1=zhubo_nopass&p2='+id,
			success: function(data){
				/*$(obj).parents("tr").remove();*/
				layer.msg('操作成功',{icon:1,time:1000});
				setTimeout(function () { 
					javascript:location.replace(location.href);
			    }, 1000);
			},
			error:function(data) {
				alert('通过失败');
			},
		});		
	/* }); */
}
function banned(id){
	/* age = prompt("请输入未通过原因","");
	layer.confirm('确认输入以上内容？',function(index){ */

		$.ajax({
			type:'POST',
			url: '<%=path%>/rp?p0=A-boss-mod&p1=anchor_banned&p2='+id,
			success: function(data){
				/*$(obj).parents("tr").remove();*/
				layer.msg('操作成功',{icon:1,time:1000});
				setTimeout(function () { 
					javascript:location.replace(location.href);
			    }, 1000);
			},
			error:function(data) {
				alert('通过失败');
			},
		});		
	/* }); */
}
function no_banned(id){
	/* age = prompt("请输入未通过原因","");
	layer.confirm('确认输入以上内容？',function(index){ */

		$.ajax({
			type:'POST',
			url: '<%=path%>/rp?p0=A-boss-mod&p1=banned_cancel&p2='+id,
			success: function(data){
				/*$(obj).parents("tr").remove();*/
				layer.msg('操作成功',{icon:1,time:1000});
				setTimeout(function () { 
					javascript:location.replace(location.href);
			    }, 1000);
			},
			error:function(data) {
				alert('通过失败');
			},
		});		
	/* }); */
}
/**查看用户相册*/
function client_xiangce(title,url,id,w,h){
	layer_show(title,url,w,h);	
}
/**查看用户相册*/
function client_shipin(title,url,id,w,h){
	layer_show(title,url,w,h);	
}

function tuijian(id){
	/* age = prompt("请输入未通过原因","");
	layer.confirm('确认输入以上内容？',function(index){ */

		$.ajax({
			type:'POST',
			url: '<%=path%>/rp?p0=A-boss-mod&p1=anchor_tuijian&p2='+id,
			success: function(data){
				/*$(obj).parents("tr").remove();*/
				layer.msg('操作成功',{icon:1,time:1000});
				
				/* if(data=='0'){
					$("#tuijian"+id).html('<a title="设为推荐" onclick="tuijian('+id+')" class="ml-5" style="text-decoration:none;    color: blue;"><i class="Hui-iconfont">设为推荐</i></a>');
				}else{
					$("#tuijian"+id).html('<a title="取消推荐" onclick="tuijian('+id+')" class="ml-5" style="text-decoration:none;    color: red;"><i class="Hui-iconfont">取消推荐</i></a>');
				} */
				setTimeout(function () { 
					javascript:location.replace(location.href);
			    }, 1000);
				
			},
			error:function(data) {
				alert('通过失败');
			},
		});		
	/* }); */
}
function sortmod(id){
	var age = prompt("请输入排序参数","");
	if(age!=null && age!=""){
		layer.confirm('确认输入以上内容？',function(index){ 

			$.ajax({
				type:'POST',
				url: '<%=path%>/rp?p0=A-boss-mod&p1=sortmod&p2='+id+'&sortid='+age,
				success: function(data){
					/*$(obj).parents("tr").remove();*/
					if(data='1'){
						layer.msg('操作成功',{icon:1,time:1000});
					}else{
						layer.msg('操作失败',{icon:1,time:1000});
					}
					/* if(data=='0'){
						$("#tuijian"+id).html('<a title="设为推荐" onclick="tuijian('+id+')" class="ml-5" style="text-decoration:none;    color: blue;"><i class="Hui-iconfont">设为推荐</i></a>');
					}else{
						$("#tuijian"+id).html('<a title="取消推荐" onclick="tuijian('+id+')" class="ml-5" style="text-decoration:none;    color: red;"><i class="Hui-iconfont">取消推荐</i></a>');
					} */
					setTimeout(function () { 
						javascript:location.replace(location.href);
				    }, 1000);
					
				},
				error:function(data) {
					layer.msg('操作失败',{icon:1,time:1000});
				},
			});		
		 });
	}
	
}
function xingmod(id){
	var age = prompt("请输入星级","");
	if(age!=null && age!=""){
		layer.confirm('确认输入以上内容？',function(index){ 

			$.ajax({
				type:'POST',
				url: '<%=path%>/rp?p0=A-boss-mod&p1=xingmod&p2='+id+'&sortid='+age,
				success: function(data){
					/*$(obj).parents("tr").remove();*/
					if(data='1'){
						$("#xing"+id).html(age+'星  <br><a title="编辑" href="javascript:;"onclick="xingmod('+id+')" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a> ');
						
						layer.msg('操作成功',{icon:1,time:1000});
					}else{
						layer.msg('操作失败',{icon:1,time:1000});
					}
					/* if(data=='0'){
						$("#tuijian"+id).html('<a title="设为推荐" onclick="tuijian('+id+')" class="ml-5" style="text-decoration:none;    color: blue;"><i class="Hui-iconfont">设为推荐</i></a>');
					}else{
						$("#tuijian"+id).html('<a title="取消推荐" onclick="tuijian('+id+')" class="ml-5" style="text-decoration:none;    color: red;"><i class="Hui-iconfont">取消推荐</i></a>');
					} */
					/* setTimeout(function () { 
						javascript:location.replace(location.href);
				    }, 1000); */
					
				},
				error:function(data) {
					layer.msg('操作失败',{icon:1,time:1000});
				},
			});		
		 });
	}
	
}
</script> 
</body>
</html>