<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<t:master>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        <script src="<c:url value='/resources/ace/ace.js'/>" type="text/javascript"></script>
        <link href="<c:url value='/resources/jquery/themes/base/jquery.ui.all.css'/>" rel="stylesheet" type="text/css" />
        <script src="<c:url value='/resources/js/angular.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/views/exercicios/edit.js'/>" type="text/javascript"></script>

        <style type="text/css" media="screen">
            .line-selected {
                background-color: #3A87AD;
                color:#D9EDF7;
            }
            .line-error {
                background-color: #d95959;
                color: #f7aaaa;
            }
            .line-warning {
                background-color: #e6cf4d;
                color: #f2ebad;             }
            .line-question {
                background-color: #5cb85c;
                color: #ffffff;
                cursor: pointer;
            }

            .ace_gutter-cell.ace_breakpoint{ 
                border-radius: 20px 0px 0px 20px; 
                box-shadow: 0px 0px 1px 1px red inset;              } 

            .ui-state-highlight { height: 91px; }
            #divEditPassos select{min-width: 120px;}
            #divEditPassos input{min-width: 100px;}
        </style>
        <script type="text/javascript">
                    var baseUrl = "<c:url value='/'/>";
                    var escolherArquivoText = '<fmt:message key="button.escolherarquivo"/>';
                    var adicionarclasseexistenteText = '<fmt:message key="button.adicionarclasseexistente"/>';
                    var erroCarregarAssinaturas = '<fmt:message key="label.exercicios.errocarregarassinaturas"/>';</script>

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

                    <div id="classesAuxiliares" class="form-group">

                        <div class="form-group" style="margin-top: 20px;">                     
                            <label><fmt:message key="label.exercicios.classesauxiliares"/>:</label>
                        </div>

                        <button type="button" ng-click="visualizaTelaInterface()" class="btn btn-primary"><fmt:message key="label.exercicios.uploadInterface"/></button>
                        <br/>
                        <br/>
                        <div class="list-group">
                            <a ng-repeat="classe in exercicio.classesAuxiliares| filter: {
                                        ehInterface: false
                                        }" href="#" ng-click="showClasseCode(classe)" class="list-group-item"><span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;{{classe.nomeClasse}}</a>
                            <a href="#" ng-click="visualizaTelaClasse()" class="list-group-item"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<fmt:message key="label.exercicios.adicionarclasseauxiliar"/></a>

                        </div>
                    </div>
                    <div id="containerEditor" style="display: none;">
                        <div id="panelEditor"></div>
                        <button id="btnSaveClasseCode" type="button" ng-click="saveClasseCode()" class="btn btn-primary btn-sm" >
                            <span class="glyphicon glyphicon-save"></span> <fmt:message key="button.salvar"/>
                        </button>
                        <button id="btnHabilitaEdicao" type="button" ng-click="habilitaEdicao()" class="btn btn-default btn-sm" >
                            <span class="glyphicon glyphicon-lock"></span> <fmt:message key="button.habilitaredicao"/>
                        </button>
                        <button type="button" ng-click="deletaClasse()" class="btn btn-default btn-sm" >
                            <span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.excluir"/>
                        </button>
                    </div>

                    <div id="casosTeste" class="form-group">
                        <div class="form-group" style="margin-top: 60px;">                     
                            <label><fmt:message key="label.exercicios.casosteste"/>:</label>
                        </div>
                        <button type="button" ng-click="adicionaCasoTeste()" class="btn btn-primary"><fmt:message key="label.exercicios.adicionarcasoteste"/></button>

                        <table class="table table-striped table-hover" style="margin-top: 10px; margin-bottom: 0px;">
                            <thead>
                                <tr>
                                    <th style="width:10px;">#</th>
                                    <th><fmt:message key="label.exercicios.acoes"/></th>   
                                    <th><fmt:message key="label.exercicios.mensagempersonalizada"/></th>
                                    <th><fmt:message key="label.exercicios.ativo"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr ng-repeat="casoTeste in exercicio.casosTeste track by $index">
                                    <td>{{$index + 1}}</td>
                                    <td style="width: 145px">
                                        <button type="button" ng-click="deletaCasoTeste(casoTeste)" class="btn btn-default glyphicon glyphicon-trash" title="Excluir"></button>
                                        <button type="button" ng-click="duplicaCasoTeste(casoTeste)" class="btn btn-default glyphicon glyphicon-file" title="Duplicar"></button>
                                        <button type="button" ng-click="editaPassosCasoTeste(casoTeste)" class="btn btn-default glyphicon glyphicon-pencil" title="Editar"></button>
                                    </td>
                                    <td>
                                        <input type="text" class="form-control mensagemPersonalizada" ng-focus="updateMessagesAutocompletes()" ng-model="casoTeste.mensagemPersonalizada">
                                    </td>
                                    <td style="width: 50px">
                                        <input type="checkbox" ng-model="casoTeste.ativo">
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                                    <button type="button" id="btnSalvar" ng-click="save()" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" ng-click="voltar()" class="btn btn-default btn-voltar"><fmt:message key="button.voltarlistagem"/></button>

                    <!--Modal exibida para carregar Interface-->
                    <div style="display:none;" id="divInterface">
                        <div id="uploadInterface">
                            <input type="file" name="upload_Interface_Solucao" id="upload_Interface_Solucao" />
                        </div>
                        <div class="form-group">
                            <label><fmt:message key="label.exercicios.nomeClasse"/></label>
                            <input type="text" class="form-control" id="nome" name="nome" disabled="disabled" ng-model="getInterface().nomeClasse">
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" ng-model="gerarTestesGetSet">
                                <fmt:message key="label.exercicios.gerarcasostestegetset"/>
                            </label>
                        </div>
                        <button type="button" ng-click="concluiTelaInterface()" class="btn btn-primary"><fmt:message key="button.concluir"/></button>

                    </div>

                    <!--Modal exibida para adicionar novas classes-->
                    <div style="display:none;" id="divNovaClasse">
                        <div class="input-group" style="width:400px; margin-bottom:3px">
                            <input type="text" class="form-control" ng-model="NomeNovaClasse" placeholder="<fmt:message key="label.exercicios.nomeclasseinforme"/>" maxlength="45">
                            <span class="input-group-addon">.java</span>
                        </div>
                        <div class="pull-left">
                            <button type="button" class="btn btn-primary btn-xs" ng-click="adicionaClasse();"><fmt:message key="button.adicionarnovaclasse"/></button>
                        </div>
                        <div class="pull-left" style="margin-left:3px">
                            <input type="file" id="upload_Classe_Auxiliar" />
                        </div>
                    </div>

                    <!--Modal exibida para editar Passos-->
                    <div style="display:none;" id="divEditPassos">
                        <label><fmt:message key="label.exercicios.passoscasosteste"/>:</label>
                        <table>
                            <tbody>
                                <tr ng-repeat="passo in editingCasoTeste.passos track by $index">
                                    <td style="width: 100px">
                                        <button type="button" ng-click="deletaPasso(passo)" class="btn btn-default glyphicon glyphicon-trash" title="Excluir"></button>
                                        <button type="button" ng-click="duplicaPasso(passo)" class="btn btn-default glyphicon glyphicon-file" title="Duplicar"></button>
                                    </td>
                                    <td>
                                        <select class="form-control" ng-model="passo.operationType"  ng-options="o.value as o.label for o in OperationTypes" ng-change="operationTypeChanged(passo)">                                                
                                        </select>
                                    </td>
                                    <td>
                                        <input type="text" class="form-control passoDataType" data-index='{{$index}}' onfocus="$(this).trigger('input');" ng-disabled="passo.operationType == 2 || isDeclaredObject(passo.expectedOutputName, passo) || isDeclaredObject(passo.expectedOutputValue, passo)" ng-model="passo.expectedOutputType" placeholder="<fmt:message key="label.exercicios.tipovariavel"/>" />
                                    </td>
                                    <td>
                                        <input type="text" class="form-control passoObject" data-index='{{$index}}' onfocus="$(this).trigger('input');" ng-show="passo.operationType != 3" ng-disabled="passo.operationType == 2"  ng-model="passo.expectedOutputName" ng-change="expectedOutputNameChanged(passo)" placeholder="<fmt:message key="label.exercicios.nomevariavel"/>"/>
                                        <input type="text" class="form-control passoObject" data-index='{{$index}}' onfocus="$(this).trigger('input');" ng-show="passo.operationType == 3" ng-model="passo.expectedOutputValue" ng-change="expectedOutputValueChanged(passo)" placeholder="<fmt:message key="label.exercicios.valornomevariavel"/>"/>
                                    </td>
                                    <td align="center"> {{ passo.operationType == 3?"==":"=" }} </td>
                                    <td>
                                        <input type="text"  class="form-control passoDataTypeOrObject" data-index='{{$index}}' onfocus="$(this).trigger('input');"  ng-model="passo.objectName" ng-change="objectNameChanged(passo)" placeholder="<fmt:message key="label.exercicios.nomeclassevariavel"/>"/>
                                    </td>
                                    <td>.</td>
                                    <td>
                                        <input type="text" class="form-control passoMethod" data-index='{{$index}}' onfocus="$(this).trigger('input');" ng-disabled="passo.objectName == 'System.Out'" ng-model="passo.methodName" ng-change="methodNameChanged(passo)" placeholder="<fmt:message key="label.exercicios.nomemetodo"/>"/>
                                    </td>
                                    <td>
                                        <table >
                                            <tr>
                                                <td>(</td>
                                                <td ng-repeat="parametro in passo.inputParameters">
                                                    <table>
                                                        <tr>
                                                            <td>
                                                                <button type="button" ng-click="deletaParametro(passo, parametro)" class="btn btn-default glyphicon glyphicon-trash" title="Excluir"></button>
                                                            </td>
                                                            <td>
                                                                <input type="text" class="form-control passoDataType" data-index='{{$index}}' onfocus="$(this).trigger('input');" ng-disabled="isDeclaredObject(parametro.objectValue, passo)" ng-model="parametro.objectType" placeholder="<fmt:message key="label.exercicios.tipoparametro"/>"/>
                                                            </td>
                                                            <td>
                                                                <input type="text" class="form-control" ng-model="parametro.objectValue" ng-change="parametroValueChanged(passo, parametro)" placeholder="<fmt:message key="label.exercicios.valorparametro"/>"/>
                                                            </td>
                                                            <td>,</td>
                                                        </tr>
                                                    </table>
                                                </td>
                                                <td>
                                                    <button type="button" ng-click="adicionaParametro(passo)" class="btn btn-default glyphicon glyphicon-plus" title="Adicionar"></button>
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
                                        <button type="button" ng-click="adicionaPasso()" class="btn btn-primary" style="margin-top: 20px;"><fmt:message key="label.exercicios.adicionarpassocasoteste"/></button>
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