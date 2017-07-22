<%--
  Created by IntelliJ IDEA.
  User: Wang Genshen
  Date: 2017-07-17
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>新闻详情</title>
    <%@include file="../master/style.inc.jsp"%>
</head>
<body>
<%@include file="../master/header.jsp"%>
<div class="container">
    <div class="row" style="margin-top: 20px;">
        <%@include file="../master/left_sidebar.jsp"%>
        <div class="col-lg-9 col-md-9 col-sm-8 col-xs-12">
            <h3 class="text-center">新闻标题</h3>
            <p class="text-center">发布时间</p>
            <p>新闻摘要</p>
            <p>
                <img src="<%=path %>/images/logo.jpg" class="img-responsive" />
            </p>
            <p>
                新闻内容
            </p>
        </div>
    </div>
</div>
<%@include file="../master/footer.jsp"%>
</body>
<%@include file="../master/script.inc.jsp"%>
</html>
