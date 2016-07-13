<%--
  Created by IntelliJ IDEA.
  User: jerrychien
  Date: 16/6/29
  Time: 23:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>用户登录</title>
    <!-- Bootstrap Core CSS -->
    <link href="../../resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <!-- MetisMenu CSS -->
    <link href="../../resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="../../resources/dist/css/sb-admin-2.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="../../resources/bower_components/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css">

    <link href="/favicon.ico" rel="icon" type="image/x-icon"/>
    <link href="/favicon.ico" rel="shortcut icon" type="image/x-icon"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Please Sign In</h3>
                </div>
                <div class="panel-body">
                    <form role="form">
                        <div class="form-group">
                            <input class="form-control" placeholder="E-mail" name="email"
                                   type="email" autofocus>
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Password" name="password"
                                   type="password" value="">
                        </div>
                        <div class="checkbox">
                            <label>
                                <input name="remember" type="checkbox" value="Remember Me">Remember
                                Me
                            </label>
                        </div>
                        <!-- Change this to a button or input when using this as a form -->
                        <a href="/" class="btn btn-lg btn-success btn-block">Login</a>
                        <%
                            String url = request.getRequestURL().toString();
                            String a = (String) request.getAttribute("mySession");
                            String b = (String) session.getAttribute("mySession");
                        %>
                        current action:<%=request.getAttribute("action")%><br/>
                        sessionAttribute:<br/>
                        当前页面URL:<%=url%><br/>
                        request.getAttribute("mySession"):<%=a%><br/>
                        session.getAttribute("mySession"): <%=b%><br/>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
