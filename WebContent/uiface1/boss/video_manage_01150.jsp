<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
   String path = request.getContextPath()+"/uiface";
String b=request.getSession().getAttribute("power")+"";
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

<title>短视频审核</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 视频管理 <span class="c-gray en">&gt;</span> <!-- 文章列表  --><a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="mt-20">
			<div class="text-c">	
				<span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
				<span>审核状态:</span>
				<select class="input-text" style="width:150px" id="searchname" name="searchname">
					<option></option>
					<option value="未通过">未通过</option>
					<option value="审核中">待审核</option>
					<option value="已通过">已通过</option>
				</select>
				<button type="submit" class="btn btn-success radius" id="searchbtn" name=""><i class="Hui-iconfont"></i>搜索</button>
			</div>	
			<%-- <div style="text-align:center;">
		<!-- ���ųߴ�-->
            <video id = "${map['video_id']}" width="320", height="240", controls="controls">
		<!-- ���ŵ�ַ-->
                <source src="http://ppt1.mingweishipin.com/e2e84b08374841249a2c41375dc97926/69f606c4bf55422ca16cffe3eadfdc8d-5287d2089db37e62345123a1be272f8b.mp4?Expires=1520155234&OSSAccessKeyId=LTAIPZHZDaUNpnca&Signature=UndjDNfW3PGWx5%2BODprDnVv4nbc%3D", type="video/mp4"></source>?Expires=1520155234&OSSAccessKeyId=LTAIPZHZDaUNpnca&Signature=UndjDNfW3PGWx5%2BODprDnVv4nbc%3D", type="video/mp4"></source>
				</video>
       		</div> --%>
	<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper ">
	<form id="memberForm">
	<table class="table table-border table-bordered table-hover table-bg table-sort">
		<thead>
			<tr class="text-c">
				<th width="30">序号</th>
				<th width="40">视频ID</th>
				<th width="40">上传人</th>
				<th width="40">短视频</th>
				<th width="40">视频说明</th>
				<th width="40">上传时间</th>
				<th width="40">收费设置</th>
				<th width="40">价格设置</th>
				<th width="40">点赞量</th>
				<th width="40">浏览量</th>
				<th width="40">转发量</th>
				<th width="40">审核状态</th>
				<!-- <th width="40">标签设置</th> -->
				<th width="40">操作</th>
			</tr>
		</thead>
		<tbody id="list-content" >
		<c:forEach var="map" items="${reList}" varStatus="status">
			<tr class="text-c">
				<td>${status.count}</td>
				<td>${map['id']}</td>
				<td>${map['nickname1']}</td>
				<td>
				<img alt="图片加载失败" src="${map['video_photo']}" onclick="system_category_playvideo('视频播放','<%=path%>/rp?p0=A-boss-search&p1=video_play&p2=${map['id']}',800,600)" style="width:50px;height:50px">
				</td>
				<td>${map['explain'] }</td>
				<td>${map['uptime'] }</td>
				<th>
					<c:choose>
						<c:when test="${map['ispay']==0 }"><span>免费</span></c:when>
						<c:when test="${map['ispay']==1 }"><span>收费</span></c:when>
					</c:choose>
				</th>
				<td>${map['price'] }</td>
				<td>${map['like_num'] }</td>
				<td>${map['liulan_num'] }</td>
				<td>${map['share_num'] }</td>
				
				<%-- <td>${map['status']}</td> --%>
				<th>
					<c:choose>
						<c:when test="${map['status']==0 }"><span>待审核</span> </c:when>
						<c:when test="${map['status']==1 }"><span>已通过</span></c:when>
						<c:when test="${map['status']==2 }"><span>未通过</span> </c:when>
					</c:choose>
				</th>
				
				<td class="td-manage">
				   <a title="通过" href="javascript:;" onclick="system_category_edit1(${map['id']})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">通过</i></a>  
				   <a title="不通过" href="javascript:;" onclick="go(${map['id']})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">不通过</i></a>	 
				   <% if(b.equals("1")){%>
				   <a title="删除" href="javascript:;" onclick="client_del(${map['id']})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">删除</i></a>	 
				   <% } %>
				</td>
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


/* var myVideo = document.getElementById("video1");

function playPause() {
    if (myVideo.paused){
        myVideo.play();
    } else {
        myVideo.pause();
    }
}

function makeBig() {
    myVideo.width = 1080;
    myVideo.height = 720;
}

function makeSmall() {
    myVideo.width = 480;
    myVideo.height = 240;
}

function makeNormal() {
    myVideo.width = 720;
    myVideo.height = 480;
}
 */

 //刷新列表     
function fresh_page(pageIndex) {
	var name = document.getElementById("searchname").value;
	//alert(name);
	//var name = $("#searchname").val();
		$.ajax({
			cache:true,
			type:"post",
			url: "<%=path %>/rp?a=A-boss-search&b=video_manage&p3="+pageIndex+"&p4="+name+"&page=tojson",
			async: true,
			error: function(request) {
				alert("提交失败 ");
			},
			success:function(data){
				var json=eval("("+data+")");
				var content = '';
				for(var i = 0;i<json.length-1;i++){
					var biaoqian = "";
					if(json[i].biaoqian==0){
						biaoqian = "";
					}else if(json[i].biaoqian==1){
						biaoqian = "优秀";
					}else if(json[i].biaoqian==2){
						biaoqian = "VIP";
					}else if(json[i].biaoqian==3){
						biaoqian = "普通";
					}
					
					var fee = "";
					if(json[i].ispay==0){
						fee = "免费";
					}else if(json[i].ispay==1){
						fee = "收费";
					}
					
					var status = "";
					if(json[i].status==0){
						status = "待审核";
					}else if(json[i].status==1){
						status = "已通过";
					}else if(json[i].status==2){
						status = "未通过";
					}
					
					content += '<tr class = "text-c">'
							+'<td>' +(Number(json[json.length-1].current)+1+i)+'</td>'
							+'<td>'+json[i].id+'</td>'
							+'<td>'+json[i].nickname1+'</td>'
							+'<td><img src="'+json[i].video_photo+'" onclick="system_category_playvideo(\'视频播放\',\'<%=path%>/rp?p0=A-boss-search&p1=video_play&p2='+json[i].id+'\',\'800\',\'600\')"  style="width:50px;height:50px"></td>'
							+'<td>'+json[i].explain+'</td>'
							+'<td>'+json[i].uptime+'</td>'
							+'<td>'+fee+'</td>'
							+'<td>'+json[i].price+'</td>'
							+'<td>'+json[i].like_num+'</td>'
						    +'<td>'+json[i].liulan_num+'</td>'
						    +'<td>'+json[i].share_num+'</td>'
						    +'<td>'+status+' </td>'
						    /* +'<td>'+biaoqian+'</td>' */
						    +'<td class="td-manage">'
							+'<a title="通过" href="javascript:;" onclick="system_category_edit1('+json[i].id+') "  class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">通过</i></a>'
							/* +'<a title="优秀" href="javascript:;" onclick="system_category_edit2('+json[i].id+') "  class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">优秀</i></a>'
							+'<a title="VIP" href="javascript:;" onclick="system_category_edit2('+json[i].id+') "  class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">VIP</i></a>' */
							+'<a title="不通过" href="javascript:;" onclick="go('+json[i].id+','+json[i].user_id+')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">不通过</i></a>'
							+'<a title="删除" href="javascript:;" onclick="client_del('+json[i].id+','+json[i].user_id+')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">删除</i></a>'
							+'</td>'
						    ;
							
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

function client_del(id){
	
	layer.confirm('确认要删除吗？',function(index){
		$.ajax({
			type: 'POST',
			url: '<%=path%>/rp?p0=A-boss-del&p1=video_del&p2='+id,
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
$("#searchbtn").click(function(){
	fresh_page(1)
	
	
	
	
	
	
});
function system_category_edit(title,url,w,h){
	layer_show(title,url,w,h);
}
function system_category_edit1(id){
	//layer_show(title,url,w,h);
	var biaoqian = '1';
	$.ajax({
		type: 'POST',
		url:'<%=path%>/rp?p0=A-boss-mod&p1=video_checkpass&p2='+id+'&p3='+biaoqian,
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
function system_category_edit2(id){
	//layer_show(title,url,w,h);
	var biaoqian = '1';
	$.ajax({
		type: 'POST',
		url:'<%=path%>/rp?p0=A-boss-mod&p1=video_checkpass&p2='+id+'&p3='+biaoqian,
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
function system_category_edit3(id){
	//layer_show(title,url,w,h);
	var biaoqian = '2';
	$.ajax({
		type: 'POST',
		url:'<%=path%>/rp?p0=A-boss-mod&p1=video_checkpass&p2='+id+'&p3='+biaoqian,
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
function go(id){
	/* age = prompt("请输入未通过原因","");
	layer.confirm('确认输入以上内容？',function(index){ */

		$.ajax({
			type:'POST',
			url: '<%=path%>/rp?p0=A-boss-mod&p1=video_checknopass&p2='+id,
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

function system_category_playvideo(title,url,w,h){
	layer_show(title,url,w,h);
}

function system_category_add(title,url,w,h){
	layer_show(title,url,w,h);
}
</script> 
</body>
</html>