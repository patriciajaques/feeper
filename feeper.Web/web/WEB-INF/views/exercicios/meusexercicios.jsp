<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
//listaExercicios[6]
//0 - ID
//1 - Nome
//2 - Descricao
//3 - DescricaoHtml
//4 - Turma
//5 - DataUltimaAlteracao
%>
<t:master>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-lista-exercicios").addClass("active");
                
                $(".btn-responder").click(function(){
                    var id = $(this).attr("data-id");
                    if (id === undefined) return;
                    document.location.href = "<c:url value='/'/>exercicios/responder/" + id;
                });
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2>${TurmaSelecionada.getNome()} - <fmt:message key="label.exercicios"/></h2>
        
        <form class="form-inline" role="form" method="POST" id="frm-paged">
            <%@include file="/WEB-INF/jspf/paginador_campos.jspf" %>
        </form>
        
        <%@include file="/WEB-INF/jspf/paginador.jspf" %>
        
        <c:if test="${not empty listaExercicios}">
            <c:forEach var="item" varStatus="status" items="${listaExercicios}">
                <blockquote>
                    <p><b>${item[1]}:</b> ${item[3]}</p>
                    <small class="pull-left">${item[4]}</small>
                    <small class="pull-right"><fmt:message key="label.exercicios.dataultimaresposta"/> <fmt:formatDate value="${item[5]}" pattern="dd/MM/yyyy HH:mm" /></small>
                    <br>
                    <p><button type="button" class="btn btn-primary btn-xs btn-responder" data-id="${item[0]}"><fmt:message key="button.responder"/></button></p>
                </blockquote>
            </c:forEach>
        </c:if>
        
        <%@include file="/WEB-INF/jspf/paginador.jspf" %>
            
    </jsp:body>
</t:master>