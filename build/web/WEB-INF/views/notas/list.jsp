<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.notas"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-minhas-notas").addClass("active");
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2>Programação I - <fmt:message key="label.notas"/></h2>
        
        <div class="panel panel-default">
            <div class="panel-body">
                <table class="table table-striped" style="margin-bottom: 0px;">
                    <thead>
                        <tr>
                            <th><fmt:message key="label.notas.exercicio"/></th>
                            <th style="text-align: center;"><fmt:message key="label.notas.niveldificuldade"/></th>
                            <th style="text-align: center;"><fmt:message key="label.notas.dataresposta"/></th>
                            <th style="text-align: center;"><fmt:message key="label.notas.notaobtida"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Lista Encadeada</td>
                            <td style="text-align: center;">Baixa</td>
                            <td style="text-align: center;">08/01/2014 15:43</td>
                            <td style="text-align: center;">8,5</td>
                        </tr>
                        <tr>
                            <td>Lista Encadeada</td>
                            <td style="text-align: center;">Baixa</td>
                            <td style="text-align: center;">08/01/2014 15:43</td>
                            <td style="text-align: center;">8,5</td>
                        </tr>
                        <tr>
                            <td>Lista Encadeada</td>
                            <td style="text-align: center;">Baixa</td>
                            <td style="text-align: center;">08/01/2014 15:43</td>
                            <td style="text-align: center;">8,5</td>
                        </tr>
                        <tr>
                            <td>Lista Encadeada</td>
                            <td style="text-align: center;">Baixa</td>
                            <td style="text-align: center;">08/01/2014 15:43</td>
                            <td style="text-align: center;">8,5</td>
                        </tr>
                        <tr>
                            <td>Lista Encadeada</td>
                            <td style="text-align: center;">Baixa</td>
                            <td style="text-align: center;">08/01/2014 15:43</td>
                            <td style="text-align: center;">8,5</td>
                        </tr>
                        <tr>
                            <td>Lista Encadeada</td>
                            <td style="text-align: center;">Baixa</td>
                            <td style="text-align: center;">08/01/2014 15:43</td>
                            <td style="text-align: center;">8,5</td>
                        </tr>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th colspan="3">Nota Final</th>
                            <th style="text-align: center;">8,5</th>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
            
    </jsp:body>
</t:master>