<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
//listaMensagensDestinatario[13]
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
//11 - DataCadastro
//12 - Texto
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
                    
                    if (idExercicio === undefined || idCodigoFonte === undefined || linha === undefined) return;
                    
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
                        'href': "<c:url value='/'/>codigos/show/" + idExercicio + "/" + idCodigoFonte + "/" + linha
                    });
                });
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><fmt:message key="label.mensagens"/></h2>
        
        <c:if test="${not empty listaMensagensDestinatario}">
            <c:forEach var="item" varStatus="status" items="${listaMensagensDestinatario}">
                <blockquote>
                    <p>${item[12]}</p>
                    <small>${item[1]} <fmt:message key="label.mensagens.em"/> <a href="#" class="fancybox" data-idexercicio="${item[9]}" data-idcodigofonte="${item[7]}" data-linha="${item[6]}">${item[10]} / ${item[8]} (<fmt:message key="label.mensagens.linha"/> ${item[6]})</a> - <fmt:message key="label.mensagens.ha"/> ${item[11]}</small><br>
                    <p>
                        <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.responder"/></button>
                    </p>
                </blockquote>
            </c:forEach>
        </c:if>
        <c:if test="${not empty listaMensagensRemetente}">
            <c:forEach var="item" varStatus="status" items="${listaMensagensRemetente}">
                <blockquote>
                    <p>${item[12]}</p>
                    <small>${item[1]} <fmt:message key="label.mensagens.em"/> <a href="#" class="fancybox" data-idexercicio="${item[9]}" data-idcodigofonte="${item[7]}" data-linha="${item[6]}">${item[10]} / ${item[8]} (<fmt:message key="label.mensagens.linha"/> ${item[6]})</a> - <fmt:message key="label.mensagens.ha"/> ${item[11]}</small><br>
                    <p>
                        <button type="button" class="btn btn-primary btn-xs"><fmt:message key="button.responder"/></button>
                    </p>
                </blockquote>
            </c:forEach>
        </c:if>
            
    </jsp:body>
</t:master>