<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%--声明我要使用C标签--%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% 
	String path = request.getContextPath() + "/uiface";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/css/style.css" />
<link href="lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css"/>
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->

<title>用户相册</title>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 图片管理 <span class="c-gray en">&gt;</span> 用户相册 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="portfolio-content">
		<input value="${reList[0].picture}" type="hidden" id="url"></input>
		<input value="${reList[0].id}" type="hidden" id="userid"/>
		<input  type="hidden" id="oldurl"/>
		<ul class="cl portfolio-area">
			<c:set var="a" value="${reList[0].picture}" ></c:set>
			<c:if test="${a!='' and a!=null}">
				<c:forEach var="map" items= "${fn:split(a,',')}" varStatus="status">
					<li class="item">
						<div class="portfoliobox">
							<div class="picbox" style="line-height: 50px;">
								<img src="${map}">
								<span><a title="编辑" href="javascript:;" onclick="photo('${map}','修改图片','<%=path%>1/boss/album_search_edit.jsp','600','160')" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a></span>
								<span><a title="删除" href="javascript:;" onclick="client_del('${map}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></span>
							</div>
						</div>
					</li>
				</c:forEach>
			</c:if>
			
		</ul>
	</div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path%>1/boss/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path%>1/boss/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path%>1/boss/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path%>1/boss/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->
<!--请在下方写此页面业务相关的脚本-->
<!--  <script type="text/javascript" src="<%=path%>/boss/static/lib/My97DatePicker/4.8/WdatePicker.js"></script> -->
<!-- <script type="text/javascript" src="<%=path%>/boss/static/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>  -->
<script type="text/javascript" src="<%=path%>1/boss/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">
/* $(function(){
	$(".portfolio-area li").Huihover();
}); */
function photo(imgurl,title,url,w,h){
    $("#oldurl").val(imgurl);
    layer_show(title,url,w,h);
}
function reload_page(){
	location.reload();
}

function client_del(delurl){
	 var url = $("#url").val();
     var id = $("#userid").val();
	layer.confirm('确认要删除吗？',function(index){
		var imgArr=url.split(",");
		var imgsrc="";
		 for (var i = 0; i <imgArr.length; i++){
			   if(imgArr[i]==delurl){
				   imgArr.splice($.inArray(imgArr[i],imgArr),1);
			   }
			}
		 var imgsrc=imgArr.join(",");
		$.ajax({
			type: 'POST',
			url: '<%=path%>/rp?p0=A-boss-mod&p1=album_picture&p2='+id+'&p3='+imgsrc,
			success: function(data){
				 if(data='1'){
					 layer.msg('已删除!',{icon:1,time:1000});
						setTimeout(function () { 
						javascript:location.replace(location.href);
					    }, 1000);
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
</script>
</body>
</html>