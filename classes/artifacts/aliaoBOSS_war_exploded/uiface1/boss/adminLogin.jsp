<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath()+"/uiface";
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String adminname ="";
/* if(request.getSession().getAttribute("adminname")!=null){
	request.getSession().setAttribute("adminname", "");
} */
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="date" content="2013-10-23">
<title>钻石商家管理后台</title>

<link href="<%=path %>1/boss/admin_login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
document.onkeydown=function(e){
	var keycode=document.all?event.keyCode:e.which;
	if(keycode==13){
		denglu();
	}
}
function denglu()
{
	
	if(document.getElementById("admin").value=="")
		{
		alert("请输入用户名");
		return false;
		}
	var userName = document.getElementById("admin").value;
	var patrn=/^(\w){6,20}$/; 
    if(!patrn.exec(userName)){
    	alert("只能输入6-20个字母、数字、下划线 ");   
  	  return false; 
    } 
	if(document.getElementById("pwd").value=="")
		{
		alert("请输入密码");
		return false;
		}
 	var pwd = document.getElementById('pwd').value;
	  if (pwd.length > 16 || pwd.length < 6)
	  {
		alert("密码长度应该在 6 - 16 位");
	    return false;
	  } 
	    document.ThisForm.submit();	
}


function init()
{
	if("${sessionScope.adminLoginStatus}"=="err"){
 		<%request.getSession().setAttribute("adminLoginStatus", "");%>
		alert("用户名或密码错误！");
	} 
}

</script>
</head>
<body onload="init()">
<div class="admin_login_wrap">
    <h1>总端登录</h1>
    <div class="adming_login_border">
        <div class="admin_input">
			<form action="<%=path%>/rp?mode1=A-boss-search&mode2=admin_login&sellerid=${account}" method="post" name="ThisForm">
            	    <section class="loginForm">
                <ul class="admin_items">
                    <li>
                        <label for="user">用户名：</label>
                        <input type="text" id="admin" name="admin" placeholder="请输入用户名"   required size="40" class="admin_input_style" />
                    </li>
                    <li>
                        <label for="pwd">密码：</label>
                        <input type="password" id="pwd" name="pwd" placeholder="请输入密码"  required size="40" class="admin_input_style" />
                    <li>
                      <input type="button" tabindex="3" value="登录" onclick="denglu()" class="btn btn-primary" />
                    </li>
                </ul>
                    </section>
            </form>
        </div>
    </div>
</div>
</body>
</html>