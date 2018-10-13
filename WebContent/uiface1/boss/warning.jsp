<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath() + "/uiface";
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5shiv.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <![endif]-->
    <link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/css/H-ui.admin.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>1/boss/lib/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/skin/default/skin.css" id="skin"/>
    <link rel="stylesheet" type="text/css" href="<%=path%>1/boss/static/h-ui.admin/css/style.css"/>
    <!--[if IE 6]>
    <script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js"></script>
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
    <span class="c-gray en">&gt;</span> 设置
    <span class="c-gray en">&gt;</span> 警告语
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
       href="javascript:location.replace(location.href);" title="刷新">
        <i class="Hui-iconfont">&#xe68f;</i>
    </a>
</nav>
<input type="hidden" name="aaid" id="aaid"/>
<div class="page-container">
    <div class="text-c">
        <%--<div class="mt-20">--%>
        <%--<div class="text-c">--%>
        <%--<input type="text" class="input-text" style="width:250px"  placeholder="请输入搜索内容" id="searchtxt" name="searchtext">	--%>
        <%--<select id="select1" class="select1">--%>
        <%--<option value=""></option>--%>
        <%--<option value="查ID">查ID</option>--%>
        <%--<option value="查昵称">查昵称</option>--%>
        <%--<option value="查手机号">查手机号</option>--%>
        <%--</select>--%>
        <%--<button type="submit" class="btn btn-success radius" id="searchbtn" name=""><i class="Hui-iconfont"></i>搜索</button>--%>
        <%--<button type="submit" class="btn btn-success radius" id="inputExcel" name=""><i class="Hui-iconfont"></i>导出EXCEL</button>--%>
        <%--</div>	--%>
        <%--</div>--%>
        <div class="mt-20">
            <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper ">
                <table class="table table-border table-bordered table-hover table-bg table-sort">
                    <thead>
                    <tr class="text-c">
                        <th width=20>序号</th>
                        <th width="20">ID</th>
                        <th width="40">警告语</th>
                        <th width="40">微信</th>
                    </tr>
                    </thead>
                    <tbody id="list-content">
                    <c:forEach var="map" items="${reList }" varStatus="status">
                        <tr class="text-c">
                            <td>${status.count}</td>
                            <td>${map['id']}</td>
                            <td>${map['warning']}<a title="编辑" href="javascript:;"onclick="e_warning(${map['id']})" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a></td>
                            <td>${map['wxcode']}<a title="编辑" href="javascript:;"onclick="e_wxcode(${map['id']})" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a></td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>
<!--_footer 作为公共模版分离出去-->
<script type="text/javascript" src="<%=path%>1/boss/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>1/boss/lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="<%=path%>1/boss/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="<%=path%>1/boss/static/h-ui.admin/js/H-ui.admin.js"></script>
<!--/_footer 作为公共模版分离出去-->
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=path%>1/boss/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<!-- <script type="text/javascript" src="<%=path%>1/static/lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="<%=path%>1/static/lib/jquery.validation/1.14.0/validate-methods.js"></script>  -->
<script type="text/javascript" src="<%=path%>1/boss/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript">

    $(function () {


    });

    /*刷新列表*/
    <%--function fresh_page() {--%>
        <%--$.ajax({--%>
            <%--cache: true,--%>
            <%--type: "POST",--%>
            <%--url: "<%=path%>/rp?p0=A-boss-search&p1=jay_search&p2=&p3=&p4=tojson",--%>
            <%--async: true,--%>
            <%--error: function (request) {--%>
                <%--alert("提交失败 ");--%>
            <%--},--%>
            <%--success: function (data) {--%>
                <%--console.info(data);--%>
                <%--var json = eval("(" + data + ")");--%>
                <%--var content = '';--%>
                <%--for (var i = 0; i < json.length - 1; i++) {--%>
                    <%--content += '<tr class="text-c">'--%>
                        <%--+ '<td>' + (Number(json[json.length - 1].current) + 1 + i) + '</td>'--%>
                        <%--+ '<td>' + json[i].id + '</td>'--%>
                        <%--+ '<td>' + json[i].warning + '' +--%>
                        <%--+'\<a title="编辑" href="javascript:;"onclick="e_warning(' + json[i].warning + ')" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a></td>'--%>
                        <%--+ '<td>' + json[i].wxcode + '' +--%>
                        <%--+'\<a title="编辑" href="javascript:;"onclick="e_wxcode(' + json[i].warning + ')" class="ml-5" style="text-decoration: none"><i class="Hui-iconfont">&#xe6df;</i></a></td>'--%>
                <%--}--%>
                <%--content += '</tr>';--%>
                <%--$("#list-content").html(content);--%>
            <%--}--%>
        <%--});--%>
    <%--}--%>
    function e_warning(id){
        var age = prompt("请输入警告语","");
        if(age!=null && age!=""){
            layer.confirm('确认输入以上内容？',function(index){
                $.ajax({
                    type:'POST',
                    url: '<%=path%>/rp?p0=A-boss-mod&p1=e_warning&p2='+id+'&p3='+age,
                    success: function(data){
                        if(data='1'){
                            location.replace(location.href);
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

    function e_wxcode(id){
        var age = prompt("请输入微信","");
        if(age!=null && age!=""){
            layer.confirm('确认输入以上内容？',function(index){
                $.ajax({
                    type:'POST',
                    url: '<%=path%>/rp?p0=A-boss-mod&p1=e_wxcode&p2='+id+'&p3='+age,
                    success: function(data){
                        if(data='1'){
                            location.replace(location.href);
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


</script>
</body>
</html>