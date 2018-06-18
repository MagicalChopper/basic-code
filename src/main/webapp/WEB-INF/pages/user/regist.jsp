<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = pageContext.getRequest().getServletContext().getContextPath();
    pageContext.setAttribute("basePath", basePath);
%>
<html>
<head>
    <title>注册</title>
</head>
<script src="/static/jquery/jquery.min.js"></script>
<body>
<div id="content">
    <div id="content-header">
        <h3>用户注册</h3>
    </div>
    <div class="container-fluid">
        <hr>
        <div class="row-fluid">
            <div class="span6">
                <div class="widget-box">
                    <div class="widget-content nopadding">
                        <form id="regist-form">
                            <div>
                                <label>用户名 :</label>
                                <div>
                                    <input id="username" type="text" placeholder="输入用户名" />
                                </div>
                            </div>
                            <div>
                                <label>密码 :</label>
                                <div>
                                    <input id="password" type="password" placeholder="输入密码" />
                                </div>
                            </div>
                            <div>
                                <label>邮箱</label>
                                <div>
                                    <input id="email" type="text" placeholder="输入邮箱"  />
                                </div>
                            </div>
                            <div id="regist">
                                <a href="javascript:regist()">注册</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {

    });

    function verify(){
        alert("可以了吧");
    }
    function regist() {
        var username = $("#username").val();
        var password = $("#password").val();
        var email = $("#email").val();
        var url = "<%=basePath%>/user/UserRegistAction.do";
        $.ajax({
            type:"POST",
            url:url,
            data:{
                username:username,
                password:password,
                email:email
            },success:function (data) {
                console.log(data)
                data = eval("("+data+")");
                console.log(data)
                if(data.succ){
                    alert("注册成功");
                }else{
                    alert("注册失败");
                }
            }
        });
    }

    String.prototype.replaceAll = function(s1,s2){
        return this.replace(new RegExp(s1,"gm"),s2);
    }
</script>
</body>
</html>
