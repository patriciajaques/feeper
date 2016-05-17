<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<t:master>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        <script src="<c:url value='/resources/ace/ace.js'/>" type="text/javascript"></script>

        <script src="<c:url value='/resources/js/angular.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/sortable.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/views/exercicios/edit.js?v=1.04'/>" type="text/javascript"></script>

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
                box-shadow: 0px 0px 1px 1px red inset;
            } 

            .ui-state-highlight { height: 91px; }
            .casoHandle{cursor: pointer;}
            .popover-title{color:red;}
            #panelEditorCaso{
                width: 100%;
                height: calc(100% - 25px);
            }
            #editorCaso{
                width: 100%;
                height: 100%;
            }
        </style>
        <script type="text/javascript">
            var baseUrl = "<c:url value='/'/>";
            var erroCarregarAssinaturasText = '<fmt:message key="label.exercicios.errocarregarassinaturas"/>';
            var confirmarExcluirText = '<fmt:message key="label.confirmaexclusao"/>';</script>
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

                        <button id="upload_descricao" type="button"  class="btn btn-primary btn-sm" >
                            <fmt:message key="button.escolherarquivo"/>
                        </button>
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
                        <br/>
                        <br/>
                        <div class="list-group">
                            <div  class="list-group-item" ng-repeat="classe in exercicio.classesAuxiliares">
                                <table style="width: 100%">
                                    <tr>
                                        <td style="width: 100%;cursor: pointer;" ng-click="showClasseCode(classe)">
                                            <span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;{{classe.nomeClasse}}
                                        </td>
                                        <td style="min-width: 150px;">
                                            <label><input type="checkbox" ng-model="classe.mostrarParaAluno" >&nbsp;&nbsp;Mostrar p. Aluno</label>
                                        </td>
                                        <td>
                                            <button class="btn btn-default glyphicon glyphicon-cog" ng-click="visualizaOpcoesClasse(classe)"></button>
                                        </td>
                                        <td>
                                            <button class="btn btn-default glyphicon glyphicon-remove" style="color: red;" ng-click="deletaClasse(classe)"></button>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <a  ng-click="visualizaNovaClasse()" style="cursor:pointer;" class="list-group-item"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<fmt:message key="label.exercicios.adicionarclasseauxiliar"/></a>
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
                        <button type="button" ng-click="ocultaEditorClasses()" class="btn btn-default btn-sm" >
                            <span class="glyphicon glyphicon-open"></span> <fmt:message key="button.ocultareditor"/>
                        </button>
                    </div>

                    <div id="casosTeste" class="form-group">
                        <div class="form-group" style="margin-top: 60px;">                     
                            <label><fmt:message key="label.exercicios.casosteste"/>:</label>
                        </div>

                        <table id="casosTable" class="table table-striped table-hover" style="margin-top: 10px; margin-bottom: 0px;">
                            <thead>
                                <tr>
                                    <th style="width:15px;"></th>
                                    <th style="width:10px;">#</th>
                                    <th><fmt:message key="label.exercicios.acoes"/></th>   
                                    <th><fmt:message key="label.exercicios.mensagemcompilacao"/></th>
                                    <th><fmt:message key="label.exercicios.mensagempersonalizada"/></th>                            
                                    <th><fmt:message key="label.exercicios.ativo"/></th>
                                </tr>
                            </thead>
                            <tbody ui-sortable="sortableCasosOptions" ng-model="exercicio.casosTeste">
                                <tr ng-repeat="casoTeste in exercicio.casosTeste track by casoTeste.ordem">
                                    <td style="width: 15px">
                                        <i class="fa fa-arrows casoHandle"></i>
                                    </td>
                                    <td>{{casoTeste.ordem}}</td>
                                    <td style="width: 160px">
                                        <button type="button" ng-click="deletaCasoTeste(casoTeste)" class="btn btn-default btn-sm" title="Excluir"><i style="color: red;" class="fa fa-remove"></i></button>
                                        <button type="button" ng-click="duplicaCasoTeste(casoTeste)" class="btn btn-default btn-sm" title="Duplicar"><i class="fa fa-files-o"></i></button>
                                        <button type="button" ng-click="copiaCasoTeste(casoTeste)" class="btn btn-default btn-sm" title="Copiar"><i class="fa fa-file-o"></i></button>
                                        <button type="button" ng-click="editaCasoTeste(casoTeste)" class="btn btn-default btn-sm" title="Editar"><i style="color: green;" class="fa fa-pencil"></i></button>
                                    </td>
                                    <td>
                                        <textarea rows="3" class="form-control mensagemProfessor" ng-model="casoTeste.mensagemCompilacao" placeholder="<fmt:message key="label.exercicios.mensagemcompilacaoinforme"/>"></textarea>
                                    </td>
                                    <td>
                                        <textarea rows="3" class="form-control mensagemProfessor" ng-model="casoTeste.mensagemPersonalizada" placeholder="<fmt:message key="label.exercicios.mensagempersonalizadainforme"/>"></textarea>
                                    </td>                      
                                    <td style="width: 50px">
                                        <input type="checkbox" ng-model="casoTeste.ativo">
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <button type="button" ng-click="colaCasoTeste()" ng-show="possuiCasoTesteCopiado() == true" class="btn btn-primary" style="margin-top: 20px;margin-bottom: 20px"><i class="fa fa-clipboard"></i>&nbsp;&nbsp;<fmt:message key="label.exercicios.colarcasoteste"/></button>
                        <button type="button" ng-click="adicionaCasoTeste()" class="btn btn-primary" style="margin-top: 20px;margin-bottom: 20px"><i class="fa fa-plus"></i></span>&nbsp;&nbsp;<fmt:message key="label.exercicios.adicionarcasoteste"/></button>
                    </div>

                    <button type="button" id="btnSalvar" ng-click="save()" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" ng-click="voltar()" class="btn btn-default btn-voltar"><fmt:message key="button.voltarlistagem"/></button>

                    <!--Modal exibida para adicionar novas classes-->
                    <div style="display:none;width: 600px;height: 150px;" id="divNovaClasse">
                        <div class="input-group" style="width:100%; margin-bottom:3px">
                            <input type="text" class="form-control" ng-model="NomeNovaClasse" placeholder="<fmt:message key="label.exercicios.nomeclasseinforme"/>" maxlength="45">
                            <span class="input-group-addon">.java</span>
                        </div>
                        <div class="pull-left">
                            <button type="button" class="btn btn-primary btn-xs" ng-click="adicionaClasse();"><fmt:message key="button.adicionarnovaclasse"/></button>
                        </div>
                        <div class="pull-left" style="margin-left:3px">
                            <button id="upload_Classe_Auxiliar" type="button" class="btn btn-primary btn-xs"><fmt:message key="button.adicionarclasseexistente"/></button>
                        </div>
                    </div>

                    <!--Modal exibida para Opções da Classe-->
                    <div style="display:none;" id="divOpcoesClasse">
                        <div class="form-group">
                            <label><fmt:message key="label.exercicios.nomeClasse"/></label>
                            <input type="text" class="form-control" id="nome" name="nome" disabled="disabled" ng-model="editingClass.nomeClasse">
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" ng-model="gerarTestesContrutores">
                                <fmt:message key="label.exercicios.gerarcasostesteconstrutores"/>
                            </label>
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" ng-model="gerarTestesGetSet">
                                <fmt:message key="label.exercicios.gerarcasostestegetset"/>
                            </label>
                        </div>
                        <div class="checkbox">
                            <label>
                                <input type="checkbox" ng-model="gerarTestesMetodos">
                                <fmt:message key="label.exercicios.gerarcasostestemetodos"/>
                            </label>
                        </div>
                        <button type="button" ng-click="concluiOpcoesClasse()" class="btn btn-primary"><fmt:message key="button.concluir"/></button>
                    </div>

                    <!--Modal exibida para editar Casos de Teste-->
                    <div style="display:none; width: 100%; height: 100%;" id="divEditCaso">
                        <label><fmt:message key="label.exercicios.edicaoocasosteste"/>:</label>
                        <div id="panelEditorCaso"></div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:body>
</t:master>