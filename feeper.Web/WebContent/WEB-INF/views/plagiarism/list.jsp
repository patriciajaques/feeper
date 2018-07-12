<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<t:master>
    <jsp:attribute name="title"><fmt:message key="title.plagiarism.detector"/></jsp:attribute>
    <jsp:attribute name="header">

        <script src="<c:url value='/resources/js/angular.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/views/plagiarism/list.js?v=1.00'/>" type="text/javascript"></script>

        <script type="text/javascript">
            var selecionarExercicio = '<fmt:message key="label.selecioneexercicio"/>';</script>

    </jsp:attribute>
    <jsp:body>
        <div ng-app="feeper" ng-controller="listPlagiarism" >
            <h2>
                <fmt:message key="label.plagiarism.detector"/>
            </h2>
            <div class="panel panel-default">
                <div class="panel-body">
                    <table style="width:100%;">
                        <tr>
                            <td colspan="2">
                                <label><fmt:message key="label.selecionarexercicio"/></label>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <select class="form-control" ng-options="e[0] as e[1] for e in exercicios" ng-model="exercicioId" placeholder='<fmt:message key="label.selecionarexercicio"/>'></select>
                            </td>
                            <td style="width: 250px;">
                                <input type="button" class="form-control btn btn-primary" ng-click="performCheck()" value='<fmt:message key="button.processar"/>' />
                            </td>
                        </tr>
                    </table>
                    <table ng-show="dados != null" class="table table-striped" style="width:100%; margin-top: 50px; border-collapse: collapse;">
                        <thead>
                            <tr>
                                <th>
                                    <fmt:message key="label.notas.acoes"/>
                                </th>
                                <th>
                                    <fmt:message key="label.notas.aluno"/>
                                </th>
                                <th>
                                    <fmt:message key="label.plagiarism.probabilidade"/>
                                </th>
                            </tr>
                        </thead>
                        <tbody ng-repeat="dado in dados">
                            <tr>
                                <td>
                                    <div class="btn-group btn-group-xs">
                                        <button type="button" class="btn btn-default" ng-click="toggleDado(dado)" ng-class="{'active':dado.expanded == true}">Expandir</button>
                                    </div>
                                </td>
                                <td>
                                    {{dado.aluno.nome}}
                                </td>
                                <td>
                                    {{dado.maxProbability| number : 2}}%
                                </td>
                            </tr>
                            <tr style="border-top: solid rgb(153,153,153) 2px;border-bottom: solid rgb(153,153,153) 2px;" ng-show="dado.expanded == true">
                                <td colspan="3">
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <h4>Classes</h4>
                                            <table class="table table-striped" style="width:100%;">
                                                <thead>
                                                    <tr>
                                                        <th>
                                                            <fmt:message key="label.notas.acoes"/>
                                                        </th>
                                                        <th>
                                                            <fmt:message key="label.notas.classe"/>
                                                        </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <tr ng-repeat="classe in dado.classes">
                                                        <td>
                                                            <div class="btn-group btn-group-xs">
                                                                <button type="button" class="btn btn-default" ng-click="mostraClasse(dado.aluno, classe)"><fmt:message key="button.exibir"/></button>
                                                            </div> 
                                                        </td>
                                                        <td>
                                                            {{classe.nomeClasse}}
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                            <h4>Colegas</h4>
                                            <table class="table table-striped" style="width:100%;">
                                                <thead>
                                                    <tr>
                                                        <th>
                                                            <fmt:message key="label.notas.acoes"/>
                                                        </th>
                                                        <th>
                                                            <fmt:message key="label.plagiarism.colega"/>
                                                        </th>
                                                        <th>
                                                            <fmt:message key="label.plagiarism.probabilidade"/>
                                                        </th>
                                                    </tr>
                                                </thead>
                                                <tbody ng-repeat="colega in dado.colegasItems">
                                                    <tr>
                                                        <td>
                                                            <div class="btn-group btn-group-xs">
                                                                <button type="button" class="btn btn-default" ng-click="toggleColega(colega)" ng-class="{'active':colega.expanded == true}">Ver Classes</button>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            {{colega.aluno.nome}}
                                                        </td>
                                                        <td>
                                                            {{colega.probability| number : 2}}%
                                                        </td>
                                                    </tr>
                                                    <tr style="border-top: solid rgb(153,153,153) 2px;border-bottom: solid rgb(153,153,153) 2px;" ng-show="colega.expanded == true">
                                                        <td colspan="3">
                                                            <div class="panel panel-default">
                                                                <div class="panel-body">
                                                                    <h4>Classes</h4>
                                                                    <table class="table table-striped" style="width:100%;">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>
                                                                                    <fmt:message key="label.notas.acoes"/>
                                                                                </th>
                                                                                <th>
                                                                                    <fmt:message key="label.notas.classe"/>
                                                                                </th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <tr ng-repeat="classe in colega.classes">
                                                                                <td>
                                                                                    <div class="btn-group btn-group-xs">
                                                                                        <button type="button" ng-click="mostraClasse(colega.aluno, classe)" class="btn btn-default"><fmt:message key="button.exibir"/></button>
                                                                                        <button type="button" ng-click="comparaClasses(dado, classe)" class="btn btn-default"><fmt:message key="button.comparar"/></button>
                                                                                    </div> 
                                                                                </td>
                                                                                <td>
                                                                                    {{classe.nomeClasse}}
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                        <tbody ng-show="dados.length == 0">
                            <tr>
                                <td colspan="4">
                                    <fmt:message key="label.nenhumregistroencontrado"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </jsp:body>
</t:master>