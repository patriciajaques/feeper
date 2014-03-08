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
                
                $(".btn-exercicio").click(function(){
                    var idExercicio = $(this).attr("data-idexercicio");
                    var idAluno = $(this).attr("data-idaluno");
                    
                    if (idExercicio === undefined || idAluno === undefined) return;
                    
                    if ($("#panelResults" + idExercicio + "_" + idAluno).length == 0)
                    {
                        $(this).parent().addClass("borda-selecao");
                    
                        var url = "<c:url value='/'/>codigos/results/" + idExercicio + "/" + idAluno;
                        $(this).parent().parent().after("<tr><td colspan='11' style='border-top:2px solid #999; border-bottom:2px solid #999; display:none' id='panelResults" + idExercicio + "_" + idAluno + "'></td></tr>");
                    
                        MostraCarregando()
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
                        
                                });
                            }
                            else{
                                $(this).parent().removeClass("borda-selecao");
                                $("#panelCodes" + id + "_" + idAluno).slideUp("fast").parent().remove();
                            }

                        });
                        
                        $("#panelResults" + idExercicio + "_" + idAluno).on('click', '.btn-download-pacote', function(){
                        
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
        
        <h2>${TurmaSelecionada.getNome()} - <fmt:message key="label.notas.resultadoexercicios"/></h2>
        
        <div class="panel panel-default">
            <div class="panel-body">
                <table class="table table-striped" style="margin-bottom: 0px;">
                    <thead>
                        <tr>
                            <th><fmt:message key="label.notas.aluno"/></th>
                            <th style="text-align: center;">#1</th>
                            <th style="text-align: center;">#2</th>
                            <th style="text-align: center;">#3</th>
                            <th style="text-align: center;">#4</th>
                            <th style="text-align: center;">#5</th>
                            <th style="text-align: center;">#6</th>
                            <th style="text-align: center;">#7</th>
                            <th style="text-align: center;">#8</th>
                            <th style="text-align: center;">#9</th>
                            <th style="text-align: center;">#10</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Fulano Siclano</td>
                            <td style="text-align: center;">
                                <button type="button" class="btn btn-success btn-xs btn-exercicio" data-toggle="tooltip" data-placement="top" title="Nome do Exercício" data-idexercicio="3" data-idaluno="3"><span class="glyphicon glyphicon-ok"></span></button>
                            </td>
                            <td style="text-align: center;">
                                <button type="button" class="btn btn-success btn-xs btn-exercicio" data-toggle="tooltip" data-placement="top" title="Nome do Exercício" data-idexercicio="2" data-idaluno="3"><span class="glyphicon glyphicon-ok"></span></button>
                            </td>
                            <td style="text-align: center;">
                                <button type="button" class="btn btn-success btn-xs btn-exercicio"><span class="glyphicon glyphicon-ok"></span></button>
                            </td>
                            <td style="text-align: center;">
                                <button type="button" class="btn btn-danger btn-xs btn-exercicio"><span class="glyphicon glyphicon-remove"></span></button>
                            </td>
                            <td style="text-align: center;">
                                <button type="button" class="btn btn-info btn-xs btn-exercicio"><span class="glyphicon glyphicon-time"></span></button>
                            </td>
                            <td style="text-align: center;">
                                <button type="button" class="btn btn-info btn-xs btn-exercicio"><span class="glyphicon glyphicon-time"></span></button>
                            </td>
                            <td style="text-align: center;">
                                <button type="button" class="btn btn-warning btn-xs btn-exercicio"><span class="glyphicon glyphicon-warning-sign"></span></button>
                            </td>
                            <td style="text-align: center;">&nbsp;</td>
                            <td style="text-align: center;">&nbsp;</td>
                            <td style="text-align: center;">&nbsp;</td>
                        </tr>
                        <tr>
                            <td>Beltrano da Silva</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                        </tr>
                        <tr>
                            <td>Fulano Siclano</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                        </tr>
                        <tr>
                            <td>Beltrano da Silva</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                        </tr>
                        <tr>
                            <td>Fulano Siclano</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                        </tr>
                        <tr>
                            <td>Beltrano da Silva</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                        </tr>
                        <tr>
                            <td>Fulano Siclano</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                        </tr>
                        <tr>
                            <td>Beltrano da Silva</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                            <td style="text-align: center;">X</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
            
    </jsp:body>
</t:master>