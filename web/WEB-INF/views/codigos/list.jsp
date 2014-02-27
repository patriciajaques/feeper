<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.codigosfavoritos"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-meus-codigos-favoritos").addClass("active");
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2>ProgramaÁ„o I - <fmt:message key="label.codigosfavoritos"/></h2>
        
        <ul class="pagination pagination-sm">
          <li><a href="#">´</a></li>
          <li class="active"><a href="#">1</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li><a href="#">4</a></li>
          <li><a href="#">5</a></li>
          <li><a href="#">ª</a></li>
        </ul>
        
        <h4>Calcular a mÈdia de um array</h4>
        
<pre class="pre-scrollable">
public static double CalculaMedia(ArrayList&lt;Integer&gt; lista)
{
    int n = lista.size()
    int soma = 0;

    for(int i = 0; i< n; i++)
    {
        soma += lista.get(i);
    }
    return soma / n;
}
</pre>
        
        <button type="button" class="btn btn-primary btn-xs">
            <span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.removerfavorito"/>
        </button>
        
        <br><br>
        
        <h4>Calcular o desvio padr„o de uma lista</h4>
        
<pre class="pre-scrollable">
public static double CalculaDesvio(ArrayList&lt;Integer&gt; lista)
{
    double media = CalculaMedia(lista);
    double desvioLinhas = 0;
    int n = lista.size();

    for(int i = 0; i< n; i++)
    {
        double valorDaLinha = (lista.get(i) - media);
        desvioLinhas += valorDaLinha * valorDaLinha;
    }

    double divisao = desvioLinhas / (n - 1);
    double raiz = Math.sqrt(divisao);
    return raiz;
}
</pre>
        
        <button type="button" class="btn btn-primary btn-xs">
            <span class="glyphicon glyphicon-trash"></span> <fmt:message key="button.removerfavorito"/>
        </button>
        
        <br><br>
        
        <ul class="pagination pagination-sm">
          <li><a href="#">´</a></li>
          <li class="active"><a href="#">1</a></li>
          <li><a href="#">2</a></li>
          <li><a href="#">3</a></li>
          <li><a href="#">4</a></li>
          <li><a href="#">5</a></li>
          <li><a href="#">ª</a></li>
        </ul>
            
    </jsp:body>
</t:master>