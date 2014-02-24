<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
                
                $("#panel-markedquestions button.close").click(function() {
                    $("#panel-markedquestions").hide("slide", "fast");
	    	});
                
                var ajaxFormOptions = { 
                    target: '#pnlMensagens',
                    clearForm: true,
                    beforeSubmit: MostraCarregando,
                    success: function() {
                        RemoveCarregando();
                    }
                }; 
                $('#frmQuestao').ajaxForm(ajaxFormOptions);
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
                <span class="glyphicon glyphicon-send"></span>&nbsp;&nbsp;<fmt:message key="menu.codigo.enviar"/>
            </a>
            <!--a href="#" class="list-group-item">
                <span class="glyphicon glyphicon-ok"></span>&nbsp;&nbsp;<fmt:message key="menu.codigo.validar"/>
            </a-->
            <a href="#" class="list-group-item btn-codigo-download">
                <span class="glyphicon glyphicon-save"></span>&nbsp;&nbsp;<fmt:message key="menu.codigo.baixar"/>
            </a>
            <!--a href="#" class="list-group-item">
                <span class="glyphicon glyphicon-trash"></span>&nbsp;&nbsp;<fmt:message key="menu.codigo.excluir"/>
            </a-->
            <a href="#" class="list-group-item">
                <span class="glyphicon glyphicon-eye-close"></span>&nbsp;&nbsp;<fmt:message key="menu.codigo.ocultarcomentarios"/>
            </a>
            <a href="#" class="list-group-item">
                <span class="glyphicon glyphicon-share-alt"></span>&nbsp;&nbsp;<fmt:message key="menu.codigo.compartilhar"/>
            </a>
            <a href="#" class="list-group-item btn-codigo-favorito">
                <span class="glyphicon glyphicon-star-empty"></span>&nbsp;&nbsp;<fmt:message key="menu.codigo.favorito"/>
            </a>
        </div>
    </jsp:attribute>
        
    <jsp:attribute name="footer">
        
        <script src="<c:url value='/resources/ace/ace.js'/>" type="text/javascript"></script>
        <script type="text/javascript">
            var newClassContent = "${CodigoFontePadraoClasse}";
            var editor;
            var arrLinhasDuvida = [];
            
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
                        CreateEditor(content, data[0] != "0");
                        
                        if (data[4].length > 0)
                            lookingForMarkedLines(data[4]);
                        
                        ControlaBotoes();
                        RemoveCarregando();
                    });
                });
                
                $(".btn-registrar-duvida").click(function(){
                    var row = editor.getSelectionRange().start.row + 1;
                    exibeQuadroDuvida(row);
                });
                
            });
            
            function CreateEditor(source, readOnly)
            {
                try {
                    editor.destroy();
                    $("#editor").remove();
                } catch (e) {}
                
                var newEditorElement = $('<div id="editor">' + source + '</div>');
                $("#panelEditor").append(newEditorElement);
                editor = ace.edit("editor");
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
            }
            
            function exibeQuadroDuvida(row)
            {
                $("#title-linenumber").html(row);
                $("#panel-markedquestions").show("slide", "fast");

                MostraCarregando();

                var idCodigoFonte = $("#hdnIdCodigoFonte").val();
                $("#hdnQuestaoIdCodigoFonte").val(idCodigoFonte);
                $("#hdnQuestaoLinha").val(row);

                $("#panel-markedquestions .panel-body").load("<c:url value='/'/>exercicios/showquestion/${Exercicio.getId()}/" + idCodigoFonte + "/" + row, function(){
                    RemoveCarregando();
                });
            }
            
            function adicionarClasse(){
                var fileName = trataString($("#txtNomeClasse").val()) + ".java";
                $("#lblFilename").html(fileName).show();
                var content = newClassContent.replace(/#@#CLASSE#@#/gi, fileName).replace(/#n#/gi, "\n");
                
                CreateEditor(content, false);
                
                $("#hdnEditor").val("");
                $("#hdnIdCodigoFonte").val("0");
                $("#hdnPrincipal").val("false");
                $("#hdnNomeCodigoFonte").val($("#lblFilename").html());
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
                    $(".ace_gutter-cell").each(function(index) {
                        $(this).attr("id", "L" + index);
                    });
            }
            
            var intervalLookingMarkedLines;
            function lookingForMarkedLines(markQuestionLines)
            {
                clearInterval(intervalLookingMarkedLines);
                var qtdeLinhasHtml = $(".ace_gutter-cell[id]").length;
                var qtdeLinhasEditor = editor.session.getLength();
                
                console.log("html: " + qtdeLinhasHtml);
                console.log("editor: " + qtdeLinhasEditor);
                
                if (qtdeLinhasHtml != qtdeLinhasEditor && markQuestionLines)
                {
                    intervalLookingMarkedLines = setInterval(function(){lookingForMarkedLines(markQuestionLines)},500);
                }
                else
                {
                    for(var i = 0; i < markQuestionLines.length; i++)
                    {
                        console.log("#L" + (markQuestionLines[i] - 1));
                        $("#L" + (markQuestionLines[i] - 1)).addClass("line-question").click(function(){
                            var row = parseInt($(this).attr("id").replace("L","")) + 1;                    
                            exibeQuadroDuvida(row);
                        });
                    }
                }
            }
            
            function ControlaBotoes()
            {
                var idCodigoFonte = $("#hdnIdCodigoFonte").val();
                var principal = $("#hdnPrincipal").val();
                var editorReadOnly = editor == null ? true : editor.getReadOnly();
                
                if (editor == null)
                {
                    $(".btn-registrar-duvida, .btn-registrar-anotacao, .btn-salvar-codigo, .btn-excluir-codigo").hide();
                }
                else
                {
                    $(".btn-registrar-duvida, .btn-registrar-anotacao, .btn-salvar-codigo, .btn-excluir-codigo").slideDown("fast");
                }
                if (editorReadOnly)
                {
                    $(".btn-salvar-codigo").prop("disabled", "disabled");
                }
                else
                {
                    $(".btn-salvar-codigo").prop("disabled", "");
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
        
        <h2>${TurmaSelecionada.getNome()}</h2>
        
        <h3>${Exercicio.getNome()}</h3>
        
        <div class="panel panel-default">
            <div class="panel-body">
                ${Exercicio.getDescricaoHtml()}
            </div>
        </div>
            
        <c:if test="${Resposta != null}">
            <c:choose>
                <c:when test="${Resposta.getIdStatus() == 1}">
                    <div class="alert alert-danger"><fmt:message key="label.exercicios.status.errocompilacao"/><br>${Resposta.getMensagem()}</div>
                </c:when>
                <c:when test="${Resposta.getIdStatus() == 2}">
                    <div class="alert alert-warning"><fmt:message key="label.exercicios.status.errosaidainvalida"/></div>
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
            
        <h3><fmt:message key="label.exercicios.classes"/></h3>
        <div class="list-group">
            <c:if test="${not empty ListaCodigoFonte}">
                <c:forEach var="item" varStatus="status" items="${ListaCodigoFonte}">
                    <a href="#" class="list-group-item btn-show-code" data-id="${item.getId()}"><span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;${item.getClasse()}</a>
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

        <button type="button" class="btn btn-primary btn-salvar-codigo">
            <span class="glyphicon glyphicon-save"></span> <fmt:message key="button.salvar"/>
        </button>
        <button type="button" class="btn btn-primary btn-excluir-codigo">
            <span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.excluir"/>
        </button>
        <button type="button" class="btn btn-primary btn-registrar-duvida">
            <span class="glyphicon glyphicon-bullhorn"></span> <fmt:message key="button.registrarduvida"/>
        </button>
        <button type="button" class="btn btn-primary btn-registrar-anotacao">
            <span class="glyphicon glyphicon-comment"></span> <fmt:message key="button.registraranotacao"/>
        </button>
        
        <!--Modal exibida para adicionar novas classes-->
        <div style="display:none;" id="divNovaClasse">
            <div class="input-group" style="width:400px; margin-bottom:3px">
                <input type="text" class="form-control" id="txtNomeClasse" placeholder="<fmt:message key="label.exercicio.nomeclasseinforme"/>" maxlength="45">
                <span class="input-group-addon">.java</span>
            </div>
            <button type="button" class="btn btn-primary" onclick="adicionarClasse();"><fmt:message key="button.adicionar"/></button>
            <button type="button" class="btn btn-default" onclick="cancelarClasse();"><fmt:message key="button.cancelar"/></button>
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
        
    </jsp:body>
        
</t:master>