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
                
                $(".btn-codigo-enviar").click(function(){
                    
                });
                
                $(".btn-salvar-codigo").click(function(){
                    $("#hdnEditor").val(editor.getSession().getValue());
                    $("#frmResponder").submit();
                });
                
                $(".btn-nova-classe").fancybox({
                    'autoSize': true,
                    'openEffect': 'fade',
                    'closeEffect': 'fade',
                    'modal': true
                });
                
                $(".btn-show-code").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                });
                
            });
            
            function adicionarClasse(){
                var fileName = $("#txtNomeClasse").val() + ".java";
                $("#lblFilename").html(fileName);
                var content = newClassContent.replace(/#@#CLASSE#@#/gi, fileName).replace(/#n#/gi, "\n");
                editor.getSession().setValue(content);
                $("#hdnEditor, #hdnIdCodigoFonte").val("");
                FechaModal();
            }
            
            function cancelarClasse(){
                $("#txtNomeClasse").val("");            
                $.fancybox.close(true);
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
        
        <h3><fmt:message key="label.exercicios.classes"/><small>Teste de texto pequeno descritivo</small></h3>
        
        <div class="list-group">
            <c:if test="${not empty ListaCodigoFonte}">
                <c:forEach var="item" varStatus="status" items="${ListaCodigoFonte}">
                    <a href="#" class="list-group-item ${item.isPrincipal() ? "active" : ""} btn-show-code" data-id="${item.getId()}"><span class="glyphicon glyphicon-file"></span>&nbsp;&nbsp;${item.getClasse()}</a>
                </c:forEach>
            </c:if>
            <a href="#divNovaClasse" class="list-group-item btn-nova-classe"><span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;<fmt:message key="label.exercicios.novaclasse"/></a>
        </div>
        
        <form role="form" action="<c:url value='/'/>exercicios/savecodigofonte" id="frmResponder" method="POST">
            <input type="hidden" id="hdnIdExercicio" name="hdnIdExercicio" value="${Exercicio.getId()}" />
            <input type="hidden" id="hdnIdCodigoFonte" name="hdnIdCodigoFonte" />
            <input type="hidden" id="hdnEditor" name="hdnEditor" />
        </form>
            
        <h3 id="lblFilename">${ListaCodigoFonte.get(0).getClasse()}</h3>
        
        <div id="editor" style="display:none;">${ListaCodigoFonte.get(0).getFonte()}</div>
        
        <button type="button" class="btn btn-primary btn-salvar-codigo"><fmt:message key="button.salvar"/></button>
        
        <div style="display:none;" id="divNovaClasse">
            <div class="input-group" style="width:400px; margin-bottom:3px">
                <input type="text" class="form-control" id="txtNomeClasse" placeholder="<fmt:message key="label.exercicio.nomeclasseinforme"/>">
                <span class="input-group-addon">.java</span>
            </div>
            <button type="button" class="btn btn-primary" onclick="adicionarClasse();"><fmt:message key="button.salvar"/></button>
            <button type="button" class="btn btn-default" onclick="cancelarClasse();"><fmt:message key="button.cancelar"/></button>
        </div>
        
    </jsp:body>
        
</t:master>