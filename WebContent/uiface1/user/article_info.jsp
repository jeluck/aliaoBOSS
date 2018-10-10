<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>

<%--声明我要使用C标签--%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath()+"/uiface";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<title>文章详情</title>
</head>
<style type="text/css">

.rich_media_meta_list img {
    max-width: 100%!important;
}
</style>
<body>
<c:if test="${reList[0]['article_type'] == '0'}">
<div class="rich_media_meta_list" >${reList[0]['article_content']}</div>
</c:if>

<c:if test="${reList[0]['article_type'] == '1'}">
<div  style="text-align:center"  >
<video id="video" controls="controls" style=" max-width: 90%!important; " >
<source src="<%=path %>/../${reList[0]['article_content']}">
</video>
</div>
</c:if>
</body>
</html>