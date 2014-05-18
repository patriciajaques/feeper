<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master>
    <jsp:attribute name="title"><fmt:message key="title.resultadoexercicios"/></jsp:attribute>
    <jsp:attribute name="header">
        
        <style>
            .borda-selecao {
                background-color: #999 !important
            }
        </style>
        
        <script type="text/javascript">
            $(function(){
                $("#menu-minhas-notas").addClass("active");
                
                MostraCarregando();
                $("#grade-resultados").load("<c:url value='/'/>notas/results", function(){
                    RemoveCarregando();
                    $(".th-exercicio").tooltip();
                });
                
                $("#grade-resultados").on('click', '.btn-exercicio', function(){
                    var idExercicio = $(this).attr("data-idexercicio");
                    var idAluno = $(this).attr("data-idaluno");
                    
                    if (idExercicio === undefined || idAluno === undefined) return;
                    
                    if ($("#panelResults" + idExercicio + "_" + idAluno).length == 0)
                    {
                        $(this).parent().addClass("borda-selecao");
                    
                        var url = "<c:url value='/'/>codigos/results/" + idExercicio + "/" + idAluno;
                        $(this).parent().parent().after("<tr><td colspan='11' style='border-top:2px solid #999; border-bottom:2px solid #999; display:none' id='panelResults" + idExercicio + "_" + idAluno + "'></td></tr>");
                    
                        MostraCarregando();
                        $("#panelResults" + idExercicio + "_" + idAluno).slideDown("fast").load(url, function(){
                            RemoveCarregando();
                        });
                        
                        //------------------------------
                        //EVENTOS DOS BOTÕES DA LISTAGEM
                        //------------------------------
                        $("#panelResults" + idExercicio + "_" + idAluno).on('click', '.btn-vercodigos', function(){
                            var id = $(this).attr("data-id");
                            var idAluno = $(this).attr("data-idaluno");

                            if (id === undefined || idAluno === undefined) return;

                            if ($("#panelCodes" + id + "_" + idAluno).length == 0)
                            {
                                var url = "<c:url value='/'/>codigos/listcode/" + id + "/" + idAluno;
                                $(this).parent().parent().parent().after("<tr><td colspan='4' style='border-top:2px solid #999; border-bottom:2px solid #999; display:none' id='panelCodes" + id + "_" + idAluno + "'></td></tr>");

                                MostraCarregando()
                                $("#panelCodes" + id + "_" + idAluno).slideDown("fast").load(url, function(){
                                    RemoveCarregando();
                                });
                                
                                //------------------------------
                                //EVENTOS DOS BOTÕES DA LISTAGEM
                                //------------------------------
                                $("#panelCodes" + id + "_" + idAluno).on('click', '.btn-exibircodigo', function(){
                                    var id = $(this).attr("data-id");
                                    var idAluno = $(this).attr("data-idaluno");

                                    if (id === undefined || idAluno === undefined) return;

                                    $.fancybox({
                                        'openEffect': 'fade',
                                        'closeEffect': 'fade',
                                        'autoResize': false,
                                        'autoSize': true,
                                        'type': 'iframe',
                                        'arrows': false,
                                        'width': '90%',
                                        'height': '90%',
                                        'href': "<c:url value='/'/>codigos/showversion/" + id + "/" + idAluno
                                    });
                                });
                                
                                $("#panelCodes" + id + "_" + idAluno).on('click', '.btn-download', function(){
                                    var id = $(this).attr("data-id");
                                    var idAluno = $(this).attr("data-idaluno");
                                    document.location.href = "<c:url value='/'/>codigos/downloadfileversion/" + id + "/" + idAluno;
                                });
                            }
                            else{
                                $(this).parent().removeClass("borda-selecao");
                                $("#panelCodes" + id + "_" + idAluno).slideUp("fast").parent().remove();
                            }

                        });
                        
                        $("#panelResults" + idExercicio + "_" + idAluno).on('click', '.btn-download-pacote', function(){
                            var id = $(this).attr("data-id");
                            var idAluno = $(this).attr("data-idaluno");
                            document.location.href = "<c:url value='/'/>codigos/downloadpkgversion/" + id + "/" + idAluno;
                        });
                    }
                    else{
                        $(this).parent().removeClass("borda-selecao");
                        $("#panelResults" + idExercicio + "_" + idAluno).slideUp("fast").parent().remove();
                    }
                    
                });
                
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        
        <h2><c:out value="${TurmaSelecionada.getNome()}"/> - <fmt:message key="label.notas.resultadoexercicios"/></h2>
        
        <div id="grade-resultados"></div>
            
    </jsp:body>
</t:master>