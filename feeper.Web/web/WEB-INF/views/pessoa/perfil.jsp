<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.pessoa"/></jsp:attribute>
    <jsp:attribute name="header">

        <script type="text/javascript">
            $(function () {
                //hack, para crash do uploadify
                setTimeout(function () {
                    initFileUpload();
                }, 0);
            });

            function initFileUpload() {

                settings = {
                    url: '<c:url value='/'/>pessoa/uploadphoto',
                    dragDrop: false,
                    allowedTypes: "jpg,png",
                    returnType: "text",
                    onSuccess: function (files, data, xhr)
                    {
                        $("#idUploadTemp").val(data);
                    },
                    showDelete: false,
                    showDone: false,
                    showAbort: false,
                    showStatusAfterSuccess: true,
                    maxFileSize: 512000,
                    multiple: false,
                    uploadButtonClass: "btn btn-primary btn-xs"
                }
                $("#file_upload").uploadFile(settings);
            }
        </script>

    </jsp:attribute>
    <jsp:body>

        <h2><fmt:message key="label.pessoa.editarperfil"/></h2>

        <c:if test="${not empty MSG_SUCESSO}">
            <div class="alert alert-success">${MSG_SUCESSO}</div>
        </c:if>
        <c:if test="${not empty MSG_ERRO}">
            <div class="alert alert-danger">${MSG_ERRO}</div>
        </c:if>

        <div class="panel panel-default" style="width:500px;">
            <div class="panel-body">

                <c:choose>
                    <c:when test="${pessoa.isPossuiFoto()}">
                        <img src="<c:url value='/resources/img/photo/photo-${pessoa.getId()}.png'/>" style="width:45px; height:45px;" alt="<c:out value="${pessoa.getNome()}"/>" class="img-circle">
                    </c:when>
                    <c:otherwise>
                        <img src="<c:url value='/resources/img/sem_foto.png'/>" style="width:45px; height:45px;" alt="<c:out value="${pessoa.getNome()}"/>" class="img-circle">
                    </c:otherwise>
                </c:choose>
                <div class="pull-right" style="margin-right:3px;">
                    <div id="file_upload">
                        <c:choose>
                            <c:when test="${pessoa.isPossuiFoto()}">
                                <fmt:message key="button.trocarfoto"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="button.adicionarfoto"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <br><br>

                <form role="form" action="<c:url value='/'/>pessoa/saveperfil" method="POST">
                    <div class="form-group">
                        <label for="nome"><fmt:message key="label.pessoa.nome"/>:</label>
                        <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.pessoa.informenome"/>" value="${pessoa.getNome()}">
                    </div>
                    <div class="form-group">
                        <label for="email"><fmt:message key="label.pessoa.email"/></label>
                        <input type="text" class="form-control" id="email" name="email" disabled value="${pessoa.getEmail()}">
                    </div>
                    <div class="form-group">
                        <label for="senha"><fmt:message key="label.pessoa.senha"/></label>
                        <input type="password" class="form-control" id="senha" name="senha">
                    </div>
                    <div class="form-group">
                        <label for="confirmar"><fmt:message key="label.pessoa.senhaconfirmar"/></label>
                        <input type="password" class="form-control" id="confirmar" name="confirmar">
                    </div>
                    <br>
                    <input type="hidden" id="idUploadTemp" name="idUploadTemp">
                    <button type="submit" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                </form>

            </div>
        </div>

    </jsp:body>
</t:master>