<%@tag description="MasterPage" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="rightmenu" fragment="true" %>
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
        <link href="<c:url value='/resources/JQueryFileUpload/uploadfile.css'/>" rel="stylesheet" type="text/css"/>
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
        <script src="<c:url value='/resources/fancybox/jquery.fancybox.pack.js'/>" type="text/javascript">
        </script><script src="<c:url value='/resources/JQueryFileUpload/jquery.uploadfile.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery-te/jquery-te-1.4.0.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/resources/jquery/jquery.cookie.js'/>" type="text/javascript"></script>

        <script type="text/javascript">
            var baseUrl = "<c:url value='/'/>";

            function MostraCarregando() {
                $.fancybox.showLoading();
            }
            function RemoveCarregando() {
                $.fancybox.hideLoading();
            }

            function FechaModal() {
                $.fancybox.close(true);
            }

            function changeTurma(id) {
                if (id === undefined)
                    return;
                document.location.href = "<c:url value='/'/>turma/change/" + id;
            }

            function CloseSession() {
                document.location.href = "<c:url value='/'/>login/logout";
            }

            $(function () {

                $("#feedback-button").fancybox({
                    'autoSize': true,
                    'autoResize': true,
                    'openEffect': 'fade',
                    'closeEffect': 'fade',
                    'modal': false
                });

                var ajaxFormOptions = {
                    beforeSubmit: MostraCarregando,
                    clearForm: true,
                    success: function (data) {
                        RemoveCarregando();
                        if (data == "ok")
                            $("#msg-feedback-sucesso").slideDown("fast");
                        else
                            $("#msg-feedback-erro").slideDown("fast");
                    }
                };
                $('#frmFeedback').ajaxForm(ajaxFormOptions);

                try {
                    parent.FechaModal();
                } catch (e) {
                }
                SessionTimeout.schedulePopup(44, '< c:url value=' / '/>timeout', '<fmt:message key="label.mensagemsessiontimeout"/>');

            });
        </script>

        <jsp:invoke fragment="header"/>

    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <a href="<c:url value='/'/>">
                        <img src="<c:url value='/resources/img/logo_p.png'/>" style="border:0px; padding-top:8px; padding-right:10px;" />
                    </a>
                </div>
                <div class="collapse navbar-collapse navbar-ex1-collapse" style="width: 100%;">
                    <ul class="nav navbar-nav">
                        <c:choose>
                            <c:when test="${UsuarioLogado.getIdPerfil() == 1}">
                                <li id="menu-lista-pessoas"><a href="<c:url value='/'/>pessoa"><fmt:message key="menu.pessoas"/></a></li>
                                <li id="menu-lista-turma"><a href="<c:url value='/'/>turma"><fmt:message key="menu.turmas"/></a></li>
                                <li id="menu-lista-exercicio"><a href="<c:url value='/'/>exercicios"><fmt:message key="menu.exercicios"/></a></li>
                                <li id="menu-lista-conquistas"><a href="<c:url value='/'/>conquistas"><fmt:message key="menu.conquistas"/></a></li>
                                <li id="menu-lista-logs"><a href="<c:url value='/'/>log"><fmt:message key="menu.logs"/></a></li>
                                </c:when>
                                <c:otherwise>
                                
                                <li id="menu-mensagens"><a href="<c:url value='/'/>mensagens"><fmt:message key="menu.mensagens"/> ${BadgeMensagens}</a></li>
                                
                                
                                <c:if test="${UsuarioLogado.isGamificado()}">
                                    <c:if test="${UsuarioLogado.isElementoRanking()}">
                                        <li id="menu-ranking"><a href="<c:url value='/'/>ranking" style="color:red"><fmt:message key="menu.leaderboard"/></a></li>    
                                    </c:if>
                                    <c:if test="${UsuarioLogado.isElementoMedalha()}">
                                        <li id="menu-minhasconquistas"><a href="<c:url value='/'/>conquistas/minhasconquistas" style="color:red"><fmt:message key="menu.conquistas"/></a></li>
                                    </c:if>
                                    <c:if test="${UsuarioLogado.isElementoPonto()}">
                                        <li id="menu-minhasconquistas"><a href="<c:url value='/'/>pontos"><fmt:message key="menu.pontos"/></a></li>
                                    </c:if>
                                        
                                </c:if>
                                        
                                <li id="menu-termo"><a href="<c:url value='/'/>termo"><fmt:message key="menu.termo"/></a></li>                                        
                                    
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><fmt:message key="menu.turmas"/> <b class="caret"></b></a>
                                    <ul class="dropdown-menu">${MinhasTurmas}</ul>
                                </li>
                                <li><p class="navbar-text"><b><c:out value="${TurmaSelecionada.getNome()}"/></b></p></li>
                                        </c:otherwise>
                                    </c:choose>

                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li style="margin-top: 3px">
                            <c:choose>
                                <c:when test="${UsuarioLogado.isPossuiFoto()}">
                                    <img src="<c:url value='/resources/img/photo/photo-${UsuarioLogado.getId()}.png'/>" style="width:45px; height:45px;" alt="<c:out value="${UsuarioLogado.getNome()}"/>" class="img-circle">
                                </c:when>
                                <c:otherwise>
                                    <img src="<c:url value='/resources/img/sem_foto.png'/>" style="width:45px; height:45px;" alt="<c:out value="${UsuarioLogado.getNome()}"/>" class="img-circle">
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <c:out value="${UsuarioLogado.getNome()}"/> <b class="caret"></b>
                            </a>
                            <ul class="dropdown-menu">


                                <li><a href="<c:url value='/'/>pessoa/perfil"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;<fmt:message key="menu.meuperfil"/></a></li>

                                <c:if test="${UsuarioLogado.getIdPerfil() == 1}">
                                    <li><a href="<c:url value='/'/>configuracoes/edit"><span class="glyphicon glyphicon-cog"></span>&nbsp;&nbsp;<fmt:message key="menu.configuracoes"/></a></li>
                                    <li class="divider"></li>
                                    </c:if>
                                    <c:if test="${UsuarioLogado.getIdPerfil() == 2}">
                                    <li><a href="<c:url value='/'/>mensagenspredefinidas/edit"><span class="glyphicon glyphicon-bookmark"></span>&nbsp;&nbsp;<fmt:message key="menu.mensagenspredefinidas"/></a></li>
                                    <li class="divider"></li>
                                    </c:if>

                                <li><a href="<c:url value='/'/>login/logout"><span class="glyphicon glyphicon-off"></span>&nbsp;&nbsp;<fmt:message key="menu.logout"/></a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container">
            <div class="row">
                <c:choose>
                    <c:when test="${UsuarioLogado.getIdPerfil() == 1}">
                        <div class="col-md-12">            
                            <jsp:doBody/>
                        </div>
                    </c:when>
                    <c:when test="${UsuarioLogado.getIdPerfil() == 2}">
                        <div class="col-md-9">            
                            <jsp:doBody/>
                        </div>
                        <div class="col-md-3">
                            
                            <div class="list-group">
                                <a href="<c:url value='/'/>turma/edit/${TurmaSelecionada.getId()}" class="list-group-item" id="menu-minha-turma">
                                    <span class="glyphicon glyphicon-wrench"></span>&nbsp;&nbsp;<fmt:message key="menu.minhaturma"/>
                                </a>
                                <a href="<c:url value='/'/>exercicios" class="list-group-item" id="menu-lista-exercicios">
                                    <span class="glyphicon glyphicon-tasks"></span>&nbsp;&nbsp;<fmt:message key="menu.exercicios"/>
                                </a>
                                <a href="<c:url value='/'/>notas" class="list-group-item" id="menu-minhas-notas">
                                    <span class="glyphicon glyphicon-th"></span>&nbsp;&nbsp;<fmt:message key="menu.resultadoexercicios"/>
                                </a>
                                <a href="<c:url value='/'/>plagiarism/list" class="list-group-item" id="menu-plagiarism-detector">
                                    <span class="glyphicon glyphicon-copyright-mark"></span>&nbsp;&nbsp;<fmt:message key="menu.plagiarismdetector"/>
                                </a>
                            </div>

                            <jsp:invoke fragment="rightmenu"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col-md-12">
                            
                            <div class="list-group">
                                      
<!--                                <a  href="<c:url value='/'/>classes" class="list-group-item col-md-3" id="menu-minhas-classes-favoritas">
                                    <span class="glyphicon glyphicon-star"></span>&nbsp;&nbsp;<fmt:message key="menu.classesfavoritas"/>
                                </a>-->
                                
                                <a href="<c:url value='/'/>exercicios/meusexercicios" class="list-group-item col-md-4" id="menu-lista-exercicios">
                                    <span class="glyphicon glyphicon-tasks"></span>&nbsp;&nbsp;<fmt:message key="menu.meusexercicios"/>
                                </a>
                                      
                                <a  href="<c:url value='/'/>notas" class="list-group-item col-md-4" id="menu-minhas-notas">
                                    <span class="glyphicon glyphicon-list"></span>&nbsp;&nbsp;<fmt:message key="menu.resultadoexercicios"/>
                                </a>
                                
                                <a href="<c:url value='/'/>colegas" class="list-group-item col-md-4" id="menu-colegas">
                                    <span class="glyphicon glyphicon-asterisk"></span>&nbsp;&nbsp;<fmt:message key="menu.colegas"/>
                                </a>
                                <br><br><br>
                            </div>
                           

                            <jsp:invoke fragment="rightmenu"/>
                        </div>
                        <div class="col-md-12">            
                            <jsp:doBody/>
                        </div>
                        
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <jsp:invoke fragment="footer"/>
        <p style="text-align: center; margin-top: 5px;"><small><fmt:message key="author"/></small></p>

        <a id="feedback-button" href="#divFeedback">
            <img src="<c:url value='/resources/img/feedback.png'/>" alt="feedback" />
        </a>
        <div id="divFeedback" style="display: none; width: 400px;">
            <div class="alert alert-success" id="msg-feedback-sucesso" style="display:none;"><fmt:message key="label.feedback.msgsucesso"/></div>
            <div class="alert alert-danger" id="msg-feedback-erro" style="display:none;"><fmt:message key="label.feedback.msgerro"/></div>
            <form role="form" action="<c:url value='/'/>feedback" id="frmFeedback" method="POST">
                <h4><fmt:message key="label.feedback.informativo"/></h4>
                <div class="form-group">
                    <label class="sr-only" for="email"><fmt:message key="label.feedback.email"/></label>
                    <input type="text" class="form-control" id="email" name="email" placeholder="<fmt:message key="label.feedback.email"/>">
                </div>
                <div class="form-group">
                    <label class="sr-only" for="mensagem"><fmt:message key="label.feedback.mensagem"/></label>
                    <textarea class="form-control" rows="3" id="mensagem" name="mensagem" placeholder="<fmt:message key="label.feedback.mensagem"/>"></textarea>
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="button.enviarfeedback"/></button>
            </form>
        </div>

    </body>
</html>