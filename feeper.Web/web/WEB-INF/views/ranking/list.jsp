<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.colegas"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-colegas").addClass("active");
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <c:if test="${UsuarioLogado.isGamificado()}">

            <h2><c:out value="${TurmaSelecionada.getNome()}"/> - <fmt:message key="label.colegas"/></h2>

            <br>
                    <br>
            <h4><fmt:message key="label.ranking.turma"/></h4>
            <ul class="mosaico">
                <c:if test="${not empty listaTurma}">
                    <c:forEach var="item" varStatus="status" items="${rankingTurma}">
                        <li>
                            <span class="quebrar-linha" style="margin-top:5px;"><c:out value="${item.getPosicao()}"/></span>
                            <c:choose>
                                <c:when test="${item.getPessoa().isPossuiFoto()}">
                                    <img src="<c:url value='/resources/img/photo/photo-${item.getPessoa().getId()}.png'/>" style="width:45px; height:45px;" alt="<c:out value="${item.getNome()}"/>" class="img-circle">
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value='/resources/img/sem_foto.png'/>" style="width:45px; height:45px;" alt="<c:out value="${item.getPessoa().getNome()}"/>" class="img-circle">
                                </c:otherwise>
                            </c:choose>
                            <span class="quebrar-linha" style="margin-top:5px;"><c:out value="${item.getPessoa().getNome()}"/></span>
                            <span class="quebrar-linha" style="margin-top:5px;"><c:out value="${item.getPontos()}"/></span>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>

            <br>
            <h4><fmt:message key="label.ranking.global"/></h4>
            <ul class="mosaico">
                <c:if test="${not empty listaTurma}">
                    <c:forEach var="item" varStatus="status" items="${listaTurma}">
                        <li>
                            <span class="quebrar-linha" style="margin-top:5px;"><c:out value="${item.getPosicao()}"/></span>
                            <c:choose>
                                <c:when test="${item.getPessoa().isPossuiFoto()}">
                                    <img src="<c:url value='/resources/img/photo/photo-${item.getPessoa().getId()}.png'/>" style="width:45px; height:45px;" alt="<c:out value="${item.getNome()}"/>" class="img-circle">
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value='/resources/img/sem_foto.png'/>" style="width:45px; height:45px;" alt="<c:out value="${item.getPessoa().getNome()}"/>" class="img-circle">
                                </c:otherwise>
                            </c:choose>
                            <span class="quebrar-linha" style="margin-top:5px;"><c:out value="${item.getPessoa().getNome()}"/></span>
                            <span class="quebrar-linha" style="margin-top:5px;"><c:out value="${item.getPontos()}"/></span>
                        </li>
                    </c:forEach>
                </c:if>
            </ul>
        </c:if>
    </jsp:body>
</t:master>