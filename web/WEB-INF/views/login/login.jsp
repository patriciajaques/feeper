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
            <p><fmt:message key="label.boasvindas.descricao"/></p>
            <p><a class="btn btn-primary btn-lg" role="button"><fmt:message key="button.desejocadastrar"/></a></p>
            
            <br>
        
            <div class="row">
                <div class="col-md-4">

                    <h3><fmt:message key="label.facalogin"/></h3>

                    <div class="panel panel-default">
                        <div class="panel-body">

                            <form role="form" action="<c:url value='/'/>login/validate" method="POST">
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
                
            </div>
            
        </div>
        
    </jsp:body>
</t:master.clean>