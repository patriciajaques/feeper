<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master.modal>
    <jsp:attribute name="title"><fmt:message key="title.codigofonte"/></jsp:attribute>
    <jsp:attribute name="header"></jsp:attribute>
    <jsp:body>
        
        <h3 id="lblFilename">${CodigoFonte.getClasse()}</h3>
        <div id="editor">${CodigoFonte.getFonte()}</div>
        
        <c:if test="${not isVersion}">
        <button type="button" class="btn btn-default btn-registrar-duvida">
            <span class="glyphicon glyphicon-comment"></span> <fmt:message key="button.registrarduvida"/>
        </button>
        </c:if>
        <button type="button" class="btn btn-default btn-fechar"><fmt:message key="button.fechar"/></button>
        
        <c:if test="${not isVersion}">
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
                    <input type="hidden" id="hdnQuestaoIdExercicio" name="hdnQuestaoIdExercicio" value="${idExercicio}">
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
                <input type="hidden" id="hdnAnotacaoIdExercicio" name="hdnAnotacaoIdExercicio" value="${idExercicio}">
                <button class="btn btn-success btn-xs" type="submit"><span class="glyphicon glyphicon-save"></span> <fmt:message key="button.salvar"/></button>
                <button class="btn btn-success btn-xs"><span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.excluir"/></button>
            </div>
            </form>
  	</div>
        </c:if>
        
        <script src="<c:url value='/resources/ace/ace.js'/>" type="text/javascript"></script>
        <script type="text/javascript">
            var editor;
            <c:if test="${not isVersion}">
            var questions = ${questions};
            var comments = [];
            </c:if>
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
                
                <c:if test="${not isVersion}">
                
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
                
                </c:if>
                
                $(".btn-fechar").click(function(){
                    self.parent.FechaModal();
                });
                
                <c:if test="${not isVersion}">
                $(".btn-registrar-duvida").click(function(){
                    var row = editor.getSelectionRange().start.row;
                    exibeQuadroDuvida(row);
                });
                
                var ajaxFormOptions = { 
                    target: '#pnlMensagens',
                    clearForm: true,
                    success: function() {
                        $("#panel-markedquestions .panel-body").animate({ scrollTop: $("#panel-markedquestions .panel-body")[0].scrollHeight}, 1000);
                    }
                }; 
                $('#frmQuestao').ajaxForm(ajaxFormOptions);
                
                var ajaxFormOptions2 = { 
                    beforeSubmit: MostraCarregando,
                    success: function(data) {
                        $("#anotacaoCodigoFonte").val(data[1]);
                        $("#panel-markedannotations .panel-body").animate({ scrollTop: $("#panel-markedannotations .panel-body")[0].scrollHeight}, 1000);
                    }
                };
                $('#frmAnotacao').ajaxForm(ajaxFormOptions2);
                </c:if>
            });
            
            <c:if test="${not isVersion}">
            function exibeQuadroDuvida(row)
            {
                row++;
                $("#panel-markedquestions #title-linenumber").html(row);
                $("#panel-markedquestions").show("slide", "fast");

                //MostraCarregando();

                $("#hdnQuestaoIdCodigoFonte").val(${idCodigoFonte});
                $("#hdnQuestaoLinha").val(row);

                $("#panel-markedquestions .panel-body").load("<c:url value='/'/>exercicios/showquestion/${idExercicio}/${idCodigoFonte}/" + row, function(){
                    //RemoveCarregando();
                    $("#panel-markedquestions .panel-body").animate({ scrollTop: $("#panel-markedquestions .panel-body")[0].scrollHeight}, 1000);
                });
            }
            
            function exibeQuadroAnotacao(row)
            {
                row++;
                $("#panel-markedannotations #title-linenumber").html(row);
                $("#panel-markedannotations").show("slide", "fast");

                //MostraCarregando();

                $("#hdnAnotacaoIdCodigoFonte").val(${idCodigoFonte});
                $("#hdnAnotacaoLinha").val(row);

                $.get("<c:url value='/'/>exercicios/showannotation/${idExercicio}/${idCodigoFonte}/" + row, function(data){
                    $("#anotacaoCodigoFonte").val(data[1]).focus();
                    //RemoveCarregando();
                    $("#panel-markedannotations .panel-body").animate({ scrollTop: $("#panel-markedannotations .panel-body")[0].scrollHeight}, 1000);
                });
            }
            </c:if>
        </script>
    </jsp:body>
</t:master.modal>