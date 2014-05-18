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
        
        <h2><c:out value="${TurmaSelecionada.getNome()}"/> - <fmt:message key="label.colegas"/></h2>
        
        <br>
        <h4><fmt:message key="label.professor"/></h4>
        <ul class="mosaico">
            <li>
                <c:choose>
                    <c:when test="${professor.isPossuiFoto()}">
                        <img src="<c:url value='/resources/img/photo/photo-${professor.getId()}.png'/>" style="width:45px; height:45px;" alt="<c:out value="${professor.getNome()}"/>" class="img-circle">
                    </c:when>
                    <c:otherwise>
                        <img src="<c:url value='/resources/img/sem_foto.png'/>" style="width:45px; height:45px;" alt="<c:out value="${professor.getNome()}"/>" class="img-circle">
                    </c:otherwise>
                </c:choose>
                <span class="quebrar-linha" style="margin-top:5px;"><c:out value="${professor.getNome()}"/></span>
            </li>
        </ul>
        
        <br>
        <h4><fmt:message key="label.colegas"/></h4>
        <ul class="mosaico">
            <c:if test="${not empty listaTurma}">
                <c:forEach var="item" varStatus="status" items="${listaTurma}">
                    <li>
                        <c:choose>
                            <c:when test="${item.isPossuiFoto()}">
                                <img src="<c:url value='/resources/img/photo/photo-${item.getId()}.png'/>" style="width:45px; height:45px;" alt="<c:out value="${item.getNome()}"/>" class="img-circle">
                            </c:when>
                            <c:otherwise>
                                <img src="<c:url value='/resources/img/sem_foto.png'/>" style="width:45px; height:45px;" alt="<c:out value="${item.getNome()}"/>" class="img-circle">
                            </c:otherwise>
                        </c:choose>
                        <span class="quebrar-linha" style="margin-top:5px;"><c:out value="${item.getNome()}"/></span>
                    </li>
                </c:forEach>
            </c:if>
        </ul>
            
    </jsp:body>
</t:master>