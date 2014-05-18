<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.logs"/></jsp:attribute>
    <jsp:attribute name="header">
        <link href="<c:url value='/resources/jquery/themes/base/jquery.ui.all.css'/>" rel="stylesheet" type="text/css" />
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
                
                $('#dataInicio').datepicker({
                    dateFormat: 'dd/mm/yy',
                    changeMonth: true,
                    changeYear: true,
                    showAnim: 'fadeIn',
                    showOtherMonths: true,
                    selectOtherMonths: true,
                    onClose: function (selectedDate) {
                        $('#dataFim').datepicker('option', 'minDate', selectedDate);
                    }
                });

                $('#dataFim').datepicker({
                    dateFormat: 'dd/mm/yy',
                    changeMonth: true,
                    changeYear: true,
                    showAnim: 'fadeIn',
                    showOtherMonths: true,
                    selectOtherMonths: true,
                    onClose: function (selectedDate) {
                        $('#dataInicio').datepicker('option', 'maxDate', selectedDate);
                    }
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
                        <label class="sr-only" for="dataInicio"><fmt:message key="label.log.datainicio"/>:</label>
                        <input type="text" class="form-control" id="dataInicio" name="dataInicio" placeholder="<fmt:message key="label.log.datainicio"/>" value="${dataInicio}">
                    </div>
                    
                    <div class="form-group">
                        <label class="sr-only" for="dataFim"><fmt:message key="label.log.datafim"/>:</label>
                        <input type="text" class="form-control" id="dataFim" name="dataFim" placeholder="<fmt:message key="label.log.datafim"/>" value="${dataFim}">
                    </div>
                    
                    <div class="form-group">
                        <select class="form-control" id="idTipoLog" name="idTipoLog">
                            <c:if test="${not empty listaTipoLog}">
                                <c:forEach var="item" varStatus="status" items="${listaTipoLog}">
                                    <option value="${item.getId()}" ${idTipoLog == item.getId() ? "selected" : ""}>${item.getNome()}</option>
                                </c:forEach>
                            </c:if>
                        </select>
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
                                    <td><c:out value="${item[0]}"/></td>
                                    <td><c:out value="${item[1]}"/></td>
                                    <td><c:out value="${item[2]}"/></td>
                                    <td><fmt:formatDate value="${item[3]}" pattern="dd/MM/yyyy HH:mm" /></td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty listaLog}">
                            <tr>
                                <td colspan="4"><fmt:message key="label.nenhumregistroencontrado"/></td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            
            </div>
        </div>
        
        <%@include file="/WEB-INF/jspf/paginador.jspf" %>
            
    </jsp:body>
</t:master>