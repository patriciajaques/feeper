<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master.modal>
    <jsp:attribute name="title"><fmt:message key="title.pessoa"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#btn-fechar").click(function(){
                   self.parent.FechaModal(); 
                });
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        <h2><c:choose>
            <c:when test="${IsAdd != null && IsAdd}">
                <fmt:message key="label.pessoa.nova"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="label.pessoa.editar"/>
            </c:otherwise>
        </c:choose></h2>

        <form role="form">
            <div class="form-group">
                <label for="nome"><fmt:message key="label.pessoa.nome"/>:</label>
                <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.pessoa.informenome"/>">
            </div>
            <div class="form-group">
                <label for="email"><fmt:message key="label.pessoa.email"/></label>
                <input type="text" class="form-control" id="email" name="email" placeholder="<fmt:message key="label.pessoa.informeemail"/>">
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" value="1" name="ativo" checked> <fmt:message key="label.pessoa.ativo"/>
                </label>
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
            <button type="button" class="btn btn-default" id="btn-fechar"><fmt:message key="button.fechar"/></button>
        </form>
        
    </jsp:body>
</t:master.modal>