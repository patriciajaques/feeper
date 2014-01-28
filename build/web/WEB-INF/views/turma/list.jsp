<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
                
                <form class="form-inline" role="form">
                    <div class="form-group">
                        <label class="sr-only" for="nome"><fmt:message key="label.turma.nome"/>:</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.turma.nometurma"/>">
                    </div>
                    <div class="form-group">
                        <label class="sr-only" for="professor"><fmt:message key="label.turma.professor"/>:</label>
                        <input type="text" class="form-control" id="professor" name="professor" placeholder="<fmt:message key="label.turma.nomeprofessor"/>">
                    </div>
                    <button type="submit" class="btn btn-primary"><fmt:message key="button.pesquisar"/></button>
                </form>
                
            </div>
        </div>
        
        <ul class="pagination pagination-sm">
          <li><a href="#">«</a></li>
          <li class="active"><a href="#">1</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li><a href="#">4</a></li>
          <li><a href="#">5</a></li>
          <li><a href="#">»</a></li>
        </ul>
        
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
                            <th><fmt:message key="label.turma.qtdealunos"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${not empty listaTurma}">
                            <c:forEach var="item" varStatus="status" items="${listaTurma}">
                                <tr>
                                    <td>
                                        <div class="btn-group btn-group-xs">
                                            <button type="button" class="btn btn-default btn-editar" data-id="${item.getId()}"><fmt:message key="button.editar"/></button>
                                            <button type="button" class="btn btn-default btn-excluir" data-id="${item.getId()}"><fmt:message key="button.excluir"/></button>
                                        </div>
                                    </td>
                                    <td>${item.getId()}</td>
                                    <td>${item.getNome()}</td>
                                    <td>{item.getProfessor()}</td>
                                    <td>AA</td>
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
        
        <ul class="pagination pagination-sm">
          <li><a href="#">«</a></li>
          <li class="active"><a href="#">1</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li><a href="#">4</a></li>
          <li><a href="#">5</a></li>
          <li><a href="#">»</a></li>
        </ul>
            
    </jsp:body>
</t:master>