<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.colegas"/></jsp:attribute>
    <jsp:attribute name="header">
        
    </jsp:attribute>
    
    <jsp:body>
        
        <c:if test="${UsuarioLogado.isGamificado() && UsuarioLogado.isElementoPonto()}">
            
            <H1>Pontuação Total: ${totalPontos} </H1>
               
            <div class="col-md-12">
                <div class="col-md-12">
                    <h4>Pontos</h4>
                <table cellpadding="10" border ="1" style="width:100%; background:#F2F2F2">
                    <thead>
                        <tr>
                            <th>Quantidade</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" varStatus="status" items="${listaPontos}">
                            <tr>
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