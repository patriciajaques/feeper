<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">

    <c:choose>
        <c:when test="${Redirect != null}">
            self.parent.document.location.href = '<c:out value="${Redirect}"></c:out>';
        </c:when>
        <c:when test="${JS != null}">
            <c:out value="${JS}"></c:out>
        </c:when>
        <c:otherwise>
            try {
                self.parent.AtualizaListagem();
            } catch (e) { }
            self.parent.FechaModal();
        </c:otherwise>
    </c:choose>
        
</script>