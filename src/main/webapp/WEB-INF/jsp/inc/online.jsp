<!--
Created by IntelliJ IDEA.
User: jerrychien
Date: 16/6/24
Time: 10:44
To change this template use File | Settings | File Templates.
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer onLineCount = (Integer) application.getAttribute("ONLINE");
%>
<h2>当前在线人数:<font color="red">
    <%=onLineCount%>
</font>人
</h2>
