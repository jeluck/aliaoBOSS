<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path=request.getContextPath()+"/uiface";

%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/static/h-ui.admin/css/style.css" />


<title>会员列表</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 文章管理 <span class="c-gray en">&gt;</span> 文章列表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="text-c">
		<input type="text" class="input-text" style="width:250px" placeholder="输入文章名称" id="searchname" name="">
		<button type="button" class="btn btn-success radius" id="btn-search" name=""><i class="Hui-iconfont">&#xe665;</i> 搜文章</button>
	</div>
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"> <a href="javascript:;" onclick="system_category_add('文章添加','boss/article_add.jsp?method=add','600','600')"  class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加文章</a></span> </div>
	<div class="mt-20">
	<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper ">
	<form id="memberForm">
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<!-- <th width="25"><input type="checkbox" name="" value=""></th> -->
				<th width="100">ID</th>
				<th width="100">文章图片</th>
				<th width="100">文章标题</th>
				<!-- <th width="100">内容</th> -->
				<th width="100">文章分类</th>
				<th width="100">阅读量</th>
				<th width="100">时间</th>
				<th width="150">操作</th>
			</tr>
		</thead>
		<tbody id="list-content" >
		<c:forEach var="map" items="${reList}">
			<tr class="text-c">
				<%-- <td><input type="checkbox" name="selectFlag" id="selectFlag"
										class="checkbox1" value="${map['id']}"></td> --%>
				<td>${map['id']}</td>
				<%-- <td><img src="<%=path %>/../${map['articel_img']}" width="100px" ></td> --%>
				<td><img src="${map['article_img']}" width="100px" ></td>
				<td>${map['article_name']}</td>
				<%-- <td><u style="cursor:pointer" class="text-primary" onclick="member_show('张三','member-show.html','10001','360','400')">${map['username']}</u></td> --%>
				<%-- <td>${map['referral']}</td> --%>
				<td>${map['sort']}</td>
				<td>${map['read_num']}</td>
				<td>${map['time']}</td>
				<td class="td-manage"> <a title="编辑" href="javascript:;" onclick="system_category_edit('文章修改','<%=path %>/art?a=A-boss-search&b=search_article_info&userid=&id=${map['id']} ','600','600') " class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>  <a title="删除" href="javascript:;" onclick="client_del(this,${map['id']})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
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
<script type="text/javascript" src="<%=path %>1/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path %>1/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>1/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path %>1/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path %>1/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript">
function deletes() {
	var currentpage = Number($("#currentpage").html());
    var flag = false;
    var j = 0;
    for (var i = 0; i < document.getElementsByName("selectFlag").length; i++) {
        if (document.getElementsByName("selectFlag")[i].checked) {
            flag = true;
            j=document.getElementsByName("selectFlag")[i].value;
        }
    }
    if (!flag) {
        alert("请选择需要删除的数据！");
        return;
    }
    if (window.confirm("确认删除吗？")) {
        with (document.getElementById("memberForm")) {
            method = "post";
            action = "<%=path%>/art?mode1=A-boss-delete&mode2=del_article&page="+currentpage;
            submit();
        }
    }
}

 //刷险列表     
function fresh_page(pageIndex) {
	var name = document.getElementById("searchname").value;
	<%-- window.location.href = "<%=path %>/art?mode=A-boss-search&mode2=search_article_name&userid=&name="+name+"&page="+pageIndex; --%> 
		$.ajax({
			cache:true,
			type:"post",
			url: "<%=path %>/art?a=A-boss-search&b=search_article_name&p3=&page="+pageIndex+"&name="+name,
			async: true,
			error: function(request) {
				alert("提交失败 ");
			},
			success:function(data){
				var json=eval("("+data+")");
				var content = '';
				for(var i = 0;i<json.length-1;i++){
					content += '<tr class = "text-c">'
							/* +'<td><input type="checkbox" name="selectFlag" id="selectFlag" class="checkbox1" value="'+json[i].id+'"></td>' */
							+'<td>'+json[i].id+'</td>'
						    +'<td><img src="'+json[i].article_img+'"  style="width:100px"></td>'
							+'<td>'+json[i].article_name+'</td>'
							//+'<td>'+json[i].referral+'</td>'
							+'<td>'+json[i].sort+'</td>'
							+'<td>'+json[i].read_num+'</td>'
							+'<td>'+json[i].time+'</td>'
							+'<td class="td-manage"><a title="编辑" href="javascript:;" onclick="system_category_edit(\'广告修改\',\'<%=path %>/JyServletInOut?a=A-boss-search&b=ArticleIdSearch&id='+json[i].id+'\',\'1000\',\'600\') "  class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a><a title="删除" href="javascript:;" onclick="member_del('+json[i].id+')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>'
							
			
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
	if(bool != 1){
		
		if(currentpage <= 1) {
			alert('当前已经是第一页');
			return;
		}
		var page =Number(currentpage-1);
		
		 window.location.href = "<%=path %>/art?mode=A-boss-search&mode2=article_list&empty=&page="+page;  
	}else{
		if(currentpage <= 1) {
			alert('当前已经是第一页');
			return;
		}
		
		fresh_page(currentpage-1);
		
	}
	
	
});

// 下一页
$("#DataTables_Table_0_next").click(function() {
	
	var currentpage = Number($("#currentpage").html());
	if(bool != 1){
		var totalpage = ${pageNo[0]};
		if(currentpage >= totalpage) {
			alert('当前已经是最后一页');
			return;
		} 
		var page =Number(currentpage+1);
		
		 window.location.href = "<%=path %>/art?mode=A-boss-search&mode2=article_list&empty=&page="+page; 
	}else{
		var totalpage = ${pageNo[0]};
		if(currentpage >= totalpage) {
			alert('当前已经是最后一页');
			return;
		} 
		fresh_page(currentpage+1);
		
		
	}
	
	
});

function member_del(id){
	 if (window.confirm("确认删除吗？")) {
	var currentpage = Number($("#currentpage").html());
	
	window.location.href = "<%=path%>/art?mode1=A-boss-delete&mode2=del_article_one&page="+currentpage+"&id="+id;

	 }
}
function client_del(obj,id){
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '<%=path%>/art?mode1=A-boss-delete&mode2=del_article_one&p2='+id,
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
//模糊查询
$("#btn-search").click(function(){
	bool=1;
	
	fresh_page(1)
	
});
function system_category_edit(title,url,w,h){
	layer_show(title,url,w,h);
	
}
function system_category_add(title,url,w,h){
	layer_show(title,url,w,h);
	
}
</script> 
</body>
</html>