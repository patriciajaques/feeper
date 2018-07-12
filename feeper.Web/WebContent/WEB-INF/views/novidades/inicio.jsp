<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.inicio"/></jsp:attribute>
    <jsp:attribute name="header">
        <style>
            .citation {
                text-align: justify;
                padding: 15px;
                max-width: 500px;
            }
            
            .presentation {
                width: 448px; 
                height: 320px; 
                padding: 10px;
            }
        </style>
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.boasvindas"/></h2>
        
        <blockquote>
            <p class="citation"><fmt:message key="label.boasvindas.descricao"/></p>
        </blockquote>

        <br>

        <div class="panel panel-default presentation">
            <div id="carousel-example-generic" class="carousel slide" data-ride="carousel" style="width:428px;">
                <div class="carousel-inner">
                    <div class="item active"><img src="<c:url value='/resources/img/print1.png'/>" width="428" height="300" alt=""></div>
                    <div class="item"><img src="<c:url value='/resources/img/print2.png'/>" width="428" height="300" alt=""></div>
                    <div class="item"><img src="<c:url value='/resources/img/print3.png'/>" width="428" height="300" alt=""></div>
                </div>
            </div>
        </div>
        
    </jsp:body>
</t:master>