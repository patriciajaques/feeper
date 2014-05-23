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
                color: #f2ebad;
            }
            .line-question {
                background-color: #5cb85c;
                color: #ffffff;
                cursor: pointer;
            }
            
            .ace_gutter-cell.ace_breakpoint{ 
                border-radius: 20px 0px 0px 20px; 
                box-shadow: 0px 0px 1px 1px red inset; 
            } 
            
        </style>
        
        <script type="text/javascript">
            $(function(){
                $("#menu-lista-exercicios").addClass("active");
                
                $(".btn-codigo-download").click(function(){
                    var id = $("#hdnIdExercicio").val();
                    document.location.href = "<c:url value='/'/>exercicios/download/" + id;
                });
                
                $(".btn-codigo-enviar").click(function(){
                    $("#frmResponder").attr("action", "<c:url value='/'/>exercicios/saveresponder");
                    $("#frmResponder").submit();
                });
                
                $(".btn-excluir-codigo").click(function(){
                    if (!confirm("<fmt:message key="label.confirmaexclusao"/>")) return;
                    var id = $("#hdnIdCodigoFonte").val();
                    document.location.href = "<c:url value='/'/>exercicios/deletecode/${Exercicio.getId()}/" + id;
                });
                
                $(".btn-nova-classe").fancybox({
                    'autoSize': true,
                    'openEffect': 'fade',
                    'closeEffect': 'fade',
                    'modal': true
                });
                
                $('#file_upload').uploadify({
                    'swf'           : '<c:url value='/resources/uploadify/uploadify.swf'/>',
                    'uploader'      : '<c:url value='/'/>exercicios/uploadclass',
                    'fileTypeDesc'  : 'Arquivos JAVA',
                    'fileTypeExts'  : '*.java',
                    'fileSizeLimit' : '500KB',
                    'buttonText'    : '<fmt:message key="button.adicionarclasseexistente"/>',
                    'multi'         : false,
                    'fileObjName'   : 'filedata',
                    'checkExisting' : false,
                    'width'         : 154,
                    'height'        : 22,
                    'removeCompleted' : false,
                    'queueID'       : 'fileQueue',
                    'itemTemplate'  : '<div style="margin-top:40px;" id="\${fileID}"><div class="uploadify-progress"><div class="uploadify-progress-bar"><!--Progress Bar--></div></div></div>',
                    'onUploadSuccess' : function(file, data, response) {
                        $("#fileQueue").html("");
                        adicionarClasse(data);
                    },
                    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
                    }
                });
                
                $("#panel-markedquestions button.close").click(function() {
                    $("#panel-markedquestions").hide("slide", "fast");
	    	});
                
                $("#panel-markedannotations button.close").click(function() {
                    $("#panel-markedannotations").hide("slide", "fast");
	    	});
                
                var ajaxFormOptions = { 
                    target: '#pnlMensagens',
                    clearForm: true,
                    beforeSubmit: MostraCarregando,
                    success: function() {
                        RemoveCarregando();
                        $("#panel-markedquestions .panel-body").animate({ scrollTop: $("#panel-markedquestions .panel-body")[0].scrollHeight}, 1000);
                    }
                }; 
                $('#frmQuestao').ajaxForm(ajaxFormOptions);
                
                var ajaxFormOptions2 = { 
                    beforeSubmit: MostraCarregando,
                    success: function(data) {
                        $("#anotacaoCodigoFonte").val(data[1]);
                        RemoveCarregando();
                        $("#panel-markedannotations .panel-body").animate({ scrollTop: $("#panel-markedquestions .panel-body")[0].scrollHeight}, 1000);
                    }
                }; 
                $('#frmAnotacao').ajaxForm(ajaxFormOptions2);
                
                <c:if test="${idRespostaEncoded != null}">
                    $.get("<c:url value='/'/>OnlineJudge?r=${idRespostaEncoded}");
                </c:if>
                
            });
            
            function cancelarClasse(){
                $("#txtNomeClasse").val("");            
                $.fancybox.close(true);
            }
            
            function trataString(valor)
            {
                $.ajax({
                    url: "<c:url value='/'/>tratastring/" + valor,
                    cache: false,
                    async: false
                })
                .done(function( data ) {
                    valor = data;
                });
                return valor;
            }
        </script>
        
    </jsp:attribute>
        
    <jsp:attribute name="rightmenu">
        <h4><fmt:message key="label.exercicios.acoesexercicio"/></h4>
        <div class="list-group">
            <a href="#" class="list-group-item btn-codigo-enviar">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;&nbsp;<fmt:message key="menu.codigo.enviar"/>
            </a>
            <a href="#" class="list-group-item btn-codigo-download">
                <span class="glyphicon glyphicon-save"></span>&nbsp;&nbsp;<fmt:message key="menu.codigo.baixar"/>
            </a>
        </div>
    </jsp:attribute>
        
    <jsp:attribute name="footer">
        
        <script src="<c:url value='/resources/ace/ace.js'/>" type="text/javascript"></script>
        <script type="text/javascript">
            var newClassContent = "${CodigoFontePadraoClasse}";
            var editor;
            
            $(function(){
                
                ControlaBotoes();
                
                $(".btn-salvar-codigo").click(function(){
                    $("#hdnEditor").val(editor.getSession().getValue());
                    $("#frmResponder").submit();
                });
                
                $(".btn-show-code").click(function(event){
                    event.preventDefault ? event.preventDefault() : event.returnValue = false;
                    
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    
                    $(".btn-show-code").removeClass("active");
                    $(this).addClass("active");
                    
                    MostraCarregando();
                    
                    $.get("<c:url value='/'/>exercicios/showcode/${Exercicio.getId()}/" + id, function(data){
                        $("#hdnNomeCodigoFonte").val(data[2]);
                        $("#lblFilename").html(data[2]);
                        $("#hdnPrincipal").val(data[3]);
                        $("#hdnIdCodigoFonte").val(data[0]);
                        var content = data[1].replace(/#'#/gi, '"');
                        CreateEditor(content, data[0] != "0", data[4], data[5]);
                        
                        if (data[6])
                            $(".btn-codigo-favorito").html("<span class=\"glyphicon glyphicon-star\"></span> <fmt:message key="button.desmarcarfavorito"/>");
                        else
                            $(".btn-codigo-favorito").html("<span class=\"glyphicon glyphicon-star-empty\"></span> <fmt:message key="button.marcarfavorito"/>");
                        
                        ControlaBotoes();
                        RemoveCarregando();
                        
                        $('html, body').animate({ scrollTop: $("#lblFilename").offset().top }, 1000);
                    });
                });
                
                $(".btn-codigo-favorito").click(function(){
                    var id = $("#hdnIdCodigoFonte").val();
                    $.get("<c:url value='/'/>codigos/savefavorite/${Exercicio.getId()}/" + id, function(data){
                        if (data != "erro" && data == "true")
                            $(".btn-codigo-favorito").html("<span class=\"glyphicon glyphicon-star\"></span> <fmt:message key="button.desmarcarfavorito"/>");
                        else if (data != "erro" && data == "false")
                            $(".btn-codigo-favorito").html("<span class=\"glyphicon glyphicon-star-empty\"></span> <fmt:message key="button.marcarfavorito"/>");
                    });
                });
                
                $(".btn-registrar-duvida").click(function(){
                    var row = editor.getSelectionRange().start.row;
                    exibeQuadroDuvida(row);
                });
                
                $(".btn-registrar-anotacao").click(function(){
                    var row = editor.getSelectionRange().start.row;
                    exibeQuadroAnotacao(row);
                });
                
                $(".btn-habilitar-edicao").click(function(){
                    editor.setReadOnly(false);
                    ControlaBotoes();
                });
                
            });
            
            function CreateEditor(source, readOnly, questions, comments)
            {
                try {
                    editor.destroy();
                    $("#editor").remove();
                } catch (e) {}
                
                $("#panelEditor").append($('<div id="editor"></div>'));
                editor = ace.edit("editor");
                editor.session.setValue(source);
                editor.container.style.opacity = "";
                editor.setOptions({
                    maxLines: 30,
                    mode: "ace/mode/java",
                    autoScrollEditorIntoView: true
                });
                editor.setReadOnly(readOnly);
                editor.setTheme("ace/theme/eclipse");
                editor.setShowPrintMargin(false);
                editor.getSession().setUseSoftTabs(true);
                editor.renderer.setHScrollBarAlwaysVisible(false);
                editor.focus();
                
                editor.on("guttermousedown", function(e){ 
                    var target = e.domEvent.target; 
                    if (target.className.indexOf("ace_gutter-cell") == -1) 
                        return; 
                    if (!editor.isFocused()) 
                        return; 
                    if (e.clientX > 25 + target.getBoundingClientRect().left) 
                        return; 

                    var row = e.getDocumentPosition().row;
                    if (target.className.indexOf("ace_question") != -1)
                        exibeQuadroDuvida(row);
                    if (target.className.indexOf("ace_comment") != -1)
                        exibeQuadroAnotacao(row);
                    e.stop();
                });
                
                if (questions)
                    for(var i = 0; i < questions.length; i++)
                        editor.getSession().addGutterDecoration(questions[i] - 1, "ace_question");
                if (comments)
                    for(var i = 0; i < comments.length; i++)
                        editor.getSession().addGutterDecoration(comments[i] - 1, "ace_comment");
            }
            
            function exibeQuadroDuvida(row)
            {
                row++;
                $("#panel-markedquestions #title-linenumber").html(row);
                $("#panel-markedquestions").show("slide", "fast");

                MostraCarregando();

                var idCodigoFonte = $("#hdnIdCodigoFonte").val();
                $("#hdnQuestaoIdCodigoFonte").val(idCodigoFonte);
                $("#hdnQuestaoLinha").val(row);

                $("#panel-markedquestions .panel-body").load("<c:url value='/'/>exercicios/showquestion/${Exercicio.getId()}/" + idCodigoFonte + "/" + row, function(){
                    RemoveCarregando();
                    $("#panel-markedquestions .panel-body").animate({ scrollTop: $("#panel-markedquestions .panel-body")[0].scrollHeight}, 1000);
                });
            }
            
            function exibeQuadroAnotacao(row)
            {
                row++;
                $("#panel-markedannotations #title-linenumber").html(row);
                $("#panel-markedannotations").show("slide", "fast");

                MostraCarregando();

                var idCodigoFonte = $("#hdnIdCodigoFonte").val();
                $("#hdnAnotacaoIdCodigoFonte").val(idCodigoFonte);
                $("#hdnAnotacaoLinha").val(row);

                $.get("<c:url value='/'/>exercicios/showannotation/${Exercicio.getId()}/" + idCodigoFonte + "/" + row, function(data){
                    $("#anotacaoCodigoFonte").val(data[1]).focus();
                    RemoveCarregando();
                    $("#panel-markedannotations .panel-body").animate({ scrollTop: $("#panel-markedannotations .panel-body")[0].scrollHeight}, 1000);
                });
            }
            
            function adicionarClasse(content){
                
                if (content.length == 0)
                {
                    if ($("#txtNomeClasse").val().length == 0)
                    {
                        $("#txtNomeClasse").parent().addClass("has-error");
                        return;
                    }
                    var fileName = trataString($("#txtNomeClasse").val());
                    content = newClassContent.replace(/#@#CLASSE#@#/gi, fileName).replace(/#n#/gi, "\n");
                }
                else
                {
                    var fileName = content.split("#@#")[0];
                    content = content.replace(fileName + "#@#", "");
                }
                
                CreateEditor(content, false);
                
                $("#hdnEditor").val("");
                $("#hdnIdCodigoFonte").val("0");
                $("#hdnPrincipal").val("false");
                $("#hdnNomeCodigoFonte").val(fileName + ".java");
                $("#txtNomeClasse").val("");
                $("#lblFilename").html(fileName + ".java").show();
                ControlaBotoes();
                FechaModal();
            }
            
            var intervalLookingRows = setInterval(function(){lookingForNewRows()},500);
            function lookingForNewRows()
            {
                if (editor == null) return;
                var qtdeLinhasHtml = $(".ace_gutter-cell[id]").length;
                var qtdeLinhasEditor = editor.session.getLength();
                
                if (qtdeLinhasHtml != qtdeLinhasEditor)
                {
                    $(".ace_gutter-cell").each(function(index) {
                        $(this).attr("id", "L" + index);
                    });
                }
            }
            
            var intervalLookingStatus;
            function lookingForNewStatus()
            {
                $.get("<c:url value='/'/>exercicios/getstatus/${Exercicio.getId()}", function(data){
                    if (data !== undefined && data != 0 && data != 5)
                        document.location.href = "<c:url value='/'/>exercicios/responder/${Exercicio.getId()}";
                });
            }
            
            function ControlaBotoes()
            {
                var idCodigoFonte = $("#hdnIdCodigoFonte").val();
                var principal = $("#hdnPrincipal").val();
                var editorReadOnly = editor == null ? true : editor.getReadOnly();
                
                if (editor == null)
                {
                    $(".btn-registrar-duvida, .btn-registrar-anotacao, .btn-salvar-codigo, .btn-excluir-codigo, .btn-habilitar-edicao, .btn-codigo-favorito").slideUp("fast");
                    return;
                }
                else
                {
                    $(".btn-registrar-duvida, .btn-registrar-anotacao, .btn-salvar-codigo, .btn-excluir-codigo, .btn-habilitar-edicao, .btn-codigo-favorito").slideDown("fast");
                }
                if (editorReadOnly)
                {
                    $(".btn-salvar-codigo").prop("disabled", "disabled");
                    $(".btn-habilitar-edicao, .btn-codigo-favorito").prop("disabled", "");
                    $(".ace_content").css("background-color", "#f3f3f3");
                }
                else
                {
                    $(".btn-salvar-codigo").prop("disabled", "");
                    $(".btn-habilitar-edicao, .btn-codigo-favorito").prop("disabled", "disabled");
                    $(".ace_content").css("background-color", "");
                }
                if (principal == "true")
                {
                    $(".btn-excluir-codigo").prop("disabled", "disabled");
                }
                else
                {
                    $(".btn-excluir-codigo").prop("disabled", "");
                }
                if (idCodigoFonte != "" && idCodigoFonte > 0 && editorReadOnly)
                {
                    $(".btn-registrar-duvida, .btn-registrar-anotacao").prop("disabled", "");
                }
                else
                {
                    $(".btn-registrar-duvida, .btn-registrar-anotacao, .btn-excluir-codigo").prop("disabled", "disabled");
                }
            }
        </script>
        
    </jsp:attribute>
        
    <jsp:body>
        
        <h2><c:out value="${TurmaSelecionada.getNome()}"/></h2>
        
        <h3><c:out value="${Exercicio.getNome()}"/></h3>
        
        <c:if test="${Resposta != null}">
            <c:choose>
                <c:when test="${Resposta.getIdStatus() == 1}">
                    <div class="alert alert-danger"><fmt:message key="label.exercicios.status.errocompilacao"/><br><c:out value="${Resposta.getMensagem()}"/></div>
                </c:when>
                <c:when test="${Resposta.getIdStatus() == 2}">
                    <div class="alert alert-warning"><fmt:message key="label.exercicios.status.errosaidainvalida"/><br><c:out value="${Resposta.getMensagem()}"/></div>
                </c:when>
                <c:when test="${Resposta.getIdStatus() == 3}">
                    <div class="alert alert-warning"><fmt:message key="label.exercicios.status.errotempolimite"/></div>
                </c:when>
                <c:when test="${Resposta.getIdStatus() == 4}">
                    <div class="alert alert-success"><fmt:message key="label.exercicios.status.resolvido"/></div>
                </c:when>
                <c:when test="${Resposta.getIdStatus() == 5}">
                    <div class="alert alert-info"><fmt:message key="label.exercicios.status.aguardando"/></div>
                </c:when>
            </c:choose>
            <small><fmt:message key="label.exercicios.dataultimaresposta"/> <fmt:formatDate value="${Resposta.getDataCadastro()}" pattern="dd/MM/yyyy HH:mm" /></small>
        </c:if>
        <c:if test="${Resposta != null && Resposta.getIdStatus() == 5}">
            <script type="text/javascript">
                intervalLookingStatus = setInterval(function(){lookingForNewStatus()}, 5000);
            </script>
        </c:if>
            
        <div class="panel panel-default">
            <div class="panel-body">
                ${Exercicio.getDescricaoHtml()}
            </div>
        </div>
            
        <h3><fmt:message key="label.exercicios.classes"/></h3>
        <div class="list-group">
            <c:if test="${not empty ListaCodigoFonte}">
                <c:forEach var="item" varStatus="status" items="${ListaCodigoFonte}">
                    <a href="#" class="list-group-item btn-show-code" data-id="${item.getId()}"><span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;<c:out value="${item.getClasse()}"/></a>
                </c:forEach>
            </c:if>
            <a href="#divNovaClasse" class="list-group-item btn-nova-classe"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<fmt:message key="label.exercicios.novaclasse"/></a>
        </div>
        <form role="form" action="<c:url value='/'/>exercicios/savecodigofonte" id="frmResponder" method="POST">
            <input type="hidden" id="hdnIdExercicio" name="hdnIdExercicio" value="${Exercicio.getId()}" />
            <input type="hidden" id="hdnIdCodigoFonte" name="hdnIdCodigoFonte" value="" />
            <input type="hidden" id="hdnPrincipal" name="hdnPrincipal" value="" />
            <input type="hidden" id="hdnNomeCodigoFonte" name="hdnNomeCodigoFonte" value="" />
            <input type="hidden" id="hdnEditor" name="hdnEditor" />
        </form>

        <h3 id="lblFilename"></h3>
        <div id="panelEditor"></div>

        <button type="button" class="btn btn-primary btn-sm btn-salvar-codigo" style="display:none;">
            <span class="glyphicon glyphicon-save"></span> <fmt:message key="button.salvar"/>
        </button>
        <button type="button" class="btn btn-default btn-sm btn-habilitar-edicao" style="display:none;">
            <span class="glyphicon glyphicon-lock"></span> <fmt:message key="button.habilitaredicao"/>
        </button>
        <button type="button" class="btn btn-default btn-sm btn-excluir-codigo" style="display:none;">
            <span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.excluir"/>
        </button>
        <button type="button" class="btn btn-default btn-sm btn-registrar-duvida" style="display:none;">
            <span class="glyphicon glyphicon-comment"></span> <fmt:message key="button.registrarduvida"/>
        </button>
        <button type="button" class="btn btn-default btn-sm btn-registrar-anotacao" style="display:none;">
            <span class="glyphicon glyphicon-eye-open"></span> <fmt:message key="button.registraranotacao"/>
        </button>
        <button type="button" class="btn btn-default btn-sm btn-codigo-favorito" style="display:none;">
            <span class="glyphicon glyphicon-star-empty"></span> <fmt:message key="menu.codigo.favorito"/>
        </button>
        
        <!--Modal exibida para adicionar novas classes-->
        <div style="display:none;" id="divNovaClasse">
            <div class="input-group" style="width:400px; margin-bottom:3px">
                <input type="text" class="form-control" id="txtNomeClasse" placeholder="<fmt:message key="label.exercicio.nomeclasseinforme"/>" maxlength="45">
                <span class="input-group-addon">.java</span>
            </div>
            <div class="pull-left">
                <button type="button" class="btn btn-primary btn-xs" onclick="adicionarClasse('');"><fmt:message key="button.adicionarnovaclasse"/></button>
            </div>
            <div class="pull-left" style="margin-left:3px">
                <input type="file" name="file_upload" id="file_upload" />
            </div>
            <div class="pull-left" style="margin-left:3px">
                <button type="button" class="btn btn-default btn-xs" onclick="cancelarClasse();"><fmt:message key="button.cancelar"/></button>
            </div>
            <div id="fileQueue" style="display:block;"></div>
        </div>
        
        <!--Modal exibida para adicionar perguntas-->
        <div class="panel panel-success shadow" id="panel-markedquestions" style="width:400px; display:none; position:fixed; top:120px; left:0px; z-index:100">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="label.exercicio.marcacoeslinha"/> #<span id="title-linenumber"></span>
                    <button type="button" class="close pull-right" aria-hidden="true">&times;</button></h3>
            </div>
            <div class="panel-body" id="pnlMensagens" style="height: 300px; overflow-y: auto">...</div>
            <div class="panel-footer">
                <form role="form" action="<c:url value='/'/>exercicios/savequestion" id="frmQuestao" method="POST" enctype="multipart/form-data">
                    <input type="hidden" id="hdnQuestaoIdCodigoFonte" name="hdnQuestaoIdCodigoFonte">
                    <input type="hidden" id="hdnQuestaoLinha" name="hdnQuestaoLinha">
                    <input type="hidden" id="hdnQuestaoIdExercicio" name="hdnQuestaoIdExercicio" value="${Exercicio.getId()}">
                    <textarea class="form-control input-sm" rows="3" name="questaoCodigoFonte"></textarea>
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
                <textarea class="form-control" rows="12" id="anotacaoCodigoFonte" name="anotacaoCodigoFonte"></textarea>
            </div>
            <div class="panel-footer">
                <input type="hidden" id="hdnAnotacaoIdCodigoFonte" name="hdnAnotacaoIdCodigoFonte">
                <input type="hidden" id="hdnAnotacaoLinha" name="hdnAnotacaoLinha">
                <input type="hidden" id="hdnAnotacaoIdExercicio" name="hdnAnotacaoIdExercicio" value="${Exercicio.getId()}">
                <button class="btn btn-success btn-xs" type="submit"><span class="glyphicon glyphicon-save"></span> <fmt:message key="button.salvar"/></button>
                <button class="btn btn-success btn-xs"><span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.excluir"/></button>
            </div>
            </form>
  	</div>
        
    </jsp:body>
        
</t:master>