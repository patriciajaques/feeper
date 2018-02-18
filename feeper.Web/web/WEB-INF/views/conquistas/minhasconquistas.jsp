<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.colegas"/></jsp:attribute>
    <jsp:attribute name="header">
        
       
        
    </jsp:attribute>
    <jsp:body>

        <c:if test="${UsuarioLogado.isGamificado()}">
            <div class="row">
                <c:if test="${not empty medalhas}">
                        <c:forEach var="item" varStatus="status" items="${medalhas}">
                            <div div class="col-lg-3 d-flex align-items-stretch">
                                <img class="card-img-top" src="<c:url value='/resources/img/medalhas/${item.getIdMedalha()}/${item.getNivel()}.png'/>" alt="Card image cap">
                                <div class="card-body">
                                  <h5 class="card-title">${item.getMedalha().getNome()}</h5>
                                  <p class="card-text">${item.getMedalha().getDescricao()}</p>
                                  <a href="#" class="btn btn-primary">Go somewhere</a>
                                </div>
                             </div>
                    </c:forEach>
                </c:if>
            </div>
        </c:if>

       
            
    </jsp:body>
</t:master>