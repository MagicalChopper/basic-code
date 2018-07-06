<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%String basePath = pageContext.getRequest().getServletContext().getContextPath();
    pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户管理界面</title>
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<!-- pagination是否分页 toolbar指定该表格使用的工具栏 -->
<table id="dg" title="用户管理" class="easyui-datagrid" fitColumns="true" pagination="true"
       rownumbers="false" url="UserListAction.do" fit="true" toolbar="#tb"
       pageSize="10" pageList="[10,20,30]">
    <thead>
        <tr>
            <th field="cb" checkbox="true" align="center"></th>
            <%--<th field="id" width="30" align="center">编号</th>--%>
            <th field="username" width="50" align="center">用户名</th>
            <th field="password" width="50" align="center">密码</th>
            <th field="email" width="50" align="center">邮箱</th>
            <th field="head" width="50" align="center">头像地址</th>
            <th field="gender" width="50" align="center">性别</th>
            <th field="phone" width="50" align="center">电话</th>
            <th field="userInfo" width="100" align="center">简介</th>
        </tr>
    </thead>
</table>
</body>
</html>