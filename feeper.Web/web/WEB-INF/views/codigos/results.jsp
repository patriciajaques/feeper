<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
//Respostas[10]
//0 - ID
//1 - IdAutor
//2 - Autor
//3 - IdExercicio
//4 - Exercicio
//5 - DataCadastroString
//6 - DataCadastro
//7 - IdStatus
//8 - Status
//9 - Mensagem  
%>
<h5 style="margin-top: 0px;"><c:out value="${nomeExercicio}"/></h5>
<div class="panel panel-default" style="margin-bottom: 0px;">
    <div class="panel-body">
        <table class="table table-striped" style="margin-bottom: 0px;">
            <thead>
                <tr>
                    <th><fmt:message key="label.notas.acoes"/></th>
                    <th><fmt:message key="label.notas.datacadastro"/></th>
                    <th><fmt:message key="label.notas.status"/></th>
                    <th><fmt:message key="label.notas.mensagem"/></th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty Respostas}">
                    <c:forEach var="item" varStatus="status" items="${Respostas}">
                        <tr>
                            <td>
                                <div class="btn-group btn-group-xs">
                                    <button type="button" class="btn btn-default btn-vercodigos" data-id="${item[0]}" data-idaluno="${idAluno}"><fmt:message key="button.vercodigos"/></button>
                                    <button type="button" class="btn btn-default btn-download-pacote" data-id="${item[0]}" data-idaluno="${idAluno}"><fmt:message key="button.download"/></button>
                                </div>
                            </td>
                            <td style="font-size: 12px;"><fmt:formatDate value="${item[6]}" pattern="dd/MM/yyyy HH:mm" /></td>
                            <td style="font-size: 12px;"><c:out value="${item[8]}"/></td>
                            <td style="font-size: 12px;"><c:out value="${item[9]}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
                <c:if test="${empty Respostas}">
                    <tr>
                        <td colspan="4" style="text-align: center;"><fmt:message key="label.nenhumregistroencontrado"/></td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>