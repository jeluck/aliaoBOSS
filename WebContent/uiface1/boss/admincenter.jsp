<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%--声明我要使用C标签--%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% 
String basePath = request.getScheme()+"://"+request.getServerName();
	String path = request.getContextPath() + "/uiface";
String adminname ="";
String b="";
if(request.getSession().getAttribute("admin")!=null&&!"".equals(request.getSession().getAttribute("admin").toString())){
	adminname=request.getSession().getAttribute("admin").toString();
	b=request.getSession().getAttribute("power")+"";
 }else{
	response.sendRedirect(path+"1/boss/adminLogin.jsp"); 
 }
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico"/>
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=path%>1/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path%>1/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>钻石后台管理系统</title>
<meta name="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="/aboutHui.shtml">后台管理系统</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml">H-ui</a> 
			<!--<span class="logo navbar-slogan f-l mr-10 hidden-xs">v3.1</span> -->
			<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
		
		<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
			<ul class="cl">
				<li>超级管理员</li>
				<li class="dropDown dropDown_hover">
					<a href="#" class="dropDown_A">admin <i class="Hui-iconfont">&#xe6d5;</i></a>
					<ul class="dropDown-menu menu radius box-shadow">
						<li><a href="javascript:;" onClick="myselfinfo()">个人信息</a></li>
						<!--  <li><a href="#">切换账户</a></li> -->
						<li><a href="<%=path%>1/boss/adminLogin.jsp">退出</a></li>
					</ul>
				</li>
				<!--  <li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li> -->
				<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
					<ul class="dropDown-menu menu radius box-shadow">
						<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
						<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
						<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
						<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
						<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
						<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
					</ul>
				</li>
			</ul>
		</nav>
	</div>
</div>
</header>
<aside class="Hui-aside">
	<div class="menu_dropdown bk_2">
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe611;</i> 用户管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
				<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=memberbackstage&p2=&p3=&p4=1&p5=tojsp" data-title="会员列表" href="javascript:void(0)">会员列表</a></li>
                <li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=anchor_search&p2=0&p3=&p4=tojsp&p5=1" data-title="主播列表" href="javascript:void(0)">主播列表</a></li>
                <li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=app_search&p2=0&p4=&p3=tojsp" data-title="主播列表" href="javascript:void(0)">app用户分析</a></li>
				</ul>
			</dd>
		</dl>
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe63a;</i> 账务管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=pay_table_search&p2=&p3=1&p4=&p5=&p6=&p7=tojsp&p8=&p9=" data-title="充值记录表" href="javascript:void(0)">充值记录表</a></li>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=income_table_search&p2=1&p3=&p4=&p5=tojsp&p6=&p7=0" data-title="主播收入明细" href="javascript:void(0)">主播收入明细</a></li>
					
					<% if(b.equals("1")){%>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=cash_withdrawal&p2=1&p3=&p4=&p5=tojsp&p6=&p7=0" data-title="主播提现明细" href="javascript:void(0)">主播提现明细</a></li>
					<% } %>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=income_table_search1&p2=1&p3=&p4=&p5=tojsp&p6=&p7=0" data-title="推广收入明细" href="javascript:void(0)">推广收入明细</a></li>
					<% if(b.equals("1")){%>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=cash_withdrawal&p2=1&p3=&p4=&p5=tojsp&p6=&p7=1" data-title="推广提现明细" href="javascript:void(0)">推广提现明细</a></li>
				    <% } %>
				</ul>
			</dd>
		</dl>
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe725;</i> 认证管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=renzheng_v&p2=1&p3=tojsp&p4=&p5=" data-title="待审核" href="javascript:void(0)">待审核</a></li>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=renzheng_v_passed&p2=1&p3=tojsp&p4=&p5=" data-title="已通过" href="javascript:void(0)">已通过</a></li>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=renzheng_v_no&p2=1&p3=tojsp&p4=&p5=" data-title="未通过" href="javascript:void(0)">未通过</a></li>
				</ul>
			</dd>
		</dl>
		<%-- <% if(b.equals("1")){%>
		 <dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe725;</i> 代理商管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=agentist_manage&p2=0&p4=&p3=tojsp" data-title="代理商申请" href="javascript:void(0)">代理商申请</a></li>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=agentist&p2=0&p4=&p3=tojsp" data-title="代理商列表" href="javascript:void(0)">代理商列表</a></li>
					<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=agent_set&p2=0&p4=&p3=tojsp" data-title="代理商相关设置" href="javascript:void(0)">代理商相关设置</a></li>
				</ul>
			</dd>
		</dl> 
		 <% } %> --%>
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe72d;</i> 视频管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
				   <li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=video_manage&p2=0&p4=&p3=tojsp" data-title="短视频审核" href="javascript:void(0)">短视频审核</a></li> 
				</ul>
			</dd>
		</dl> 
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe72d;</i> 图片管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
			   <li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=album_list&p2=0&p4=&p3=tojsp" data-title="相册审核" href="javascript:void(0)">相册审核</a></li> 
				</ul>
			</dd>
		</dl>
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe646;</i> 礼物管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
			        <li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=gift_list&p2=&p3=1&p4=tojsp" data-title="礼物管理" href="javascript:void(0)">礼物管理</a></li> 
				</ul>
			</dd>
		</dl>
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe72d;</i> 举报管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
				   <li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=blacklist_manage&p2=0&p4=&p3=tojsp" data-title="举报管理" href="javascript:void(0)">举报管理</a></li> 
				</ul>
			</dd>
		</dl>
		<% if(b.equals("1")){%>
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe72d;</i> 设置<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
				<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=recycle_photo&p2=1&p3=tojsp" data-title="广告轮播图设置" href="javascript:void(0)">广告轮播图设置</a></li>
				<%-- <li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=cash_set&p2=0&p4=&p3=tojsp" data-title="提现相关设置" href="javascript:void(0)">提现相关设置</a></li> --%>
				<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=fencheng_set&p2=0&p4=&p3=tojsp" data-title="主播分成相关设置" href="javascript:void(0)">主播分成相关设置</a></li> 
				<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=recharge_set&p2=0&p4=&p3=tojsp" data-title="钻石充值设置" href="javascript:void(0)">钻石充值设置</a></li>
				<%-- <li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=fenxiao_search_set&p2=0&p4=&p3=tojsp" data-title="分销设置" href="javascript:void(0)">分销设置</a></li>  --%>
				<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=fenxiang_search&p2=0&p4=&p3=tojsp" data-title="分销设置" href="javascript:void(0)">分销设置</a></li> 
				<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=notification_search&p2=0&p4=&p3=tojsp" data-title="系统通知" href="javascript:void(0)">系统通知</a></li> 
				<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=automsg_search&p2=0&p4=&p3=tojsp" data-title="自动打招呼" href="javascript:void(0)">自动打招呼</a></li>
				<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=jay_search&p2=1&p4=&p3=tojsp" data-title="警告语" href="javascript:void(0)">警告语</a></li>
				</ul>
			</dd>
		</dl>
		<% } %>
		<% if(b.equals("1")){%>
		<dl id="menu-product">
			<dt><i class="Hui-iconfont">&#xe72d;</i> 管理员设置<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			<dd>
				<ul>
				<li><a data-href="<%=path%>/rp?p0=A-boss-search&p1=admin_list&p2=1&p3=&p4=tojsp" data-title="管理员管理" href="javascript:void(0)">管理员管理</a></li>
				</ul>
			</dd>
		</dl>
		<% } %>
</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active">
					<span title="欢迎页面" data-href="http://120.27.98.128:9118/uiface1/welcome.html">欢迎页面</span>
					<em></em></li>
		</ul>
	</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="http://120.27.98.128:9118/uiface1/welcome.html"></iframe>
	</div>
</div>
</section>

<div class="contextMenu" id="Huiadminmenu">
	<ul>
		<li id="closethis">关闭当前 </li>
		<li id="closeall">关闭全部 </li>
</ul>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path%>1/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path%>1/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path%>1/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=path%>1/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path%>1/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">
$(function(){
	
});
/*个人信息*/
function myselfinfo(){
	layer.open({
		type: 1,
		area: ['300px','200px'],
		fix: false, //不固定
		maxmin: true,
		shade:0.4,
		title: '查看信息',
		content: '<div>管理员信息</div>'
	});
}

/*资讯-添加*/
function article_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*图片-添加*/
function picture_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*产品-添加*/
function product_add(title,url){
	var index = layer.open({
		type: 2,
		title: title,
		content: url
	});
	layer.full(index);
}
/*用户-添加*/
function member_add(title,url,w,h){
	layer_show(title,url,w,h);
}


</script> 

<!--此乃百度统计代码，请自行删除-->
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "https://hm.baidu.com/hm.js?080836300300be57b7f34f4b3e97d911";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<!--/此乃百度统计代码，请自行删除-->
</body>
</html>