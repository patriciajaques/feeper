<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="java.util.List" %>
<%
int idUsuarioLogado = Integer.parseInt(request.getAttribute("idUsuarioLogado").toString());
List<Object[]> lista = (List<Object[]>)request.getAttribute("lista");
if (lista != null && lista.size() > 0)
{
    int idPessoaAux = 0;
    for (Object[] item : lista) {
        %><div class="row"><div class="col-md-12 pull-left"><%
        if (idPessoaAux != Integer.parseInt(item[0].toString()))
        {
            idPessoaAux = Integer.parseInt(item[0].toString());
            %><h5 class="<%=idUsuarioLogado == idPessoaAux ? "pull-left" : "pull-right"%>"><%=item[1]%> <fmt:message key="label.mensagens.diz"/>:&nbsp;</h5><%
        }
        %><div class="alert alert-success <%=idUsuarioLogado == idPessoaAux ? "pull-left" : "pull-right"%>" style="padding:3px; margin-bottom:3px;"><%=item[3]%></div><%
    }
}  
%>