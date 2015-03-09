<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">

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

        </style>
        <script src="<c:url value='/resources/ace/ace.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/views/exercicios/solucionar.js'/>" type="text/javascript"></script>
        <script type="text/javascript">
            var exercicioID = "${Exercicio.getId()}";
            var fileID = "${fileID}";

            var baseUrl = "<c:url value='/'/>";
            var newClassContent = "${CodigoFontePadraoClasse}";
            var confirmaExclusaoText = '<fmt:message key="label.confirmaexclusao"/>';
            var desmarcarfavoritaText = '<fmt:message key="button.desmarcarfavorita"/>';
            var marcarfavoritaText = '<fmt:message key="button.marcarfavorita"/>';
        </script>
    </jsp:attribute>

    <jsp:attribute name="rightmenu">
        <h4><fmt:message key="label.exercicios.acoes"/></h4>
        <div class="list-group">
            <a href="#" class="list-group-item btn-classe-enviar">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;&nbsp;<fmt:message key="menu.solucao.enviar"/>
            </a>
            <a href="#" class="list-group-item btn-classe-download">
                <span class="glyphicon glyphicon-save"></span>&nbsp;&nbsp;<fmt:message key="menu.solucao.baixar"/>
            </a>
        </div>
    </jsp:attribute>
    <jsp:body>
        <h2><c:out value="${Exercicio.getNome()}"/></h2>

        <c:if test="${Solucao != null}">
            <h4><fmt:message key="label.exercicios.resultado"/></h4>
            <c:choose>
                <c:when test="${Solucao.getIdStatus() == 1}">
                    <div class="alert alert-info"><fmt:message key="label.exercicios.status.aguardando"/></div>
                </c:when>
                <c:when test="${Solucao.getIdStatus() == 2}">
                    <div class="alert alert-danger"><fmt:message key="label.exercicios.status.errocompilacao"/></div>
                </c:when>
                <c:when test="${Solucao.getIdStatus() == 3}">
                    <div class="alert alert-warning"><fmt:message key="label.exercicios.status.resultadoinvalido"/></div>
                </c:when>
                <c:when test="${Solucao.getIdStatus() == 4 &&  empty Solucao.getErros()}">
                    <div class="alert alert-success"><fmt:message key="label.exercicios.status.resolvido"/></div>
                </c:when>
                <c:when test="${Solucao.getIdStatus() == 4 && not empty Solucao.getErros()}">
                    <div class="alert alert-success"><fmt:message key="label.exercicios.status.resolvidocomalerta"/></div>
                </c:when>
            </c:choose>  
            <small><fmt:message key="label.exercicios.dataultimaresposta"/> <fmt:formatDate value="${Solucao.getDataCadastro()}" pattern="dd/MM/yyyy HH:mm" /></small>

            <c:if test="${not empty Solucao.getErros()}">
                <div class="list-group">
                    <h4>
                        <fmt:message key="label.exercicios.errosalertas"/>
                    </h4>         
                    <table class="table table-bordered" style="margin-bottom: 0px;">
                        <thead>
                            <tr>
                                <th></th>
                                <th><fmt:message key="label.exercicios.linha"/></th>
                                <th><fmt:message key="label.exercicios.mensagem"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="erro" items="${Solucao.getErros()}">
                                <tr>
                                    <td>
                                        ${erro.getErrorType() == 2
                                          ?
                                          "<button type=\"button\" class=\"btn btn-warning btn-xs\"><span class=\"glyphicon glyphicon-warning-sign\"></span> Alerta</button>"
                                          :
                                          "<button type=\"button\" class=\"btn btn-danger btn-xs\"><span class=\"glyphicon glyphicon-remove\"></span> Erro</button>"
                                        }
                                    </td>
                                    <td>${erro.getLinhaErro()>0 ? erro.getLinhaErro() : ""}</td>
                                    <td>${erro.getMensagemPersonalizada()}
                                        <c:if test="${not empty erro.getMensagemErro()}">
                                            <h6><fmt:message key="label.exercicios.mensagemSistema"/></h6>
                                            ${erro.getMensagemErro()}</td>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>            
        </c:if>
        <c:if test="${Solucao != null && Solucao.getIdStatus() == 1}">
            <script type="text/javascript">
                var intervalLookingStatus = setInterval(function () {
                    lookingForNewStatus()
                }, 5000);
            </script>
        </c:if>

        <div class="panel panel-default">
            <div class="panel-body">
                <c:if test="${Exercicio.getUsaDescricaoPDF() != true}">
                    ${Exercicio.getDescricaoHtml()}
                </c:if>
                <c:if test="${Exercicio.getUsaDescricaoPDF() == true}">
                    <iframe border="0" width="100%" height="600px" src="<c:url value='/'/>exercicios/verdescricao?exercicioId=${Exercicio.getId()}">
                    </iframe>
                </c:if>
            </div>
        </div>
        <h3><fmt:message key="label.exercicios.classes"/></h3>
        <div class="list-group">
            <c:if test="${not empty Classes}">
                <c:forEach var="item" varStatus="status" items="${Classes}">
                    <a href="#" class="list-group-item btn-show-class-code" data-id="${item.getId()}"><span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;<c:out value="${item.getNomeClasse()}"/></a>
                </c:forEach>
            </c:if>
            <a href="#divNovaClasse" class="list-group-item btn-nova-classe"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<fmt:message key="label.exercicios.novaclasse"/></a>
        </div>
        <form role="form" action="<c:url value='/'/>exercicios/saveclasse" id="frmSolucionar" method="POST">
            <input type="hidden" id="hdnIdExercicio" name="hdnIdExercicio" value="${Exercicio.getId()}" />
            <input type="hidden" id="hdnIdClasse" name="hdnIdClasse" value="" />
            <input type="hidden" id="hdnNomeClasse" name="hdnNomeClasse" value="" />
            <input type="hidden" id="hdnEditor" name="hdnEditor" />
        </form>

        <h3 id="lblFilename"></h3>
        <div id="panelEditor"></div>

        <button type="button" class="btn btn-primary btn-sm btn-salvar-classe" style="display:none;">
            <span class="glyphicon glyphicon-save"></span> <fmt:message key="button.salvar"/>
        </button>
        <button type="button" class="btn btn-default btn-sm btn-habilitar-edicao" style="display:none;">
            <span class="glyphicon glyphicon-lock"></span> <fmt:message key="button.habilitaredicao"/>
        </button>
        <button type="button" class="btn btn-default btn-sm btn-excluir-classe" style="display:none;">
            <span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.excluir"/>
        </button>
        <button type="button" class="btn btn-default btn-sm btn-registrar-duvida" style="display:none;">
            <span class="glyphicon glyphicon-comment"></span> <fmt:message key="button.registrarduvida"/>
        </button>
        <button type="button" class="btn btn-default btn-sm btn-registrar-anotacao" style="display:none;">
            <span class="glyphicon glyphicon-eye-open"></span> <fmt:message key="button.registraranotacao"/>
        </button>
        <button type="button" class="btn btn-default btn-sm btn-classe-favorita" style="display:none;">
            <span class="glyphicon glyphicon-star-empty"></span> <fmt:message key="menu.classe.favorita"/>
        </button>

        <!--Modal exibida para adicionar novas classes-->
        <div style="display:none;width: 600px;height: 150px;" id="divNovaClasse">
            <div class="input-group" style="width:400px; margin-bottom:3px">
                <input type="text" class="form-control" id="txtNomeClasse" placeholder="<fmt:message key="label.exercicios.nomeclasseinforme"/>" maxlength="45">
                <span class="input-group-addon">.java</span>
            </div>
            <div class="pull-left">
                <button type="button" class="btn btn-primary btn-xs" onclick="adicionarClasse('');"><fmt:message key="button.adicionarnovaclasse"/></button>
            </div>
            <div class="pull-left" style="margin-left:3px">
                <div id="file_upload">
                    <fmt:message key="button.adicionarclasseexistente"/>
                </div>
            </div>
            <div class="pull-left" style="margin-left:3px">
                <button type="button" class="btn btn-default btn-xs" onclick="cancelarClasse();"><fmt:message key="button.cancelar"/></button>
            </div>
        </div>

        <!--Modal exibida para adicionar perguntas-->
        <div class="panel panel-success shadow" id="panel-markedquestions" style="width:400px; display:none; position:fixed; top:120px; left:0px; z-index:100">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="label.exercicio.marcacoeslinha"/> #<span id="title-linenumber"></span>
                    <button type="button" class="close pull-right" aria-hidden="true">&times;</button></h3>
            </div>
            <div class="panel-body" id="pnlMensagens" style="height: 300px; overflow-y: auto">...</div>
            <div class="panel-footer">
                <form role="form" action="<c:url value='/'/>exercicios/savequestion" id="frmQuestao" method="POST">
                    <input type="hidden" id="hdnQuestaoIdExercicioClasse" name="hdnQuestaoIdExercicioClasse">
                    <input type="hidden" id="hdnQuestaoLinha" name="hdnQuestaoLinha">
                    <input type="hidden" id="hdnQuestaoIdExercicio" name="hdnQuestaoIdExercicio" value="${Exercicio.getId()}">
                    <textarea class="form-control input-sm" rows="3" name="questaoClasse"></textarea>
                    <button class="btn btn-success btn-xs" type="submit"><span class="glyphicon glyphicon-ok"></span> <fmt:message key="button.enviarpergunta"/></button>
                </form>
            </div>
        </div>

        <!--Modal exibida para adicionar anotaçõess-->
        <div class="panel panel-success shadow" id="panel-markedannotations" style="width:400px; display:none; position:fixed; top:120px; left:0px; z-index:100">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="label.exercicio.anotacoeslinha"/> #<span id="title-linenumber"></span>
                    <button type="button" class="close pull-right" aria-hidden="true">&times;</button></h3>
            </div>
            <form role="form" action="<c:url value='/'/>exercicios/saveannotation" id="frmAnotacao" method="POST">
                <div class="panel-body" id="pnlAnotacoes" style="height: 300px; overflow-y: auto">
                    <textarea class="form-control" rows="12" id="anotacaoClasse" name="anotacaoClasse"></textarea>
                </div>
                <div class="panel-footer">
                    <input type="hidden" id="hdnAnotacaoIdClasse" name="hdnAnotacaoIdClasse">
                    <input type="hidden" id="hdnAnotacaoLinha" name="hdnAnotacaoLinha">
                    <input type="hidden" id="hdnAnotacaoIdExercicio" name="hdnAnotacaoIdExercicio" value="${Exercicio.getId()}">
                    <button class="btn btn-success btn-xs" type="submit"><span class="glyphicon glyphicon-save"></span> <fmt:message key="button.salvar"/></button>
                    <button class="btn btn-success btn-xs"><span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.excluir"/></button>
                </div>
            </form>
        </div>
    </jsp:body>
</t:master>