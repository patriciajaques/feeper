<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script src="<c:url value='/resources/js/angular.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/js/views/mensagenspredefinidas/edit.js?v=1.01'/>" type="text/javascript"></script>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.mensagenspredefinidas"/></jsp:attribute>
    <jsp:attribute name="header">
        <style>
            .ui-state-highlight { height: 91px; }
        </style>
        <script type="text/javascript">
            var baseUrl = "<c:url value='/'/>";
        </script>

    </jsp:attribute>
    <jsp:body>
        <div ng-app="feeper" ng-controller="editMensagensPredefinidas" >
            <h2>
                <fmt:message key="label.mensagens.editar"/>
            </h2>
            <div class="panel panel-default">
                <div class="panel-body">

                    <table class="table table-striped table-hover" style="margin-bottom: 0px;">
                        <thead>
                            <tr>
                                <th><fmt:message key="label.exercicios.acoes"/></th>
                                <th><fmt:message key="label.mensagens.mensagemPredefinida"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr ng-repeat="mensagem in mensagens">
                                <td>
                                    <button type="button" ng-click="deletaMensagem(mensagem)" class="btn btn-default btn-xs"><fmt:message key="button.excluir"/></button>
                                </td>
                                <td>
                                    <input type="text" class="form-control" ng-model="mensagem.mensagemPredefinida">
                                </td>
                            </tr>
                        </tbody>
                        <tfoot>
                            <tr>
                                <td colspan="2">
                                    <button type="button" ng-click="adicionaMensagem()" class="btn btn-primary"><fmt:message key="button.adicionar"/></button>
                                </td>
                            </tr>
                        </tfoot>
                    </table>

                    <button type="button" ng-click="save()" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" ng-click="voltar()" class="btn btn-default btn-voltar"><fmt:message key="button.voltarlistagem"/></button>
                </div>
            </div>
        </div>
    </jsp:body>
</t:master>