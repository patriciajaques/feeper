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
//7 - IdClasse
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
                    var idClasse = $(this).attr("data-idclasse");
                    var linha = $(this).attr("data-linha");
                    var resposta = $(this).attr("data-resposta");
                    var aluno = $(this).attr("data-aluno");
                    var url = "";
                    
                    if (idExercicio === undefined || idClasse === undefined || linha === undefined || resposta === undefined || aluno === undefined) return;
                    
                    if (resposta == "true")
                        url = "<c:url value='/'/>classes/showversion/" + idClasse + "/" + aluno
                    else
                        url = "<c:url value='/'/>classes/show/" + idExercicio + "/" + idClasse + "/" + linha
                    
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
        
        <h2>TERMO DE CONSENTIMENTO LIVRE E ESCLARECIDO – 
           <c:if test="${usuario.getTermo()== 1}">
               Participando 
           </c:if>
           <c:if test="${usuario.getTermo()== -1}">
               Não Participando 
           </c:if>
         </h2>
        
        
        Você está sendo convidado(a) a participar, como voluntário(a), no projeto de pesquisa
para o desenvolvimento do Feeper, um ambiente de apoio às disciplinas de
programação em laboratório, sendo coordenado pela professora e pesquisadora Patrícia
A. Jaques Maillard, do Programa de Pós-Graduação em Computação Aplicada (PPGCA),
da Universidade do Vale do Rio dos Sinos (UNISINOS). <br> <br>

A sua participação nesta pesquisa consistirá em ser um dos utilizadores avaliadores do
Feeper. Você utilizará o Feeper para verificarmos se o mesmo está lhe auxiliando
efetivamente nas atividades de programação. Para tanto, você deverá usar o sistema e
realizar as atividades determinadas no período especificado pela(os) pesquisadora(es)
do projeto. Você poderá ser convidado(a) a realizar testes para verificar seu
conhecimento em programação, assim como responder a questionários sobre o Feeper e
seu interesse em programação. Você também poderá ser convidado(a) a participar de
sessões individuais para narrar suas ações enquanto estiver usando Feeper. <br> <br>

Durante todo o experimento, as suas ações realizadas no Feeper serão gravadas no
banco de dados do sistema. Essas gravações e dados serão utilizados apenas para fins
de pesquisa para melhoria do sistema e de forma anônima. Cabe aqui salientar que a
sua identidade será preservada, pois não serão divulgados nome, informações ou
imagens que possam identificá-lo(a). Se você é aluno(a) da UNISINOS ou outra
instituição de nível superior, é também importante observar que a sua participação
nesse experimento não terá nenhum impacto nas suas notas nas disciplinas cursadas
nessa universidade. Por se tratar de utilização de um software educacional, acreditamos
que os riscos a essa pesquisa sejam mínimos. Entre eles podemos citar a possibilidade
de constrangimento ao responder os questionários e/ou testes de conhecimento; 
desconforto; estresse; cansaço ao responder às perguntas ou fazer exercícios; e quebra
de anonimato. Os pesquisadores farão o possível para minimizar esses inconvenientes,
mas você pode sempre optar por desistir de participar ou retirar seu consentimento se
perceber qualquer um desses inconvenientes. Como benefícios da participação desse
experimento, você estará desenvolvendo suas habilidades de programação. Outros
benefícios potenciais são desenvolvimento da habilidade de resolução de problemas,
aumento da motivação para aprender matemática e maior engajamento para resolver
exercícios de álgebra, assim como aumento da sua auto-eficácia.  <br> <br>

Após ser esclarecido(a) sobre as informações acima, no caso de aceitar fazer parte desta
pesquisa, por favor assinale o campo abaixo “Declaro que entendi os objetivos, riscos e
benefícios da minha participação na pesquisa e concordo em participar”. Caso não
deseje disponibilizar seus dados para essa pesquisa, basta escolher a opção “Não desejo
participar dessa pesquisa”. <br> <br>
A qualquer momento você pode desistir de participar e retirar seu consentimento. Sua
recusa não trará nenhum prejuízo em sua relação com o pesquisador ou com a
instituição. Em caso de dúvida você pode procurar a professora e pesquisadora Patrícia
A. Jaques Maillard no telefone (51) 3591-1226 ou pelo e-mail pjaques@unisinos.br. <br> <br>
        

<form class="form-group" action="/feeper.Web/termo" method="POST">
    <button type="submit" name="action" value="1" class="btn btn-primary">Declaro que entendi os objetivos, riscos e benefícios da minha participação na pesquisa
e concordo em participar</button>
    <button type="submit" name="action" value="-1" class="btn btn-default btn-fechar">Não desejo participar dessa pesquisa</button>
</form>
            
    </jsp:body>
</t:master>