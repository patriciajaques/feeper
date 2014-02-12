<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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

<c:if test="${CodigoFonte != null}">
    <small><fmt:message key="label.exercicios.dataultimaresposta"/> <fmt:formatDate value="${CodigoFonte.getDataAlteracao()}" pattern="dd/MM/yyyy HH:mm" /></small>
</c:if>