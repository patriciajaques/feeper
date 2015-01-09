<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="panel panel-default" style="margin-bottom: 0px;">
    <div class="panel-body">
        <table class="table table-striped" style="margin-bottom: 0px;">
            <thead>
                <tr>
                    <th><fmt:message key="label.notas.acoes"/></th>
                    <th><fmt:message key="label.notas.classe"/></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty classes}">
                    <c:forEach var="item" varStatus="status" items="${classes}">
                        <tr>
                            <td>
                                <div class="btn-group btn-group-xs">
                                    <button type="button" class="btn btn-default btn-exibirclasse" data-id="${item.getId()}" data-idaluno="${idAluno}"><fmt:message key="button.exibir"/></button>
                                    <button type="button" class="btn btn-default btn-download" data-id="${item.getId()}" data-idaluno="${idAluno}"><fmt:message key="button.download"/></button>
                                </div>
                            </td>
                            <td><c:out value="${item.getNomeClasse()}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty classes}">
                    <tr>
                        <td colspan="2" style="text-align: center;"><fmt:message key="label.nenhumregistroencontrado"/></td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>