<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.turmas"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-lista-turma").addClass("active");
                
                $("#btn-novo-aluno").click(function(){
                    $.fancybox({
                        'autoSize': false,
                        'openEffect': 'fade',
                        'closeEffect': 'fade',
                        'width': 500,
                        'height': 350,
                        'href': "<c:url value='/'/>turma/addaluno/${turma.getId()}",
                        'type': 'iframe',
                        'modal': true
                    });
                });
                
                $("#btn-novo-exercicio").click(function(){
                    $.fancybox({
                        'autoSize': false,
                        'openEffect': 'fade',
                        'closeEffect': 'fade',
                        'width': 500,
                        'height': 350,
                        'href': "<c:url value='/'/>turma/addexercicio/${turma.getId()}",
                        'type': 'iframe',
                        'modal': true
                    });
                });
                
                $(".btn-voltar").click(function(){
                    document.location.href = "<c:url value='/'/>turma";
                });
                
                $("#professor").autocomplete({
                    source: function (request, response) {
                        $.ajax({
                            url: '<c:url value='/'/>pessoa/search/2/' + $("#professor").val(),
                            type: 'GET',
                            dataType: 'json'
                        }).done(function (data) {
                            response($.map(data, function (item) {
                                return { label: item[1], value: item[1], id: item[0] };
                            }));
                        }).fail(function () {
                            $('.ui-autocomplete-loading').removeClass("ui-autocomplete-loading");
                        });
                    },
                    minLength: 5,
                    select: function (event, ui) {
                        $("#idProfessor").val(ui.item.id);
                    },
                    change: function (event, ui) {
                        //$("#idProfessor").val("");
                    }
                });
                
                $("#btn-enviar-convites").click(function(){
                    if (!confirm("<fmt:message key="label.confirmaenviodosconvites"/>")) return;
                    document.location.href = "<c:url value='/'/>turma/enviarconvites/${turma.getId()}";
                });
                
                $(".btn-excluir").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    if (!confirm("<fmt:message key="label.confirmaexclusao"/>")) return;
                    document.location.href = "<c:url value='/'/>turma/deletealuno/${turma.getId()}/" + id;
                });
                
                $(".btn-convite").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    if (!confirm("<fmt:message key="label.confirmaenvioconvite"/>")) return;
                    document.location.href = "<c:url value='/'/>turma/enviarconvite/${turma.getId()}/" + id;
                });
                
                $(".btn-excluir-exercicio").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    if (!confirm("<fmt:message key="label.confirmaexclusao"/>")) return;
                    document.location.href = "<c:url value='/'/>turma/deleteexercicio/${turma.getId()}/" + id;
                });
                
                $(".btn-exercicio-visivel").click(function(){
                    var btn = $(this);
                    var id = $(this).attr("data-id");
                    var visivel = $(this).attr("data-visivel") == "1";
                    var msgConfirmacao = visivel ? "<fmt:message key="label.confirmaocultarexercicio"/>" : "<fmt:message key="label.confirmaexibicaoexercicio"/>";
                    var labelBotao = visivel ? "<fmt:message key="button.exibirexercicio"/>" : "<fmt:message key="button.ocultarexercicio"/>";
                    
                    if (id === undefined) return;
                    if (!confirm(msgConfirmacao)) return;
                    
                    var url = "<c:url value='/'/>turma/exerciciovisivel/${turma.getId()}/" + id;
                    $.get(url, function(data){
                        if (data == "ok")
                        {
                            $(btn).html(labelBotao).attr("data-visivel", visivel ? "0" : "1");
                            $("#msgSucessoExercicio").html("<fmt:message key="label.turma.exercicio.sucessotrocavisibilidade"/>").show();
                            $("#msgErroExercicio").html("").hide();
                            $("#spExercicio${turma.getId()}_" + id).removeClass(visivel ? "glyphicon-ok" : "glyphicon-remove").addClass(visivel ? "glyphicon-remove" : "glyphicon-ok");
                        }
                        else
                        {
                            $("#msgSucessoExercicio").html("").hide();
                            $("#msgErroExercicio").html("<fmt:message key="label.turma.exercicio.errotrocavisibilidade"/>").show();
                        }
                    });
                });
                
                
                $('#dataEncerramento').datepicker({
                    dateFormat: 'dd/mm/yy',
                    changeMonth: true,
                    changeYear: true,
                    showAnim: 'fadeIn',
                    showOtherMonths: true,
                    selectOtherMonths: true
                });
                
            });
            
            function AtualizaListagem()
            {
                location.reload();
            }
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
        
        <c:if test="${not empty MSG_SUCESSO}">
            <div class="alert alert-success">${MSG_SUCESSO}</div>
        </c:if>
        <c:if test="${not empty MSG_ERRO}">
            <div class="alert alert-danger">${MSG_ERRO}</div>
        </c:if>
        
        <div class="panel panel-default">
            <div class="panel-body">
                
                <form role="form" action="<c:url value='/'/>${IsAdd != null && IsAdd ? "turma/saveadd" : "turma/saveedit/"}" method="POST">
                    
                    <div class="row">
                        <div class="col-xs-4">
                            <div class="form-group">
                                <label for="nome"><fmt:message key="label.turma.nome"/>:</label>
                                <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.turma.nomeinforme"/>" value="${turma.getNome()}">
                            </div>
                        </div>
                        <div class="col-xs-4">
                            <div class="form-group">
                                <label for="professor"><fmt:message key="label.turma.professor"/>:</label>
                                <input type="text" class="form-control" id="professor" name="professor" placeholder="<fmt:message key="label.turma.professorinforme"/>" value="${turma.getProfessor().getNome()}">
                                <input type="hidden" id="idProfessor" name="idProfessor" value="${turma.getIdProfessor()}">
                            </div>
                        </div>
                        <div class="col-xs-3">
                            <div class="form-group">
                                <label for="dataEncerramento"><fmt:message key="label.turma.dataencerramento"/>:</label>
                                <input type="text" class="form-control" id="dataEncerramento" name="dataEncerramento" placeholder="<fmt:message key="label.turma.dataencerramentoinforme"/>" value="<fmt:formatDate value="${turma.getDataEncerramento()}" pattern="dd/MM/yyyy" />">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="ativo" ${IsAdd != null && IsAdd ? "checked" : turma.isAtivo() ? "checked" : ""}> <fmt:message key="label.turma.ativo"/>
                                </label>
                            </div>
                        </div>
                    </div>
                    
                    <input type="hidden" id="id" name="id" value="${turma.getId()}">
                    <button type="submit" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" class="btn btn-default btn-voltar"><fmt:message key="button.voltarlistagem"/></button>
                </form>

            </div>
        </div>
        
        <c:if test="${IsAdd != null && !IsAdd}">
            <h2><fmt:message key="label.turma.listaalunos"/></h2>
            
            <div class="alert alert-success" id="msgSucesso" style="display:none"></div>
            <div class="alert alert-danger" id="msgErro" style="display:none"></div>
            
            <button type="button" id="btn-novo-aluno" class="btn btn-primary"><fmt:message key="button.adicionaraluno"/></button>
            <button type="button" id="btn-enviar-convites" class="btn btn-default"><span class="glyphicon glyphicon-envelope"></span>&nbsp;&nbsp;<fmt:message key="button.enviarconvitealunos"/></button>
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
                            <c:if test="${not empty turma.getAlunos()}">
                                <c:forEach var="item" varStatus="status" items="${turma.getAlunos()}">
                                    <tr id="trAluno${turma.getId()}_${item.getId()}">
                                        <td>
                                            <div class="btn-group btn-group-xs">
                                                <button type="button" class="btn btn-default btn-excluir" data-id="${item.getId()}"><fmt:message key="button.excluir"/></button>
                                                <button type="button" class="btn btn-default btn-convite" data-id="${item.getId()}"><fmt:message key="button.enviarconvite"/></button>
                                            </div>
                                        </td>
                                        <td><c:out value="${item.getId()}"/></td>
                                        <td><c:out value="${item.getNome()}"/></td>
                                        <td><c:out value="${item.getEmail()}"/></td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty turma.getAlunos()}">
                                <tr>
                                    <td colspan="4" style="text-align: center"><fmt:message key="label.nenhumregistroencontrado"/></td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>

                </div>
            </div>
                            
            <h2><fmt:message key="label.turma.listaexercicios"/></h2>
            
            <div class="alert alert-success" id="msgSucessoExercicio" style="display:none"></div>
            <div class="alert alert-danger" id="msgErroExercicio" style="display:none"></div>
            
            <button type="button" id="btn-novo-exercicio" class="btn btn-primary"><fmt:message key="button.adicionarexercicio"/></button>
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
                                <th><fmt:message key="label.turma.tituloexercicio"/></th>
                                <th><fmt:message key="label.turma.visivel"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${not empty turma.getExercicios()}">
                                <c:forEach var="item" varStatus="status" items="${turma.getExercicios()}">
                                    <tr id="trExercicio${turma.getId()}_${item[0]}">
                                        <td>
                                            <div class="btn-group btn-group-xs">
                                                <button type="button" class="btn btn-default btn-excluir-exercicio" data-id="${item[0]}"><fmt:message key="button.excluir"/></button>
                                                <button type="button" class="btn btn-default btn-exercicio-visivel" data-id="${item[0]}" data-visivel="${item[2] ? "1" : "0"}">
                                                    <c:if test="${not item[2]}">
                                                        <fmt:message key="button.exibirexercicio"/>
                                                    </c:if>
                                                    <c:if test="${item[2]}">
                                                        <fmt:message key="button.ocultarexercicio"/>
                                                    </c:if>
                                                </button>
                                            </div>
                                        </td>
                                        <td><c:out value="${item[0]}"/></td>
                                        <td><c:out value="${item[1]}"/></td>
                                        <td>
                                            <c:if test="${not item[2]}">
                                                <span class="glyphicon glyphicon-remove" id="spExercicio${turma.getId()}_${item[0]}"></span>
                                            </c:if>
                                            <c:if test="${item[2]}">
                                                <span class="glyphicon glyphicon-ok" id="spExercicio${turma.getId()}_${item[0]}"></span>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty turma.getExercicios()}">
                                <tr>
                                    <td colspan="4" style="text-align: center"><fmt:message key="label.nenhumregistroencontrado"/></td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>

                </div>
            </div>
        </c:if>

    </jsp:body>
</t:master>