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
	<span class="c-gray en">&gt;</span> 会员列表 
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
		<i class="Hui-iconfont">&#xe68f;</i>
	</a>
</nav>
<input type="hidden" name="aaid" id="aaid"  />
<div class="page-container">
	<div class="text-c">
		<div class="mt-20">
			<div class="text-c">
				<input type="text" class="input-text" style="width:250px"  placeholder="请输入搜索内容" id="searchtxt" name="searchtext">	
				<select id="select1" class="select1">
				  <option value=""></option>
				   <option value="查ID">查ID</option>
				   <option value="查昵称">查昵称</option>
				   <option value="查手机号">查手机号</option>
				</select>
				<button type="submit" class="btn btn-success radius" id="searchbtn" name=""><i class="Hui-iconfont"></i>搜索</button>
				<button type="submit" class="btn btn-success radius" id="inputExcel" name=""><i class="Hui-iconfont"></i>导出EXCEL</button>
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
				<th width="40">用户名</th>
				<th width="40">昵称</th>
				<th width="40">邀请人昵称</th>
				<th width="40">性别</th>
				<th width="40">手机号码</th>
				<th width="40">账户余额</th>
				<%--<th width="40">身高(单位：cm)</th>--%>
				<%--<th width="40">体重(单位：kg)</th>--%>
				<%--<th width="40">星座</th>--%>
				<th width="40">注册时间</th>
				<%--<th width="40">城市</th>--%>
				<th width="40">是否在线</th>
				<th width="40">分享收入</th>

			</tr>
		</thead>
		<tbody id="list-content">
			<c:forEach var = "map" items="${reList }" varStatus="status">
				<tr class="text-c">
					<td>${status.count}</td>
					<td>${map['id']}</td>
					<td><img alt="" src="${map['photo']}" onclick="photo(${map['id']},'修改头像','<%=path%>1/boss/photo_add2.jsp','600','160')" style="width:80px"></td>
					<td>${map['username']}</td>
					<td>${map['nickname']} <a title="编辑" href="javascript:;"onclick="anchor_nickname(${map['id']})" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a></td>
					<td>${map['inviterName']}</td>
					<td>${map['gender']}</td>
					<td>${map['phonenum']} <a title="编辑" href="javascript:;"onclick="anchor_phonenum(${map['id']})" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a></td>
					<td>${map['money']}</td>
					<%--<td>${map['height']}</td>--%>
					<%--<td>${map['weight']}</td>--%>
					<%--<td>${map['constellation']}</td>--%>
					<td>${map['register_time']}</td>
					<%--<td>${map['city']}</td>--%>
					<c:choose>
							<c:when test="${map['online']=='0' }"><td>离线</td></c:when>
							<c:when test="${map['online']=='1' }"><td>在线</td></c:when>
							<c:when test="${map['online']=='2' }"><td>在聊</td></c:when>
					</c:choose>
					<td><a style="color: blue;" href="<%=path%>/rp?p0=A-boss-search&p1=income_table_search1&p2=1&p3=&p4=&p5=tojsp&p6=&p7=${map['id']}" >查看详情</a></td>
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
		
		var fangshi = $("#select1").val();
		if(fangshi!=''){
			fresh_page(1);
		}else if(fangshi == ''){
			alert('请选择查询方式');
		}
		
	});


    $("#inputExcel").click(function(){
        var neirong = $("#searchtxt").val();
        var fangshi = $("#select1").val();
        var url = "<%=path%>/rp?p0=A-boss-user-execl&p1=userdataexecl&p2="+neirong+"&p3="+fangshi;
        window.open(url);
        // $.ajax({
        //     type: 'POST',
        //     url:url,
		// 	success: function(data){
        //         /*$(obj).parents("tr").remove();*/
        //
        //         layer.msg('操作成功',{icon:1,time:1000});
        //     },
        //     error:function(data) {
        //         alert('提交失败');
        //     },
        // });
    });

});

/*刷新列表*/
function fresh_page(pageIndex){
	/* var startdate = $("#datemin").val();
	var enddate = $("#datemax").val(); */
	var neirong = $("#searchtxt").val();
	var fangshi = $("#select1").val();
	
	/* 获取输入的性别 */
	/* 获取输入的起始时间和结束时间 */
	/* 固定死判断条件，如性别，时间段：开始时间---结束时间 */
	$.ajax({
		cache: true,
		type: "POST",
		url: "<%=path%>/rp?p0=A-boss-search&p1=memberbackstage&p2="+neirong+"&p3="+fangshi+"&p4="+pageIndex+"&p5=tojson",
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
				   }else if(json[i].online=='0'){
				     online='离线';
				}  else if(json[i].online=='2'){
					online='在聊';
				}
				 content +='<tr class="text-c">'
					+'<td>'+(Number(json[json.length-1].current)+1+i)+'</td>'
					+'<td>'+json[i].id+'</td>'
					+'<td><img alt="" src="'+json[i].photo+'" onclick="photo(\'+json[i].id+\',\\\'修改头像\\\',\\\'<%=path%>1/boss/photo_add2.jsp\\\',\\\'600\\\',\\\'160\\\')" style="width:80px"></td>'
					+'<td>'+json[i].username+'</td>'
					+'<td>'+json[i].nickname+''
					 +'<a title="编辑" href="javascript:;"onclick="anchor_nickname('+json[i].id+')" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a></td>'
				 	+'<td>'+json[i].inviterName+'</td>'
					+'<td>'+json[i].gender+'</td>'
					+'<td>'+json[i].phonenum+''
					 +'<a title="编辑" href="javascript:;"onclick="anchor_phonenum('+json[i].id+')" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a></td>'
					+'<td>'+json[i].money+'</td>'
					// +'<td>'+json[i].height+'</td>'
					// +'<td>'+json[i].weight+'</td>'
					// +'<td>'+json[i].constellation+'</td>'
					+'<td>'+json[i].register_time+'</td>'
					// +'<td>'+json[i].city+'</td>'
					+'<td>'+online+'</td>'
					+'<td><a  style="color: blue;" href="http://119.23.16.29:8091/uiface/rp?p0=A-boss-search&p1=income_table_search1&p2=1&p3=&p4=&p5=tojsp&p6=&p7='+json[i].id+'" >查看详情</a></td>'
					/* +'<td class="td-manage">'
					+shezhi1+'</td>' */; 
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

function anchor_nickname(id){
    var age = prompt("请输入昵称","");
    if(age!=null && age!=""){
        layer.confirm('确认输入以上内容？',function(index){
            $.ajax({
                type:'POST',
                url: '<%=path%>/rp?p0=A-boss-mod&p1=anchor_nickname&p2='+id+'&p3='+age,
                success: function(data){
                    if(data='1'){
                        fresh_page(Number($("#currentpage").html()));
                        layer.msg('操作成功',{icon:1,time:1000});
                    }else{
                        layer.msg('操作失败',{icon:1,time:1000});
                    }
                },
                error:function(data) {
                    layer.msg('操作失败',{icon:1,time:1000});
                },
            });
        });
    }
}

function anchor_phonenum(id){
    var age = prompt("请输入手机号","");
    if(age!=null && age!=""){
        layer.confirm('确认输入以上内容？',function(index){
            $.ajax({
                type:'POST',
                url: '<%=path%>/rp?p0=A-boss-mod&p1=anchor_phonenum&p2='+id+'&p3='+age,
                success: function(data){
                    if(data='1'){
                        fresh_page(Number($("#currentpage").html()));
                        layer.msg('操作成功',{icon:1,time:1000});
                    }else{
                        layer.msg('操作失败',{icon:1,time:1000});
                    }
                },
                error:function(data) {
                    layer.msg('操作失败',{icon:1,time:1000});
                },
            });
        });
    }
}

/**查看用户相册*/
function client_xiangce(title,url,id,w,h){
	layer_show(title,url,w,h);	
}
/**查看用户相册*/
function client_shipin(title,url,id,w,h){
	layer_show(title,url,w,h);	
}

function photo(id,title,url,w,h){
    $("#aaid").val(id);
    layer_show(title,url,w,h);
}
</script> 
</body>
</html>