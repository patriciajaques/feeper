<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.turmas"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <link href="<c:url value='/resources/jquery/themes/base/jquery.ui.all.css'/>" rel="stylesheet" type="text/css" />
        
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
                
                $(".btn-excluir").click(function(){
                    var id = $(this).attr("data-id");
                    var idTurma = $(this).attr("data-idturma");
                    if (id === undefined || idTurma === undefined) return;
                    if (!confirm("<fmt:message key="label.confirmaexclusao"/>")) return;
                    document.location.href = "<c:url value='/'/>turma/deletealuno/" + idTurma + "/" + id;
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
        
        <div class="panel panel-default">
            <div class="panel-body">
                
                <form role="form" action="<c:url value='/'/>${IsAdd != null && IsAdd ? "turma/saveadd" : "turma/saveedit/"}" method="POST">
                    <input type="hidden" id="id" name="id" value="${turma.getId()}">
                    <div class="form-group">
                        <label for="nome"><fmt:message key="label.turma.nome"/>:</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.turma.nomeinforme"/>" value="${turma.getNome()}">
                    </div>
                    <div class="form-group">
                        <label for="professor"><fmt:message key="label.turma.professor"/>:</label>
                        <input type="text" class="form-control" id="professor" name="professor" placeholder="<fmt:message key="label.turma.professorinforme"/>" value="${turma.getProfessor().getNome()}">
                        <input type="hidden" id="idProfessor" name="idProfessor" value="${turma.getIdProfessor()}">
                    </div>
                    <div class="form-group">
                        <label for="dataEncerramento"><fmt:message key="label.turma.dataencerramento"/>:</label>
                        <input type="text" class="form-control" id="dataEncerramento" name="dataEncerramento" placeholder="<fmt:message key="label.turma.dataencerramentoinforme"/>" value="<fmt:formatDate value="${turma.getDataEncerramento()}" pattern="dd/MM/yyyy" />">
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="ativo" ${IsAdd != null && IsAdd ? "checked" : turma.isAtivo() ? "checked" : ""}> <fmt:message key="label.turma.ativo"/>
                        </label>
                    </div>
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
            <button type="button" id="btn-enviar-convites" class="btn btn-default"><fmt:message key="button.enviarconvitealunos"/></button>
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
                                                <button type="button" class="btn btn-default btn-excluir" data-idturma="${turma.getId()}" data-id="${item.getId()}"><fmt:message key="button.excluir"/></button>
                                            </div>
                                        </td>
                                        <td>${item.getId()}</td>
                                        <td>${item.getNome()}</td>
                                        <td>${item.getEmail()}</td>
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
        </c:if>

    </jsp:body>
</t:master>