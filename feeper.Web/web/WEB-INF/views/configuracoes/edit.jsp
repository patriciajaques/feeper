<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script src="<c:url value='/resources/js/angular.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/js/views/configuracoes/edit.js'/>" type="text/javascript"></script>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.configuracoes"/></jsp:attribute>
    <jsp:attribute name="header">
        <style>
            .ui-state-highlight { height: 91px; }
        </style>
        <script type="text/javascript">
            var baseUrl = "<c:url value='/'/>";
        </script>

    </jsp:attribute>
    <jsp:body>
        <div ng-app="feeper" ng-controller="editConfiguracoes" >
            <h2>
                <fmt:message key="label.configuracoes.editar"/>
            </h2>
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="form-group">
                        <label><fmt:message key="label.configuracoes.enderecocorretorjava"/></label>
                        <input type="text" class="form-control" ng-model="configuracao.enderecoSistemaCorretorJava">
                    </div>

                    <button type="button" ng-click="save()" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" ng-click="voltar()" class="btn btn-default btn-voltar"><fmt:message key="button.voltarlistagem"/></button>
                </div>
            </div>
        </div>
    </jsp:body>
</t:master>