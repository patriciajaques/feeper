<%@tag description="MasterPage.Clean" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<!DOCTYPE html>
<html lang="pt">
    <head>
        <title>feeper - <jsp:invoke fragment="title"/></title>
        <meta http-equiv="Content-Type" content="text/html">
        
        <!--[if IE]><link rel="shortcut icon" href="<c:url value='/resources/img/favicon.ico'/>"><![endif]-->
        <link rel="icon" href="<c:url value='/resources/img/favicon.png'/>">
        
        <link href="<c:url value='/resources/css/bootstrap.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/css/util.css'/>" rel="stylesheet" type="text/css" />
        <!--link href="<c:url value='/resources/css/webkit-scrollbars.css'/>" rel="stylesheet" type="text/css" /-->
        <link href="<c:url value='/resources/assets/google-code-prettify/prettify.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/css/site.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/fancybox/jquery.fancybox.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/uploadify/uploadify.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/jquery-te/jquery-te-1.4.0.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/jquery/ui/jquery-ui.min.css'/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value='/resources/jquery/ui/jquery-ui.structure.min.css'/>" rel="stylesheet" type="text/css"/>
        <link href="<c:url value='/resources/jquery/ui/jquery-ui.theme.min.css'/>" rel="stylesheet" type="text/css"/>
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" >
        
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
        <script src="<c:url value='/resources/assets/js/html5shiv.js'/>"></script>
        <script src="<c:url value='/resources/assets/js/respond.min.js'/>"></script>
        <![endif]-->
        
        <script src="<c:url value='/resources/jquery/jquery-2.1.3.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery/ui/jquery-ui.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/util.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery/MvcGrid.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery/funcoes.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery/jquery.form.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery/jquery.meio.mask.js'/>" type="text/javascript"></script>
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
            </div>
        </nav>
        
        <div class="container">
            <jsp:doBody/>
        </div>
        
        <p style="text-align: center; margin-top: 50px;"><small><fmt:message key="author"/></small></p>
        
    </body>
</html>