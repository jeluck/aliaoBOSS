<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%--声明我要使用C标签--%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<% 
	String path = request.getContextPath() + "/uiface";
%>
<!DOCTYPE html>
<!-- saved from url=(0019)http://andalb.com/p -->
<html data-dpr="1" style="font-size: 64px;"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title>蝌蚪窝</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no,minimal-ui">
    <meta name="format-detection" content="telephone=no">
    <title>蝌蚪窝--免费一对一视频激情聊天</title>
    <style type="text/css">
        *{margin:0; padding:0;}
        a{text-decoration: none;}
        img{max-width: 100%; height: auto;}
        .weixin-tip{display: none; position: fixed; left:0; top:0; bottom:0; background: rgba(0,0,0,0.8); filter:alpha(opacity=80);  height: 100%; width: 100%; z-index: 100;}
        .weixin-tip p{text-align: center; margin-top: 10%; padding:0 5%;}
    </style>
    <link rel="stylesheet" href="./p_files/style.css">
</head>

<body style="font-size: 12px;">
    <div class="weixin-tip">
		<p>
            <img src="./p_files/kedouwo.png" alt="微信打开">
        </p>
    </div>
    <!--头部-->
    <header>
        <div class="detail_title"><img src="./p_files/kedouwo.png" id="logo"></div>
    </header>
    <!--内容-->
    <div class="panes cf">
        <div class="panes_notes">系统已为您筛选附近的人</div>
        <div class="panes_cont" id="list">
	        <c:forEach var="map" items="${reList}" varStatus="status">
				<dl>
					<a href="javascript:void(0)">
						<dt>
							<img src="${status.count}">
							<div class="panes_line">在线</div>
							<div class="panes_cation">
								<i>
									<img src="./p_files/cation.png">
								</i>真人认证
							</div>
						</dt>
						<dd>
							<span class="panes_title">${map['nickname']}</span><span class="panes_red">${map['age']}岁</span>
							<div class="panes_bore"><i><img src="./p_files/adver.png"></i>2.2km</div>
						</dd>
					</a>
				</dl>
			</c:forEach>
		</div>
    <div class="foot">
        <div class="footer"><i><img src="./p_files/member.png"></i>超高成功率私享恋爱神器</div>
    </div>
    <!-- 底部弹窗 -->
    <div class="box_vid">
        <div class="box_video" style="display: block;">
            <dl>
                <dt><img src="http://img.fzkjxy.com/mj/m/img/9.jpg" id="img1"></dt>
                <dd>
                    <h5 id="nickname">似梦似幻</h5>
                    <p>正在邀请您进行视频聊天</p>
                </dd>
            </dl>
            <div class="box_right"><a href="javascript:void(0);" onclick="module()"><i><img src="./p_files/tel.png"></i>立即接听</a></div>
        </div>
    </div>
    <!-- 点击弹窗 -->
    <div class="box_popup" style="display: none;">
        <div class="popup">
            <div class="popup_cont" id="text">很抱歉您未安装《蝌蚪窝》</div>
            <!--onclick="lc(&#39;http://t1.51meijian.com/meijian/apk/meijian_main_1.4.0.28.apk&#39;)"-->
			<!--这里是onclick单击事件,以后换成蝌蚪窝的下载地址-->
			<div class="popup_button"><a href="javascript:;">点击这里，立即安装</a></div>
            <div class="popup_close"><img src="./p_files/close.png"></div>
        </div>
    </div>


</body></html>