<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.codigosfavoritos"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-meus-codigos-favoritos").addClass("active");
                
                $(".btn-remover-favorito").click(function(){
                    var id = $(this).attr("data-id");
                    var idExercicio = $(this).attr("data-idexercicio");
                    if (id === undefined || idExercicio === undefined) return;
                    
                    MostraCarregando();
                    $.get("<c:url value='/'/>codigos/savefavorite/"+ idExercicio +"/"+ id, function(data){
                        RemoveCarregando();
                        if (data != "erro")
                            $("#panelCodigoFonte"+ id +"_"+ idExercicio).slideUp("fast").remove();
                    });
                });
                
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.codigosfavoritos"/></h2>
        
        <c:if test="${not empty MSG_SUCESSO}">
            <div class="alert alert-success">${MSG_SUCESSO}</div>
        </c:if>
        <c:if test="${not empty MSG_ERRO}">
            <div class="alert alert-danger">${MSG_ERRO}</div>
        </c:if>
        
        <%@include file="/WEB-INF/jspf/paginador.jspf" %>
        
        <c:if test="${not empty listaCodigo}">
            <c:forEach var="item" varStatus="status" items="${listaCodigo}">
                <div id="panelCodigoFonte${item.getId()}_${item.getIdExercicio()}" style="margin-bottom:25px;">
                    <h4>${item.getExercicio().getNome()} > ${item.getClasse()}</h4>
<pre class="pre-scrollable">
${item.getFonte()}
</pre>
                    <button type="button" class="btn btn-primary btn-xs btn-remover-favorito" data-id="${item.getId()}" data-idexercicio="${item.getIdExercicio()}">
                        <span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.removerfavorito"/>
                    </button>
                </div>
            </c:forEach>
        </c:if>
                
        <%@include file="/WEB-INF/jspf/paginador.jspf" %>
            
    </jsp:body>
</t:master>