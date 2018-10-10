<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
   String path = request.getContextPath()+"/uiface";
%>
 <html>
    <head>
        <meta charset="utf-8" />
        <title>test</title>
    </head>
    <body>
		<div style="text-align:center;">
       
<!-- 播放尺寸-->
            <video id = "video1" width="720", height="480", controls="controls">
<!-- 播放地址${param.url}-->
                <source src="${param.url}" , type="video/mp4"></source>
                
				</video>
		
        </div>
        <script type="text/javascript">
            var myVideo = document.getElementById("video1");//${param.videoid}

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
        </script>
       
    </body>
</html>