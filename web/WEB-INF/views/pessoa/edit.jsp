<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master.modal>
    <jsp:attribute name="title"><fmt:message key="title.pessoa"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#btn-fechar").click(function(){
                   self.parent.FechaModal(); 
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
            <input type="hidden" id="idTurma" name="idTurma" value="${IdTurma}">
            <input type="hidden" id="tipoPessoa" name="tipoPessoa" value="${pessoa.getTipoPessoa()}">
            <div class="form-group">
                <label for="nome"><fmt:message key="label.pessoa.nome"/>:</label>
                <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.pessoa.informenome"/>" value="${pessoa.getNome()}">
            </div>
            <div class="form-group">
                <label for="email"><fmt:message key="label.pessoa.email"/></label>
                <input type="text" class="form-control" id="email" name="email" placeholder="<fmt:message key="label.pessoa.informeemail"/>" value="${pessoa.getEmail()}">
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="ativo" ${IsAdd != null && IsAdd ? "checked" : pessoa.isAtivo() ? "checked" : ""}> <fmt:message key="label.pessoa.ativo"/>
                </label>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
            <button type="button" class="btn btn-default" id="btn-fechar"><fmt:message key="button.fechar"/></button>
        </form>
        
    </jsp:body>
</t:master.modal>