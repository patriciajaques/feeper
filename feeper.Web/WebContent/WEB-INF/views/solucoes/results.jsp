<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h5 style="margin-top: 0px;"><c:out value="${nomeExercicio}"/></h5>
<div class="panel panel-default" style="margin-bottom: 0px;">
    <div class="panel-body">
        <table class="table table-striped" style="margin-bottom: 0px;">
            <thead>
                <tr>
                    <th><fmt:message key="label.notas.acoes"/></th>
                    <th><fmt:message key="label.notas.datacadastro"/></th>
                    <th><fmt:message key="label.notas.status"/></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty Solucoes}">
                    <c:forEach var="item" items="${Solucoes}">
                        <tr>
                            <td>
                                <div class="btn-group btn-group-xs">
                                    <button type="button" class="btn btn-default btn-verclasses" data-id="${item.getId()}" data-idaluno="${idAluno}"><fmt:message key="button.verclasses"/></button>
                                    <c:if test="${not empty item.getErros()}">
                                        <button type="button" class="btn btn-default btn-vermensagens" data-id="${item.getId()}" data-idaluno="${idAluno}"><fmt:message key="button.vermensagens"/></button>
                                    </c:if>
                                    <button type="button" class="btn btn-default btn-download-pacote" data-id="${item.getId()}" data-idaluno="${idAluno}"><fmt:message key="button.downloadPKG"/></button>
                                </div>
                            </td>
                            <td style="font-size: 12px;"><fmt:formatDate value="${item.getDataCadastro()}" pattern="dd/MM/yyyy HH:mm" /></td>
                            <td style="font-size: 12px;">
                                <c:choose>
                                    <c:when test="${item.getIdStatus() == 1}">
                                        <span>Aguardando</span>
                                    </c:when>
                                    <c:when test="${item.getIdStatus() == 2}">
                                        <span>Erro de Compilação</span>
                                    </c:when>
                                    <c:when test="${item.getIdStatus() == 3}">
                                        <span>Resultado Inválido</span>
                                    </c:when>
                                    <c:when test="${item.getIdStatus() == 4 && empty item.getErros()}">
                                        <span>Resolvido</span>
                                    </c:when>
                                    <c:when test="${item.getIdStatus() == 4 && not empty item.getErros()}">
                                        <span>Resolvido com Alertas</span>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty Solucoes}">
                    <tr>
                        <td colspan="4" style="text-align: center;"><fmt:message key="label.nenhumregistroencontrado"/></td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>