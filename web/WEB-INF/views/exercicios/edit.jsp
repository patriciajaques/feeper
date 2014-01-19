<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-cadastro-exercicio").addClass("active");
                
                
                $('#file_upload').uploadify({
                    'swf'           : '<c:url value='/resources/uploadify/uploadify.swf'/>',
                    'uploader'      : '<c:url value='/'/>exercicios/upload',
                    'fileTypeDesc'  : 'Arquivos PDF',
                    'fileTypeExts'  : '*.pdf',
                    'fileSizeLimit' : '500KB',
                    'buttonText'    : '<fmt:message key="button.escolherarquivo"/>',
                    'multi'         : false,
                    'fileObjName'   : 'filedata',
                    'checkExisting' : false,
                    'width'         : 146,
                    'height'        : 34,
                    'onUploadSuccess' : function(file, data, response) {
                        //alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data);
                    },
                    'onUploadError' : function(file, errorCode, errorMsg, errorString) {
                        //alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
                    }
                });
                
                $(".rd-detalhamento").click(function(){
                    var show = $(this).attr("show-div");
                    $(".div-detalhamento").slideUp("fast");
                    $("#" + show).slideDown("fast");
                });
                
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        <h2>
        <c:choose>
            <c:when test="${IsAdd != null && IsAdd}">
                <fmt:message key="label.exercicios.novo"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="label.exercicios.editar"/>
            </c:otherwise>
        </c:choose>
        </h2>
        
        <div class="panel panel-default">
            <div class="panel-body">

                <form role="form">
                    <div class="form-group">
                        <label for="titulo"><fmt:message key="label.exercicios.titulo"/>:</label>
                        <input type="text" class="form-control" id="titulo" name="titulo" placeholder="<fmt:message key="label.exercicios.tituloinforme"/>">
                    </div>
                    <div class="form-group">
                        <label for="nivel"><fmt:message key="label.exercicios.dificuldade"/>:</label>
                        <select class="form-control" id="nivel" name="nivel">
                            <option value="1">Baixa</option>
                            <option value="2">Média</option>
                            <option value="3">Alta</option>
                        </select>
                    </div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" value="1" name="ativo" checked>
                            <fmt:message key="label.exercicios.ativo"/>
                        </label>
                    </div>
                    <div class="form-group">
                        <label for="encerramento"><fmt:message key="label.exercicios.detalhamento"/>:</label><br>
                        <label class="radio-inline">
                            <input type="radio" id="rdHTML" name="rdDetalhamento" class="rd-detalhamento" value="html" show-div="editorhtml" checked="">
                            <fmt:message key="label.exercicios.utilizareditorhtml"/>
                        </label>
                        <label class="radio-inline">
                            <input type="radio" id="rdPDF" name="rdDetalhamento" class="rd-detalhamento" value="pdf" show-div="uploadpdf">
                            <fmt:message key="label.exercicios.utilizarpdf"/>
                        </label>
                    </div>
                    
                    <div id="editorhtml" class="div-detalhamento">
                        <div class="panel panel-default">
                            <div class="panel-body">
                              Editor HTML
                            </div>
                        </div>
                    </div>
                    <div id="uploadpdf" style="display:none;" class="div-detalhamento">
                        <input type="file" name="file_upload" id="file_upload" />
                    </div>
                        
                    <button type="submit" class="btn btn-primary"><fmt:message key="button.salvar"/></button>
                    <button type="button" class="btn btn-default"><fmt:message key="button.voltarlistagem"/></button>
                </form>

            </div>
        </div>
                
        <c:if test="${IsAdd != null && !IsAdd}">

            <h2><fmt:message key="label.exercicios.cadastrarentradassaidas"/></h2>
            
            <button type="button" id="btn-nova-linha" class="btn btn-primary"><fmt:message key="button.novalinha"/></button>
            <br /><br />
            
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><fmt:message key="label.registroscadastrados"/></h3>
                </div>
                <div class="panel-body">

                    <table class="table table-striped table-hover" style="margin-bottom: 0px;">
                        <thead>
                            <tr>
                                <th><fmt:message key="label.exercicios.acoes"/></th>
                                <th><fmt:message key="label.exercicios.entrada"/></th>
                                <th><fmt:message key="label.exercicios.saida"/></th>
                                <th><fmt:message key="label.exercicios.mensagemperzonalizada"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>
                                    <div class="btn-group btn-group-xs">
                                        <button type="button" class="btn btn-default"><fmt:message key="button.excluir"/></button>
                                    </div>
                                </td>
                                <td>
                                    <textarea class="form-control" rows="3"></textarea>
                                </td>
                                <td>
                                    <textarea class="form-control" rows="3"></textarea>
                                </td>
                                <td>
                                    <textarea class="form-control" rows="3"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="btn-group btn-group-xs">
                                        <button type="button" class="btn btn-default"><fmt:message key="button.excluir"/></button>
                                    </div>
                                </td>
                                <td>
                                    <textarea class="form-control" rows="3"></textarea>
                                </td>
                                <td>
                                    <textarea class="form-control" rows="3"></textarea>
                                </td>
                                <td>
                                    <textarea class="form-control" rows="3"></textarea>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <div class="btn-group btn-group-xs">
                                        <button type="button" class="btn btn-default"><fmt:message key="button.excluir"/></button>
                                    </div>
                                </td>
                                <td>
                                    <textarea class="form-control" rows="3"></textarea>
                                </td>
                                <td>
                                    <textarea class="form-control" rows="3"></textarea>
                                </td>
                                <td>
                                    <textarea class="form-control" rows="3"></textarea>
                                </td>
                            </tr>
                        </tbody>
                    </table>

                </div>
            </div>
            <button type="button" id="btn-nova-linha" class="btn btn-primary"><fmt:message key="button.novalinha"/></button>
            <br><br>
            
        </c:if>
                
        

    </jsp:body>
</t:master>