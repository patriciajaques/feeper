<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.turmas"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-cadastro-turma").addClass("active");
                
                $("#btn-novo-aluno").click(function(){
                    $.fancybox({
			'autoSize': false,
                        'openEffect': 'fade',
			'closeEffect': 'fade',
			'width': 500,
			'height': 350,
			'href': '<c:url value='/'/>pessoa/addaluno',
			'type': 'iframe',
                        'modal': true
                    });
                });
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        <h2><c:choose>
            <c:when test="${IsAdd != null && IsAdd}">
                <fmt:message key="label.turma.nova"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="label.turma.editar"/>
            </c:otherwise>
        </c:choose></h2>
        
        <div class="panel panel-default">
            <div class="panel-body">

                <form role="form">
                    <div class="form-group">
                        <label for="nome"><fmt:message key="label.turma.nome"/>:</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="Informe o nome da turma">
                    </div>
                    <div class="form-group">
                        <label for="professor"><fmt:message key="label.turma.professor"/>:</label>
                        <input type="text" class="form-control" id="professor" name="professor" placeholder="Informe o nome do professor">
                    </div>
                    <div class="form-group">
                        <label for="encerramento"><fmt:message key="label.turma.dataencerramento"/>:</label>
                        <input type="text" class="form-control" id="encerramento" name="encerramento" placeholder="Informe a data de encerramento">
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" value="1" name="ativo" checked> <fmt:message key="label.turma.ativo"/>
                        </label>
                    </div>
                    <button type="submit" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" class="btn btn-default"><fmt:message key="button.voltarlistagem"/></button>
                </form>

            </div>
        </div>
        
        <c:if test="${IsAdd != null && !IsAdd}">
            <h2><fmt:message key="label.turma.listaalunos"/></h2>
            <button type="button" id="btn-novo-aluno" class="btn btn-primary"><fmt:message key="button.novoaluno"/></button>
            <button type="button" id="btn-novo-aluno" class="btn btn-default"><fmt:message key="button.enviarconvitealunos"/></button>
            <br /><br />
            
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
                                <th><fmt:message key="label.turma.nomealuno"/></th>
                                <th><fmt:message key="label.turma.email"/></th>
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
                                <td>Fábio Alves</td>
                                <td>arnistrong@gmail.com</td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="btn-group btn-group-xs">
                                        <button type="button" class="btn btn-default"><fmt:message key="button.editar"/></button>
                                        <button type="button" class="btn btn-default"><fmt:message key="button.excluir"/></button>
                                    </div>
                                </td>
                                <td>2</td>
                                <td>Fulano da Silva</td>
                                <td>fulano.silva@gmail.com</td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="btn-group btn-group-xs">
                                        <button type="button" class="btn btn-default"><fmt:message key="button.editar"/></button>
                                        <button type="button" class="btn btn-default"><fmt:message key="button.excluir"/></button>
                                    </div>
                                </td>
                                <td>3</td>
                                <td>Siclano Alves</td>
                                <td>siclano.alves@gmail.com</td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="btn-group btn-group-xs">
                                        <button type="button" class="btn btn-default"><fmt:message key="button.editar"/></button>
                                        <button type="button" class="btn btn-default"><fmt:message key="button.excluir"/></button>
                                    </div>
                                </td>
                                <td>4</td>
                                <td>Beltrano Lima</td>
                                <td>beltrano.lima@gmail.com</td>
                            </tr>
                        </tbody>
                    </table>

                </div>
            </div>
        </c:if>

    </jsp:body>
</t:master>