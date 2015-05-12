<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="panel panel-default" style="margin-bottom: 0px;">
    <div class="panel-body">
        <table class="table table-striped" style="margin-bottom: 0px;">
            <thead>
                <tr>
                    <th></th>
                    <th><fmt:message key="label.notas.linha"/></th>
                    <th><fmt:message key="label.notas.mensagem"/></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty erros}">
                    <c:forEach var="erro" items="${erros}">
                        <tr>
                            <td>
                                ${erro.getErrorType() == 1
                                  ?
                                  "<button type=\"button\" class=\"btn btn-danger btn-xs\"><span class=\"glyphicon glyphicon-remove\"></span></button>"
                                  :
                                  "<button type=\"button\" class=\"btn btn-warning btn-xs\"><span class=\"glyphicon glyphicon-warning-sign\"></span></button>"
                                }
                            </td>
                            <td>${erro.getLinhaErro()>0?erro.getLinhaErro():""}</td>
                            <td>${erro.getMensagemPersonalizada()}
                                <c:if test="${not empty erro.getMensagemErro()}">
                                    <h6 style="color: blue; margin-top: 30px;"><fmt:message key="label.notas.mensagemSistema"/></h6>
                                    ${erro.getMensagemErro()}</td>
                                </c:if>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty erros}">
                    <tr>
                        <td colspan="4" style="text-align: center;"><fmt:message key="label.nenhumregistroencontrado"/></td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>
