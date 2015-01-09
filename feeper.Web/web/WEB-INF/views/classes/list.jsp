<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.classesfavoritas"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-meus-classes-favoritas").addClass("active");
                
                $(".btn-remover-favorita").click(function(){
                    var id = $(this).attr("data-id");
                    var idExercicio = $(this).attr("data-idexercicio");
                    if (id === undefined || idExercicio === undefined) return;
                    
                    MostraCarregando();
                    $.get("<c:url value='/'/>classes/savefavorite/"+ idExercicio +"/"+ id, function(data){
                        RemoveCarregando();
                        if (data != "erro")
                            $("#panelClasse"+ id +"_"+ idExercicio).slideUp("fast").remove();
                    });
                });
                
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.classesfavoritas"/></h2>
        
        <c:if test="${not empty MSG_SUCESSO}">
            <div class="alert alert-success">${MSG_SUCESSO}</div>
        </c:if>
        <c:if test="${not empty MSG_ERRO}">
            <div class="alert alert-danger">${MSG_ERRO}</div>
        </c:if>
        
        <%@include file="/WEB-INF/jspf/paginador.jspf" %>
        
        <c:if test="${not empty listaClasses}">
            <c:forEach var="item" varStatus="status" items="${listaClasses}">
                <div id="panelClasse${item.getId()}_${item.getIdExercicio()}" style="margin-bottom:25px;">
                    <h4><c:out value="${item.getNomeClasse()}"/></h4>
<pre class="pre-scrollable">
<c:out value="${item.getCodigo()}"/>
</pre>
                    <button type="button" class="btn btn-primary btn-xs btn-remover-favorita" data-id="${item.getId()}" data-idexercicio="${item.getIdExercicio()}">
                        <span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.removerfavorita"/>
                    </button>
                </div>
            </c:forEach>
        </c:if>
                
        <%@include file="/WEB-INF/jspf/paginador.jspf" %>
            
    </jsp:body>
</t:master>