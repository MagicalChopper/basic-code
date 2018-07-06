<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String basePath = pageContext.getRequest().getServletContext().getContextPath();
    pageContext.setAttribute("basePath", basePath);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>员工管理系统登录</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/jquery-easyui-1.3.3/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/static/jquery-easyui-1.3.3/themes/icon.css">
    <script type="text/javascript" src="<%=basePath%>/static/jquery-easyui-1.3.3/jquery.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>

    <style type=text/css>
        BODY {
            TEXT-ALIGN: center;
            PADDING-BOTTOM: 0px;
            BACKGROUND-COLOR: #ddeef2;
            MARGIN: 0px;
            PADDING-LEFT: 0px;
            PADDING-RIGHT: 0px;
            PADDING-TOP: 0px
        }

        A:link {
            COLOR: #000000;
            TEXT-DECORATION: none
        }

        A:visited {
            COLOR: #000000;
            TEXT-DECORATION: none
        }

        A:hover {
            COLOR: #ff0000;
            TEXT-DECORATION: underline
        }

        A:active {
            TEXT-DECORATION: none
        }

        .input {
            BORDER-BOTTOM: #ccc 1px solid;
            BORDER-LEFT: #ccc 1px solid;
            LINE-HEIGHT: 20px;
            WIDTH: 182px;
            HEIGHT: 20px;
            BORDER-TOP: #ccc 1px solid;
            BORDER-RIGHT: #ccc 1px solid
        }

        .input1 {
            BORDER-BOTTOM: #ccc 1px solid;
            BORDER-LEFT: #ccc 1px solid;
            LINE-HEIGHT: 20px;
            WIDTH: 120px;
            HEIGHT: 20px;
            BORDER-TOP: #ccc 1px solid;
            BORDER-RIGHT: #ccc 1px solid;
        }
    </style>
    <script type="text/javascript">

        /**
         * 点击刷新验证码
         */
        function changeCode(){
            //生成新验证码的地址
            var src='IdentifyingCode.do?temp='+(
                new Date().getTime().toString(16));
            //调用jquery的attr方法改变id是IdentifyingCode的img的src属性
            $("#identifyingCode").attr("src",src);
            return false;
        }

        /**
         * 登录
         */
        function login(){
            var username=$("#username").val();
            var password=$("#password").val();
            var identifyingCode=$("#identifyingCode2").val();

            if(username==null||username==""){
                alert("用户名不能为空！");
                return;
            }
            if(password==null||password==""){
                alert("密码不能为空！");
                return;
            }
            if(identifyingCode==null||identifyingCode==""){
                alert("验证码不能为空");
                return;
            }
            //提交表单
            $.ajax({
                type:"post",
                url:"<%=basePath%>/login/LoginAction.do",
                data:{
                    username:username,
                    password:password,
                    identifyingCode:identifyingCode
                },
                success:function(result){
                    var res = eval("("+result+")");
                    console.log(result)
                    console.log(res)
                    if (res.succ) {
                        //跳转主页面
                        window.location.href="<%=basePath%>/login/LoginSuccAction.do";
                    } else {
                        $.messager.alert("系统提示",res.msg);
                        resetValue();
                        changeCode();
                    }
                }
            });
        }

        /**
         * 打开添加用户的页面
         */
        function openRegDialog(){
            $("#reg-dlg").dialog("open").dialog("setTitle","一分钟注册新用户");
        }

        /**
         * 清空输入框
         */
        function resetValue(){
            $("#username").val("");
            $("#password").val("");
            $("#identifyingCode2").val("");
            $("#reg-username").val("");
            $("#reg-password").val("");
            $("#confirmPassword").val("");
            $("#email").val("");
            $("#phone").val("");
            $("#userInfo").val("");
        }

        var checkUserNameFlag = false;
        var checkPasswordFlag = false;
        var checkConfirmPasswordFlag = false;
        var checkPhoneFlag = false;
        var checkEmailFlag = false;

        /**
         * 查询用户名是否已经存在
         */
        function checkUserIsExists(){
            var username = $("#reg-username").val();
            if(username==""){
                $("#username-msg").html("<font color='red'>请输入用户名</font>");
                return;
            }
            $.ajax({
                type:"post",
                url:"<%=basePath%>/user/UserNameRegCheckAction.do",
                data:{username:username},
                success:function(result){
                    var res = eval("("+result+")");
                    if (res.succ) {
                        $("#username-msg").html("<font color='green'>用户名可以注册</font>");
                        checkUserNameFlag = true;
                    } else {
                        $("#username-msg").html("<font color='red'>用户名已存在</font>");
                        checkUserNameFlag = false;
                    }
                }
            });
        }

        /**
         * 检查密码是否合法
         */
        function checkPassIsFormat(){
            var password = $("#reg-password").val();
            if(password==""){
                $("#password-msg").html("<font color='red'>请输入密码</font>");
                return;
            }
            var regularNums  = /^[0-9]+$/;//纯数字
            var regularLllegalCharacter = new RegExp("[^a-zA-Z0-9\_\u4e00-\u9fa5]","i");//非法字符
            if(regularNums.test(password)||password.length<6||password.length>16){
                $("#password-msg").html("<font color='red'>密码必须6-16位非纯数字</font>");
                checkPasswordFlag = false;
            }else if(regularNums.test(password.charAt(0))){
                $("#password-msg").html("<font color='red'>密码不能以数字开头</font>");
                checkPasswordFlag = false;
            }else if(regularLllegalCharacter.test(password)){
                $("#password-msg").html("<font color='red'>密码含有非法字符</font>");
                checkPasswordFlag = false;
            }else{
                $("#password-msg").html("");
                checkPasswordFlag = true;
            }
        }

        /**
         * 确认密码验证
         */
        function checkConfirmPasswordRight() {
            var confirmPassword = $("#confirmPassword").val();
            if(confirmPassword==""){
                $("#confirmPassword-msg").html("<font color='red'>请输入密码</font>");
                return;
            }
            var password = $("#reg-password").val();
            if(password!=confirmPassword){
                $("#confirmPassword-msg").html("<font color='red'>两次输入密码不同</font>");
                checkConfirmPasswordFlag = false;
            }else{
                $("#confirmPassword-msg").html("");
                checkConfirmPasswordFlag = true;
            }
        }

        /**
         * 手机格式验证
         */
        function checkPhone(){
            var phone = $("#phone").val();
            if(phone==""){
                $("#phone-msg").html("<font color='red'>请输入手机号码</font>");
                return;
            }
            var regularNums  = /^[0-9]+$/;//纯数字
            if(regularNums.test(phone) && phone.length>4 && phone.length<12){
                $("#phone-msg").html("");
                checkPhoneFlag = true;
            }else{
                $("#phone-msg").html("<font color='red'>手机号码不合法</font>");
                checkPhoneFlag = false;
            }
        }

        /**
         * 邮箱格式验证
         */
        function checkEmail(){
            var email = $("#email").val();
            var index = email.indexOf("@");
            var emailSuffix = email.substr(index);//后缀带@
            var emailPrefix = email.substr(0,index);//前缀不带@
            if(index<=0){
                $("#email-msg").html("<font color='red'>邮箱不合法</font>");
                checkEmailFlag = false;
            }else{
                if(emailSuffix=="@qq.com"||emailSuffix=="@163.com"){
                    $("#email-msg").html("");
                    checkEmailFlag = true;
                }else{
                    $("#email-msg").html("<font color='red'>邮箱不合法</font>");
                    checkEmailFlag = false;
                }
            }
        }

        /**
         * 点击注册保存用户
         */
        function regUser(){
            //提交id是reg-fm的表单
            $("#reg-fm").form("submit",{
                url:"<%=basePath%>/user/UserRegistAction.do",
                //提交的时候执行是否为空的校验
                onSubmit:function(){
                    return $(this).form("validate") && checkUserNameFlag && checkPasswordFlag && checkConfirmPasswordFlag && checkEmailFlag && checkPhoneFlag;
                },
                success:function(result){
                    var result = eval("("+result+")");
                    if (result.succ) {
                        $.messager.alert("系统提示","注册成功,请等待审核");
                        //重设弹出框的表单值
                        resetValue();
                        closeRegDialog()
                    } else {
                        $.messager.alert("系统提示","保存失败");
                    }
                }
            });
        }

        //关闭弹出框
        function closeRegDialog(){
            $("#reg-dlg").dialog("close");//关闭弹框
            //清除弹框里填写的值
            resetValue();
        }
    </script>
</head>
<body>
    <FORM id="login">
        <TABLE style="MARGIN: auto; WIDTH: 100%; HEIGHT: 100%" border=0
               cellSpacing=0 cellPadding=0>
            <TBODY>
            <TR>
                <TD height=150>&nbsp;</TD>
            </TR>
            <TR style="HEIGHT: 254px">
                <TD>
                    <DIV style="MARGIN: 0px auto; WIDTH: 936px"><IMG
                            style="DISPLAY: block" src="<%=basePath%>/static/images/body_03.jpg"></DIV>
                    <DIV style="BACKGROUND-COLOR: #278296">
                        <DIV style="MARGIN: 0px auto; WIDTH: 936px">
                            <DIV
                                    style="BACKGROUND: url(<%=basePath%>/static/images/body_05.jpg) no-repeat; HEIGHT: 155px">
                                <DIV
                                        style="TEXT-ALIGN: left; WIDTH: 265px; FLOAT: right; HEIGHT: 125px; _height: 95px">
                                    <TABLE border=0 cellSpacing=0 cellPadding=0 width="100%">
                                        <TBODY>
                                        <TR>
                                            <TD style="HEIGHT: 45px"><INPUT type="text" class=input  name="username" id="username"></TD>
                                        </TR>
                                        <TR>
                                            <TD><INPUT type="password" class=input  name="password" id="password"/></TD>
                                        </TR>
                                        <TR>
                                            <td>
                                                <input id="identifyingCode2" type="text" class="input" value="" name="identifyingCode2"/>
                                            </td>
                                        </TR>
                                        <TR>
                                            <td>
                                                <img id="identifyingCode" alt="验证码" src="/IdentifyingCode.do"
                                                     width="100" height="25" onclick="changeCode()">
                                            </td>
                                        </TR>
                                        </TBODY>
                                    </TABLE>
                                </DIV>
                                <DIV style="HEIGHT: 1px; CLEAR: both"></DIV>
                                <DIV style="WIDTH: 380px; FLOAT: right; CLEAR: both">
                                    <TABLE border=0 cellSpacing=0 cellPadding=0 width=300>
                                        <TBODY>

                                        <TR>
                                            <%--登录--%>
                                            <TD width=100 align=right><INPUT
                                                    style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px"
                                                    id=btnLogin src="<%=basePath%>/static/images/btn1.jpg" type=image name=btnLogin onclick="login(); return false;"></TD>
                                            <%--重置--%>
                                            <TD width=100 align=middle><INPUT
                                                style="BORDER-RIGHT-WIDTH: 0px; BORDER-TOP-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px"
                                                id=btnReset src="<%=basePath%>/static/images/btn2.jpg" type=image name=btnReset onclick="login.reset(); return false;"></TD>
                                            <%--注册--%>
                                            <TD width=100 align=right><a onclick="openRegDialog();">立即注册</a></TD>
                                        </TR>
                                        </TBODY>
                                    </TABLE>
                                </DIV>
                            </DIV>
                        </DIV>
                    </DIV>
                    <DIV style="MARGIN: 0px auto; WIDTH: 936px"><IMG
                            src="<%=basePath%>/static/images/body_06.jpg"></DIV>
                </TD>
            </TR>
            <TR style="HEIGHT: 30%">
                <TD>&nbsp;</TD>
            </TR>
            </TBODY>
        </TABLE>
    </FORM>


    <!-- 下面的div用来显示添加用户 -->
    <div id="reg-dlg" class="easyui-dialog" style="width:620px;height:250px;padding:10px 20px"
         closed="true" buttons="#reg-dlg-buttons">
        <form id="reg-fm" method="post">
            <table cellspacing="8px">
                <tr>
                    <td>用户名:</td>
                    <td><input type="text" id="reg-username" name="username"
                               class="easyui-validatebox" required="required"
                               onblur="checkUserIsExists()">
                        &nbsp;<font color="red">*</font><span id="username-msg"></span>
                    </td>
                </tr>
                <tr>
                    <td>密码:</td>
                    <td><input type="password" id="reg-password" name="password"
                               class="easyui-validatebox" required="required"
                               onblur="checkPassIsFormat()">
                        &nbsp;<font color="red">*</font><span id="password-msg"></span>
                    </td>
                </tr>
                <tr>
                    <td>确认密码:</td>
                    <td><input type="password" id="confirmPassword" name="confirmPassword"
                               class="easyui-validatebox" required="required"
                               onblur="checkConfirmPasswordRight()">
                        &nbsp;<font color="red">*</font><span id="confirmPassword-msg"></span>
                    </td>
                </tr>
                <tr>
                    <td>性别:</td>
                    <td>
                        <input type="radio" name="gender" value="1" checked="checked">男<br/>
                        <input type="radio" name="gender" value="2">女<br/>
                    </td>
                </tr>
                <tr>
                    <td>电话:</td>
                    <td><input type="text" id="phone" name="phone"
                               class="easyui-validatebox" required="required"
                               onblur="checkPhone()">
                        &nbsp;<font color="red">*</font><span id="phone-msg"></span>
                    </td>
                </tr>
                <tr>
                    <td>邮箱:</td>
                    <td><input type="text" id="email" name="email"
                               class="easyui-validatebox" required="required"
                               onblur="checkEmail()">
                        &nbsp;<font color="red">*</font><span id="email-msg"></span>
                    </td>
                </tr>
                <tr>
                    <td>用户简介:</td>
                    <td>
						<textarea rows="5" cols="21" id="userInfo"
                                  name="userInfo" class="easyui-validatebox">
						</textarea>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <!-- 添加用户的弹出框编写结束 -->
    <!-- 下面这个div是用来对上面的div框补充 提交按钮和重置按钮的 -->
    <div id="reg-dlg-buttons"><!-- 注意这里的div id 和上面那个div的 buttons="#dlg-buttons"相对应-->
        <a href="javascript:regUser();" class="easyui-linkbutton" iconCls="icon-ok">注册</a>
        <a href="javascript:closeRegDialog();" class="easyui-linkbutton"
           iconCls="icon-cancel">关闭</a>
    </div>
</body>
</html>

<script type="text/javascript">
</script>