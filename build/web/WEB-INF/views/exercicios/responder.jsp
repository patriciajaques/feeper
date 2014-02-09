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
                
                $('#tab-cabecalho a').click(function (e) {
                    e.preventDefault();
                    $(this).tab('show');
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
            <a href="#" class="list-group-item">
                <span class="glyphicon glyphicon-star-empty"></span>&nbsp;&nbsp;<fmt:message key="menu.codigo.favorito"/>
            </a>
        </div>
    </jsp:attribute>
        
    <jsp:attribute name="footer">
        
        <script src="<c:url value='/resources/ace/ace.js'/>" type="text/javascript"></script>
        <script type="text/javascript">
            $(function(){
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
                
                $(".btn-codigo-enviar").click(function(){
                    $("#hdnEditor").val(editor.getSession().getValue());
                    $("#frmResponder").submit();
                });
                
                $(".btn-codigo-download").click(function(){
                    var id = $("#hdnIdExercicio").val();
                    document.location.href = "<c:url value='/'/>exercicios/download/" + id;
                });
            });
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
        
        <h3><fmt:message key="label.exercicios.editesuaresposta"/></h3>
        
        <form role="form" action="<c:url value='/'/>exercicios/saveresponder" id="frmResponder" method="POST">
            <input type="hidden" id="hdnIdExercicio" name="hdnIdExercicio" value="${Exercicio.getId()}" />
            <input type="hidden" id="hdnEditor" name="hdnEditor" />
        </form>
        
        <c:choose>
            <c:when test="${CodigoFonte != null}">
                <div id="editor" style="display:none;">${CodigoFonte.getFonte()}</div>
            </c:when>
            <c:otherwise>
                <div id="editor" style="display:none;">/* package qualquer; // Não coloque nome no package */

import java.util.*;
import java.lang.*;
import java.io.*;

/* O nome da classe deve ser "Solution" */
class Solution
{
    public static void main (String[] args) throws java.lang.Exception
    {
        // Coloque aqui o seu código
    }
}
</div>
            </c:otherwise>
        </c:choose>
                
        <c:if test="${CodigoFonte != null && CodigoFonte.getIdStatus() == 5}">
            <div class="alert alert-info"><fmt:message key="label.exercicios.status.aguardando"/></div>
        </c:if>
                
        <c:if test="${CodigoFonteResultado != null}">
            <c:choose>
                <c:when test="${CodigoFonteResultado.getIdStatus() == 1}">
                    <div class="alert alert-danger"><fmt:message key="label.exercicios.status.errocompilacao"/><br>${CodigoFonteResultado.getMensagem()}</div>
                </c:when>
                <c:when test="${CodigoFonteResultado.getIdStatus() == 2}">
                    <div class="alert alert-warning"><fmt:message key="label.exercicios.status.errosaidainvalida"/></div>
                </c:when>
                <c:when test="${CodigoFonteResultado.getIdStatus() == 3}">
                    <div class="alert alert-warning"><fmt:message key="label.exercicios.status.errotempolimite"/></div>
                </c:when>
                <c:when test="${CodigoFonteResultado.getIdStatus() == 4}">
                    <div class="alert alert-success"><fmt:message key="label.exercicios.status.resolvido"/></div>
                </c:when>
            </c:choose>
        </c:if>
                    
        <small><fmt:message key="label.exercicios.dataultimaresposta"/> <fmt:formatDate value="${CodigoFonte.getDataAlteracao()}" pattern="dd/MM/yyyy HH:mm" /></small>
        
    </jsp:body>
        
</t:master>