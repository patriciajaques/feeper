<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.pessoa"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-lista-pessoas").addClass("active");
                
                $(".btn-novo").click(function(){
                    document.location.href = "<c:url value='/'/>pessoa/add";
                });
                
                $(".btn-editar").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    document.location.href = "<c:url value='/'/>pessoa/edit/" + id;
                });
                
                $(".btn-excluir").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    if (!confirm("<fmt:message key="label.confirmaexclusao"/>")) return;
                    document.location.href = "<c:url value='/'/>pessoa/delete/" + id;
                });
                
                $(".btn-pesquisar").click(function(){
                    setSearch();
                });
                
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.pessoas"/></h2>
        
        <c:if test="${not empty MSG_SUCESSO}">
            <div class="alert alert-success">${MSG_SUCESSO}</div>
        </c:if>
        <c:if test="${not empty MSG_ERRO}">
            <div class="alert alert-danger">${MSG_ERRO}</div>
        </c:if>
        
        <button type="button" class="btn btn-primary btn-novo"><fmt:message key="button.novapessoa"/></button>
        <br /><br />
        
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="label.filtropesquisa"/></h3>
            </div>
            <div class="panel-body">
                
                <form class="form-inline" role="form" method="POST" id="frm-paged">
                    <div class="form-group">
                        <label class="sr-only" for="nome"><fmt:message key="label.pessoa.nome"/>:</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.pessoa.nomepessoa"/>" value="${nome}">
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
                            <th><fmt:message key="label.pessoa.acoes"/></th>
                            <th>#</th>
                            <th><fmt:message key="label.pessoa.nome"/></th>
                            <th><fmt:message key="label.pessoa.email"/></th>
                            <th><fmt:message key="label.pessoa.ativo"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${not empty listaPessoa}">
                            <c:forEach var="item" varStatus="status" items="${listaPessoa}">
                                <tr>
                                    <td>
                                        <div class="btn-group btn-group-xs">
                                            <button type="button" class="btn btn-default btn-editar" data-id="${item.getId()}"><fmt:message key="button.editar"/></button>
                                            <button type="button" class="btn btn-default btn-excluir" data-id="${item.getId()}" disabled="disabled"><fmt:message key="button.excluir"/></button>
                                        </div>
                                    </td>
                                    <td>${item.getId()}</td>
                                    <td>${item.getNome()}</td>
                                    <td>${item.getEmail()}</td>
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
                        <c:if test="${empty listaPessoa}">
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