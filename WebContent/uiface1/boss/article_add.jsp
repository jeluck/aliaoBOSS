<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib prefix="ck" uri="http://ckeditor.com"%>
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
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="<%=path %>1/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="<%=path %>1/static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<!--/meta 作为公共模版分离出去-->

<title>新增文章 - 资讯管理 - H-ui.admin v3.1</title>
<meta name="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">

</head>
<body>
<article class="page-container">
	<form  
	<c:choose>
			<c:when test="${param.method=='add'}">action="<%=path%>/art?a=A-boss-add&b=ArticleAdd" </c:when>
		</c:choose>
	 class="form form-horizontal" id="form-article-add"  name="ThisForm"  enctype="">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">文章标题：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="" placeholder="请输入文章标题" id="articlename" name="articlename">
			</div>
		</div>
	  <div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">文章图片：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<div class="uploader-thum-container">
					<div id="fileList1" class="uploader-list"></div>
					<input type="file" id="fileimg"   />
					<input type="hidden" name="imgname" id="imges" value=""/>
					<button type="button" class="btn btn-primary radius"  onclick=upimg() name=""><i class="Hui-iconfont">&#xe642;</i>上传</button>
				</div>
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-2">文章分类：</label>
			<div class="formControls col-xs-8 col-sm-9"> <span class="select-box">
				<select name="articletype" id="articletype" class="select">
				
				</select>
				</span> </div>
		</div>		


		 <div class="row cl">
		        <label class="form-label col-xs-4 col-sm-2">内容类型：</label>
				<div class="formControls col-xs-8 col-sm-9"> 
						<input type="checkbox"  id="radiourl" onclick="titlechange('0')"  value="1"  checked="checked" >图文详情
						<input type="checkbox"  id="radiomy"  style="margin-left:10%" onclick="titlechange('1')"   >视频
				</div>
					<input type="hidden"  id="arttype"  name="arttype" value="0"/>
		</div>
        <div class="row cl"  id="artcontent" >
			<label class="form-label col-xs-4 col-sm-2">文章内容：</label>
			<div class="formControls col-xs-8 col-sm-9"> 
				<!-- <input type="text" class="input-text" value="" placeholder="请输入文章内容" id="articlereferral" name="articlereferral"> -->
				
				<textarea cols="170" id="descs" name="descs"></textarea>
                <ck:replace replace="descs" basePath="ckeditor"></ck:replace>
				
			</div>
		</div>
        
        
        <div class="row cl" id="artvideo"  style="display:none">
			<label class="form-label col-xs-4 col-sm-2">视频文件：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<div class="uploader-thum-container">
					<div id="fileList1" class="uploader-list"></div>
					<input type="file" id="filevid"   />
					<input type="hidden" name="vidname" id="video" value=""/>
					<button type="button" class="btn btn-primary radius"  onclick=upvid() name=""><i class="Hui-iconfont">&#xe642;</i>上传</button>
				</div>
			</div>
		</div>
        
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button  class="btn btn-primary radius"  onclick="layer_open()"><i class="Hui-iconfont">&#xe632;</i> 保存并提交</button>
				<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button> 
			</div>
		</div>
	</form>
</article>




<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path %>1/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="<%=path %>1/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path %>1/static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="<%=path %>1/static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer /作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path %>1/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path %>1/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path %>1/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="<%=path %>1/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="<%=path %>1/lib/webuploader/0.1.5/webuploader.min.js"></script> 
<script type="text/javascript" src="<%=path %>1/lib/ueditor/1.4.3/ueditor.config.js"></script> 
<script type="text/javascript" src="<%=path %>1/lib/ueditor/1.4.3/ueditor.all.min.js"> </script> 
<script type="text/javascript" src="<%=path %>1/lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
var flag="0";
$(function(){
	
    $.ajax({
        url:'<%=path %>/art?mode=A-user-search&mode2=get_sort',
        type:'POST',
        dataType:'JSON',
        //data:{'txid':txid},
        success:function(data){
            if(data != ''){
				// 将获取到的分类放入select中
				var selDom = $("#articletype");//根据id获取select的jquery对象
				for (var i=0;i<data.length;i++)
				{
					selDom.append("<option value='"+data[i].id+"'>"+data[i].sort+"</option>");//添加option
				}
				
            }
        }
    })
	
	$('.skin-minimal input').iCheck({
		checkboxClass: 'icheckbox-blue',
		radioClass: 'iradio-blue',
		increaseArea: '20%'
	});
	
	//表单验证
	$("#form-article-add").validate({
		rules:{
			"articlename":{
				required:true,
			},
			"articlereading":{
				required:true,
			},
			"articleprice":{
				required:true,
			},
			"articlestatus":{
				required:true,
			},
		},
		onkeyup:false,
		focusCleanup:true,
		success:"valid",
		submitHandler:function(form){
			//$("#referral").val($("#descs").val());
			var oFiles = document.querySelector("#fileimg").files;
			if(oFiles.length){
				if(flag=="1"){
					if(arttype==0){
						var oFiles = document.querySelector("#filevid").files;
						if(oFiles.length){
							if(flag1=="1"){
								alert('进入方法1');
								document.ThisForm.submit();
								//var index = parent.layer.getFrameIndex(window.name);
								//$("#form-article-add").ajaxSubmit();
								setTimeout(function () {
									var index = parent.layer.getFrameIndex(window.name);
									//parent.$('.btn-refresh').click();
									parent.location.reload();
									parent.layer.close(index);
							    }, 100);
							}else{
								alert("请点击视频上传按钮");	
							}
						}else{
							alert("请选择需要上传的视频");
						}
					}else{
						alert('进入方法2');
						//document.ThisForm.submit();
						
					   $("#form-article-add").ajaxSubmit();
						setTimeout(function () {
							var index = parent.layer.getFrameIndex(window.name);
							//parent.$('.btn-refresh').click();
							parent.location.reload();
							parent.layer.close(index);
					    }, 100);
					}
				}else{
					alert("请点击图片上传按钮");	
				}
			}else{
				alert("请选择需要上传的图片");
			}
		}
	});
	
	

});

function layer_close11(){
	
	var btn = document.getElementById("descs");
	
	alert(btn.value);
	
	
	alert($("#descs").val());
	$("#referral").val($("#descs").val());
}
function layer_open(){
	for (instance in CKEDITOR.instances)
        CKEDITOR.instances[instance].updateElement();

     }	

function upimg(){
	
	var oFiles = document.querySelector("#fileimg").files;
	if(oFiles.length){
		var file=oFiles[0];
		console.log(file);
		var formdata = new FormData(); 
		formdata.append("imgFile", file); 
		$.ajax({ 
			url :"<%=path%>/JyFileUploadServlet", 
			type : 'post', 
			data : formdata, 
			cache : false, 
			contentType : false, 
			processData : false, 
			
			success : function(data) { 
				var img = data;
				document.getElementById("imges").value = img;
				alert("上传成功");
			}
		})
	flag="1";
	}  else{
		alert("请选择需要上传的图片");
	}
	
}


var flag1="0";
function upvid(){
	
	var oFiles = document.querySelector("#filevid").files;
	if(oFiles.length){
		var file=oFiles[0];
		console.log(file);
		var formdata = new FormData(); 
		formdata.append("imgFile", file); 
		$.ajax({ 
			url :"<%=path%>/JyFileUploadVideoServlet", 
			type : 'post', 
			data : formdata, 
			cache : false, 
			contentType : false, 
			processData : false, 
			success : function(data) { 
				var img = data;
				//document.getElementById("video").value = "<video id=\"video\" controls=\"controls\"><source src=\""+img+"\"></video>";
				document.getElementById("video").value = img;
				alert("上传成功");
			}
		})
	flag1="1";
	}  else{
		alert("请选择需要上传的文件");
	}
	
}

var arttype=2;
function titlechange(type) {
	
	
	if(type == '0') {
		var obj = document.getElementById("radiomy");
		obj.checked = false;
		var obj1 = document.getElementById("radiourl");
		obj1.checked = true;
		
		/* $("#radiourl").attr("checked","checked");
		$("#radiomy").removeAttr("checked"); */
		arttype=0;
		$("#arttype").val(arttype);
		$("#artcontent").css("display","block");
		$("#artvideo").css("display","none");
	} else if(type == '1') {
		var obj = document.getElementById("radiourl");
		obj.checked = false;
		
		var obj1 = document.getElementById("radiomy");
		obj1.checked = true;
		/* $("#radiomy").attr("checked","checked");
		$("#radiourl").removeAttr("checked"); */
		arttype=1;
		$("#arttype").val(arttype);
		$("#artcontent").css("display","none");
		$("#artvideo").css("display","block");
		return;
	} else {
		return;
	}
}


</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>