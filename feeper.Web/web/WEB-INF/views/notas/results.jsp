<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.List" %>
<%@page import="feeper.Data.entity.Exercicio" %>
<%@page import="feeper.Data.model.EStatusResposta" %>
<%
//grade[6]
//0 - IdPessoa
//1 - NomePessoa
//2 - IdExercicio
//3 - NomeExercicio
//4 - IdResposta
//5 - IdStatusResposta
    
List<Object[]> lista = (List<Object[]>)request.getAttribute("grade");
List<Exercicio> listaExercicios = (List<Exercicio>)request.getAttribute("listaExercicios");
if (lista != null && lista.size() > 0 && listaExercicios != null && listaExercicios.size() > 0)
{
%>
<div class="panel panel-default">
    <div class="panel-body">
        <table class="table table-striped" style="margin-bottom: 0px;">
            <thead>
                <tr>
                    <th><fmt:message key="label.notas.aluno"/></th>
                    <%
                    int contagemExercicio = 0;
                    for (Exercicio item : listaExercicios) {
                        %><th style="text-align: center;"><div class="th-exercicio" data-toggle="tooltip" data-placement="top" title="<c:out value="<%=item.getNome()%>"/>">#<%=++contagemExercicio%></div></th><%
                    }
                    %>
                </tr>
            </thead>
            <tbody>
                <%
                int idPessoaAux = 0;
                for (Object[] item : lista) {
                    if (idPessoaAux != Integer.parseInt(item[0].toString()))
                    {
                        if (idPessoaAux != 0)
                        {
                        %></tr><%
                        }
                        idPessoaAux = Integer.parseInt(item[0].toString());
                        %><tr><td><c:out value="<%=item[1]%>"/></td><%
                    }
                    Integer idStatus = (Integer)item[5];
                    String botao = "";
                    
                    if (idStatus != null)
                    {
                        switch(idStatus) {
                            case EStatusResposta.AGUARDANDO:
                                botao = "<button type=\"button\" class=\"btn btn-info btn-xs btn-exercicio\" data-idexercicio=\"%d\" data-idaluno=\"%d\"><span class=\"glyphicon glyphicon-time\"></span></button>";
                                break;
                            case EStatusResposta.ERRO_COMPILACAO:
                                botao = "<button type=\"button\" class=\"btn btn-danger btn-xs btn-exercicio\" data-idexercicio=\"%d\" data-idaluno=\"%d\"><span class=\"glyphicon glyphicon-remove\"></span></button>";
                                break;
                            case EStatusResposta.RESOLVIDO:
                                botao = "<button type=\"button\" class=\"btn btn-success btn-xs btn-exercicio\" data-idexercicio=\"%d\" data-idaluno=\"%d\"><span class=\"glyphicon glyphicon-ok\"></span></button>";
                                break;
                            case EStatusResposta.SAIDA_INVALIDA:
                            case EStatusResposta.TEMPO_LIMITE:
                                botao = "<button type=\"button\" class=\"btn btn-warning btn-xs btn-exercicio\" data-idexercicio=\"%d\" data-idaluno=\"%d\"><span class=\"glyphicon glyphicon-warning-sign\"></span></button>";
                                break;
                        }
                        botao = String.format(botao, Integer.parseInt(item[2].toString()), Integer.parseInt(item[0].toString()));
                    }
                    else
                    {
                        botao = "&nbsp;";
                    }
                    %>
                    <td style="text-align: center;"><%=botao%></td>
                    <%
                }
                %>
            </tbody>
        </table>
    </div>
</div>
<%
}
%>