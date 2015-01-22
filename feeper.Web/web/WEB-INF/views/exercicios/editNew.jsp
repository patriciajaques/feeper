<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script src="<c:url value='/resources/AngularJS/angular.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/resources/AngularJS/Exercicios/edit.js'/>" type="text/javascript"></script>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        <style>
            .ui-state-highlight { height: 91px; }
        </style>
        <script type="text/javascript">
            var baseUrl = "<c:url value='/'/>";
            var uploadDescricaoText = '<fmt:message key="button.escolherarquivo"/>';
            var uploadInterfaceText = '<fmt:message key="label.exercicios.uploadInterface"/>';</script>

    </jsp:attribute>
    <jsp:body>
        <div ng-app="feeper" ng-controller="editExercicios" >
            <h2>
                {{exercicio.Id > 0 ? '<fmt:message key="label.exercicios.novo"/>' : '<fmt:message key="label.exercicios.editar"/>'}}
            </h2>
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="form-group">
                        <label><fmt:message key="label.exercicios.titulo"/></label>
                        <input type="text" class="form-control" id="nome" name="nome" ng-model="exercicio.nome">
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="ativo" ng-model="exercicio.ativo">
                            <fmt:message key="label.exercicios.ativo"/>
                        </label>
                    </div>
                    <div class="form-group">
                        <label><fmt:message key="label.exercicios.detalhamento"/>:</label>
                        <br>
                        <label class="radio-inline">
                            <input type="radio" id="rdHTML" name="rdDetalhamento" class="rd-detalhamento" value="html" show-div="editorhtml" checked="checked">
                            <fmt:message key="label.exercicios.utilizareditorhtml"/>
                        </label>
                        <label class="radio-inline">
                            <input type="radio" id="rdPDF" name="rdDetalhamento" class="rd-detalhamento" value="pdf" show-div="uploadpdf">
                            <fmt:message key="label.exercicios.utilizarpdf"/>
                        </label>
                    </div>

                    <div id="editorhtml" class="div-detalhamento">
                        <textarea class="editor" name="htmlcontent" ng-model="exercicio.descricaoHtml">
                        </textarea>
                    </div>
                    <div id="uploadpdf" style="display:none;" class="div-detalhamento">
                        <input type="file" name="upload_descricao" id="upload_descricao" />
                    </div>

                    <div id="casosTeste" class="form-group">
                        <label><fmt:message key="label.exercicios.cadastrarcasosteste"/>:</label>
                        <div id="uploadInterface" class="div-detalhamento">
                            <input type="file" name="upload_Interface_Solucao" id="upload_Interface_Solucao" />
                        </div>

                        <div ng-repeat="casoTeste in exercicio.casosTeste">
                            <div>
                                <label><input type="checkbox" ng-model='casoTeste.ativo'>Ativo</label>
                                <input type="text" ng-model="casoTeste.mensagemPersonalizada"/>
                            </div>
                            <table id="edicaoLivre" ng-show="exercicio.interfaceSolucao == null">
                                <tbody>
                                    <tr ng-repeat="passo in casoTeste.passos">
                                        <td>
                                            <select ng-model="passo.operationType">                                                
                                                <option value="1">Atribui à</option>
                                                <option value="2">Executa</option>
                                                <option value="3">Verifica se</option>
                                            </select>
                                        </td>
                                        <td>
                                            <input type="text" ng-model="passo.expectedOutputType"/>
                                        </td>
                                        <td>
                                            <input type="text" ng-show="passo.operationType != '3'" ng-model="passo.expectedOutputName"/>
                                            <input type="text" ng-show="passo.operationType == '3'" ng-model="passo.expectedOutputValue"/>
                                        </td>
                                        <td> {{ passo.operationType == '3'?"==":"=" }} </td>
                                        <td>
                                            <input type="text" ng-model="passo.objectName"/>
                                        </td>
                                        <td>.</td>
                                        <td>
                                            <input type="text" ng-model="passo.methodName"/>
                                        </td>
                                        <td>
                                            (
                                            <ul>
                                                <li ng-repeat="parametro in passo.inputParameters">
                                                    <input type="text" ng-model="parametro.objectType"/>      
                                                    <input type="text" ng-model="parametro.objectValue"/>
                                                    <a style="cursor: pointer;" ng-click="deletaParametro(passo, parametro)">Excluir Parâmetro</a> 
                                                </li>
                                                <li>
                                                    <a style="cursor: pointer;" ng-click="adicionaParametro(passo)">Adicionar Parâmetro</a> 
                                                </li>
                                            </ul>
                                            );
                                        </td>
                                        <td>
                                            <a style="cursor: pointer;" ng-click="deletaPasso(casoTeste, passo)">Excluir Passo</a>
                                        </td>
                                    </tr>
                                </tbody>
                                <tfoot>
                                <td colspan="9">
                                    <a style="cursor: pointer;" ng-click="adicionaPasso(casoTeste)">Adicionar Passo</a> 
                                </td>
                                </tfoot>
                            </table>
                            <a style="cursor: pointer;" ng-click="deletaCasoTeste(casoTeste)">Excluir Caso</a>
                        </div>
                        <a style="cursor: pointer;" ng-click="adicionaCasoTeste()">Adicionar Caso</a>  
                    </div>

                    <button type="button" ng-click="save()" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" ng-click="voltar()" class="btn btn-default btn-voltar"><fmt:message key="button.voltarlistagem"/></button>

                </div>
            </div>
        </div>
    </jsp:body>
</t:master>