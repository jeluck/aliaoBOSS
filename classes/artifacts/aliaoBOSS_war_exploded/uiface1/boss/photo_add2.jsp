<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="ck" uri="http://ckeditor.com"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName();
int webPort = request.getServerPort();
if(webPort != 80) {
	basePath = basePath+":"+webPort;
}
String path = basePath+"/uiface";
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
<link rel="Bookmark" href="/favicon.ico">
<link rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/uiface1/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/uiface1/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/uiface1/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/uiface1/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/uiface1/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<!--/meta 作为公共模版分离出去-->

<title>娃娃增加</title>
<meta name="keywords"
	content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description"
	content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
	<article class="page-container">
		<form  action="<%=path%>/rp?a=A-boss-add&b=photo_add" method="post"
			class="form form-horizontal" id="form-article-add" name="ThisForm">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">图片:</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div class="uploader-thum-container">
						<div id="fileList1" class="uploader-list"></div>
						<input type="file" id="fileimg" /> <input type="hidden"
							name="imgname" id="imges"  />
						<button type="button" class="btn btn-primary radius"
							onclick=upimg() name="">
							<i class="Hui-iconfont">&#xe642;</i>上传
						</button>
					</div>
				</div>
			</div>

		</form>
	</article>
	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/uiface1/lib/layer/2.4/layer.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/static/h-ui.admin/js/H-ui.admin.js"></script>
	<!--/_footer /作为公共模版分离出去-->
	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/lib/My97DatePicker/4.8/WdatePicker.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/lib/jquery.validation/1.14.0/validate-methods.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/lib/jquery.validation/1.14.0/messages_zh.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/lib/webuploader/0.1.5/webuploader.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/lib/ueditor/1.4.3/ueditor.config.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/lib/ueditor/1.4.3/ueditor.all.min.js"> </script>
	<script type="text/javascript"
		src="<%=basePath%>/uiface1/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript">
$(function(){
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	//表单验证
	$("#form-article-add").validate({
		rules:{
			"babyname":{
				required:true,
			},
			"babyprice":{
				required:true,
			},
			"changeprice":{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			document.ThisForm.submit();
			//$("#form-article-add").ajaxSubmit();
			setTimeout(function () {
				var index = parent.layer.getFrameIndex(window.name);
				//parent.$('.btn-refresh').click();
				parent.location.reload();
				parent.layer.close(index);
		    }, 100);
		}
	});
});



/* function layer_close11(){
	
	var btn = document.getElementById("descs");
	
	alert(btn.value);
	
	
	alert($("#descs").val());
	$("#referral").val($("#descs").val());
} */

var flag="0";
function upimg(){
    var id = this.parent.$("#aaid").val();
    // var url = document.querySelector("#fileimg").value;
	var oFiles = document.querySelector("#fileimg").files;
	if(oFiles.length){
		var file=oFiles[0];
		var formdata = new FormData();
		formdata.append("imgFile", file);
        $.ajax({
			url :"<%=path%>/UploadFtpServlet",
			type : 'post',
			data : formdata,
			cache : false,
			contentType : false,
			processData : false,
			success : function(data) {
				var img = data;
				document.getElementById("imges").value = img;
				// alert(this.parent.$("#aaid").val());
                anchor_photo(id,img);
				// alert("上传成功");
			}
		})
	flag="1";
	}  else{
		alert("请选择需要上传的图片");
	}
}

//主播头像
function anchor_photo(id,imgsrc){
    if(imgsrc == ""){
        layer.msg('操作失败',{icon:1,time:1000});
        return;
	}
    var currentpageindex =  Number(this.parent.$("#currentpage").html());
    $.ajax({
        type:'POST',
        url: '<%=path%>/rp?p0=A-boss-mod&p1=anchor_photo&p2='+id+'&p3='+imgsrc,
        success: function(data){
            if(data='1'){
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            }else{
                layer.msg('操作失败',{icon:1,time:1000});
            }
        },
        error:function(data) {
            layer.msg('操作失败',{icon:1,time:1000});
        },
    });
    window.parent.fresh_page(currentpageindex);
}
</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>