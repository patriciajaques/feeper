<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.pessoa"/></jsp:attribute>
    <jsp:attribute name="header">

        <link href="<c:url value='/resources/jquery/themes/base/jquery.ui.all.css'/>" rel="stylesheet" type="text/css" />

        <script type="text/javascript">
            $(function () {
                $("#menu-teste").addClass("active");

                $(".btn-voltar").click(function () {
                    document.location.href = "<c:url value='/'/>pessoa";
                });
            });
        </script>

        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="label.filtropesquisa"/></h3>
            </div>
            <div class="panel-body">

                <form class="form-inline" role="form" method="POST" id="frm">

                </form>

            </div>
        </div>
    </jsp:attribute>
    <jsp:body>       
    </jsp:body>
</t:master>