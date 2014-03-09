<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.log"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-lista-logs").addClass("active");
                
                $(".btn-novo").click(function(){
                    document.location.href = "<c:url value='/'/>log/add";
                });
                
                $(".btn-editar").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    document.location.href = "<c:url value='/'/>log/edit/" + id;
                });
                
                $(".btn-excluir").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    if (!confirm("<fmt:message key="label.confirmaexclusao"/>")) return;
                    document.location.href = "<c:url value='/'/>log/delete/" + id;
                });
                
                $(".btn-pesquisar").click(function(){
                    setSearch();
                });
                
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.logs"/></h2>
        
        <c:if test="${not empty MSG_SUCESSO}">
            <div class="alert alert-success">${MSG_SUCESSO}</div>
        </c:if>
        <c:if test="${not empty MSG_ERRO}">
            <div class="alert alert-danger">${MSG_ERRO}</div>
        </c:if>
        
        <button type="button" class="btn btn-primary btn-novo"><fmt:message key="button.novalog"/></button>
        <br /><br />
        
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="label.filtropesquisa"/></h3>
            </div>
            <div class="panel-body">
                
                <form class="form-inline" role="form" method="POST" id="frm-paged">
                    <div class="form-group">
                        <label class="sr-only" for="descricao"><fmt:message key="label.log.descricao"/>:</label>
                        <input type="text" class="form-control" id="descricao" name="descricao" placeholder="<fmt:message key="label.log.descricao"/>" value="${descricao}">
                    </div>
                    
                    <div class="form-group">
                        <label class="sr-only" for="nome"><fmt:message key="label.log.nome"/>:</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.log.nomelog"/>" value="${nome}">
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
                            <th>#</th>
                            <th><fmt:message key="label.log.pessoa"/></th>
                            <th><fmt:message key="label.log.mensagem"/></th>
                            <th><fmt:message key="label.log.tipolog"/></th>
                            <th><fmt:message key="label.log.datacadastro"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${not empty listaLog}">
                            <c:forEach var="item" varStatus="status" items="${listaLog}">
                                <tr>
                                    <td>${item.getId()}</td>
                                    <td>${item.getIdPessoa()}</td>
                                    <td>${item.getMensagem()}</td>
                                    <td>${item.getIdTipoLog()}</td>
                                    <td>${item.getDataCadastro()}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty listaLog}">
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