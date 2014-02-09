<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master.clean>
    <jsp:attribute name="title"><fmt:message key="title.login"/></jsp:attribute>
    <jsp:attribute name="header"></jsp:attribute>
    <jsp:body>
        
        <div class="jumbotron">
            <h1><fmt:message key="label.boasvindas"/></h1>
            <p style="text-align: justify"><fmt:message key="label.boasvindas.descricao"/></p>
            <!--p><a class="btn btn-primary btn-lg" role="button"><fmt:message key="button.desejocadastrar"/></a></p-->
            
            <br>
        
            <div class="row">
                <div class="col-md-4">

                    <h3><fmt:message key="label.facalogin"/></h3>

                    <div class="panel panel-default">
                        <div class="panel-body">
                            <form role="form" action="<c:url value='/'/>login/validate" method="POST">
                                <c:if test="${not empty MSG_SUCESSO}">
                                    <div class="alert alert-success">${MSG_SUCESSO}</div>
                                </c:if>
                                <c:if test="${not empty MSG_ERRO}">
                                    <div class="alert alert-danger">${MSG_ERRO}</div>
                                </c:if>
                                <div class="form-group">
                                    <input type="email" class="form-control" id="email" name="email" placeholder="<fmt:message key="label.email"/>">
                                </div>
                                <div class="form-group">
                                    <input type="password" class="form-control" id="senha" name="senha" placeholder="<fmt:message key="label.senha"/>">
                                </div>
                                <button type="submit" class="btn btn-primary"><fmt:message key="button.entrar"/></button>
                            </form>
                        </div>
                    </div>

                </div>
                <div class="col-md-8">
                    
                    <div class="panel panel-default pull-right" style="width:448px; height:320px; padding: 10px;">
                        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="width:428px;">
                            <div class="carousel-inner">
                                <div class="item active"><img src="<c:url value='/resources/img/print1.png'/>" width="428" height="300" alt=""></div>
                                <div class="item"><img src="<c:url value='/resources/img/print2.png'/>" width="428" height="300" alt=""></div>
                                <div class="item"><img src="<c:url value='/resources/img/print3.png'/>" width="428" height="300" alt=""></div>
                            </div>
                        </div>
                    </div>
                    
                </div>
                
            </div>
            
        </div>
        
    </jsp:body>
</t:master.clean>