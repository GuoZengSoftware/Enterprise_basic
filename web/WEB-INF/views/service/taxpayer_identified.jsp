<%--
  Created by IntelliJ IDEA.
  User: Wang Genshen
  Date: 2017-07-21
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>一般纳税人认定</title>
    <%@include file="../master/style.inc.jsp"%>
</head>
<body>
<%@include file="../master/header.jsp"%>
<div class="container">
    <div class="row-offcanvas row-offcanvas-left" style="margin-top: 20px;">
        <div class="row">
            <%@include file="../master/left_sidebar_canvas.jsp"%>
            <div class="col-lg-9 col-md-9 col-sm-8 col-xs-12 ">
                <p class="visible-xs">
                    <button type="button" class="btn btn-default" data-toggle="offcanvas">所有服务</button>
                </p>
                <h3>一般纳税人认定</h3>
                <p>申请条件：</p>
                <ol>
                    <li>查账征收企业</li>
                    <li>工业企业注册资本50万以上，商贸企业注册资本80万以上</li>
                </ol>
                <p>所需材料：</p>
                <ol>
                    <li>税务登记证副本原件</li>
                    <li>公章</li>
                </ol>
            </div>
        </div>
        <%@include file="../master/footer.jsp"%>
    </div>
</div>
</body>
<%@include file="../master/script.inc.jsp"%>
</html>