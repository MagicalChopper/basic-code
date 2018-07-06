<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String basePath = pageContext.getRequest().getServletContext().getContextPath();
    pageContext.setAttribute("basePath", basePath);
    String localAddr = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();//即http://localhost:8080
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>员工管理系统主页</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

        /**
         * 打开选项卡
         */
        function openTab(text,url,iconCls){
            if($("#tabs").tabs("exists",text)){
                $("#tabs").tabs("select",text);
            }else{
                var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='<%=localAddr%>/"+url+"'></iframe>";
                $("#tabs").tabs("add",{
                    title:text,
                    iconCls:iconCls,
                    closable:true,
                    content:content
                });
            }
        }

        /**
         * 注销
         */
        function logout(){
            $.messager.confirm("系统提示","您确定要退出系统吗",function(r){
                if(r){
                    window.location.href="/login.jsp";
                }
            });
        }

    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height: 78px;background-color: #E0ECFF">
    <table style="padding:5px;width:100%">
        <tr>
            <td width="50%">
                <img src="/static/images/bglogo1.png"/>
            </td>
            <td valign="bottom" align="right" width="50%">

                <font size="3">&nbsp;&nbsp;<strong>欢迎您:</strong></font>,
                <font size="3"><strong></strong></font>

                <%--<a onclick="updHeader();">--%>
                    <%--<img src="/ygxt/static/user-head/${user.header}" style="width:40px;height:40px;" >--%>
                <%--</a>--%>
                <button onclick="logout();">注销</button>
            </td>

        </tr>
    </table>
</div>
<div region="center">
    <div class="easyui-tabs" fit="true" border="false" id="tabs">
        <div title="首页" data-options="iconCls:'icon-home'">
            <div align="center" style="padding-top: 10px;padding-left:10px;">


                <div id="schedule-div" class="easyui-draggable" data-options="handle:'#myinfo'" style="width:48%;height:200px;border:1px solid #ccc;float:right;">
                </div>

                <div id="info-div" class="easyui-draggable" data-options="handle:'#myinfo'" style="width:48%;;height:200px;border:1px solid #ccc;float: right;">
                </div>
            </div>
        </div>
    </div>
</div>
<div region="west" style="width: 200px" title="导航菜单" split="true">
    <div class="easyui-accordion" data-options="fit:true,border:false">

        <div title="公司管理"  data-options="iconCls:'icon-khgl'" style="padding:10px">
            <a href="javascript:openTab('用户管理','/user/UserManageAction.do','icon-user')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-user'" style="width: 150px;">员工管理</a>
            <a href="javascript:openTab('角色管理','/role/RoleManageAction.do','icon-cpxxgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cpxxgl'" style="width: 150px;">职位管理</a>
            <a href="javascript:openTab('菜单管理','/menu/MenuManageAction.do','icon-sjzdgl')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-sjzdgl'" style="width: 150px;">菜单管理</a>
        </div>

    </div>
</div>

</div>
</body>
</html>