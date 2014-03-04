<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.List" %>
<%
//lista[6]
//0 - IdPessoa
//1 - Nome
//2 - DataCadastro
//3 - Texto
//4 - Publico
//5 - LinhaInicio
int idUsuarioLogado = Integer.parseInt(request.getAttribute("idUsuarioLogado").toString());
List<Object[]> lista = (List<Object[]>)request.getAttribute("lista");
if (lista != null && lista.size() > 0)
{
    int idPessoaAux = 0;
    String dataCadastro = "";
    for (Object[] item : lista) {
        if (idPessoaAux != Integer.parseInt(item[0].toString()))
        {
            idPessoaAux = Integer.parseInt(item[0].toString());
            %><div class="row"><div class="col-md-12 pull-left"><h5 class="<%=idUsuarioLogado == idPessoaAux ? "pull-left" : "pull-right"%>"><%=item[1]%> <fmt:message key="label.mensagens.diz"/>:&nbsp;</h5></div></div><%
        }
        dataCadastro = item[2].toString();
        %><div class="row"><div class="col-md-12 pull-left"><div class="alert alert-success <%=idUsuarioLogado == idPessoaAux ? "pull-left" : "pull-right"%>" style="padding:3px; margin-bottom:3px;"><%=item[3]%></div></div></div><%
    }
    %><div class="row"><div class="col-md-12 pull-left"><small class="<%=idUsuarioLogado == idPessoaAux ? "pull-left" : "pull-right"%>" style="padding:3px; margin-bottom:3px;"><fmt:message key="label.mensagens.ha"/> <%=dataCadastro%></small></div></div><%
}  
%>