<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<t:master.modal>
    <jsp:attribute name="title"><fmt:message key="title.pessoa"/></jsp:attribute>
    <jsp:attribute name="header">
         
        <script type="text/javascript">
            $(function(){
                $(".btn-fechar").click(function(){
                    self.parent.FechaModal();
                });
                
                $("#nome").autocomplete({
                    source: function (request, response) {
                        $.ajax({
                            url: '<c:url value='/'/>pessoa/search/3/' + $("#nome").val(),
                            type: 'GET',
                            dataType: 'json'
                        }).done(function (data) {
                            response($.map(data, function (item) {
                                return { label: item[1], value: item[1], id: item[0], email: item[2] };
                            }));
                        }).fail(function () {
                            $('.ui-autocomplete-loading').removeClass("ui-autocomplete-loading");
                        });
                    },
                    minLength: 5,
                    select: function (event, ui) {
                        $("#id").val(ui.item.id);
                        $("#email").val(ui.item.email).prop("disabled", true);
                        $("#nome").prop("data-nome", ui.item.value);
                    },
                    change: function (event, ui) {
                        var nome = $("#nome").val();
                        var original = $("#nome").prop("data-nome");
                        if (original !== "" && original !== undefined && nome !== original)
                        {
                            $("#nome").val("").prop("data-nome", "");
                            $("#email").val("").prop("disabled", false);
                            $("#id").val("0");
                        }
                    }
                });
            });
        </script>
        
    </jsp:attribute>
    <jsp:body>
        <h2><c:choose>
            <c:when test="${IsAdd != null && IsAdd}">
                <fmt:message key="label.pessoa.nova"/>
            </c:when>
            <c:otherwise>
                <fmt:message key="label.pessoa.editar"/>
            </c:otherwise>
        </c:choose></h2>

        <form role="form" action="<c:url value='/turma/saveaddaluno'/>" method="POST">
            <input type="hidden" id="id" name="id" value="0">
            <input type="hidden" id="idTurma" name="idTurma" value="${idTurma}">
            <div class="form-group">
                <label for="nome"><fmt:message key="label.pessoa.nome"/>:</label>
                <input type="text" class="form-control" id="nome" name="nome" placeholder="<fmt:message key="label.pessoa.informenome"/>">
            </div>
            <div class="form-group">
                <label for="email"><fmt:message key="label.pessoa.email"/></label>
                <input type="text" class="form-control" id="email" name="email">
            </div>
            <button type="submit" class="btn btn-primary"><fmt:message key="button.adicionar"/></button>
            <button type="button" class="btn btn-default btn-fechar"><fmt:message key="button.fechar"/></button>
        </form>
        
    </jsp:body>
</t:master.modal>