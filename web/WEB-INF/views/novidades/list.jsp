<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.novidades"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-novidades").addClass("active");
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.novidades"/></h2>
        <blockquote>
            <p>Pessoal estou com uma dúvida no exercício 1, não estou conseguindo montar uma lista encadeada. Alguém pode me ajudar?</p>
            <small>Fábio Alves in <cite title="Exercício 1">Exercício 1</cite> - há 1 minuto</small><br>
            <p>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.curtir"/></button>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.responder"/></button>
            </p>
        </blockquote>

        <blockquote>
            <p>Pessoal estou com uma dúvida no exercício 1, não estou conseguindo montar uma lista encadeada. Alguém pode me ajudar?</p>
            <small>Fábio Alves in <cite title="Exercício 1">Exercício 1</cite> - há 1 minuto</small><br>
            <p>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.curtir"/></button>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.responder"/></button>
            </p>
        </blockquote>

        <blockquote>
            <p>Pessoal estou com uma dúvida no exercício 1, não estou conseguindo montar uma lista encadeada. Alguém pode me ajudar?</p>
            <small>Fábio Alves in <cite title="Exercício 1">Exercício 1</cite> - há 1 minuto</small><br>
            <p>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.curtir"/></button>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.responder"/></button>
            </p>
        </blockquote>
            
    </jsp:body>
</t:master>