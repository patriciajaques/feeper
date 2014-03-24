<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            var trTemplate = "<tr id=\"trLinhaNova#id#\" class=\"linha-validacao\"><td><div class=\"btn-group btn-group-xs\"><button type=\"button\" class=\"btn btn-default btn-excluir\" data-context=\"frmSaveValidacao\" data-source=\"0\" data-id=\"#id#\"><fmt:message key="button.excluir"/></button></div></td><td><textarea class=\"form-control\" rows=\"3\" id=\"entrada#id#\" name=\"entrada#id#\"></textarea></td><td><textarea class=\"form-control\" rows=\"3\" id=\"saida#id#\" name=\"saida#id#\"></textarea></td><td><textarea class=\"form-control\" rows=\"3\" id=\"mensagem#id#\" name=\"mensagem#id#\"></textarea></td></tr>";
            var trTemplateClasse = "<tr id=\"trLinhaNova#id#\" class=\"linha-validacao\"><td><div class=\"btn-group btn-group-xs\"><button type=\"button\" class=\"btn btn-default btn-excluir\" data-context=\"frmSaveClasseValidacao\" data-source=\"0\" data-id=\"#id#\"><fmt:message key="button.excluir"/></button></div></td><td><textarea class=\"form-control\" rows=\"3\" id=\"fonte#id#\" name=\"fonte#id#\"></textarea></td><td><textarea class=\"form-control\" rows=\"3\" id=\"saida#id#\" name=\"saida#id#\"></textarea></td><td><textarea class=\"form-control\" rows=\"3\" id=\"mensagemCompilacao#id#\" name=\"mensagemCompilacao#id#\"></textarea></td><td><textarea class=\"form-control\" rows=\"3\" id=\"mensagem#id#\" name=\"mensagem#id#\"></textarea></td></tr>";
    
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
                    var context = $(this).attr("data-context");
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    var source = parseInt($(this).attr("data-source"));
                    var trId = (source === 1) ? "#trLinha" + id : "#trLinhaNova" + id;
                    $("#"+ context + " " + trId).remove();
                });
                
                $(".btn-nova-linha").click(function(){
                    novaLinha($(this));
                });
                
                $(".btn-nova-classe").click(function(){
                    var obj = $(this);
                    $.fancybox({
                        'autoSize': true,
                        'openEffect': 'fade',
                        'closeEffect': 'fade',
                        'modal': true,
                        'href': '#divNovaClasse'
                    });
                });
                
            });
            
            function novaLinha(obj)
            {
                var context = $(obj).attr("data-context");
                var cont = parseInt($("#"+ context + " #contador").val()) + 1;
                var linha = context == "frmSaveValidacao" ?
                                trTemplate.replace(/#id#/gi, cont) :
                                trTemplateClasse.replace(/#id#/gi, cont);

                $("#"+ context + " #tbody-validacoes").append(linha);
                $("#"+ context + " #contador").val(cont);
                return cont;
            }
            
            function adicionarClasse()
            {
                if ($("#classe").val().length == 0 || $("#classe").val().length == 0) return;
                var obj = $(".btn-nova-classe").get(0);
                var context = $(obj).attr("data-context");
                var classe = $("#classe").val();
                var atributos = $("#atributos").val().split("\n");
                
                //Nova linha para testar a classe. ex: Classe x = new Classe();
                var cont = novaLinha(obj);
                var newClasse = classe + " x = new " + classe + "();";
                $("#"+ context + " #fonte" + cont).val(newClasse);
                $("#"+ context + " #saida" + cont).val("");
                $("#"+ context + " #mensagemCompilacao" + cont).val("<fmt:message key="label.exercicio.mensagemvalidacao.nomeclasse"/>");
                $("#"+ context + " #mensagem" + cont).val("");

                for (i = 0; i < atributos.length; i++)
                {
                    //Linha para testar o método SET
                    cont = novaLinha(obj);
                    var fonte = newClasse + "\n" + "x.set" + atributos[i] + "(123);";
                    
                    $("#"+ context + " #fonte" + cont).val(fonte);
                    $("#"+ context + " #saida" + cont).val("");
                    $("#"+ context + " #mensagemCompilacao" + cont).val("<fmt:message key="label.exercicio.mensagemvalidacao.metodoset"/>");
                    $("#"+ context + " #mensagem" + cont).val("");
                    
                    //Linha para testar o método GET
                    cont = novaLinha(obj);
                    fonte = fonte + "\n" + "System.out.println(x.get" + atributos[i] + "());";
                    
                    $("#"+ context + " #fonte" + cont).val(fonte);
                    $("#"+ context + " #saida" + cont).val("123");
                    $("#"+ context + " #mensagemCompilacao" + cont).val("<fmt:message key="label.exercicio.mensagemvalidacao.metodoget"/>");
                    $("#"+ context + " #mensagem" + cont).val("<fmt:message key="label.exercicio.mensagemvalidacao.retornoget"/>");
                }
                
                cancelarClasse();
            }
            
            function cancelarClasse()
            {
                $("#atributos").val("");            
                $("#classe").val("");            
                $.fancybox.close(true);
            }
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
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" name="ativo" ${IsAdd != null && IsAdd ? "checked" : exercicio.isAtivo() ? "checked" : ""}>
                            <fmt:message key="label.exercicios.ativo"/>
                        </label>
                    </div>
                    <div class="form-group">
                        <label for="encerramento"><fmt:message key="label.exercicios.detalhamento"/>:</label><br>
                        <label class="radio-inline">
                            <input type="radio" id="rdHTML" name="rdDetalhamento" class="rd-detalhamento" value="html" show-div="editorhtml" checked="checked">
                            <fmt:message key="label.exercicios.utilizareditorhtml"/>
                        </label>
                        <label class="radio-inline">
                            <input type="radio" id="rdPDF" name="rdDetalhamento" class="rd-detalhamento" value="pdf" show-div="uploadpdf" disabled="disabled">
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
                    <button type="button" class="btn btn-default btn-voltar"><fmt:message key="button.voltarlistagem"/></button>
                </form>

            </div>
        </div>
                
        <c:if test="${IsAdd != null && !IsAdd}">

            <h2><fmt:message key="label.exercicios.cadastrarentradassaidas"/></h2>
            
            <form role="form" id="frmSaveValidacao" action="<c:url value='/'/>exercicios/savevalidacao" method="POST">
                <input type="hidden" id="idExercicio" name="idExercicio" value="${exercicio.getId()}">
                <input type="hidden" id="contador" name="contador" value="${exercicio.getValidacoes().size()}">
                
                
                <button type="button" class="btn btn-primary btn-nova-linha" data-context="frmSaveValidacao"><fmt:message key="button.novalinha"/></button>
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
                                    <th><fmt:message key="label.exercicios.mensagempersonalizada"/></th>
                                </tr>
                            </thead>
                            <tbody id="tbody-validacoes">
                                <c:if test="${not empty exercicio.getValidacoes()}">
                                    <c:forEach var="item" varStatus="status" items="${exercicio.getValidacoes()}">
                                        <tr id="trLinha${item.getId()}" class="linha-validacao">
                                            <td>
                                                <div class="btn-group btn-group-xs">
                                                    <input type="hidden" id="idValidacao${status.index + 1}" name="idValidacao${status.index + 1}" value="${item.getId()}">
                                                    <button type="button" class="btn btn-default btn-excluir" data-context="frmSaveValidacao" data-source="1" data-id="${item.getId()}"><fmt:message key="button.excluir"/></button>
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
                <button type="button" class="btn btn-primary btn-nova-linha" data-context="frmSaveValidacao"><fmt:message key="button.novalinha"/></button>
                <button type="submit" class="btn btn-primary"><fmt:message key="button.salvarvalidacao"/></button>
            </form>
            <br><br>
            
            <h2><fmt:message key="label.exercicios.cadastrarclassesteste"/></h2>
            
            <form role="form" id="frmSaveClasseValidacao" action="<c:url value='/'/>exercicios/saveclassevalidacao" method="POST">
                <input type="hidden" id="idExercicio" name="idExercicio" value="${exercicio.getId()}">
                <input type="hidden" id="contador" name="contador" value="${exercicio.getClassesValidacao().size()}">
                
                
                <button type="button" class="btn btn-primary btn-nova-linha" data-context="frmSaveClasseValidacao"><fmt:message key="button.novalinha"/></button>
                <button type="button" class="btn btn-primary btn-nova-classe" data-context="frmSaveClasseValidacao"><fmt:message key="button.novaclasse"/></button>
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
                                    <th><fmt:message key="label.exercicios.classeteste"/></th>
                                    <th><fmt:message key="label.exercicios.saida"/></th>
                                    <th><fmt:message key="label.exercicios.mensagemcompilacao"/></th>
                                    <th><fmt:message key="label.exercicios.mensagempersonalizada"/></th>
                                </tr>
                            </thead>
                            <tbody id="tbody-validacoes">
                                <c:if test="${not empty exercicio.getClassesValidacao()}">
                                    <c:forEach var="item" varStatus="status" items="${exercicio.getClassesValidacao()}">
                                        <tr id="trLinha${item.getId()}" class="linha-validacao">
                                            <td>
                                                <div class="btn-group btn-group-xs">
                                                    <input type="hidden" id="idClasseValidacao${status.index + 1}" name="idClasseValidacao${status.index + 1}" value="${item.getId()}">
                                                    <button type="button" class="btn btn-default btn-excluir" data-context="frmSaveClasseValidacao" data-source="1" data-id="${item.getId()}"><fmt:message key="button.excluir"/></button>
                                                </div>
                                            </td>
                                            <td>
                                                <textarea class="form-control" rows="3" id="fonte${status.index + 1}" name="fonte${status.index + 1}">${item.getFonte()}</textarea>
                                            </td>
                                            <td>
                                                <textarea class="form-control" rows="3" id="saida${status.index + 1}" name="saida${status.index + 1}">${item.getSaida()}</textarea>
                                            </td>
                                            <td>
                                                <textarea class="form-control" rows="3" id="mensagemCompilacao${status.index + 1}" name="mensagemCompilacao${status.index + 1}">${item.getMensagemCompilacao()}</textarea>
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
                <button type="button" class="btn btn-primary btn-nova-linha" data-context="frmSaveClasseValidacao"><fmt:message key="button.novalinha"/></button>
                <button type="button" class="btn btn-primary btn-nova-classe" data-context="frmSaveClasseValidacao"><fmt:message key="button.novaclasse"/></button>
                <button type="submit" class="btn btn-primary"><fmt:message key="button.salvarvalidacao"/></button>
            </form>
            <br><br>
            
            <!--Modal exibida para adicionar novas classes-->
            <div style="display:none;" id="divNovaClasse">
                <input type="text" class="form-control" id="classe" name="classe" placeholder="<fmt:message key="label.exercicio.nomeclasseinforme"/>" maxlength="45">
                <textarea class="form-control" rows="5" id="atributos" name="atributos" placeholder="<fmt:message key="label.exercicio.atributoclasseinforme"/>"></textarea>
                <button type="button" class="btn btn-primary btn-xs" onclick="adicionarClasse();"><fmt:message key="button.adicionarnovaclasse"/></button>
                <button type="button" class="btn btn-default btn-xs" onclick="cancelarClasse();"><fmt:message key="button.cancelar"/></button>
            </div>
            
        </c:if>
                
        

    </jsp:body>
</t:master>