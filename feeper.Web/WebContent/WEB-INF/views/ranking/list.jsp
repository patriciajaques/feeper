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
        
        <c:if test="${UsuarioLogado.isGamificado() && UsuarioLogado.isElementoRanking()}">

            <h2><c:out value="${TurmaSelecionada.getNome()}"/> - <fmt:message key="label.colegas"/></h2>

            <div class="col-md-12">
                <div class="col-md-6">
                    <h4><fmt:message key="label.ranking.turma"/></h4>
                <table cellpadding="10" border ="1" style="width:100%; background:#F2F2F2">
                    <thead>
                        <tr>
                            <th>Posição</th>
                            <th>Foto</th>
                            <th>Nome</th>
                            <th>Pontuação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" varStatus="status" items="${rankingTurma}">
                            <tr>
                                <td><c:out value="${item.getPosicao()}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.getPessoa().isPossuiFoto()}">
                                            
                                            <img src="<c:url value='/resources/img/photo/photo-${item.getPessoa().getId()}.png'/>" alt="<c:out value=""/>" >
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<c:url value='/resources/img/sem_foto.png'/>" alt="<c:out value="${item.getPessoa().getNome()}"/>" >
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><c:out value="${item.getPessoa().getNome()}"/></td>
                                <td><c:out value="${item.getPontos()}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </div>
                    
                <div class="col-md-6">
                    <h4><fmt:message key="label.ranking.global"/></h4>
                <table cellpadding="10" border ="1" style="width:100%; background:#F2F2F2">
                    <thead>
                        <tr>
                            <th>Posição</th>
                            <th>Foto</th>
                            <th>Nome</th>
                            <th>Pontuação</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" varStatus="status" items="${listaTurma}">
                            <tr>
                                <td><c:out value="${item.getPosicao()}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.getPessoa().isPossuiFoto()}">
                                            <img src="<c:url value='/resources/img/photo/photo-${item.getPessoa().getId()}.png'/>" alt="<c:out value=""/>" >
                                        </c:when>
                                        <c:otherwise>
                                            <img src="<c:url value='/resources/img/sem_foto.png'/>" alt="<c:out value="${item.getPessoa().getNome()}"/>" >
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td><c:out value="${item.getPessoa().getNome()}"/></td>
                                <td><c:out value="${item.getPontos()}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </div>
            </div>
            
            
            <br>
            <br>

        </c:if>
    </jsp:body>
</t:master>