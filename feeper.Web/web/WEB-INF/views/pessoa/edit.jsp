<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.pessoa"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <link href="<c:url value='/resources/jquery/themes/base/jquery.ui.all.css'/>" rel="stylesheet" type="text/css" />
        
        <script type="text/javascript">
            $(function(){
                $("#menu-lista-pessoas").addClass("active");
            
                $(".btn-voltar").click(function(){
                   document.location.href = "<c:url value='/'/>pessoa";
                });
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        <h2><c:choose>
            <c:when test="${IsAdd != null && IsAdd}">
                <fmt:message key="label.pessoa.nova"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="label.pessoa.editar"/>
            </c:otherwise>
        </c:choose></h2>
        
        <c:if test="${not empty MSG_SUCESSO}">
            <div class="alert alert-success">${MSG_SUCESSO}</div>
        </c:if>
        <c:if test="${not empty MSG_ERRO}">
            <div class="alert alert-danger">${MSG_ERRO}</div>
        </c:if>
        
        <div class="panel panel-default">
            <div class="panel-body">

                <form role="form" action="<c:url value='/'/>${IsAdd != null && IsAdd ? "pessoa/saveadd" : "pessoa/saveedit/"}" method="POST">
                    <input type="hidden" id="id" name="id" value="${pessoa.getId()}">
                    
                    <div class="row">
                        <div class="col-xs-4">
                            <div class="form-group">
                                <label for="nome"><fmt:message key="label.pessoa.nome"/>:</label>
                                <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.pessoa.informenome"/>" value="${pessoa.getNome()}">
                            </div>
                        </div>
                        <div class="col-xs-4">    
                            <div class="form-group">
                                <label for="email"><fmt:message key="label.pessoa.email"/></label>
                                <input type="text" class="form-control" id="email" name="email" placeholder="<fmt:message key="label.pessoa.informeemail"/>" value="${pessoa.getEmail()}">
                            </div>
                        </div>
                    </div>
                    <div class="radio-inline">
                        <label>
                            <input type="radio" name="idPerfil" id="idPerfilAluno" value="3" ${IsAdd != null && IsAdd ? "checked" : pessoa.getIdPerfil() == 3 ? "checked" : ""}>
                            <fmt:message key="label.pessoa.aluno"/>
                        </label>
                    </div>
                    <div class="radio-inline">
                        <label>
                            <input type="radio" name="idPerfil" id="idPerfilProfessor" value="2" ${IsAdd != null && IsAdd ? "checked" : pessoa.getIdPerfil() == 2 ? "checked" : ""}>
                            <fmt:message key="label.pessoa.professor"/>
                        </label>
                    </div>
                    <div class="radio-inline">
                        <label>
                            <input type="radio" name="idPerfil" id="idPerfilAdmin" value="1" ${IsAdd != null && IsAdd ? "checked" : pessoa.getIdPerfil() == 1 ? "checked" : ""}>
                            <fmt:message key="label.pessoa.administrador"/>
                        </label>
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="ativo" ${IsAdd != null && IsAdd ? "checked" : pessoa.isAtivo() ? "checked" : ""}> <fmt:message key="label.pessoa.ativo"/>
                        </label>
                    </div>
                    <br>
                    <button type="submit" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" class="btn btn-default btn-voltar"><fmt:message key="button.voltarlistagem"/></button>
                </form>
                
            </div>
        </div>
        
    </jsp:body>
</t:master>