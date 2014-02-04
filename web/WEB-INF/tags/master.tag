<%@tag description="MasterPage" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="rightmenu" fragment="true" %>
<!DOCTYPE html>
<html lang="pt">
    <head>
        <title>feeper - <jsp:invoke fragment="title"/></title>
        
        <link href="<c:url value='/resources/css/bootstrap.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/css/util.css'/>" rel="stylesheet" type="text/css" />
        <!--link href="<c:url value='/resources/css/webkit-scrollbars.css'/>" rel="stylesheet" type="text/css" /-->
        <link href="<c:url value='/resources/assets/google-code-prettify/prettify.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/css/site.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/fancybox/jquery.fancybox.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/uploadify/uploadify.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/jquery-te/jquery-te-1.4.0.css'/>" rel="stylesheet" type="text/css" />
        
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="<c:url value='/resources/assets/js/html5shiv.js'/>"></script>
        <script src="<c:url value='/resources/assets/js/respond.min.js'/>"></script>
        <![endif]-->
        
        <script src="<c:url value='/resources/jquery/jquery-1.9.1.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery/ui/jquery-ui.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/util.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery/MvcGrid.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/assets/google-code-prettify/prettify.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/fancybox/jquery.fancybox.pack.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/uploadify/jquery.uploadify.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery-te/jquery-te-1.4.0.min.js'/>" type="text/javascript"></script>
        
        <script type="text/javascript">
            function MostraCarregando() {
                $.fancybox.showLoading();
            }
            function RemoveCarregando() {
                $.fancybox.hideLoading();
            }

            function FechaModal() {
                $.fancybox.close(true);
            }
        </script>
        
        <jsp:invoke fragment="header"/>

    </head>
    <body>
        <nav class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <a href="#">
                        <img src="<c:url value='/resources/img/logo_p.png'/>" style="border:0px; padding-top:8px; padding-right:10px;" />
                    </a>
                </div>
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                    <ul class="nav navbar-nav">
                        <li id="menu-novidades"><a href="<c:url value='/'/>novidades"><fmt:message key="menu.novidades"/> <span class="badge badge-important">7</span></a></li>
                        <li id="menu-mensagens"><a href="<c:url value='/'/>mensagens"><fmt:message key="menu.mensagens"/> <span class="badge badge-important">3</span></a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="menu.turmas"/> <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Programação I</a></li>
                                <li><a href="#">Laboratório I</a></li>
                            </ul>
                        </li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li style="margin-top: 3px">
                            <img src="<c:url value='/resources/img/foto.png'/>" alt="..." class="img-circle">
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                ${UsuarioLogado.getNome()} <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="<c:url value='/'/>conquistas"><span class="glyphicon glyphicon-certificate"></span>&nbsp;&nbsp;<fmt:message key="menu.minhasconquistas"/></a></li>
                                <li class="divider"></li>
                                <li><a href="#"><span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;<fmt:message key="menu.configuracoes"/></a></li>
                                <li><a href="<c:url value='/'/>login/logout"><span class="glyphicon glyphicon-off"></span>&nbsp;&nbsp;<fmt:message key="menu.logout"/></a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        
        <div class="container">
            <div class="row">
                <div class="col-md-9">            
                    <jsp:doBody/>
                </div>
                <div class="col-md-3">
                    <h4>Programação I</h4>
                    <div class="list-group">
                        <a href="<c:url value='/'/>codigos" class="list-group-item" id="menu-meus-codigos-favoritos">
                            <span class="glyphicon glyphicon-star"></span>&nbsp;&nbsp;<fmt:message key="menu.codigosfavoritos"/>
                        </a>
                        <a href="<c:url value='/'/>exercicios" class="list-group-item" id="menu-lista-exercicios">
                            <span class="glyphicon glyphicon-tasks"></span>&nbsp;&nbsp;<fmt:message key="menu.exercicios"/>
                        </a>
                        <a href="<c:url value='/'/>notas" class="list-group-item" id="menu-minhas-notas">
                            <span class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;<fmt:message key="menu.minhasnotas"/>
                        </a>
                        <a href="<c:url value='/'/>colegas" class="list-group-item" id="menu-colegas">
                            <span class="glyphicon glyphicon-asterisk"></span>&nbsp;&nbsp;<fmt:message key="menu.colegas"/>
                        </a>
                    </div>
                        
                    <jsp:invoke fragment="rightmenu"/>
                </div>
            </div>
        </div>
        
        <jsp:invoke fragment="footer"/>
        
    </body>
</html>