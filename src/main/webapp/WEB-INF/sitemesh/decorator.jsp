<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="jerrychien">
    <link href="../../resources/bower_components/bootstrap/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <!-- MetisMenu CSS -->
    <link href="../../resources/bower_components/metisMenu/dist/metisMenu.min.css" rel="stylesheet">
    <!-- Timeline CSS -->
    <link href="../../resources/dist/css/timeline.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="../../resources/dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- Morris Charts CSS -->
    <link href="../../resources/bower_components/morrisjs/morris.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="../../resources/bower_components/font-awesome/css/font-awesome.min.css"
          rel="stylesheet" type="text/css">
    <!-- 从被装饰页面获取title标签内容,并设置默认值-->
    <title><decorator:title default="默认title"/></title>
    <!-- 从被装饰页面获取head标签内容 -->
    <decorator:head/>
</head>
<body>
<%--<h4>SiteMesh装饰header</h4>--%>
<%--<br/>--%>
<%--<!-- 从被装饰页面获取body标签内容 -->--%>
<%--<decorator:body></decorator:body>--%>
<%--<br/>--%>
<%--<h4>SiteMesh装饰header</h4>--%>
<div id="wrapper">

    <!-- Navigation -->
    <jsp:include page="navigation.jsp"/>

    <%--body--%>

    <div id="page-wrapper">
        <decorator:body/>
        <%--<jsp:include page="dashboard.jsp"/>--%>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="../../resources/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="../../resources/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- Metis Menu Plugin JavaScript -->
<script src="../../resources/bower_components/metisMenu/dist/metisMenu.min.js"></script>
<!-- Morris Charts JavaScript -->
<script src="../../resources/bower_components/raphael/raphael-min.js"></script>
<script src="../../resources/bower_components/morrisjs/morris.min.js"></script>
<script src="../../resources/js/morris-data.js"></script>
<!-- Custom Theme JavaScript -->
<script src="../../resources/dist/js/sb-admin-2.js"></script>
</body>
</html>
