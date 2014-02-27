<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.turmas"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-lista-turma").addClass("active");
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.turma"/> ${turma.nome}</h2>
        
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">Filtros de Pesquisa</h3>
            </div>
            <div class="panel-body">
                
                <form class="form-inline" role="form">
                    <div class="form-group">
                        <label class="sr-only" for="nome">Nome:</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="Nome da turma">
                    </div>
                    <div class="form-group">
                        <label class="sr-only" for="professor">Professor:</label>
                        <input type="text" class="form-control" id="professor" name="professor" placeholder="Nome do professor">
                    </div>
                    <button type="submit" class="btn btn-primary">Pesquisar</button>
                </form>
                
            </div>
        </div>
        
        
        <ul class="mosaico">
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
            <li>
                <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">F·bio Pacheco Alves</span>
            </li>
        </ul>
            
    </jsp:body>
</t:master>