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
            var escolherArquivoText = '<fmt:message key="button.escolherarquivo"/>';
        </script>

    </jsp:attribute>
    <jsp:body>
        <div ng-app="feeper" ng-controller="editExercicios" >
            <h2 ng-show="exercicio.id == 0">
                <fmt:message key="label.exercicios.novo"/>
            </h2>
            <h2 ng-show="exercicio.id > 0">
                <fmt:message key="label.exercicios.editar"/>
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
                            <input type="radio" id="rdHTML" name="rdDetalhamento" class="rd-detalhamento" ng-value="falseValue" ng-model="exercicio.usaDescricaoPDF">
                            <fmt:message key="label.exercicios.utilizareditorhtml"/>
                        </label>
                        <label class="radio-inline">
                            <input type="radio" id="rdPDF" name="rdDetalhamento" class="rd-detalhamento" ng-value="trueValue" ng-model="exercicio.usaDescricaoPDF">
                            <fmt:message key="label.exercicios.utilizarpdf"/>
                        </label>
                    </div>

                    <div id="editorhtml" ng-show="exercicio.usaDescricaoPDF == false" class="div-detalhamento">
                        <textarea class="editor" name="htmlcontent" >
                        </textarea>
                    </div>
                    <div id="uploadpdf" ng-show="exercicio.usaDescricaoPDF == true" class="div-detalhamento">
                        <input type="file" name="upload_descricao" id="upload_descricao" />
                        <!--Este ng-repeat é uma gambiarra para não dar o erro 404, mas o que é um programador sem gambiarra-->
                        <div ng-repeat="url in arquivoPDFURLs">                            
                            <iframe border="0" width="100%" height="600px" ng-src="{{url.domain}}" >
                            </iframe>
                        </div>
                    </div>

                    <div id="casosTeste" class="form-group">
                        <div class="form-group" style="margin-top: 20px;">                     
                            <label><fmt:message key="label.exercicios.casosteste"/>:</label>
                        </div>

                        <button type="button" ng-click="visualizaTelaInterface()" class="btn btn-primary"><fmt:message key="label.exercicios.uploadInterface"/></button>

                        <table class="table table-striped table-hover" style="margin-bottom: 0px;">
                            <thead>
                                <tr>
                                    <th><fmt:message key="label.exercicios.acoes"/></th>
                                    <th><fmt:message key="label.exercicios.mensagempersonalizada"/></th>
                                    <th><fmt:message key="label.exercicios.ativo"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr ng-repeat="casoTeste in exercicio.casosTeste">
                                    <td>
                                        <button type="button" ng-click="deletaCasoTeste(casoTeste)" class="btn btn-default btn-xs"><fmt:message key="button.excluir"/></button>
                                        <button type="button" ng-click="editaPassosCasoTeste(casoTeste)" class="btn btn-default btn-xs"><fmt:message key="label.exercicios.editarpassoscasosteste"/></button>
                                    </td>
                                    <td>
                                        <input type="text" class="form-control" ng-model="casoTeste.mensagemPersonalizada">
                                    </td>
                                    <td>
                                        <input type="checkbox" ng-model="casoTeste.ativo">
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="3">
                                        <button type="button" ng-click="adicionaCasoTeste()" class="btn btn-primary"><fmt:message key="label.exercicios.adicionarcasoteste"/></button>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>

                    <button type="button" ng-click="save()" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" ng-click="voltar()" class="btn btn-default btn-voltar"><fmt:message key="button.voltarlistagem"/></button>

                    <!--Modal exibida para carregar Interface-->
                    <div style="display:none;" id="divUploadInterface">
                        <div id="uploadInterface">
                            <input type="file" name="upload_Interface_Solucao" id="upload_Interface_Solucao" />
                        </div>
                        <div class="form-group">
                            <label><fmt:message key="label.exercicios.nomeclasse"/></label>
                            <input type="text" class="form-control" id="nome" name="nome" ng-model="exercicio.interfaceSolucao.nomeClasse">
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" ng-model="gerarTestesGetSet">
                                <fmt:message key="label.exercicios.gerarcasostestegetset"/>
                            </label>
                        </div>
                        <button type="button" ng-click="concluiUploadInterface()" class="btn btn-primary"><fmt:message key="button.concluir"/></button>

                    </div>

                    <!--Modal exibida para editar Passos-->
                    <div style="display:none;" id="divEditPassos">
                        <label><fmt:message key="label.exercicios.passoscasosteste"/>:</label>
                        <table ng-show="exercicio.interfaceSolucao == null">
                            <tbody>
                                <tr ng-repeat="passo in editingCasoTeste.passos">
                                    <td>
                                        <span style="cursor: pointer;" class="glyphicon glyphicon-minus" ng-click="deletaPasso(passo)"></span>
                                    </td>
                                    <td>
                                        <select ng-model="passo.operationType" ng-options="o.value as o.label for o in OperationTypes">                                                
                                        </select>
                                    </td>
                                    <td>
                                        <input type="text" ng-disabled="passo.operationType == 2" ng-model="passo.expectedOutputType" />
                                    </td>
                                    <td>
                                        <input type="text" ng-disabled="passo.operationType == 2" ng-show="passo.operationType != 3" ng-model="passo.expectedOutputName"/>
                                        <input type="text" ng-disabled="passo.operationType == 2" ng-show="passo.operationType == 3" ng-model="passo.expectedOutputValue"/>
                                    </td>
                                    <td> {{ passo.operationType == 3?"==":"=" }} </td>
                                    <td>
                                        <input type="text"  ng-model="passo.objectName"/>
                                    </td>
                                    <td>.</td>
                                    <td>
                                        <input type="text"  ng-model="passo.methodName"/>
                                    </td>
                                    <td>
                                        <table >
                                            <tr>
                                                <td>(</td>
                                                <td ng-repeat="parametro in passo.inputParameters">
                                                    <table>
                                                        <tr>
                                                            <td><span style="cursor: pointer;" class="glyphicon glyphicon-minus" ng-click="deletaParametro(passo, parametro)"></span></td>
                                                            <td><input type="text" ng-model="parametro.objectType"/></td>
                                                            <td><input type="text" ng-model="parametro.objectValue"/></td>
                                                            <td>,</td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td>
                                                    <span style="cursor: pointer;" class="glyphicon glyphicon-plus" ng-click="adicionaParametro(passo)"></span> 
                                                </td>
                                                <td>);</td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="9">
                                        <button type="button" ng-click="adicionaPasso()" class="btn btn-primary"><fmt:message key="label.exercicios.adicionarpassocasoteste"/></button>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                        <table ng-show="exercicio.interfaceSolucao != null">
                            <tbody>
                                <tr ng-repeat="passo in editingCasoTeste.passos">
                                    <td>
                                        <span style="cursor: pointer;" class="glyphicon glyphicon-minus" ng-click="deletaPasso(passo)"></span>
                                    </td>
                                    <td>
                                        <select ng-model="passo.operationType" ng-options="o.value as o.label for o in OperationTypes">                                                
                                        </select>
                                    </td>
                                    <td>
                                        <input type="text" ng-disabled="passo.operationType == 2" ng-model="passo.expectedOutputType" />
                                    </td>
                                    <td>
                                        <input type="text" ng-disabled="passo.operationType == 2" ng-show="passo.operationType != 3" ng-model="passo.expectedOutputName"/>
                                        <input type="text" ng-disabled="passo.operationType == 2" ng-show="passo.operationType == 3" ng-model="passo.expectedOutputValue"/>
                                    </td>
                                    <td> {{ passo.operationType == 3?"==":"=" }} </td>
                                    <td>
                                        <input type="text"  ng-model="passo.objectName"/>
                                    </td>
                                    <td>.</td>
                                    <td>
                                        <input type="text"  ng-model="passo.methodName"/>
                                    </td>
                                    <td>
                                        <table >
                                            <tr>
                                                <td>(</td>
                                                <td ng-repeat="parametro in passo.inputParameters">
                                                    <table>
                                                        <tr>
                                                            <td><span style="cursor: pointer;" class="glyphicon glyphicon-minus" ng-click="deletaParametro(passo, parametro)"></span></td>
                                                            <td><input type="text" ng-model="parametro.objectType"/></td>
                                                            <td><input type="text" ng-model="parametro.objectValue"/></td>
                                                            <td>,</td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td>
                                                    <span style="cursor: pointer;" class="glyphicon glyphicon-plus" ng-click="adicionaParametro(passo)"></span> 
                                                </td>
                                                <td>);</td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="9">
                                        <button type="button" ng-click="adicionaPasso()" class="btn btn-primary"><fmt:message key="label.exercicios.adicionarpassocasoteste"/></button>
                                    </td>
                                </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:master>