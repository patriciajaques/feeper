<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
//lista[17]
//0 - IdAutor
//1 - Autor
//2 - IdMensagemCabecalho
//3 - Publico
//4 - IdPessoa
//5 - Nome
//6 - LinhaInicio
//7 - IdCodigoFonte
//8 - Classe
//9 - IdExercicio
//10 - Exercicio
//11 - IdAluno
//12 - Resposta
//13 - DataCadastro
//14 - DataCadastroOrder
//15 - NovaMensagem
//16 - Texto
%>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.mensagens"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <script type="text/javascript">
            $(function(){
                $("#menu-mensagens").addClass("active");
                
                $(".fancybox").click(function(event){
                    var idExercicio = $(this).attr("data-idexercicio");
                    var idCodigoFonte = $(this).attr("data-idcodigofonte");
                    var linha = $(this).attr("data-linha");
                    var resposta = $(this).attr("data-resposta");
                    var aluno = $(this).attr("data-aluno");
                    var url = "";
                    
                    if (idExercicio === undefined || idCodigoFonte === undefined || linha === undefined || resposta === undefined || aluno === undefined) return;
                    
                    if (resposta == "true")
                        url = "<c:url value='/'/>codigos/showversion/" + idCodigoFonte + "/" + aluno
                    else
                        url = "<c:url value='/'/>codigos/show/" + idExercicio + "/" + idCodigoFonte + "/" + linha
                    
                    event.preventDefault ? event.preventDefault() : event.returnValue = false;
                    $.fancybox({
                        'openEffect': 'fade',
                        'closeEffect': 'fade',
                        'autoResize': false,
                        'autoSize': false,
                        'type': 'iframe',
                        'arrows': false,
                        'width': '90%',
                        'height': '90%',
                        'href': url
                    });
                });
                
                $(".new-message").animate({
                    'border-left-color': '#5BC0DE',
                    'background-color': '#F4F8FA'
                }, 2000);
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.mensagens"/></h2>
        
        <c:if test="${not empty listaMensagens}">
            <c:forEach var="item" varStatus="status" items="${listaMensagens}">
                <blockquote class="${item[15] == 1 ? "new-message" : "old-message"}">
                    <p><c:out value="${item[16]}"/></p>
                    <small><c:out value="${item[1]}"/> <fmt:message key="label.mensagens.em"/> <a href="#" class="fancybox" data-idexercicio="${item[9]}" data-idcodigofonte="${item[7]}" data-linha="${item[6]}" data-resposta="${item[12]}" data-aluno="${item[11]}"><c:out value="${item[10]}"/> / <c:out value="${item[8]}"/> (<fmt:message key="label.mensagens.linha"/> ${item[6]})</a> - <fmt:message key="label.mensagens.ha"/> ${item[13]}</small><br>
                    <p>
                        <button type="button" class="btn btn-primary btn-xs fancybox" data-idexercicio="${item[9]}" data-idcodigofonte="${item[7]}" data-linha="${item[6]}" data-resposta="${item[12]}" data-aluno="${item[11]}"><fmt:message key="button.responder"/></button>
                    </p>
                </blockquote>
            </c:forEach>
        </c:if>
            
    </jsp:body>
</t:master>