<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master.modal>
    <jsp:attribute name="title"><fmt:message key="title.classe"/></jsp:attribute>
    <jsp:attribute name="header"></jsp:attribute>
    <jsp:body>
        
        <h3 id="lblFilename"><c:out value="${classe.getNomeClasse()}"/></h3>
        <div id="editor"><c:out value="${classe.getCodigo()}"/></div>
        
        <button type="button" class="btn btn-default btn-registrar-duvida">
            <c:choose>
                <c:when test="${UsuarioLogado.getIdPerfil() == 2}">
                    <span class="glyphicon glyphicon-comment"></span> <fmt:message key="button.registrarcomentario"/>
                </c:when>
                <c:when test="${UsuarioLogado.getIdPerfil() == 3}">
                    <span class="glyphicon glyphicon-comment"></span> <fmt:message key="button.registrarduvida"/>
                </c:when>
            </c:choose>
        </button>
        
        <button type="button" class="btn btn-default btn-fechar"><fmt:message key="button.fechar"/></button>
        
        <!--Modal exibida para adicionar perguntas-->
        <div class="panel panel-success shadow" id="panel-markedquestions" style="width:400px; display:none; margin-top: 10px;">
            <div class="panel-heading">
                <h3 class="panel-title"><fmt:message key="label.exercicio.marcacoeslinha"/> #<span id="title-linenumber"></span>
                    <button type="button" class="close pull-right" aria-hidden="true">&times;</button></h3>
            </div>
            <div class="panel-body" id="pnlMensagens" style="height: 300px; overflow-y: auto">...</div>
            <div class="panel-footer">
                <form role="form" action="<c:url value='/'/>classes/saveversionquestion" id="frmQuestao" method="POST" enctype="multipart/form-data">
                    <input type="hidden" id="hdnQuestaoIdExercicioSolucaoClasse" name="hdnQuestaoIdExercicioSolucaoClasse">
                    <input type="hidden" id="hdnQuestaoLinha" name="hdnQuestaoLinha">
                    <input type="hidden" id="hdnQuestaoIdExercicio" name="hdnQuestaoIdExercicio">
                    <textarea class="form-control input-sm" rows="3" name="questaoExercicioSolucaoClasse"></textarea>
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
            <form role="form" action="<c:url value='/'/>classes/saveversionannotation" id="frmAnotacao" method="POST">
            <div class="panel-body" id="pnlAnotacoes" style="height: 300px; overflow-y: auto">
                <textarea class="form-control" rows="12" id="anotacaoExercicioSolucaoClasse" name="anotacaoExercicioSolucaoClasse"></textarea>
            </div>
            <div class="panel-footer">
                <input type="hidden" id="hdnAnotacaoIdExercicioSolucaoClasse" name="hdnAnotacaoIdExercicioSolucaoClasse">
                <input type="hidden" id="hdnAnotacaoLinha" name="hdnAnotacaoLinha">
                <input type="hidden" id="hdnAnotacaoIdExercicio" name="hdnAnotacaoIdExercicio">
                <button class="btn btn-success btn-xs" type="submit"><span class="glyphicon glyphicon-save"></span> <fmt:message key="button.salvar"/></button>
                <button class="btn btn-success btn-xs"><span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.excluir"/></button>
            </div>
            </form>
  	</div>
        
        <script src="<c:url value='/resources/ace/ace.js'/>" type="text/javascript"></script>
        <script type="text/javascript">
            var editor;
            var questions = ${questions};
            var comments = [];
            var readOnly = true;
            
            $(function(){
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
                
                $("#panel-markedquestions button.close").click(function() {
                    $("#panel-markedquestions").hide("slide", "fast");
	    	});
                
                $("#panel-markedannotations button.close").click(function() {
                    $("#panel-markedannotations").hide("slide", "fast");
	    	});
                
                $(".btn-fechar").click(function(){
                    self.parent.FechaModal();
                });
                
                $(".btn-registrar-duvida").click(function(){
                    var row = editor.getSelectionRange().start.row;
                    exibeQuadroDuvida(row);
                });
                
                var ajaxFormOptions = { 
                    target: '#pnlMensagens',
                    clearForm: true,
                    beforeSubmit: function() {
                        $.fancybox.showLoading();
                    },
                    success: function() {
                        $.fancybox.hideLoading();                
                        $("#panel-markedquestions .panel-body").animate({ scrollTop: $("#panel-markedquestions .panel-body")[0].scrollHeight}, 1000);
                    }
                }; 
                $('#frmQuestao').ajaxForm(ajaxFormOptions);
                
                var ajaxFormOptions2 = { 
                    beforeSubmit: function() {
                        $.fancybox.showLoading();
                    },
                    success: function(data) {
                        $.fancybox.hideLoading();
                        $("#anotacaoExercicioSolucaoClasse").val(data[1]);
                        $("#panel-markedannotations .panel-body").animate({ scrollTop: $("#panel-markedannotations .panel-body")[0].scrollHeight}, 1000);
                    }
                };
                $('#frmAnotacao').ajaxForm(ajaxFormOptions2);
            });
            
            function exibeQuadroDuvida(row)
            {
                row++;
                $("#panel-markedquestions #title-linenumber").html(row);
                $("#panel-markedquestions").show("slide", "fast");
                $('html, body').animate({ scrollTop: $("#panel-markedquestions").offset().top }, 1000);

                $("#hdnQuestaoIdExercicioSolucaoClasse").val(${idExercicioSolucaoClasse});
                $("#hdnQuestaoIdExercicio").val(${idExercicio});
                $("#hdnQuestaoLinha").val(row);

                $("#panel-markedquestions .panel-body").load("<c:url value='/'/>classes/showversionquestion/${idExercicioSolucaoClasse}/" + row, function(){
                    $("#panel-markedquestions .panel-body").animate({ scrollTop: $("#panel-markedquestions .panel-body")[0].scrollHeight}, 1000);
                });
            }
            
            function exibeQuadroAnotacao(row)
            {
                row++;
                $("#panel-markedannotations #title-linenumber").html(row);
                $("#panel-markedannotations").show("slide", "fast");

                $("#hdnAnotacaoIdExercicioSolucaoClasse").val(${idExercicioSolucaoClasse});
                $("#hdnAnotacaoIdExercicio").val(${idExercicio});
                $("#hdnAnotacaoLinha").val(row);

                $.get("<c:url value='/'/>classes/showversionannotation/${idExercicioSolucaoClasse}/" + row, function(data){
                    $("#anotacaoeExercicioSolucaoClasse").val(data[1]).focus();
                    $("#panel-markedannotations .panel-body").animate({ scrollTop: $("#panel-markedannotations .panel-body")[0].scrollHeight}, 1000);
                });
            }
        </script>
    </jsp:body>
</t:master.modal>