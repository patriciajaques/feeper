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

        <form role="form" action="<c:url value='/'/>${IsAdd != null && IsAdd ? "pessoa/saveadd" : "pessoa/saveedit/"}" method="POST">
            <input type="hidden" id="id" name="id" value="${pessoa.getId()}">
            <div class="form-group">
                <label for="nome"><fmt:message key="label.pessoa.nome"/>:</label>
                <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.pessoa.informenome"/>" value="${pessoa.getNome()}">
            </div>
            <div class="form-group">
                <label for="email"><fmt:message key="label.pessoa.email"/></label>
                <input type="text" class="form-control" id="email" name="email" placeholder="<fmt:message key="label.pessoa.informeemail"/>" value="${pessoa.getEmail()}">
            </div>
            <div class="btn-group" data-toggle="buttons">
                <label class="btn btn-primary ${pessoa.getIdPerfil() == 3 ? "active" : ""}">
                    <input type="radio" name="idPerfil" id="idPerfilAluno" value="3"> <fmt:message key="label.pessoa.aluno"/>
                </label>
                <label class="btn btn-primary ${pessoa.getIdPerfil() == 2 ? "active" : ""}">
                    <input type="radio" name="idPerfil" id="idPerfilProfessor" value="2"> <fmt:message key="label.pessoa.professor"/>
                </label>
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="ativo" ${IsAdd != null && IsAdd ? "checked" : pessoa.isAtivo() ? "checked" : ""}> <fmt:message key="label.pessoa.ativo"/>
                </label>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
            <button type="button" class="btn btn-default btn-voltar"><fmt:message key="button.voltarlistagem"/></button>
        </form>
        
    </jsp:body>
</t:master>