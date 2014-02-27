<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
    <c:choose>
        <c:when test="${Redirect != null}">
            self.parent.document.location.href = '${Redirect}';
        </c:when>
        <c:when test="${JS != null}">
            ${JS}
        </c:when>
        <c:otherwise>
            try {
                self.parent.AtualizaListagem();
            } catch (e) { }
            self.parent.FechaModal();
        </c:otherwise>
    </c:choose>
</script>