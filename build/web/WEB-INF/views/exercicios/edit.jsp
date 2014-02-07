<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            var trTemplate = "<tr id=\"trLinhaNova#id#\" class=\"linha-validacao\"><td><div class=\"btn-group btn-group-xs\"><button type=\"button\" class=\"btn btn-default btn-excluir\" data-source=\"0\" data-id=\"#id#\"><fmt:message key="button.excluir"/></button></div></td><td><textarea class=\"form-control\" rows=\"3\" id=\"entrada#id#\" name=\"entrada#id#\"></textarea></td><td><textarea class=\"form-control\" rows=\"3\" id=\"saida#id#\" name=\"saida#id#\"></textarea></td><td><textarea class=\"form-control\" rows=\"3\" id=\"mensagem#id#\" name=\"mensagem#id#\"></textarea></td></tr>";
    
            $(function(){
                $("#menu-lista-exercicio").addClass("active");
                
                $(".editor").jqte();
                
                
                $('#file_upload').uploadify({
                    'swf'           : '<c:url value='/resources/uploadify/uploadify.swf'/>',
                    'uploader'      : '<c:url value='/'/>exercicios/upload',
                    'fileTypeDesc'  : 'Arquivos PDF',
                    'fileTypeExts'  : '*.pdf',
                    'fileSizeLimit' : '500KB',
                    'buttonText'    : '<fmt:message key="button.escolherarquivo"/>',
                    'multi'         : false,
                    'fileObjName'   : 'filedata',
                    'checkExisting' : false,
                    'width'         : 146,
                    'height'        : 34,
                    'removeCompleted' : false,
                    'onUploadSuccess' : function(file, data, response) {
                        alert(data);
                        $("#idUploadTemp").val(data);
                    },
                    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
                        //alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
                    }
                });
                
                $(".rd-detalhamento").click(function(){
                    var show = $(this).attr("show-div");
                    $(".div-detalhamento").hide();
                    $("#" + show).show();
                });
                
                $(".btn-voltar").click(function(){
                    document.location.href = "<c:url value='/'/>exercicios";
                });
                
                $(document).on("click", ".btn-excluir", function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    var source = parseInt($(this).attr("data-source"));
                    var trId = (source === 1) ? "#trLinha" + id : "#trLinhaNova" + id;
                    $(trId).remove();
                });
                
                $(".btn-nova-linha").click(function(){
                    var cont = parseInt($("#contador").val()) + 1;
                    var linha = trTemplate.replace(/#id#/gi, cont);
                    $("#tbody-validacoes").append(linha);
                    $("#contador").val(cont);
                });
                
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        <h2>
        <c:choose>
            <c:when test="${IsAdd != null && IsAdd}">
                <fmt:message key="label.exercicios.novo"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="label.exercicios.editar"/>
            </c:otherwise>
        </c:choose>
        </h2>
        
        <div class="panel panel-default">
            <div class="panel-body">

                <form role="form" action="<c:url value='/'/>${IsAdd != null && IsAdd ? "exercicios/saveadd" : "exercicios/saveedit/"}" method="POST">
                    <input type="hidden" id="id" name="id" value="${exercicio.getId()}">
                    <div class="form-group">
                        <label for="titulo"><fmt:message key="label.exercicios.titulo"/>:</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.exercicios.tituloinforme"/>" value="${exercicio.getNome()}">
                    </div>
                    <div class="form-group">
                        <label for="nivel"><fmt:message key="label.exercicios.dificuldade"/>:</label>
                        <select class="form-control" id="idNivelDificuldade" name="idNivelDificuldade">
                            <option value="1">Baixa</option>
                            <option value="2">Média</option>
                            <option value="3">Alta</option>
                        </select>
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="ativo" ${IsAdd != null && IsAdd ? "checked" : exercicio.isAtivo() ? "checked" : ""}>
                            <fmt:message key="label.exercicios.ativo"/>
                        </label>
                    </div>
                    <div class="form-group">
                        <label for="encerramento"><fmt:message key="label.exercicios.detalhamento"/>:</label><br>
                        <label class="radio-inline">
                            <input type="radio" id="rdHTML" name="rdDetalhamento" class="rd-detalhamento" value="html" show-div="editorhtml" checked="">
                            <fmt:message key="label.exercicios.utilizareditorhtml"/>
                        </label>
                        <label class="radio-inline">
                            <input type="radio" id="rdPDF" name="rdDetalhamento" class="rd-detalhamento" value="pdf" show-div="uploadpdf">
                            <fmt:message key="label.exercicios.utilizarpdf"/>
                        </label>
                    </div>
                    
                    <div id="editorhtml" class="div-detalhamento">
                        <textarea class="editor" name="htmlcontent">
                            <c:choose>
                                <c:when test="${IsAdd != null && IsAdd}">
                                    <fmt:message key="label.exercicios.descricaoinforme"/>
                                </c:when>
                                <c:otherwise>
                                    ${exercicio.getDescricaoHtml()}
                                </c:otherwise>
                            </c:choose>
                        </textarea>
                    </div>
                    <div id="uploadpdf" style="display:none;" class="div-detalhamento">
                        <input type="hidden" id="idUploadTemp" name="idUploadTemp">
                        <input type="file" name="file_upload" id="file_upload" />
                    </div>
                        
                    <button type="submit" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" class="btn btn-default"><fmt:message key="button.voltarlistagem"/></button>
                </form>

            </div>
        </div>
                
        <c:if test="${IsAdd != null && !IsAdd}">

            <h2><fmt:message key="label.exercicios.cadastrarentradassaidas"/></h2>
            
            <form role="form" action="<c:url value='/'/>exercicios/savevalidacao" method="POST">
                <input type="hidden" id="idExercicio" name="idExercicio" value="${exercicio.getId()}">
                <input type="hidden" id="contador" name="contador" value="${exercicio.getValidacoes().size()}">
                
                
                <button type="button" class="btn btn-primary btn-nova-linha"><fmt:message key="button.novalinha"/></button>
                <button type="submit" class="btn btn-primary"><fmt:message key="button.salvarvalidacao"/></button>
                <br /><br />

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="label.registroscadastrados"/></h3>
                    </div>
                    <div class="panel-body">

                        <table class="table table-striped table-hover" style="margin-bottom: 0px;">
                            <thead>
                                <tr>
                                    <th><fmt:message key="label.exercicios.acoes"/></th>
                                    <th><fmt:message key="label.exercicios.entrada"/></th>
                                    <th><fmt:message key="label.exercicios.saida"/></th>
                                    <th><fmt:message key="label.exercicios.mensagemperzonalizada"/></th>
                                </tr>
                            </thead>
                            <tbody id="tbody-validacoes">
                                <c:if test="${not empty exercicio.getValidacoes()}">
                                    <c:forEach var="item" varStatus="status" items="${exercicio.getValidacoes()}">
                                        <tr id="trLinha${item.getId()}" class="linha-validacao">
                                            <td>
                                                <div class="btn-group btn-group-xs">
                                                    <input type="hidden" id="idValidacao${status.index + 1}" name="idValidacao${status.index + 1}" value="${item.getId()}">
                                                    <button type="button" class="btn btn-default btn-excluir" data-source="1" data-id="${item.getId()}"><fmt:message key="button.excluir"/></button>
                                                </div>
                                            </td>
                                            <td>
                                                <textarea class="form-control" rows="3" id="entrada${status.index + 1}" name="entrada${status.index + 1}">${item.getEntrada()}</textarea>
                                            </td>
                                            <td>
                                                <textarea class="form-control" rows="3" id="saida${status.index + 1}" name="saida${status.index + 1}">${item.getSaida()}</textarea>
                                            </td>
                                            <td>
                                                <textarea class="form-control" rows="3" id="mensagem${status.index + 1}" name="mensagem${status.index + 1}">${item.getMensagem()}</textarea>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                            </tbody>
                        </table>

                    </div>
                </div>
                <button type="button" class="btn btn-primary btn-nova-linha"><fmt:message key="button.novalinha"/></button>
                <button type="submit" class="btn btn-primary"><fmt:message key="button.salvarvalidacao"/></button>
            </form>
            <br><br>
            
        </c:if>
                
        

    </jsp:body>
</t:master>