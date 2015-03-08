
var editor = null;

$(function () {

    $.ajaxSetup({cache: false});
    $("#menu-lista-exercicios").addClass("active");
    ControlaBotoes();

    settings = {
        url: baseUrl + 'exercicios/uploadclass',
        dragDrop: false,
        allowedTypes: "java",
        returnType: "text",
        onSuccess: function (files, data, xhr)
        {
            adicionarClasse(data);
        },
        showDelete: false,
        showDone: false,
        showAbort: false,
        showStatusAfterSuccess: false,
        maxFileSize: 512000,
        multiple: false,
        uploadButtonClass: "btn btn-primary btn-xs"
    }
    $("#file_upload").uploadFile(settings);

    var ajaxFormOptions = {
        target: '#pnlMensagens',
        clearForm: true,
        beforeSubmit: MostraCarregando,
        success: function () {
            RemoveCarregando();
            $("#panel-markedquestions .panel-body").animate({scrollTop: $("#panel-markedquestions .panel-body")[0].scrollHeight}, 1000);
        }
    };
    $('#frmQuestao').ajaxForm(ajaxFormOptions);

    var ajaxFormOptions2 = {
        beforeSubmit: MostraCarregando,
        success: function (data) {
            $("#anotacaoClasse").val(data[1]);
            RemoveCarregando();
            $("#panel-markedannotations .panel-body").animate({scrollTop: $("#panel-markedquestions .panel-body")[0].scrollHeight}, 1000);
        }
    };
    $('#frmAnotacao').ajaxForm(ajaxFormOptions2);

    $(".btn-classe-download").click(function () {
        var id = $("#hdnIdExercicio").val();
        document.location.href = baseUrl + "exercicios/download/" + id;
    });

    $(".btn-classe-enviar").click(function () {
        $("#frmSolucionar").attr("action", baseUrl + "exercicios/enviarcorrecao");
        $("#frmSolucionar").submit();
    });

    $(".btn-excluir-classe").click(function () {
        if (!confirm(confirmaExclusaoText))
            return;
        var id = $("#hdnIdClasse").val();
        document.location.href = baseUrl + "exercicios/deleteclasse/" + exercicioID + "/" + id;
    });

    $(".btn-nova-classe").fancybox({
        'autoSize': true,
        'openEffect': 'fade',
        'closeEffect': 'fade', 'modal': true
    });

    $("#panel-markedquestions button.close").click(function () {
        $("#panel-markedquestions").hide("slide", "fast");
    });

    $("#panel-markedannotations button.close").click(function () {
        $("#panel-markedannotations").hide("slide", "fast");
    });

    $(".btn-salvar-classe").click(function () {
        $("#hdnEditor").val(editor.getSession().getValue());
        $("#frmSolucionar").submit();
    });

    $(".btn-show-class-code").click(function (event) {
        event.preventDefault ? event.preventDefault() : event.returnValue = false;
        var id = $(this).attr("data-id");
        if (id === undefined)
            return;
        $(".btn-show-class-code").removeClass("active");
        $(this).addClass("active");
        MostraCarregando();
        $.get(baseUrl + "exercicios/showclassecode/" + exercicioID + "/" + id, function (data) {
            $("#hdnIdClasse").val(data[0]);
            $("#hdnNomeClasse").val(data[1]);
            $("#lblFilename").html(data[1]);
            var content = data[2].replace(/#'#/gi, '"');
            CreateEditor(content, data[0] != "0", data[3], data[4]);
            if (data[5])
                $(".btn-classe-favorita").html("<span class=\"glyphicon glyphicon-star\"></span>" + desmarcarfavoritaText);
            else
                $(".btn-classe-favorita").html("<span class=\"glyphicon glyphicon-star-empty\"></span>" + marcarfavoritaText);
            ControlaBotoes();
            RemoveCarregando();
            $('html, body').animate({scrollTop: $("#lblFilename").offset().top}, 1000);
        });
    });

    $(".btn-classe-favorita").click(function () {
        var id = $("#hdnIdClasse").val();
        $.get(baseUrl + "classes/savefavorite/" + exercicioID + "/" + id, function (data) {
            if (data != "erro" && data == "true")
                $(".btn-classe-favorita").html("<span class=\"glyphicon glyphicon-star\"></span>" + desmarcarfavoritaText);
            else if (data != "erro" && data == "false")
                $(".btn-ckasse-favorita").html("<span class=\"glyphicon glyphicon-star-empty\"></span>" + marcarfavoritaText);
        });
    });

    $(".btn-registrar-duvida").click(function () {
        var row = editor.getSelectionRange().start.row;
        exibeQuadroDuvida(row);
    });

    $(".btn-registrar-anotacao").click(function () {
        var row = editor.getSelectionRange().start.row;
        exibeQuadroAnotacao(row);
    });

    $(".btn-habilitar-edicao").click(function () {
        editor.setReadOnly(false);
        ControlaBotoes();
    });
});

//functions
function cancelarClasse() {
    $("#txtNomeClasse").val("");
    $.fancybox.close(true);
}

function trataString(valor)
{
    $.ajax({
        url: baseUrl + "tratastring/" + valor,
        cache: false,
        async: false
    }).done(function (data) {
        valor = data;
    });
    return valor;
}

function CreateEditor(source, readOnly, questions, comments)
{
    try {
        editor.destroy();
        $(" #editor").remove();
    } catch (e) {
    }

    $("#panelEditor").append($('<div id="editor"></div>'));
    editor = ace.edit("editor");
    editor.session.setValue(source);
    editor.container.style.opacity = "";
    editor.setOptions({
        maxLines: 30,
        mode: "ace/mode/java",
        autoScrollEditorIntoView: true
    });
    editor.setReadOnly(readOnly);
    editor.setTheme("ace/theme/eclipse");
    editor.setShowPrintMargin(false);
    editor.getSession().setUseSoftTabs(true);
    editor.renderer.setHScrollBarAlwaysVisible(false);
    editor.focus();
    editor.on("guttermousedown", function (e) {
        var target = e.domEvent.target;
        if (target.className.indexOf("ace_gutter-cell") == -1)
            return;
        if (!editor.isFocused())
            return;
        if (e.clientX > 25 + target.getBoundingClientRect().left)
            return;
        var row = e.getDocumentPosition().row;
        if (target.className.indexOf("ace_question") != -1)
            exibeQuadroDuvida(row);
        if (target.className.indexOf("ace_comment") != -1)
            exibeQuadroAnotacao(row);
        e.stop();
    });
    if (questions)
        for (var i = 0; i < questions.length; i++)
            editor.getSession().addGutterDecoration(questions[i] - 1, "ace_question");
    if (comments)
        for (var i = 0; i < comments.length; i++)
            editor.getSession().addGutterDecoration(comments[i] - 1, "ace_comment");
}

function exibeQuadroDuvida(row)
{
    row++;
    $("#panel-markedquestions #title-linenumber").html(row);
    $("#panel-markedquestions").show("slide", "fast");
    MostraCarregando();
    var idClasse = $("#hdnIdClasse").val();
    $("#hdnQuestaoIdClasse").val(idClasse);
    $("#hdnQuestaoLinha").val(row);
    $("#panel-markedquestions .panel-body").load(baseUrl + "exercicios/showquestion/" + exercicioID + "/" + idClasse + "/" + row, function () {
        RemoveCarregando();
        $("#panel-markedquestions .panel-body").animate({scrollTop: $("#panel-markedquestions .panel-body")[0].scrollHeight}, 1000);
    });
}

function exibeQuadroAnotacao(row)
{
    row++;
    $("#panel-markedannotations #title-linenumber").html(row);
    $("#panel-markedannotations").show("slide", "fast");
    MostraCarregando();
    var idClasse = $("#hdnIdClasse").val();
    $("#hdnAnotacaoIdClasse").val(idClasse);
    $("#hdnAnotacaoLinha").val(row);
    $.get(baseUrl + "exercicios/showannotation/" + exercicioID + "/" + idClasse + "/" + row, function (data) {
        $("#anotacaoClasse").val(data[1]).focus();
        RemoveCarregando();
        $("#panel-markedannotations .panel-body").animate({scrollTop: $("#panel-markedannotations .panel-body")[0].scrollHeight}, 1000);
    });
}

function adicionarClasse(content) {

    if (content.length == 0)
    {
        if ($("#txtNomeClasse").val().length == 0)
        {
            $("#txtNomeClasse").parent().addClass("has-error");
            return;
        }
        var fileName = trataString($("#txtNomeClasse").val());
        content = newClassContent.replace(/#@#CLASSE#@#/gi, fileName).replace(/#n#/gi, "\n");
    }
    else
    {
        var fileName = content.split("#@#")[0];
        content = content.replace(fileName + "#@#", "");
    }

    CreateEditor(content, false);
    $("#hdnEditor").val("");
    $("#hdnIdClasse").val("0");
    $("#hdnNomeClasse").val(fileName);
    $("#txtNomeClasse").val("");
    $("#lblFilename").html(fileName + ".java").show();
    ControlaBotoes();
    FechaModal();
}

var intervalLookingRows = setInterval(function () {
    lookingForNewRows()
}, 500);

function lookingForNewRows()
{
    if (editor == null)
        return;
    var qtdeLinhasHtml = $(".ace_gutter-cell[id]").length;
    var qtdeLinhasEditor = editor.session.getLength();
    if (qtdeLinhasHtml != qtdeLinhasEditor)
    {
        $(".ace_gutter-cell").each(function (index) {
            $(this).attr("id", "L" + index);
        });
    }
}

function lookingForNewStatus()
{
    $.get(baseUrl + "exercicios/getstatus/" + exercicioID, function (data) {
        if (data !== undefined && data != 0 && data != 5)
            document.location.href = baseUrl + "exercicios/solucionar/" + exercicioID;
    });
}

function ControlaBotoes()
{
    var idClasse = $("#hdnIdClasse").val();
    var editorReadOnly = editor == null ? true : editor.getReadOnly();
    if (editor == null)
    {
        $(".btn-registrar-duvida, .btn-registrar-anotacao, .btn-salvar-classe, .btn-excluir-classe, .btn-habilitar-edicao, .btn-classe-favorita").slideUp("fast");
        return;
    }
    else
    {
        $(".btn-registrar-duvida, .btn-registrar-anotacao, .btn-salvar-classe, .btn-excluir-classe, .btn-habilitar-edicao, .btn-classe-favorita").slideDown("fast");
    }
    if (editorReadOnly)
    {
        $(".btn-salvar-classe").prop("disabled", "disabled");
        $(".btn-habilitar-edicao, .btn-classe-favorita").prop("disabled", "");
        $(".ace_content").css("background-color", "#f3f3f3");
    }
    else
    {
        $(".btn-salvar-classe").prop("disabled", "");
        $(".btn-habilitar-edicao, .btn-classe-favorita").prop("disabled", "disabled");
        $(".ace_content").css("background-color", "");
    }
    $(".btn-excluir-classe").prop("disabled", "");
    if (idClasse != "" && idClasse > 0 && editorReadOnly)
    {
        $(".btn-registrar-duvida, .btn-registrar-anotacao").prop("disabled", "");
    }
    else
    {
        $(".btn-registrar-duvida, .btn-registrar-anotacao, .btn-excluir-classe").prop("disabled", "disabled");
    }
}

