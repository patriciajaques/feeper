<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.turmas"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-lista-turma").addClass("active");
                
                $(".btn-novo").click(function(){
                    document.location.href = "<c:url value='/'/>turma/add";
                });
                
                $(".btn-editar").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    document.location.href = "<c:url value='/'/>turma/edit/" + id;
                });
                
                $(".btn-excluir").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    if (!confirm("<fmt:message key="label.confirmaexclusao"/>")) return;
                    document.location.href = "<c:url value='/'/>turma/delete/" + id;
                });
                
                $(".btn-pesquisar").click(function(){
                    setSearch();
                });
                
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.turmas"/></h2>
        
        <c:if test="${not empty MSG_SUCESSO}">
            <div class="alert alert-success">${MSG_SUCESSO}</div>
        </c:if>
        <c:if test="${not empty MSG_ERRO}">
            <div class="alert alert-danger">${MSG_ERRO}</div>
        </c:if>
        
        <button type="button" class="btn btn-primary btn-novo"><fmt:message key="button.novaturma"/></button>
        <br /><br />
        
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="label.filtropesquisa"/></h3>
            </div>
            <div class="panel-body">
                
                <form class="form-inline" role="form" method="POST" id="frm-paged">
                    <div class="form-group">
                        <label class="sr-only" for="nome"><fmt:message key="label.turma.nome"/>:</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.turma.nometurma"/>" value="${nome}">
                    </div>
                    <div class="form-group">
                        <label class="sr-only" for="professor"><fmt:message key="label.turma.professor"/>:</label>
                        <input type="text" class="form-control" id="professor" name="professor" placeholder="<fmt:message key="label.turma.nomeprofessor"/>" value="${professor}">
                    </div>
                    <button type="submit" class="btn btn-primary btn-pesquisar"><fmt:message key="button.pesquisar"/></button>
                    <%@include file="/WEB-INF/jspf/paginador_campos.jspf" %>
                </form>
                
            </div>
        </div>
        
        <%@include file="/WEB-INF/jspf/paginador.jspf" %>
        
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="label.registroscadastrados"/></h3>
            </div>
            <div class="panel-body">
                
                <table class="table table-striped table-hover" style="margin-bottom: 0px;">
                    <thead>
                        <tr>
                            <th><fmt:message key="label.turma.acoes"/></th>
                            <th>#</th>
                            <th><fmt:message key="label.turma.nometurma"/></th>
                            <th><fmt:message key="label.turma.professor"/></th>
                            <th><fmt:message key="label.turma.ativo"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${not empty listaTurma}">
                            <c:forEach var="item" varStatus="status" items="${listaTurma}">
                                <tr>
                                    <td>
                                        <div class="btn-group btn-group-xs">
                                            <button type="button" class="btn btn-default btn-editar" data-id="${item.getId()}"><fmt:message key="button.editar"/></button>
                                            <button type="button" class="btn btn-default btn-excluir" data-id="${item.getId()}" disabled="disabled"><fmt:message key="button.excluir"/></button>
                                        </div>
                                    </td>
                                    <td><c:out value="${item.getId()}"/></td>
                                    <td><c:out value="${item.getNome()}"/></td>
                                    <td><c:out value="${item.getProfessor().getNome()}"/></td>
                                    <td>
                                        <c:if test="${not item.isAtivo()}">
                                            <span class="glyphicon glyphicon-remove"></span>
                                        </c:if>
                                        <c:if test="${item.isAtivo()}">
                                            <span class="glyphicon glyphicon-ok"></span>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty listaTurma}">
                            <tr>
                                <td colspan="5"><fmt:message key="label.nenhumregistroencontrado"/></td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            
            </div>
        </div>
        
        <%@include file="/WEB-INF/jspf/paginador.jspf" %>
            
    </jsp:body>
</t:master>