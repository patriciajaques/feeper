<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <style type="text/css" media="screen">
            
        </style>
        
        <script type="text/javascript">
            $(function(){
                $("#menu-lista-exercicios").addClass("active");
                
                $(".btn-codigo-download").click(function(){
                    var id = $("#hdnIdExercicio").val();
                    document.location.href = "<c:url value='/'/>exercicios/download/" + id;
                });
                
            });
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
            var editor = ace.edit("editor");
            $("#editor").show();
            editor.container.style.opacity = "";
            editor.setOptions({
                maxLines: 30,
                mode: "ace/mode/java",
                autoScrollEditorIntoView: true
            });

            <c:if test="${CodigoFonte != null && CodigoFonte.getIdStatus() == 5}">
                //editor.setReadOnly(true);
            </c:if>

            editor.setTheme("ace/theme/eclipse");
            editor.setShowPrintMargin(false);
            editor.getSession().setUseSoftTabs(true);
            editor.renderer.setHScrollBarAlwaysVisible(false);
            editor.focus();
            
            $(function(){
                
                //prepareEditor();
                
                $(".btn-codigo-enviar").click(function(){
                    $("#frmResponder").attr("action", "<c:url value='/'/>exercicios/saveresponder");
                    $("#frmResponder").submit();
                });
                
                $(".btn-salvar-codigo").click(function(){
                    $("#hdnEditor").val(editor.getSession().getValue());
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
                        editor.getSession().setValue(content);
                        
                        if (data[3] == true)
                            $(".btn-excluir-codigo").hide();
                        else
                            $(".btn-excluir-codigo").show();
                            
                        
                        RemoveCarregando();
                    });
                });
                
            });
            
            function adicionarClasse(){
                var fileName = trataString($("#txtNomeClasse").val()) + ".java";
                $("#lblFilename").html(fileName);
                var content = newClassContent.replace(/#@#CLASSE#@#/gi, fileName).replace(/#n#/gi, "\n");
                editor.getSession().setValue(content);
                $("#hdnEditor").val("");
                $("#hdnIdCodigoFonte").val("0");
                $("#hdnPrincipal").val("false");
                $("#hdnNomeCodigoFonte").val($("#lblFilename").html());
                FechaModal();
            }
            
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
            
            var actual_selected_linenumber = 0;
            
            function prepareEditor(){
	    	$(".ace_gutter-cell").each(function(index) {
                    $(this).attr("id", "L" + index);
	    	}).mousedown(function(event) {
                    event.preventDefault ? event.preventDefault() : event.returnValue = false;

                    //$("ol.linenums > li").removeClass("selected");
                    //$("ol.linenums > li:not(.selected)").popover("destroy");

                    var new_selected_linenumber = parseInt($(this).attr("id").replace("L",""));

                    if (actual_selected_linenumber == new_selected_linenumber) {
                        //$(this).popover("destroy");
                        return;
                    }

                    if (event.shiftKey==1) {
                        var initial = actual_selected_linenumber > new_selected_linenumber ? new_selected_linenumber : actual_selected_linenumber;
                        var finish = actual_selected_linenumber > new_selected_linenumber ? actual_selected_linenumber : new_selected_linenumber;
                        for(i = initial; i <= finish; i++)
                            $("#L" + i).addClass("selected");
//                        $("#L" + new_selected_linenumber).popover({
//                            content: $("#popover-content").html(),
//                            placement: "top",
//                            container: "body",
//                            html: true
//                        });
                    } else {
//                        $(this).addClass("selected").popover({
//                            content: $("#popover-content").html(),
//                            placement: "top",
//                            container: "body",
//                            html: true
//                        });
                    }
                    actual_selected_linenumber = new_selected_linenumber;
	    	});
            }
            
            var intervalLooking = setInterval(function(){lookingForEditor()},1000);
            function lookingForEditor()
            {
                if ($(".ace_gutter-cell").length > 0)
                {
                    clearInterval(intervalLooking);
                    prepareEditor();
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
                    <a href="#" class="list-group-item ${status.index == 0 ? "active" : ""} btn-show-code" data-id="${item.getId()}"><span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;${item.getClasse()}</a>
                </c:forEach>
            </c:if>
            <a href="#divNovaClasse" class="list-group-item btn-nova-classe"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<fmt:message key="label.exercicios.novaclasse"/></a>
        </div>
        <form role="form" action="<c:url value='/'/>exercicios/savecodigofonte" id="frmResponder" method="POST">
            <input type="hidden" id="hdnIdExercicio" name="hdnIdExercicio" value="${Exercicio.getId()}" />
            <input type="hidden" id="hdnIdCodigoFonte" name="hdnIdCodigoFonte" value="${ListaCodigoFonte.get(0).getId()}" />
            <input type="hidden" id="hdnPrincipal" name="hdnPrincipal" value="${ListaCodigoFonte.get(0).isPrincipal()}" />
            <input type="hidden" id="hdnNomeCodigoFonte" name="hdnNomeCodigoFonte" value="${ListaCodigoFonte.get(0).getClasse()}" />
            <input type="hidden" id="hdnEditor" name="hdnEditor" />
        </form>

        <h3 id="lblFilename">${ListaCodigoFonte.get(0).getClasse()}</h3>

        <div id="editor" style="display:none;">${ListaCodigoFonte.get(0).getFonte()}</div>

        <button type="button" class="btn btn-primary btn-xs btn-salvar-codigo"><fmt:message key="button.salvar"/></button>
        <button type="button" class="btn btn-primary btn-xs btn-excluir-codigo" style="${ListaCodigoFonte.get(0).isPrincipal() ? "display:none;" : ""}"><fmt:message key="button.excluir"/></button>
        
        <div style="display:none;" id="divNovaClasse">
            <div class="input-group" style="width:400px; margin-bottom:3px">
                <input type="text" class="form-control" id="txtNomeClasse" placeholder="<fmt:message key="label.exercicio.nomeclasseinforme"/>" maxlength="45">
                <span class="input-group-addon">.java</span>
            </div>
            <button type="button" class="btn btn-primary" onclick="adicionarClasse();"><fmt:message key="button.adicionar"/></button>
            <button type="button" class="btn btn-default" onclick="cancelarClasse();"><fmt:message key="button.cancelar"/></button>
        </div>
        
    </jsp:body>
        
</t:master>