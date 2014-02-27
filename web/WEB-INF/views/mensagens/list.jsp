<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.mensagens"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-mensagens").addClass("active");
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.mensagens"/></h2>
        <blockquote>
            <p>Pessoal estou com uma d˙vida no exercÌcio 1, n„o estou conseguindo montar uma lista encadeada. AlguÈm pode me ajudar?</p>
            <small>F·bio Alves in <cite title="ExercÌcio 1">ExercÌcio 1</cite> - h· 1 minuto</small><br>
            <p>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.curtir"/></button>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.responder"/></button>
            </p>
        </blockquote>

        <blockquote>
            <p>Pessoal estou com uma d˙vida no exercÌcio 1, n„o estou conseguindo montar uma lista encadeada. AlguÈm pode me ajudar?</p>
            <small>F·bio Alves in <cite title="ExercÌcio 1">ExercÌcio 1</cite> - h· 1 minuto</small><br>
            <p>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.curtir"/></button>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.responder"/></button>
            </p>
        </blockquote>

        <blockquote>
            <p>Pessoal estou com uma d˙vida no exercÌcio 1, n„o estou conseguindo montar uma lista encadeada. AlguÈm pode me ajudar?</p>
            <small>F·bio Alves in <cite title="ExercÌcio 1">ExercÌcio 1</cite> - h· 1 minuto</small><br>
            <p>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.curtir"/></button>
                <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.responder"/></button>
            </p>
        </blockquote>
            
    </jsp:body>
</t:master>