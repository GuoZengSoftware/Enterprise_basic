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
    <title>所有案例</title>
    <%@include file="../master/style.inc.jsp"%>
</head>
<body>
<%@include file="../master/header.jsp"%>
<div class="container">
    <div class="row" style="margin-top: 20px;">
        <%@include file="../master/left_sidebar.jsp"%>
        <div class="col-lg-9 col-md-9 col-sm-8 col-xs-12">
            <h3>所有案例</h3>

            <c:forEach items="${requestScope.casePager.results }" var="aCase" varStatus="status">
                <a href="<%=path%>/case/detail?id=${aCase.id}">
                    <div class="row" style="margin-bottom: 15px;">
                        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-3">
                            <img src="<%=path%>/${aCase.image }" class="img-responsive" style="max-width:100px;max-height: 100px;" />
                        </div>
                        <div class="col-lg-7 col-md-7 col-sm-7 col-xs-6">
                            <p>${aCase.name }</p>
                            <small>${aCase.customer }</small>
                        </div>
                        <div class="col-lg-2 col-md-2 col-sm-2 col-xs-3">
                                ${aCase.createdTime }
                        </div>
                    </div>
                </a>
                <hr />
            </c:forEach>

            <div class="row">
                <nav aria-label="..." class="text-center">
                    <ul class="pager">
                        <li><a href="<%=path%>/case/list?page=${requestScope.casePager.page - 1}">上一页</a></li>
                        <li><a href="<%=path%>/case/list?page=${requestScope.casePager.page + 1}">下一页</a></li>
                    </ul>
                </nav>
            </div>

        </div>
    </div>

    <%@include file="../master/footer.jsp"%>
</div>

</body>
<%@include file="../master/script.inc.jsp"%>
</html>
