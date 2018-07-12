var app = angular.module('feeper', ['ui.sortable']);
app.controller('editExercicios', function ($scope, $http, $sce) {

    var exercicioId = window.location.pathname.substr(window.location.pathname.lastIndexOf("/") + 1, window.location.pathname.length);
    //se for novo não vem ID
    if (exercicioId.toString().toLowerCase() == "edit") {
        exercicioId = 0;
    }
    $scope.init = function () {

        $scope.falseValue = false;
        $scope.trueValue = true;
        $(".editor").jqte();
        //hack, para crash do uploadify
        setTimeout(function () {
            $scope.initControls();
        }, 0);
        $http({
            url: baseUrl + 'exercicios/getJson',
            method: 'POST',
            params: {
                'exercicioId': exercicioId
            }
        }).success(function (data) {
            $scope.exercicio = data[0];
            $scope.NewClassContent = data[1];
            $scope.arquivoPDFURLs = [];
            var url = {domain: $sce.trustAsResourceUrl(baseUrl + 'exercicios/verdescricao?exercicioId=' + $scope.exercicio.id)};
            $scope.arquivoPDFURLs.push(url);
            $(".jqte_editor").html($scope.exercicio.descricaoHtml);
            if ($scope.exercicio.classesAuxiliares != null) {
                for (var i = 0; i < $scope.exercicio.classesAuxiliares.length; i++) {
                    if ($scope.exercicio.classesAuxiliares[i].assinatura == null) {
                        alert(erroCarregarAssinaturasText);
                        break;
                    }
                }
            }
            setTimeout(function () {
                $scope.updateCasosControls();
            }, 100);
        });
    }

    $scope.save = function () {

        if ($scope.exercicio.nome == null || $scope.exercicio.nome.length == 0) {
            alert('Informe primeiramente o título do exercício');
            return;
        }

        $("#btnSalvar").prop("disabled", true);
        $("#btnSalvar").text("Salvando...");
        $scope.exercicio.descricaoHtml = $(".jqte_editor").html();
        $http({
            url: baseUrl + 'exercicios/saveJson',
            method: 'POST',
            data: $scope.exercicio,
        }).success(function (sucess) {
            if (sucess) {
                window.location.href = baseUrl + 'exercicios';
            } else {
                $("#btnSalvar").prop("disabled", false);
                $("#btnSalvar").text("Salvar");
                alert("Ocorreu um erro ao salvar. Favor tentar novamente!");
            }
        }
        ).error(function () {
            $("#btnSalvar").prop("disabled", false);
            $("#btnSalvar").text("Salvar");
            alert("Ocorreu um erro ao salvar. Favor tentar novamente!");
        });
    }

    $scope.voltar = function () {
        window.location.href = baseUrl + 'exercicios';
    }

    $scope.onDescricaoCarregada = function (data) {

        $scope.exercicio.idUploadTemp = data;
        $scope.arquivoPDFURLs = [];
        var url = {domain: $sce.trustAsResourceUrl(baseUrl + 'exercicios/verdescricao?uploadTempId=' + $scope.exercicio.idUploadTemp)};
        $scope.arquivoPDFURLs.push(url);
    }

    $scope.visualizaNovaClasse = function () {

        $scope.NomeNovaClasse = "";
        $.fancybox("#divNovaClasse", {
            'openEffect': 'fade',
            'closeEffect': 'fade',
            'closeClick': false,
            helpers: {
                overlay: {
                    locked: false
                }
            }
        });
    }

    $scope.onClasseCarregada = function (data) {

        if ($scope.exercicio.classesAuxiliares == null) {
            $scope.exercicio.classesAuxiliares = [];
        }

        $scope.exercicio.classesAuxiliares.push(data);
        $scope.carregarAssinaturas();
        $.fancybox.close();
    }

    $scope.carregarAssinaturas = function () {
        $http({
            url: baseUrl + 'exercicios/carregaAssinaturas',
            method: 'POST',
            data: $scope.exercicio.classesAuxiliares,
        }).success(function (data) {

            $scope.exercicio.classesAuxiliares = data;
            for (var i = 0; i < $scope.exercicio.classesAuxiliares.length; i++) {
                if ($scope.exercicio.classesAuxiliares[i].assinatura == null) {
                    alert(erroCarregarAssinaturasText);
                    break;
                }
            }
        }).error(function (message) {
            alert(erroCarregarAssinaturasText);
        });
    }

    $scope.adicionaClasse = function () {

        var novaClasse = new Object();
        novaClasse.nomeClasse = $scope.NomeNovaClasse;
        novaClasse.mostrarParaAluno = false;
        novaClasse.codigo = $scope.NewClassContent.replace(/#@#CLASSE#@#/gi, novaClasse.nomeClasse);
        if ($scope.exercicio.classesAuxiliares == null) {
            $scope.exercicio.classesAuxiliares = [];
        }

        $scope.exercicio.classesAuxiliares.push(novaClasse);
        $.fancybox.close();
        $scope.showClasseCode(novaClasse);
    }

    $scope.showClasseCode = function (classe) {

        $scope.editingClass = classe;
        $("#btnSaveClasseCode").prop("disabled", true);
        $("#btnHabilitaEdicao").prop("disabled", false);
        CreateEditor($scope.editingClass.codigo, true);
    }

    $scope.saveClasseCode = function () {

        $scope.editingClass.codigo = editor.session.getValue();
        $("#containerEditor").hide();
        $scope.editingClass = null;
        $scope.carregarAssinaturas();
    }

    $scope.habilitaEdicao = function () {

        editor.setReadOnly(false);
        $("#btnSaveClasseCode").prop("disabled", false);
        $("#btnHabilitaEdicao").prop("disabled", true);
        $(".ace_content").css("background-color", "");
    }

    $scope.ocultaEditorClasses = function () {
        $("#containerEditor").hide();
        $scope.editingClass = null;
    }

    $scope.deletaClasse = function (classe) {

        if (!confirm(confirmarExcluirText))
            return;
        $scope.ocultaEditorClasses();
        var index = $scope.exercicio.classesAuxiliares.indexOf(classe);
        $scope.exercicio.classesAuxiliares.splice(index, 1);
    }

    $scope.visualizaOpcoesClasse = function (classe) {

        $scope.editingClass = classe;
        $.fancybox("#divOpcoesClasse", {
            'openEffect': 'fade',
            'closeEffect': 'fade',
            'closeClick': false,
            helpers: {
                overlay: {
                    locked: false
                }
            }
        });
    }

    $scope.concluiOpcoesClasse = function () {

        $.fancybox.close();
        if ($scope.gerarTestesContrutores == true) {
            $scope.criaTesteConstrutores();
        }
        if ($scope.gerarTestesGetSet == true) {
            $scope.criaTesteSetters();
            $scope.criaTesteGetters();
        }
        if ($scope.gerarTestesMetodos == true) {
            $scope.criaTesteMetodos();
        }

        setTimeout(function () {
            $scope.updateCasosControls();
        }, 100);
    }

    $scope.canAutomateConstructorTest = function (constructor) {

        if (constructor.parametros != null) {
            for (var i = 0; i < constructor.parametros.length; i++) {
                var parametro = constructor.parametros[i];
                switch (parametro.type.toLowerCase()) {
                    case 'string':
                    case 'char':
                    case 'short':
                    case 'int':
                    case 'integer':
                    case 'long':
                    case 'double':
                    case 'boolean':
                        continue;
                    default :
                        return false;
                }
            }
        }
        return true;
    }

    $scope.criaTesteConstrutores = function () {

        for (var i = 0; i < $scope.editingClass.assinatura.membros.length; i++) {
            var construtor = $scope.editingClass.assinatura.membros[i];
            //somente construtores publicos
            if (construtor.memberType == 2 && construtor.modifier == 1 && $scope.canAutomateConstructorTest(construtor)) {

                var caso = new Object();
                //iniciar aqui o caso
                caso.ativo = true;
                caso.ordem = $scope.getnextCasoOrdem();
                var complemento = (construtor.parametros == null || construtor.parametros.length == 0) ? "que não recebe" : "que recebe " + construtor.parametros.length;
                caso.mensagemCompilacao = "O construtor " + complemento + " parâmetros da classe " + $scope.editingClass.nomeClasse + ", não foi implementado ou não segue a assinatura especificada.\r\n\r\nRevise este construtor!";
                var coreCodigo = "\t\t" + $scope.editingClass.nomeClasse + " ";
                coreCodigo += $scope.editingClass.nomeClasse.toLowerCase() + "1 = new ";
                coreCodigo += $scope.editingClass.nomeClasse + "(";
                if (construtor.parametros != null && construtor.parametros.length > 0) {

                    var parametros = new Array();
                    for (var j = 0; j < construtor.parametros.length; j++) {
                        var parametroConstrutor = construtor.parametros[j];
                        if (parametroConstrutor.type.toLowerCase() == "string") {
                            parametros.push("\"teste\"");
                        } else if (parametroConstrutor.type.toLowerCase() == "char") {
                            parametros.push("'C'");
                        } else if (parametroConstrutor.type.toLowerCase() == "bool" || parametroConstrutor.type.toLowerCase() == "boolean") {
                            parametros.push("true");
                        } else {
                            parametros.push("666");
                        }
                    }

                    coreCodigo += parametros.join(", ");
                }

                coreCodigo += ");\n";
                caso.codigo = $scope.getCasoCompleto(coreCodigo);
                $scope.exercicio.casosTeste.push(caso);
            }
        }
    }

    $scope.canAutomateFieldTest = function (membro) {

        switch (membro.type.toLowerCase()) {
            case 'string':
                return true;
            case 'string':
                return true;
            case 'short':
                return true;
            case 'int':
            case 'integer':
                return true;
            case 'long':
                return true;
            case 'double':
                return true;
            case 'boolean':
                return true;
        }
        return false;
    }

    $scope.methodIsGetterOrSetter = function (methodName) {
        for (var i = 0; i < $scope.editingClass.assinatura.membros.length; i++) {
            var membro = $scope.editingClass.assinatura.membros[i];
            //somente fields não publicas
            if (membro.memberType == 1 && membro.modifier != 1 && $scope.canAutomateFieldTest(membro)) {

                var setName = 'set' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
                var getName = 'get' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
                if (methodName == setName || methodName == getName)
                    return true;
            }
        }
        return false;
    }

    $scope.criaTesteSetters = function () {
        for (var i = 0; i < $scope.editingClass.assinatura.membros.length; i++) {
            var membro = $scope.editingClass.assinatura.membros[i];
            //somente fields não publicas
            if (membro.memberType == 1 && membro.modifier != 1 && $scope.canAutomateFieldTest(membro)) {

                var setName = 'set' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
                var caso = new Object();
                //iniciar aqui o caso
                caso.ativo = true;
                caso.ordem = $scope.getnextCasoOrdem();
                caso.mensagemPersonalizada = "O método " + setName + " da classe " + $scope.editingClass.nomeClasse + ", não está setando o valor do atributo " + membro.name + ".\r\n\r\n Revise o método " + setName + "!";
                caso.mensagemCompilacao = " O método de modificação do atributo " + membro.name + " da classe " + $scope.editingClass.nomeClasse + ", não foi implementado ou não segue a assinatura especificada.\r\n\r\n Revise o método(" + setName + ") e parâmetros recebidos por ele!";
                var coreCodigo = "\t\t" + $scope.editingClass.nomeClasse + " ";
                coreCodigo += $scope.editingClass.nomeClasse.toLowerCase() + "1 = new ";
                coreCodigo += $scope.editingClass.nomeClasse + "();\n";
                coreCodigo += "\t\t" + $scope.editingClass.nomeClasse.toLowerCase() + "1.";
                coreCodigo += setName + "(";
                var valor = "";
                if (membro.type.toLowerCase() == "string") {
                    valor = "\"teste\"";
                } else if (membro.type.toLowerCase() == "char") {
                    valor = "'C'";
                } else if (membro.type.toLowerCase() == "bool" || membro.type.toLowerCase() == "boolean") {
                    valor = "true";
                } else {
                    valor = "666";
                }
                coreCodigo += valor + ");\n\n";

                coreCodigo += "\t\t//get_Private_Field_Acessor é um método injetado automaticamente nas classes do aluno e que retorna o valor de uma variável privada através de reflection\n";
                coreCodigo += "\t\tAssert.assertEquals(";
                coreCodigo += "(Object)" + valor + ", ";
                coreCodigo += "(Object)" + $scope.editingClass.nomeClasse.toLowerCase() + '1.';
                coreCodigo += "get_Private_Field_Acessor(\"" + membro.name + "\"));\n";
                caso.codigo = $scope.getCasoCompleto(coreCodigo);
                $scope.exercicio.casosTeste.push(caso);
            }
        }
    }

    $scope.criaTesteGetters = function () {
        for (var i = 0; i < $scope.editingClass.assinatura.membros.length; i++) {
            var membro = $scope.editingClass.assinatura.membros[i];
            //somente fields não publicas
            if (membro.memberType == 1 && membro.modifier != 1 && $scope.canAutomateFieldTest(membro)) {

                var setName = 'set' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
                var getName = 'get' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
                var caso = new Object();
                //iniciar aqui o caso
                caso.ativo = true;
                caso.ordem = $scope.getnextCasoOrdem();
                caso.mensagemPersonalizada = "O método " + getName + "() da classe " + $scope.editingClass.nomeClasse + ", deveria ter retornado o valor do atributo " + membro.name + ", mas retornou outro valor.\r\n\r\nRevise este método!";
                caso.mensagemCompilacao = "O método de acesso ao atributo " + membro.name + " da classe " + $scope.editingClass.nomeClasse + ", não foi implementado ou não segue a assinatura especificada(" + getName + "()).\r\n\r\nRevise este método!";
                var coreCodigo = "\t\t" + $scope.editingClass.nomeClasse + " ";
                coreCodigo += $scope.editingClass.nomeClasse.toLowerCase() + "1 = new ";
                coreCodigo += $scope.editingClass.nomeClasse + "();\n";
                coreCodigo += "\t\t" + $scope.editingClass.nomeClasse.toLowerCase() + "1.";
                coreCodigo += setName + "(";
                var valor = "";
                if (membro.type.toLowerCase() == "string") {
                    valor = "\"teste\"";
                } else if (membro.type.toLowerCase() == "char") {
                    valor = "'C'";
                } else if (membro.type.toLowerCase() == "bool" || membro.type.toLowerCase() == "boolean") {
                    valor = "true";
                } else {
                    valor = "666";
                }
                coreCodigo += valor + ");\n";
                coreCodigo += "\t\tAssert.assertEquals(";
                coreCodigo += "(Object)" + valor + ", ";
                coreCodigo += "(Object)" + $scope.editingClass.nomeClasse.toLowerCase() + '1.';
                coreCodigo += getName + "());\n";
                caso.codigo = $scope.getCasoCompleto(coreCodigo);
                $scope.exercicio.casosTeste.push(caso);
            }
        }
    }

    $scope.canAutomateMethodTest = function (method) {

        if (method.parametros != null) {
            for (var i = 0; i < method.parametros.length; i++) {
                var parametro = method.parametros[i];
                switch (parametro.type.toLowerCase()) {
                    case 'string':
                    case 'char':
                    case 'short':
                    case 'int':
                    case 'integer':
                    case 'long':
                    case 'double':
                    case 'boolean':
                        continue;
                    default :
                        return false;
                }
            }
        }
        return true;
    }

    $scope.criaTesteMetodos = function () {

        for (var i = 0; i < $scope.editingClass.assinatura.membros.length; i++) {
            var membro = $scope.editingClass.assinatura.membros[i];
            //somente métodos publicos
            if (membro.memberType == 3 && membro.modifier == 1 && $scope.canAutomateMethodTest(membro) && $scope.methodIsGetterOrSetter(membro.name) == false) {

                var caso = new Object();
                //iniciar aqui o caso
                caso.ativo = true;
                caso.ordem = $scope.getnextCasoOrdem();
                var complemento = (membro.parametros == null || membro.parametros.length == 0) ? ", que não recebe" : ", que recebe " + membro.parametros.length;
                if (membro.type == "void")
                {
                    caso.mensagemCompilacao = "O método " + membro.name + " da classe " + $scope.editingClass.nomeClasse + complemento + " parâmetros, não foi implementado ou não segue a assinatura especificada.\r\n\r\nRevise este método!";
                } else
                {
                    caso.mensagemCompilacao = "O método " + membro.name + " da classe " + $scope.editingClass.nomeClasse + complemento + " parâmetros e que retorna um valor do tipo " + membro.type + ", não foi implementado ou não segue a assinatura especificada.\r\n\r\nRevise este método!";
                }

                var coreCodigo = "\t\t" + $scope.editingClass.nomeClasse + " ";
                coreCodigo += $scope.editingClass.nomeClasse.toLowerCase() + "1 = new ";
                coreCodigo += $scope.editingClass.nomeClasse + "();\n";
                if (membro.type != "void") {
                    coreCodigo += "\t\t" + membro.type + " " + membro.type.toLowerCase() + "1 = ";
                    coreCodigo += $scope.editingClass.nomeClasse.toLowerCase() + "1.";
                } else {
                    coreCodigo += "\t\t" + $scope.editingClass.nomeClasse.toLowerCase() + "1.";
                }

                coreCodigo += membro.name + "(";
                if (membro.parametros != null && membro.parametros.length > 0) {

                    var parametros = new Array();
                    for (var j = 0; j < membro.parametros.length; j++) {
                        var parametro = membro.parametros[j];
                        if (parametro.type.toLowerCase() == "string") {
                            parametros.push("\"teste\"");
                        } else if (parametro.type.toLowerCase() == "char") {
                            parametros.push("'C'");
                        } else if (parametro.type.toLowerCase() == "bool" || parametro.type.toLowerCase() == "boolean") {
                            parametros.push("true");
                        } else {
                            parametros.push("666");
                        }
                    }

                    coreCodigo += parametros.join(", ");
                }

                coreCodigo += ");\n";
                caso.codigo = $scope.getCasoCompleto(coreCodigo);
                $scope.exercicio.casosTeste.push(caso);
            }
        }
    }

    $scope.adicionaCasoTeste = function () {
        if ($scope.exercicio.casosTeste == null)
            $scope.exercicio.casosTeste = [];
        var caso = new Object();
        //iniciar aqui o caso
        caso.ativo = true;
        caso.ordem = $scope.getnextCasoOrdem();
        var template = "\t\t//COMO ESCREVER VALORES NO SYSTEM.IN, EX:\n"
        template += "\t\t//String userInput = \"linha1\" + System.getProperty(\"line.separator\") + \"linha2\"\n";
        template += "\t\t//System.setIn(new ByteArrayInputStream(userInput.getBytes()));\n\n";

        template += "\t\t//COMO LER VALORES DO SYSTEM.OUT, EX:\n";
        template += "\t\t//String programOutput = outContent.toString();\n\n";

        template += "\t\t//COMO FAZER VERIFICAÇÕES COM O JUNIT, VEJA AQUI:\n";
        template += "\t\t//http://junit.sourceforge.net/javadoc/org/junit/Assert.html\n\n";

        template += "\t\t//FAÇA O SEU TESTE AQUI:\n";

        caso.codigo = $scope.getCasoCompleto(template);
        $scope.exercicio.casosTeste.push(caso);

        setTimeout(function () {
            $scope.updateCasosControls();
        }, 100);
    }

    $scope.duplicaCasoTeste = function (casoTeste) {

        var novoCaso = JSON.parse(JSON.stringify(casoTeste))
        novoCaso.id = 0;
        novoCaso.ordem = $scope.getnextCasoOrdem();
        var index = $scope.exercicio.casosTeste.indexOf(casoTeste);
        $scope.exercicio.casosTeste.splice(index, 0, novoCaso);
        for (var i = 0; i < $scope.exercicio.casosTeste.length; i++) {

            $scope.exercicio.casosTeste[i].ordem = i + 1;
        }

        setTimeout(function () {
            $scope.updateCasosControls();
        }, 100);
    }

    $scope.copiaCasoTeste = function (casoTeste) {

        $.cookie('casoTesteCopiado', JSON.stringify(casoTeste));
    }

    $scope.possuiCasoTesteCopiado = function () {

        var data = $.cookie('casoTesteCopiado');
        return data != null && data.length > 0;
    }

    $scope.colaCasoTeste = function () {

        var data = $.cookie('casoTesteCopiado');
        if (data == null || data.length == 0)
            return;
        var caso = JSON.parse(data);
        caso.id = 0;
        caso.ordem = $scope.getnextCasoOrdem();
        $scope.exercicio.casosTeste.push(caso);

        setTimeout(function () {
            $scope.updateCasosControls();
        }, 100);
    }

    $scope.deletaCasoTeste = function (casoTeste) {

        if (!confirm(confirmarExcluirText))
            return;
        var index = $scope.exercicio.casosTeste.indexOf(casoTeste);
        $scope.exercicio.casosTeste.splice(index, 1);
    }

    $scope.editaCasoTeste = function (casoTeste) {

        $scope.editingCasoTeste = casoTeste;
        $.fancybox("#divEditCaso", {
            'autoSize': false,
            'width': '90%',
            'height': '90%',
            'openEffect': 'fade',
            'closeEffect': 'fade',
            'closeClick': false,
            'closeBtn': true,
            'beforeClose': function () {

                $scope.$apply(function () {
                    $scope.concluiEdicaoCasoTeste();
                });
            },
            'onUpdate': function () {

                editorCaso.renderer.updateFull(true);
            },
            helpers: {
                overlay: {
                    locked: false
                }
            }
            ,
        });

        CreateEditorCaso($scope.editingCasoTeste.codigo);
    }

    $scope.concluiEdicaoCasoTeste = function () {

        $scope.editingCasoTeste.codigo = editorCaso.session.getValue();
    }

    $scope.showToolTip = function (elementID, placement, message) {
        $('#' + elementID).tooltip({
            html: true, placement: placement,
            title: message
        });
        $('#' + elementID).focus();
        $('#' + elementID).blur(function () {
            $('#' + elementID).tooltip('hide');
            $('#' + elementID).tooltip('destroy');
        });
    }

    $scope.getnextCasoOrdem = function () {
        if ($scope.exercicio.casosTeste == null || $scope.exercicio.casosTeste.length == 0)
            return 1;
        else
            return $scope.exercicio.casosTeste[$scope.exercicio.casosTeste.length - 1].ordem + 1;
    }

    $scope.containsValue = function (array, value) {
        for (var i = 0; i < array.length; i++) {
            if (array[i].value == value)
                return true;
        }
        return false;
    }

    $scope.initControls = function () {

        $("#menu-lista-exercicio").addClass("active");
        var settings = {
            url: baseUrl + 'exercicios/uploaddescricao',
            dragDrop: false,
            allowedTypes: "pdf",
            returnType: "json",
            onSuccess: function (files, data, xhr)
            {
                $scope.$apply($scope.onDescricaoCarregada(data));
            },
            showDelete: false,
            showDone: false,
            showAbort: false,
            showStatusAfterSuccess: false,
            maxFileSize: 512000,
            multiple: false,
            uploadButtonClass: "btn btn-primary"
        }
        $("#upload_descricao").uploadFile(settings);
        settings = {
            url: baseUrl + 'exercicios/uploadClasseAuxiliar',
            dragDrop: false,
            allowedTypes: "java",
            returnType: "json",
            onSuccess: function (files, data, xhr)
            {
                $scope.$apply($scope.onClasseCarregada(data));
            },
            showDelete: false,
            showDone: false,
            showAbort: false,
            showStatusAfterSuccess: false,
            maxFileSize: 512000,
            multiple: false,
            uploadButtonClass: "btn btn-primary btn-xs"
        }
        $("#upload_Classe_Auxiliar").uploadFile(settings);
    }

    $scope.sortableCasosOptions = {
        handle: '.casoHandle',
        axis: 'y',
        stop: function (e, ui) {
            for (var i = 0; i < $scope.exercicio.casosTeste.length; i++) {
                $scope.exercicio.casosTeste[i].ordem = i + 1;
            }
        }
    };
    $scope.updateCasosControls = function () {

        $(".mensagemProfessor").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: baseUrl + 'mensagenspredefinidas/search?term=' + request.term,
                    type: 'GET',
                    dataType: 'json'
                }).done(function (data) {
                    response($.map(data, function (item) {
                        return {label: item, value: item};
                    }));
                }).fail(function () {
                    $('.ui-autocomplete-loading').removeClass("ui-autocomplete-loading");
                });
            },
            select: function (event, ui) {
                $(event.target).val(ui.item.value);
                $(event.target).trigger("change");
            },
            minLength: 0
        });
    }

    $scope.getCasoCompleto = function (coreCaso) {

        var casoTeste = "";
        casoTeste += "import java.io.ByteArrayInputStream;\n";
        casoTeste += "import java.io.ByteArrayOutputStream;\n";
        casoTeste += "import java.io.InputStream;\n";
        casoTeste += "import java.io.PrintStream;\n";
        casoTeste += "import java.util.Arrays;\n";
        casoTeste += "import java.util.Collection;\n";
        casoTeste += "import java.util.Scanner;\n";
        casoTeste += "import org.junit.After;\n";
        casoTeste += "import org.junit.Before;\n";
        casoTeste += "import org.junit.Test;\n";
        casoTeste += "import org.junit.Assert;\n\n\n";
        casoTeste += "//ATENÇÃO : NÃO MUDAR O NOME DOS TESTES\n";
        casoTeste += "public class teste_Feeper {\n\n";
        casoTeste += "\tprivate InputStream stdin = null;\n";
        casoTeste += "\tprivate PrintStream stdout = null;\n";
        casoTeste += "\tprivate final ByteArrayOutputStream outContent = new ByteArrayOutputStream();\n\n";
        casoTeste += "\t@Before\n";
        casoTeste += "\tpublic void setUpStreams() {\n\n";
        casoTeste += "\t\tstdin = System.in;\n";
        casoTeste += "\t\tstdout = System.out;\n";
        casoTeste += "\t\tSystem.setOut(new PrintStream(outContent));\n";
        casoTeste += "\t}\n\n\n";
        casoTeste += "\t@Test\n";
        casoTeste += "\tpublic void test() throws Exception {\n\n";
        casoTeste += coreCaso;
        casoTeste += "\t}\n\n\n";
        casoTeste += "\t@After\n";
        casoTeste += "\tpublic void cleanUpStreams() {\n\n";
        casoTeste += "\t\tSystem.setIn(stdin);\n";
        casoTeste += "\t\tSystem.setOut(stdout);\n";
        casoTeste += "\t}\n";
        casoTeste += "}";
        return casoTeste;
    }

    $.ajaxSetup({cache: false});
    $scope.init();
});
function CreateEditor(source, readOnly)
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
    $(".ace_content").css("background-color", "#f3f3f3");
    editor.focus();
    $("#containerEditor").show();
    $('html, body').animate({scrollTop: $("#containerEditor").offset().top - 100}, 1000);
}

function CreateEditorCaso(source)
{
    try {
        editorCaso.destroy();
        $(" #editorCaso").remove();
    } catch (e) {
    }

    $("#panelEditorCaso").append($('<div id="editorCaso"></div>'));
    editorCaso = ace.edit("editorCaso");
    editorCaso.session.setValue(source);
    editorCaso.container.style.opacity = "";
    editorCaso.setOptions({
        mode: "ace/mode/java",
    });
    editorCaso.setReadOnly(false);
    editorCaso.setTheme("ace/theme/eclipse");
    editorCaso.setShowPrintMargin(false);
    editorCaso.getSession().setUseSoftTabs(true);
    editorCaso.renderer.setHScrollBarAlwaysVisible(false);
    editorCaso.focus();
}