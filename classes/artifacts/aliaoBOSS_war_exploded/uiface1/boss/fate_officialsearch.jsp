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

<link rel="stylesheet" type="text/css" href="<%=path %>1/boss/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/boss/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/boss/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/boss/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/boss/static/h-ui.admin/css/style.css" />

<title>官方分配</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 红娘系统 <span class="c-gray en">&gt;</span> <!-- 文章列表  --><a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="mt-20">
	<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper ">
	<form id="memberForm">
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="30">序号</th>
				<th width="40">用户ID</th>
				<th width="40">用户昵称</th>
				<th width="40">用户联系方式</th>
				<th width="40">心仪对象ID</th>
				<th width="40">心仪对象昵称</th>
				<th width="40">心仪对象联系方式</th>
				<th width="40">红娘</th>
				<th width="40">申请时间</th>
				<th width="40">审核状态</th>
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody id="list-content" >
		<c:forEach var="map" items="${reList}" varStatus="status">
			<tr class="text-c">
				<td>${status.count}</td>
				<td>${map['user_id']}</td>
				<td>${map['name']}</td>
				<td>${map['phone']}</td>
				<td>${map['target_id']}</td>
				<td>${map['name1']}</td>
				<td>${map['phone1']}</td>
				<td>${map['matchmaker_id']}</td>
				<td>${map['time'] }</td>
				<td>${map['state'] }</td> 
				<td class="td-manage"> <a title="编辑" href="javascript:;" onclick="system_category_edit('官方分配红娘','<%=path%>/rp?mode=A-boss-mod&b=guanfangfenpei_mod1&id=${map['id']} ','600','600') " class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">分配红娘</i></a></td>
			
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	</form>
	<div class="dataTables_info" id="DataTables_Table_0_info" role="status" aria-live="polite">显示 <span id="pagefirst">${pageNo[2]+1}</span> 到 <span id="pagelast">${pageNo[3]}</span> ，共 <span id="total">${pageNo[1]}</span>条</div>
	<div class="dataTables_paginate paging_simple_numbers" id="DataTables_Table_0_paginate" ><a class="paginate_button previous disabled" aria-controls="DataTables_Table_0" data-dt-idx="0" tabindex="0" id="DataTables_Table_0_previous">上一页</a><span><a class="paginate_button current" aria-controls="DataTables_Table_0" data-dt-idx="1" tabindex="0"><span id="currentpage">${pageNo[4]}</span></a></span><a class="paginate_button next disabled" aria-controls="DataTables_Table_0" data-dt-idx="2" tabindex="0" id="DataTables_Table_0_next">下一页</a></div>
	</div>
	</div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path %>1/boss/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path %>1/boss/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>1/boss/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path %>1/boss/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path %>1/boss/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">


 //刷新列表     
function fresh_page(pageIndex) {
	//var name = document.getElementById("searchname").value;
		$.ajax({
			cache:true,
			type:"post",
			url: "<%=path %>/rp?a=A-boss-search&b=guanfangfenpei&p3="+pageIndex+"&p9=&p4=tojson",
			async: true,
			error: function(request) {
				alert("提交失败 ");
			},
			success:function(data){
				var json=eval("("+data+")");
				var content = '';
				for(var i = 0;i<json.length-1;i++){
					content += '<tr class = "text-c">'
							+'<td>' +(Number(json[json.length-1].current)+1+i)+'</td>'
							+'<td>'+json[i].user_id+'</td>'
							+'<td>'+json[i].name+'</td>'
							+'<td>'+json[i].phone+'</td>'
						    /* +'<td><img src="../img/imgheadpic/'+json[i].rz_photo+'" style="width:50px;height:50px"></td>' */
						    +'<td>'+json[i].target_id+'</td>'
						    +'<td>'+json[i].name1+'</td>'
						    +'<td>'+json[i].phone1+'</td>'
						    +'<td>'+json[i].matchmaker_id+'</td>'
						    +'<td>'+json[i].time+'</td>'
						    +'<td>'+json[i].state+'</td>'
						    +'<td class="td-manage">'
							/* +'<a title="通过" href="javascript:;" onclick="system_category_edit1('+json[i].id+','+json[i].updateid+') "  class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">通过</i></a>'
							+'<a title="不通过" href="javascript:;" onclick="go('+json[i].id+','+json[i].updateid+')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">未通过</i></a>' */
							+'<a title="编辑" href="javascript:;" onclick="system_category_edit(\'编辑\',\'<%=path%>/rp?p0=mode=A-boss-mod&b=guanfangfenpei_mod1&id='+json[i].id+'\',\'\',\'600\',\'510\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">分配红娘</i></a>'
							+'</td>'
						    ;
							/* +'<td>'+json[i].exchange_price+'</td>' */
							/* +'<td class="td-manage">'
							+'<a title="通过" href="javascript:;" onclick="system_category_edit1('+json[i].id1+','+json[i].id+') "  class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">通过</i></a>'
							+'<a title="不通过" href="javascript:;" onclick="go('+json[i].id1+','+json[i].id+')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">未通过</i></a>'
							+'</td>' */
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

var bool;
//上一页
$("#DataTables_Table_0_previous").click(function() {
	var currentpage = Number($("#currentpage").html());
		if(currentpage <= 1) {
			alert('当前已经是第一页');
			return;
		}	
		fresh_page(currentpage-1);
	
	
	
});

// 下一页
$("#DataTables_Table_0_next").click(function() {
	var currentpage = Number($("#currentpage").html());
		var totalpage = ${pageNo[0]};
		if(currentpage >= totalpage) {
			alert('当前已经是最后一页');
			return;
		} 
		fresh_page(currentpage+1);
	
});

function client_del(obj,id){
	
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '<%=path%>/ep?p0=A-boss-delete&p1=del_baby&p2='+id,
			success: function(data){
				/*$(obj).parents("tr").remove();*/
				layer.msg('已删除!',{icon:1,time:1000});
				setTimeout(function() { 
					javascript:location.replace(location.href);
			    }, 1000);
			},
			error:function(data) {
				alert('提交失败');
			},
		});		
	});
}
//模糊查询
$("#btn-search").click(function(){
	fresh_page(1)
});
function system_category_edit(title,url,w,h){
	layer_show(title,url,w,h);
}
function system_category_edit1(id,userid){
	//layer_show(title,url,w,h);
	
	$.ajax({
		type: 'POST',
		url:'<%=path%>/rp?p0=A-boss-mod&p1=dongtai_checkpass&p2='+id+'&p3='+userid,
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
	age = prompt("请输入未通过原因","");
	layer.confirm('确认输入以上内容？',function(index){

		$.ajax({
			type:'POST',
			url: '<%=path%>/rp?p0=A-boss-mod&p1=dongtai_checknopass&p2='+id+'&p4='+userid+'&p3='+age,
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
	});
}

function system_category_add(title,url,w,h){
	layer_show(title,url,w,h);
}
</script> 
</body>
</html>