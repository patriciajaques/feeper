<%@tag description="MasterPage.Clean" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<!DOCTYPE html>
<html lang="pt">
    <head>
        <title>feeper - <jsp:invoke fragment="title"/></title>
        
        <link href="<c:url value='/resources/css/bootstrap.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/css/util.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/css/webkit-scrollbars.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/assets/google-code-prettify/prettify.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/css/site.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/fancybox/jquery.fancybox.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/resources/uploadify/uploadify.css'/>" rel="stylesheet" type="text/css" />
        
        <script src="<c:url value='/resources/jquery/jquery-1.9.1.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery/ui/jquery-ui.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/bootstrap.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/js/util.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/assets/google-code-prettify/prettify.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/fancybox/jquery.fancybox.pack.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/uploadify/jquery.uploadify.min.js'/>" type="text/javascript"></script>
        
        <jsp:invoke fragment="header"/>

    </head>
    <body class="clean-modal">
        <div class="container">
            <jsp:doBody/>
        </div>
    </body>
</html>