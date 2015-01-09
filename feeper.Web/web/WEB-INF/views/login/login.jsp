<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="pt">
    <head>
        <title>feeper - <fmt:message key="title.login"/></title>
        
        <!--[if IE]><link rel="shortcut icon" href="<c:url value='/resources/img/favicon.ico'/>"><![endif]-->
        <link rel="icon" href="<c:url value='/resources/img/favicon.png'/>">
        
        <link href="<c:url value='/resources/css/bootstrap.css'/>" rel="stylesheet" type="text/css" />
        
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="<c:url value='/resources/assets/js/html5shiv.js'/>"></script>
        <script src="<c:url value='/resources/assets/js/respond.min.js'/>"></script>
        <![endif]-->
        
        <script src="<c:url value='/resources/jquery/jquery-1.9.1.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>" type="text/javascript"></script>
        
        <style>
            body {
                padding-top: 40px;
                padding-bottom: 40px;
                background-color: #eee;
            }

            .form-signin {
                max-width: 330px;
                padding: 15px;
                margin: 0 auto;
            }
            .form-signin .form-signin-heading,
            .form-signin .checkbox {
                margin-bottom: 10px;
            }
            .form-signin .checkbox {
                font-weight: normal;
            }
            .form-signin .form-control {
                position: relative;
                height: auto;
                -webkit-box-sizing: border-box;
                   -moz-box-sizing: border-box;
                        box-sizing: border-box;
                padding: 10px;
                font-size: 16px;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .form-signin input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-signin input[type="password"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
            
            
        </style>
    </head>
    <body>
        
        <div class="container">
            
            <form class="form-signin" role="form" action="<c:url value='/'/>login/validate" id="frmLogin" method="POST">
                <div class="panel panel-primary">
                    <div class="panel-body"><a href="<c:url value='/'/>" title="<fmt:message key="label.feeper"/>"><img src="<c:url value='/resources/img/logo_p.png'/>" style="width:75px; margin-left: 95px;" /></a></div>
                </div>
                
                <br>
                
                <h2 class="form-signin-heading"><fmt:message key="label.facalogin"/></h2>
                <c:if test="${not empty MSG_SUCESSO}">
                    <div class="alert alert-success">${MSG_SUCESSO}</div>
                </c:if>
                <c:if test="${not empty MSG_ERRO}">
                    <div class="alert alert-danger">${MSG_ERRO}</div>
                </c:if>
                <input type="email" class="form-control" placeholder="<fmt:message key="label.email"/>" required autofocus id="email" name="email">
                <input type="password" class="form-control" placeholder="<fmt:message key="label.senha"/>" required id="senha" name="senha">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="button.entrar"/></button>
                <a href="<c:url value='/'/>senha/esqueciminhasenha" class="btn btn-default btn-block" role="button"><fmt:message key="button.esqueciminhasenha"/></a>
            </form>

        </div>
        
        <p style="text-align: center; margin-top: 50px;"><small><fmt:message key="author"/></small></p>
        
    </body>
</html>