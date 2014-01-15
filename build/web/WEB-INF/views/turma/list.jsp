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
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.turmas"/></h2>
        
        <button type="button" class="btn btn-primary"><fmt:message key="button.novaturma"/></button>
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
                        <tr>
                            <td>
                                <div class="btn-group btn-group-xs">
                                    <button type="button" class="btn btn-default"><fmt:message key="button.editar"/></button>
                                    <button type="button" class="btn btn-default"><fmt:message key="button.excluir"/></button>
                                </div>
                            </td>
                            <td>1</td>
                            <td>Programação 1</td>
                            <td>Patricia Jaques</td>
                            <td>35</td>
                        </tr>
                        <tr>
                            <td>
                                <div class="btn-group btn-group-xs">
                                    <button type="button" class="btn btn-default"><fmt:message key="button.editar"/></button>
                                    <button type="button" class="btn btn-default"><fmt:message key="button.excluir"/></button>
                                </div>
                            </td>
                            <td>2</td>
                            <td>Programação 2</td>
                            <td>Fulano da Silva</td>
                            <td>41</td>
                        </tr>
                        <tr>
                            <td>
                                <div class="btn-group btn-group-xs">
                                    <button type="button" class="btn btn-default"><fmt:message key="button.editar"/></button>
                                    <button type="button" class="btn btn-default"><fmt:message key="button.excluir"/></button>
                                </div>
                            </td>
                            <td>3</td>
                            <td>Laboratório 1</td>
                            <td>Siclano Alves</td>
                            <td>39</td>
                        </tr>
                        <tr>
                            <td>
                                <div class="btn-group btn-group-xs">
                                    <button type="button" class="btn btn-default"><fmt:message key="button.editar"/></button>
                                    <button type="button" class="btn btn-default"><fmt:message key="button.excluir"/></button>
                                </div>
                            </td>
                            <td>4</td>
                            <td>Laboratório 2</td>
                            <td>Beltrano Lima</td>
                            <td>45</td>
                        </tr>
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