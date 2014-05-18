<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master.modal>
    <jsp:attribute name="title"><fmt:message key="title.exercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <link href="<c:url value='/resources/jquery/themes/base/jquery.ui.all.css'/>" rel="stylesheet" type="text/css" />
        
        <script type="text/javascript">
            $(function(){
                $(".btn-fechar").click(function(){
                    self.parent.FechaModal();
                });
                
                $("#checkAll").click(function(){
                    $(".chk-exercicio").prop("checked", $(this).is(":checked"));
                });
                
                
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        <h2><fmt:message key="label.turma.adicionarexercicio"/></h2>

        <form role="form" action="<c:url value='/turma/saveaddexercicio'/>" method="POST">
            <input type="hidden" id="idTurma" name="idTurma" value="${idTurma}">

            <button type="submit" class="btn btn-primary"><fmt:message key="button.adicionar"/></button>
            <button type="button" class="btn btn-default btn-fechar"><fmt:message key="button.fechar"/></button>
            
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><fmt:message key="label.registroscadastrados"/></h3>
                </div>
                <div class="panel-body">

                    <table class="table table-striped table-hover" style="margin-bottom: 0px;">
                        <thead>
                            <tr>
                                <th><input type="checkbox" id="checkAll"></th>
                                <th>#</th>
                                <th><fmt:message key="label.exercicios.titulo"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${not empty lista}">
                                <c:forEach var="item" varStatus="status" items="${lista}">
                                    <tr>
                                        <td>
                                            <input type="checkbox" class="chk-exercicio" name="chkExercicio" value="${item.getId()}">
                                        </td>
                                        <td><c:out value="${item.getId()}"/></td>
                                        <td><c:out value="${item.getNome()}"/></td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty lista}">
                                <tr>
                                    <td colspan="3"><fmt:message key="label.nenhumregistroencontrado"/></td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>

                </div>
            </div>
                            
            <button type="submit" class="btn btn-primary"><fmt:message key="button.adicionar"/></button>
            <button type="button" class="btn btn-default btn-fechar"><fmt:message key="button.fechar"/></button>
        </form>
        
    </jsp:body>
</t:master.modal>