<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.colegas"/></jsp:attribute>
    <jsp:attribute name="header">
        
       
        
    </jsp:attribute>
    <jsp:body>

        <c:if test="${UsuarioLogado.isGamificado() && UsuarioLogado.isElementoMedalha()}">
            <div class="row">
                <c:if test="${not empty medalhas}">
                        <c:forEach var="item" varStatus="status" items="${medalhas}">
                            <div class="col-md-2 img-responsive" style="height:300px;">
                                <img src="<c:url value='/resources/img/medalhas/${item.getIdMedalha()}/${item.getNivel()}.png'/>" alt="Card image cap">
                                <div>
                                  <h4>${item.getMedalha().getNome()}</h4>
                                  <p>${item.getMedalha().getDescricao()}</p>
                                </div>
                             </div>
                    </c:forEach>
                </c:if>
            </div>
        </c:if>

       
            
    </jsp:body>
</t:master>