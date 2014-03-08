<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.conquistas"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                //$("#menu-colegas").addClass("active");
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.conquistas"/></h2>
        
        <ul class="mosaico">
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 1</span>
            </li>
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 2</span>
            </li>
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 3</span>
            </li>
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 4</span>
            </li>
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 5</span>
            </li>
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 6</span>
            </li>
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 7</span>
            </li>
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 8</span>
            </li>
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 9</span>
            </li>
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 10</span>
            </li>
            <li style="cursor:pointer">
                <img src="<c:url value='/resources/img/trofeu.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 11</span>
            </li>
            <li style="background-color: #F8F8F8">
                <img src="<c:url value='/resources/img/trofeu_off.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 12</span>
            </li>
            <li style="background-color: #F8F8F8">
                <img src="<c:url value='/resources/img/trofeu_off.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 13</span>
            </li>
            <li style="background-color: #F8F8F8">
                <img src="<c:url value='/resources/img/trofeu_off.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 14</span>
            </li>
            <li style="background-color: #F8F8F8">
                <img src="<c:url value='/resources/img/trofeu_off.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 15</span>
            </li>
            <li style="background-color: #F8F8F8">
                <img src="<c:url value='/resources/img/trofeu_off.png'/>" alt="..." class="img-circle">
                <span class="quebrar-linha" style="margin-top:5px;">Conquista 16</span>
            </li>
        </ul>
            
    </jsp:body>
</t:master>