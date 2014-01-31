<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-lista-exercicios").addClass("active");
                
                $(".btn-responder").click(function(){
                    document.location.href = "<c:url value='/'/>exercicios/responder";
                });
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2>Programação I - <fmt:message key="label.exercicios"/></h2>
        
        <ul class="pagination pagination-sm">
          <li><a href="#">«</a></li>
          <li class="active"><a href="#">1</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li><a href="#">4</a></li>
          <li><a href="#">5</a></li>
          <li><a href="#">»</a></li>
        </ul>
        
        <blockquote>
            <p><b>Lista Encadeada:</b> Monte uma lista simplesmente encadeada para armazenar uma lista de valores e devolver estes valores ordenados de forma descrescente.</p>
            <small class="pull-left">Programação I - Dificuldade Baixa</small>
            <small class="pull-right">Data da Última Resposta</small>
            <br>
            <p>
                <button type="button" class="btn btn-primary btn-xs btn-responder"><fmt:message key="button.responder"/></button>
            </p>
        </blockquote>
        
        <blockquote>
            <p><b>Lista Encadeada:</b> Monte uma lista simplesmente encadeada para armazenar uma lista de valores e devolver estes valores ordenados de forma descrescente.</p>
            <small class="pull-left">Programação I - Dificuldade Baixa</small>
            <small class="pull-right">Data da Última Resposta</small>
            <br>
            <p>
                <button type="button" class="btn btn-primary btn-xs btn-responder"><fmt:message key="button.responder"/></button>
            </p>
        </blockquote>
        
        <blockquote>
            <p><b>Lista Encadeada:</b> Monte uma lista simplesmente encadeada para armazenar uma lista de valores e devolver estes valores ordenados de forma descrescente.</p>
            <small class="pull-left">Programação I - Dificuldade Baixa</small>
            <small class="pull-right">Data da Última Resposta</small>
            <br>
            <p>
                <button type="button" class="btn btn-primary btn-xs btn-responder"><fmt:message key="button.responder"/></button>
            </p>
        </blockquote>
        
        <blockquote>
            <p><b>Lista Encadeada:</b> Monte uma lista simplesmente encadeada para armazenar uma lista de valores e devolver estes valores ordenados de forma descrescente.</p>
            <small class="pull-left">Programação I - Dificuldade Baixa</small>
            <small class="pull-right">Data da Última Resposta</small>
            <br>
            <p>
                <button type="button" class="btn btn-primary btn-xs btn-responder"><fmt:message key="button.responder"/></button>
            </p>
        </blockquote>
        
        <ul class="pagination pagination-sm">
          <li><a href="#">«</a></li>
          <li class="active"><a href="#">1</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li><a href="#">4</a></li>
          <li><a href="#">5</a></li>
          <li><a href="#">»</a></li>
        </ul>
            
    </jsp:body>
</t:master>