var app = angular.module('feeper', []);
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
            }
            else {
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
        novaClasse.codigo = $scope.NewClassContent.replace(/#@#CLASSE#@#/gi, novaClasse.nomeClasse).replace(/#n#/gi, "\n");

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

    $scope.criaTesteConstrutores = function () {

        for (var i = 0; i < $scope.editingClass.assinatura.membros.length; i++) {
            var membro = $scope.editingClass.assinatura.membros[i];
            //somente construtores publicos
            if (membro.memberType == 2 && membro.modifier == 1 && $scope.canAutomateConstructorTest(membro)) {

                var caso = new Object();
                //iniciar aqui o caso
                caso.ativo = true;
                caso.ordem = $scope.getnextCasoOrdem();
                caso.mensagemCompilacao = "Você não inseriu um construtor com " + (membro.parametros == null ? 0 : membro.parametros.length) + " parâmetros na classe " + $scope.editingClass.nomeClasse;
                caso.passos = [];
                var passo = new Object();
                passo.ordem = $scope.getnextPassoOrdem(caso);
                passo.operationType = 1;
                passo.expectedOutputType = $scope.editingClass.nomeClasse;
                passo.expectedOutputName = $scope.editingClass.nomeClasse.toLowerCase() + '1';
                passo.objectName = $scope.editingClass.nomeClasse;
                passo.inputParameters = [];

                if (membro.parametros != null) {
                    for (var j = 0; j < membro.parametros.length; j++) {
                        var parametroConstrutor = membro.parametros[j];

                        var parametro = new Object();
                        parametro.ordem = $scope.getnextParametroOrdem(passo);
                        parametro.objectType = parametroConstrutor.type;
                        var valor = null
                        if (parametroConstrutor.type.toLowerCase() == 'string') {
                            valor = 'teste';
                        }
                        else if (parametroConstrutor.type.toLowerCase() == 'bool' || parametroConstrutor.type.toLowerCase() == 'boolean') {
                            valor = true;
                        }
                        else {
                            valor = 666;
                        }
                        parametro.objectValue = valor;
                        passo.inputParameters.push(parametro);
                    }
                }
                caso.passos.push(passo);
                $scope.exercicio.casosTeste.push(caso);
            }
        }
    }

    $scope.canAutomateConstructorTest = function (constructor) {

        if (constructor.parametros != null) {
            for (var i = 0; i < constructor.parametros.length; i++) {
                var parametro = constructor.parametros[i];
                switch (parametro.type.toLowerCase()) {
                    case 'string':
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
                caso.mensagemPersonalizada = "O método " + setName + " não está setando o valor do atributo " + membro.name + ". Verifique o método " + setName + "!";
                caso.mensagemCompilacao = " O método de modificação do atributo " + membro.name + " não possui a assinatura esperada. Revise o nome(" + setName + "), parâmetros recebidos e retornados desse método!";
                caso.passos = [];
                var passo1 = new Object();
                passo1.ordem = $scope.getnextPassoOrdem(caso);
                passo1.operationType = 1;
                passo1.expectedOutputType = $scope.editingClass.nomeClasse;
                passo1.expectedOutputName = $scope.editingClass.nomeClasse.toLowerCase() + '1';
                passo1.objectName = $scope.editingClass.nomeClasse;
                caso.passos.push(passo1);
                var passo2 = new Object();
                passo2.ordem = $scope.getnextPassoOrdem(caso);
                passo2.operationType = 2;
                passo2.objectName = $scope.editingClass.nomeClasse.toLowerCase() + '1';
                passo2.methodName = setName;
                passo2.inputParameters = []
                var parametro = new Object();
                parametro.ordem = $scope.getnextParametroOrdem(passo2);
                parametro.objectType = membro.type;
                var valor = null
                if (membro.type.toLowerCase() == 'string') {
                    valor = membro.name + '_Teste';
                }
                else if (membro.type.toLowerCase() == 'bool' || membro.type.toLowerCase() == 'boolean') {
                    valor = true;
                }
                else {
                    valor = 666;
                }
                parametro.objectValue = valor;
                passo2.inputParameters.push(parametro);
                caso.passos.push(passo2);
                var passo3 = new Object();
                passo3.ordem = $scope.getnextPassoOrdem(caso);
                passo3.operationType = 3;
                passo3.expectedOutputType = membro.type;
                passo3.expectedOutputName = valor;
                passo3.objectName = passo2.objectName = $scope.editingClass.nomeClasse.toLowerCase() + '1';
                passo3.methodName = "get_Private_Field_Acessor";
                passo3.inputParameters = [];
                parametro = new Object();
                parametro.ordem = $scope.getnextParametroOrdem(passo3);
                parametro.objectType = "String";
                parametro.objectValue = membro.name;
                passo3.inputParameters.push(parametro);
                caso.passos.push(passo3);
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
                caso.mensagemPersonalizada = "O método " + getName + "() deveria ter retornado o valor do atributo " + membro.name + " , mas retornou outro valor. Verifique esse método!";
                caso.mensagemCompilacao = "O método de acesso ao atributo " + membro.name + " não possui a assinatura esperada(" + getName + "()).Revise esse método!";
                caso.passos = [];
                var passo1 = new Object();
                passo1.ordem = $scope.getnextPassoOrdem(caso);
                passo1.operationType = 1;
                passo1.expectedOutputType = $scope.editingClass.nomeClasse;
                passo1.expectedOutputName = $scope.editingClass.nomeClasse.toLowerCase() + '1';
                passo1.objectName = $scope.editingClass.nomeClasse;
                caso.passos.push(passo1);
                var passo2 = new Object();
                passo2.ordem = $scope.getnextPassoOrdem(caso);
                passo2.operationType = 2;
                passo2.objectName = $scope.editingClass.nomeClasse.toLowerCase() + '1';
                passo2.methodName = setName;
                passo2.inputParameters = []
                var parametro = new Object();
                parametro.ordem = $scope.getnextParametroOrdem(passo2);
                parametro.objectType = membro.type;
                var valor = null
                if (membro.type.toLowerCase() == 'string') {
                    valor = membro.name + '_Teste';
                }
                else if (membro.type.toLowerCase() == 'bool' || membro.type.toLowerCase() == 'boolean') {
                    valor = true;
                }
                else {
                    valor = 666;
                }
                parametro.objectValue = valor;
                passo2.inputParameters.push(parametro);
                caso.passos.push(passo2);
                var passo3 = new Object();
                passo3.ordem = $scope.getnextPassoOrdem(caso);
                passo3.operationType = 3;
                passo3.expectedOutputType = membro.type;
                passo3.expectedOutputName = valor;
                passo3.objectName = passo2.objectName = $scope.editingClass.nomeClasse.toLowerCase() + '1';
                passo3.methodName = getName;
                caso.passos.push(passo3);
                $scope.exercicio.casosTeste.push(caso);
            }
        }
    }

    $scope.canAutomateFieldTest = function (membro) {

        switch (membro.type.toLowerCase()) {
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

    $scope.criaTesteMetodos = function () {

        for (var i = 0; i < $scope.editingClass.assinatura.membros.length; i++) {
            var membro = $scope.editingClass.assinatura.membros[i];
            //somente métodos publicos
            if (membro.memberType == 3 && membro.modifier == 1 && $scope.canAutomateMethodTest(membro) && $scope.methodIsGetterOrSetter(membro.name) == false) {

                var caso = new Object();
                //iniciar aqui o caso
                caso.ativo = true;
                caso.ordem = $scope.getnextCasoOrdem();
                if (membro.type == "void")
                {
                    caso.mensagemCompilacao = "Você deveria ter implementado um método " + membro.name + " que recebe " + (membro.parametros == null ? 0 : membro.parametros.length) + " parâmetros!";
                }
                else
                {
                    caso.mensagemCompilacao = "Você deveria ter implementado um método " + membro.name + " que recebe " + (membro.parametros == null ? 0 : membro.parametros.length) + " parâmetros e retorna um valor do tipo " + membro.type + "!";
                }
                caso.passos = [];
                var passo1 = new Object();
                passo1.ordem = $scope.getnextPassoOrdem(caso);
                passo1.operationType = 1;
                passo1.expectedOutputType = $scope.editingClass.nomeClasse;
                passo1.expectedOutputName = $scope.editingClass.nomeClasse.toLowerCase() + '1';
                passo1.objectName = $scope.editingClass.nomeClasse;
                caso.passos.push(passo1);
                var passo2 = new Object();
                passo2.ordem = $scope.getnextPassoOrdem(caso);
                if (membro.type == "void") {
                    passo2.operationType = 2;
                }
                else {
                    passo2.operationType = 1;
                    passo2.expectedOutputType = membro.type;
                    passo2.expectedOutputName = membro.type.toLowerCase() + '1';
                }
                passo2.objectName = $scope.editingClass.nomeClasse.toLowerCase() + '1';
                passo2.methodName = membro.name;
                passo2.inputParameters = []

                if (membro.parametros != null) {
                    for (var j = 0; j < membro.parametros.length; j++) {
                        var parametroConstrutor = membro.parametros[j];

                        var parametro = new Object();
                        parametro.ordem = $scope.getnextParametroOrdem(passo2);
                        parametro.objectType = parametroConstrutor.type;
                        var valor = null
                        if (parametroConstrutor.type.toLowerCase() == 'string') {
                            valor = 'teste';
                        }
                        else if (parametroConstrutor.type.toLowerCase() == 'bool' || parametroConstrutor.type.toLowerCase() == 'boolean') {
                            valor = true;
                        }
                        else {
                            valor = 666;
                        }
                        parametro.objectValue = valor;
                        passo2.inputParameters.push(parametro);
                    }
                }
                caso.passos.push(passo2);
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

    $scope.adicionaCasoTeste = function () {
        if ($scope.exercicio.casosTeste == null)
            $scope.exercicio.casosTeste = [];
        var caso = new Object();
        //iniciar aqui o caso
        caso.ativo = true;
        caso.ordem = $scope.getnextCasoOrdem();
        $scope.exercicio.casosTeste.push(caso);

        setTimeout(function () {
            $scope.updateCasosControls();
        }, 100);
    }

    $scope.duplicaCasoTeste = function (casoTeste) {

        var novoCaso = JSON.parse(JSON.stringify(casoTeste))
        novoCaso.id = 0;
        novoCaso.ordem = casoTeste.ordem + 1;
        if (novoCaso.passos != null) {
            for (var i = 0; i < novoCaso.passos.length; i++) {
                var passo = novoCaso.passos[i];
                passo.id = 0;
                passo.idCasoTeste = 0;
                if (passo.inputParameters != null) {
                    for (var j = 0; j < passo.inputParameters.length; j++) {
                        var item = passo.inputParameters[j];
                        item.id = 0;
                        item.idPasso = 0;
                    }
                }
            }

        }
        var index = $scope.exercicio.casosTeste.indexOf(casoTeste);
        $scope.exercicio.casosTeste.splice(index, 0, novoCaso);
        for (var i = index + 1; i < $scope.exercicio.casosTeste.length; i++) {

            $scope.exercicio.casosTeste[i].ordem += 1;
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

    $scope.editaPassosCasoTeste = function (casoTeste) {
        $scope.editingCasoTeste = casoTeste;
        $.fancybox("#divEditPassos", {
            'openEffect': 'fade',
            'closeEffect': 'fade',
            'closeClick': false,
            'closeBtn': false,
            helpers: {
                overlay: {
                    locked: false
                }
            }
        });
        setTimeout(function () {
            $scope.updatePassosControls();
        }, 100);
    }

    $scope.concluiEdicaoPassosCasoTeste = function () {

        if ($scope.validaCasoTeste() != true) {
            return;
        }

        $.fancybox.close();
    }
    $scope.validaCasoTeste = function () {

        if ($scope.editingCasoTeste.passos == null || $scope.editingCasoTeste.passos.length == 0)
            return true;

        var lacosAbertos = 0;
        var openLacoIndex = 0;

        for (var i = 0; i < $scope.editingCasoTeste.passos.length; i++) {
            var item = $scope.editingCasoTeste.passos[i];

            var outputType = item.expectedOutputType;
            var outputName = item.expectedOutputName;
            var comparisionType = item.comparisionType;
            var objectName = item.objectName;
            var methodName = item.methodName;

            if (item.operationType == 1) {
                if (outputName == null || outputName.length == 0) {
                    $scope.showToolTip('expectedOutputName_' + i, 'right', 'Por favor especificar aqui o nome do objeto a qual o valor será atribuído.');
                    return false;
                } else if ((outputType == null || outputType.length == 0) && $scope.isDeclaredObject(outputName, item) == false) {
                    $scope.showToolTip('expectedOutputType_' + i, 'right', 'É necessário especificar o tipo dos objetos que não foram inicializados anteriormente.');
                    return false;
                } else if ((objectName == null || objectName.length == 0)) {

                    $scope.showToolTip('objectName_' + i, 'left', 'Por favor especificar aqui alguma das seguintes opções:<br/><br/>1. O nome da classe para chamar o construtor.<br/>2. O nome de algum objeto instanciado anteriormente.<br/>3. O valor caso esteja instanciando um tipo primitivo.');
                    return false;
                }
            }
            else if (item.operationType == 2) {

                if (objectName == null || objectName.length == 0 || $scope.isDeclaredObject(objectName, item) == false) {

                    $scope.showToolTip('objectName_' + i, 'left', 'Por favor especificar aqui o nome de algum objeto instanciado anteriormente.');
                    return false;
                } else if (methodName == null || methodName.length == 0) {

                    $scope.showToolTip('methodName_' + i, 'left', 'Por favor especificar aqui o método do objeto especificado que será executado.');
                    return false;
                }
            }
            else if (item.operationType == 3) {
                if (outputName == null || outputName.length == 0) {
                    $scope.showToolTip('expectedOutputName_' + i, 'right', 'Por favor especificar aqui alguma das seguintes opções:<br/><br/>1. O nome de algum objeto inicializado anteriormente.<br/>2. O valor a ser comparado caso esteja comparando tipos primitivos.');
                    return false;
                } else if ((outputType == null || outputType.length == 0) && $scope.isDeclaredObject(outputName, item) == false) {
                    $scope.showToolTip('expectedOutputType_' + i, 'right', 'É necessário especificar o tipo dos objetos que não foram inicializados anteriormente.');
                    return false;
                } else if ((objectName == null || objectName.length == 0)) {

                    $scope.showToolTip('objectName_' + i, 'left', 'Por favor especificar aqui alguma das seguintes opções:<br/><br/>1. O nome de algum objeto instanciado anteriormente.<br/>2. O valor a ser comparado caso esteja comparando tipos primitivos.');
                    return false;
                }
            }
            else if (item.operationType == 4) {
                lacosAbertos += 1;
                openLacoIndex = i;
                if (outputName == null || outputName.length == 0) {

                    $scope.showToolTip('expectedOutputName_' + i, 'right', 'Por favor especificar aqui alguma das seguintes opções:<br/><br/>1. O nome de algum objeto inicializado anteriormente.<br/>2. O valor a ser interado caso esteja interando tipos primitivos.');
                    return false;
                } else if ((outputType == null || outputType.length == 0) && $scope.isDeclaredObject(outputName, item) == false) {

                    $scope.showToolTip('expectedOutputType_' + i, 'right', 'É necessário especificar o tipo dos objetos que não foram inicializados anteriormente.');
                    return false;
                } else if ((comparisionType == null || comparisionType.length == 0)) {

                    $scope.showToolTip('comparisionType_' + i, 'right', 'É necessário especificar o tipo de comparação da interação.');
                    return false;
                }
                else if ((objectName == null || objectName.length == 0)) {

                    $scope.showToolTip('objectName_' + i, 'left', 'Por favor especificar aqui alguma das seguintes opções:<br/><br/>1. O nome de algum objeto instanciado anteriormente.<br/>2. O valor a ser interado caso esteja interando tipos primitivos.');
                    return false;
                }
            }
            else if (item.operationType == 5) {
                lacosAbertos -= 1;
            }

            if (item.inputParameters != null) {
                for (var j = 0; j < item.inputParameters.length; j++) {
                    var parametro = item.inputParameters[j];

                    var parameterType = parametro.objectType;
                    var parameterValue = parametro.objectValue;

                    if (parameterValue == null || parameterValue.length == 0) {

                        $scope.showToolTip('parameterValue_' + i + '_' + j, 'left', 'Por favor especificar aqui alguma das seguintes opções:<br/><br/>1. O nome de algum objeto inicializado anteriormente.<br/>2. O valor do parâmetro caso ele seja de um tipo primitivo.');
                        return false;
                    } else if ((parameterType == null || parameterType.length == 0) && $scope.isDeclaredObject(parameterValue, item) == false) {

                        $scope.showToolTip('parameterType_' + i + '_' + j, 'left', 'É necessário especificar o tipo dos objetos que não foram inicializados anteriormente.');
                        return false;
                    }
                }
            }
        }

        if (lacosAbertos > 0) {
            $scope.showToolTip('operationType_' + openLacoIndex, 'right', 'Você deve fechar todos os laços abertos.');
            return false;
        }
        return true;
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

    $scope.adicionaPasso = function () {
        if ($scope.editingCasoTeste.passos == null)
            $scope.editingCasoTeste.passos = [];
        var passo = new Object();
        //iniciar aqui o Passo
        passo.ordem = $scope.getnextPassoOrdem($scope.editingCasoTeste);
        passo.operationType = 1;
        $scope.editingCasoTeste.passos.push(passo);
        $.fancybox.update();
        setTimeout(function () {
            $scope.updatePassosControls();
        }, 100);
    }

    $scope.duplicaPasso = function (passo) {
        var novoPasso = JSON.parse(JSON.stringify(passo))
        novoPasso.id = 0;
        novoPasso.ordem = passo.ordem + 1;
        if (novoPasso.inputParameters != null) {
            for (var j = 0; j < novoPasso.inputParameters.length; j++) {
                var item = novoPasso.inputParameters[j];
                item.id = 0;
            }
        }

        var index = $scope.editingCasoTeste.passos.indexOf(passo);
        $scope.editingCasoTeste.passos.splice(index + 1, 0, novoPasso);
        $scope.expectedOutputNameBlured(novoPasso);

        for (var i = index + 1; i < $scope.editingCasoTeste.passos.length; i++) {

            $scope.editingCasoTeste.passos[i].ordem += 1;
        }
        $.fancybox.update();
        setTimeout(function () {
            $scope.updatePassosControls();
        }, 100);
    }
    $scope.copiaPassos = function () {

        var passos = new Array();
        for (var i = 0; i < $scope.editingCasoTeste.passos.length; i++) {
            if ($scope.editingCasoTeste.passos[i].selected)
                passos.push($scope.editingCasoTeste.passos[i]);
        }
        if (passos.length > 0) {
            $.cookie('passosCopiados', JSON.stringify(passos));
        }
    }

    $scope.possuiPassoSelecionado = function () {

        if ($scope.editingCasoTeste == null || $scope.editingCasoTeste.passos == null)
            return  false;
        for (var i = 0; i < $scope.editingCasoTeste.passos.length; i++) {
            if ($scope.editingCasoTeste.passos[i].selected)
                return  true;
        }
        return false;
    }

    $scope.possuiPassoCopiado = function () {

        var data = $.cookie('passosCopiados');
        return data != null && data.length > 0;
    }

    $scope.colaPassos = function () {

        var data = $.cookie('passosCopiados');
        if (data == null || data.length == 0)
            return;
        if ($scope.editingCasoTeste.passos == null)
            $scope.editingCasoTeste.passos = [];
        var passos = JSON.parse(data);
        for (var i = 0; i < passos.length; i++) {

            var passo = passos[i];
            passo.id = 0;
            passo.ordem = $scope.getnextPassoOrdem($scope.editingCasoTeste);
            if (passo.inputParameters != null) {
                for (var j = 0; j < passo.inputParameters.length; j++) {
                    var item = passo.inputParameters[j];
                    item.id = 0;
                }
            }

            $scope.editingCasoTeste.passos.push(passo);
            $scope.expectedOutputNameBlured(passo);
        }

        $.fancybox.update();
        setTimeout(function () {
            $scope.updatePassosControls();
        }, 100);
    }

    $scope.deletaPasso = function (passo) {

        if (!confirm(confirmarExcluirText))
            return;
        var index = $scope.editingCasoTeste.passos.indexOf(passo);
        $scope.editingCasoTeste.passos.splice(index, 1);
        $.fancybox.update();
    }

    $scope.getnextPassoOrdem = function (casoDeTeste) {
        if (casoDeTeste.passos == null || casoDeTeste.passos.length == 0)
            return 1;
        else
            return casoDeTeste.passos[casoDeTeste.passos.length - 1].ordem + 1;
    }

    $scope.isExpectedOutputTypeDisabled = function (passo) {

        if (passo.operationType == 2)
            return true;
        else if (passo.operationType == 5)
            return true;
        else if ($scope.isDeclaredObject(passo.expectedOutputName, passo))
            return true;

        return false;
    }

    $scope.isExpectedOutputNameDisabled = function (passo) {

        if (passo.operationType == 2)
            return true;
        else if (passo.operationType == 5)
            return true;

        return false;
    }

    $scope.isObjectNameDisabled = function (passo) {

        if (passo.operationType == 5)
            return true;

        return false;
    }

    $scope.isMethodNameDisabled = function (passo) {

        if (passo.objectName == 'System.Out')
            return true;
        if (passo.operationType == 5)
            return true;

        return false;
    }

    $scope.isParameterTypeDisabled = function (passo, parametro) {

        if ($scope.isDeclaredObject(parametro.objectValue, passo))
            return true;

        return false;
    }

    $scope.isAddParameterDisabled = function (passo) {

        if (passo.operationType == 5)
            return true;

        return false;
    }

    $scope.operationTypeChanged = function (passo) {
        if (passo.operationType == 2) {
            passo.expectedOutputName = "";
            passo.expectedOutputType = "";
        } else if (passo.operationType == 5) {
            passo.expectedOutputName = "";
            passo.expectedOutputType = "";
            passo.comparisionType = "";
            passo.objectName = "";
            passo.methodName = "";
            passo.inputParameters = [];
        }

        setTimeout(function () {
            $scope.updatePassosControls();
        }, 100);
    }

    $scope.expectedOutputNameBlured = function (passo) {
        if ($scope.isDeclaredObject(passo.expectedOutputName, passo)) {
            passo.expectedOutputType = "";
        }
    }

    $scope.objectNameChanged = function (passo) {
        if (passo.objectName == 'System.Out') {
            passo.methodName = "";
            passo.inputParameters = [];
        }

        setTimeout(function () {
            $scope.updatePassosControls();
        }, 100);
    }

    $scope.methodNameChanged = function (passo) {

        if ($scope.exercicio.classesAuxiliares == null || (passo.inputParameters != null && passo.inputParameters.length > 0))
            return;
        var objectType = $scope.getObjectType(passo.objectName);
        for (var i = 0; i < $scope.exercicio.classesAuxiliares.length; i++) {
            var assinatura = $scope.exercicio.classesAuxiliares[i].assinatura;
            if (assinatura == null || assinatura.nomeClasse != objectType)
                continue;
            for (var i = 0; i < assinatura.membros.length; i++) {
                var membro = assinatura.membros[i];
                //se é um setter
                if (membro.memberType == 1) {

                    var setter = 'set' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
                    if (passo.methodName == setter) {

                        passo.inputParameters = [];
                        var parametro = new Object();
                        parametro.ordem = $scope.getnextParametroOrdem(passo);
                        parametro.objectType = membro.type;
                        passo.inputParameters.push(parametro);
                        $.fancybox.update();
                        setTimeout(function () {
                            $scope.updatePassosControls();
                        }, 100);
                        return;
                    }
                }
                //se é um método
                if (membro.memberType == 3 && passo.methodName == membro.name) {

                    passo.inputParameters = [];
                    for (var i = 0; i < membro.parametros.length; i++) {
                        var parametro = new Object();
                        parametro.ordem = $scope.getnextParametroOrdem(passo);
                        parametro.objectType = membro.parametros[i].type;
                        passo.inputParameters.push(parametro);
                    }
                    $.fancybox.update();
                    setTimeout(function () {
                        $scope.updatePassosControls();
                    }, 100);
                    return;
                }
            }
        }
    }

    $scope.adicionaParametro = function (passo) {
        if (passo.inputParameters == null) {
            passo.inputParameters = [];
        }

        var parametro = new Object();         //iniciar aqui o Parametro
        parametro.ordem = $scope.getnextParametroOrdem(passo);
        passo.inputParameters.push(parametro);
        $.fancybox.update();
        setTimeout(function () {
            $scope.updatePassosControls();
        }, 100);
    }

    $scope.deletaParametro = function (passo, parametro) {

        if (!confirm(confirmarExcluirText))
            return;

        var index = passo.inputParameters.indexOf(parametro);
        passo.inputParameters.splice(index, 1);
        $.fancybox.update();
    }

    $scope.parametroValueBlured = function (passo, parametro) {

        if ($scope.isDeclaredObject(parametro.objectValue, passo)) {
            parametro.objectType = "";
        }
    }

    $scope.getnextParametroOrdem = function (passo) {
        if (passo.inputParameters == null || passo.inputParameters.length == 0)
            return 1;
        else
            return passo.inputParameters[passo.inputParameters.length - 1].ordem + 1;
    }

    $scope.onAddParametroKeyDown = function (passo, evento) {

        //Tab
        if (evento.which == 9) {
            var index = $scope.editingCasoTeste.passos.indexOf(passo);
            if (index == $scope.editingCasoTeste.passos.length - 1) {
                $scope.adicionaPasso();
            }
        }
    }

    $scope.getKnowTypes = function (request, passo, showSystemOut) {
        var knowTypes = [];
        if ($scope.exercicio.classesAuxiliares != null) {
            for (var i = 0; i < $scope.exercicio.classesAuxiliares.length; i++) {

                var assinatura = $scope.exercicio.classesAuxiliares[i].assinatura;
                if (assinatura != null && assinatura.nomeClasse.toLowerCase().indexOf(request.term.toLowerCase()) >= 0) {
                    knowTypes.push({label: assinatura.nomeClasse, value: assinatura.nomeClasse});
                }
            }
        }

        for (var i = 0; i < $scope.editingCasoTeste.passos.length; i++) {
            var item = $scope.editingCasoTeste.passos[i];
            if (item != passo
                    && item.expectedOutputType != null
                    && item.expectedOutputType.length > 0
                    && $scope.containsValue(knowTypes, item.expectedOutputType) == false
                    && item.expectedOutputType.toLowerCase().indexOf(request.term.toLowerCase()) >= 0) {

                knowTypes.push({label: item.expectedOutputType, value: item.expectedOutputType});
            }
        }

        if ("object".indexOf(request.term.toLowerCase()) >= 0 && $scope.containsValue(knowTypes, "Object") == false)
            knowTypes.push({label: "Object", value: "Object"});
        if ("string".indexOf(request.term.toLowerCase()) >= 0 && $scope.containsValue(knowTypes, "String") == false)
            knowTypes.push({label: "String", value: "String"});
        if ("short".indexOf(request.term.toLowerCase()) >= 0 && $scope.containsValue(knowTypes, "short") == false)
            knowTypes.push({label: "short", value: "short"});
        if ("int".indexOf(request.term.toLowerCase()) >= 0 && $scope.containsValue(knowTypes, "int") == false)
            knowTypes.push({label: "int", value: "int"});
        if ("integer".indexOf(request.term.toLowerCase()) >= 0 && $scope.containsValue(knowTypes, "Integer") == false)
            knowTypes.push({label: "Integer", value: "Integer"});
        if ("long".indexOf(request.term.toLowerCase()) >= 0 && $scope.containsValue(knowTypes, "long") == false)
            knowTypes.push({label: "long", value: "long"});
        if ("double".indexOf(request.term.toLowerCase()) >= 0 && $scope.containsValue(knowTypes, "Double") == false)
            knowTypes.push({label: "Double", value: "Double"});
        if ("boolean".indexOf(request.term.toLowerCase()) >= 0 && $scope.containsValue(knowTypes, "Boolean") == false)
            knowTypes.push({label: "Boolean", value: "Boolean"});
        if ("system.out".indexOf(request.term.toLowerCase()) >= 0 && $scope.containsValue(knowTypes, "System.Out") == false && showSystemOut)
            knowTypes.push({label: "System.Out", value: "System.Out"});
        return knowTypes;
    }

    $scope.getKnowMethods = function (request, passo) {
        var knowMethods = [];
        var objectType = $scope.getObjectType(passo.objectName);
        if ($scope.exercicio.classesAuxiliares != null) {
            for (var i = 0; i < $scope.exercicio.classesAuxiliares.length; i++) {

                var assinatura = $scope.exercicio.classesAuxiliares[i].assinatura;
                if (assinatura != null && assinatura.nomeClasse == objectType) {

                    for (var i = 0; i < assinatura.membros.length; i++) {
                        var membro = assinatura.membros[i];
                        if (membro.memberType == 1) {

                            var getter = 'get' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
                            if ($scope.containsValue(knowMethods, getter) == false
                                    && getter.toLowerCase().indexOf(request.term.toLowerCase()) >= 0) {
                                knowMethods.push({label: getter, value: getter});
                            }

                            var setter = 'set' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
                            if ($scope.containsValue(knowMethods, setter) == false
                                    && setter.toLowerCase().indexOf(request.term.toLowerCase()) >= 0) {

                                knowMethods.push({label: setter, value: setter});
                            }
                        } else if (membro.memberType == 3
                                && $scope.containsValue(knowMethods, membro.name) == false
                                && membro.name.toLowerCase().indexOf(request.term.toLowerCase()) >= 0) {

                            knowMethods.push({label: membro.name, value: membro.name});
                        }
                    }
                }
            }
        }

        for (var i = 0; i < $scope.editingCasoTeste.passos.length; i++) {
            var item = $scope.editingCasoTeste.passos[i];
            if (item != passo
                    && item.methodName != null
                    && item.methodName.length > 0 && $scope.getObjectType(item.objectName) == objectType
                    && $scope.containsValue(knowMethods, item.methodName) == false
                    && item.methodName.toLowerCase().indexOf(request.term.toLowerCase()) >= 0) {

                knowMethods.push({label: item.methodName, value: item.methodName});
            }
        }

        return knowMethods;
    }

    $scope.getKnowObjects = function (request, passo) {

        var knowObjects = [];
        var endIndex = $scope.editingCasoTeste.passos.indexOf(passo);
        for (var i = 0; i < endIndex; i++) {
            var item = $scope.editingCasoTeste.passos[i];
            if (item.expectedOutputName != null
                    && item.expectedOutputName.length > 0
                    && $scope.containsValue(knowObjects, item.expectedOutputName) == false
                    && item.expectedOutputName.toLowerCase().indexOf(request.term.toLowerCase()) >= 0) {

                knowObjects.push({label: item.expectedOutputName, value: item.expectedOutputName});
            }
        }

        return knowObjects;
    }

    $scope.containsValue = function (array, value) {
        for (var i = 0; i < array.length; i++) {
            if (array[i].value == value)
                return true;
        }
        return false;
    }

    $scope.getObjectType = function (objectName) {
        for (var i = 0; i < $scope.editingCasoTeste.passos.length; i++) {
            var item = $scope.editingCasoTeste.passos[i];
            if (item.expectedOutputName == objectName)
                return item.expectedOutputType;
        }
        return null;
    }

    $scope.isDeclaredObject = function (objectName, passo) {

        if (objectName == null)
            return  false;

        var StringObjName = objectName.toString();

        if (StringObjName.indexOf("[") > 0)
            StringObjName = StringObjName.substr(0, StringObjName.indexOf("["));

        var index = $scope.editingCasoTeste.passos.indexOf(passo);
        var anotherLacoOpened = false;
        for (var i = index - 1; i >= 0; i--) {
            var item = $scope.editingCasoTeste.passos[i];

            if (item.operationType == 3) {
                continue;
            } else if (item.operationType == 5) {
                anotherLacoOpened = true;
                continue;
            }
            else if (item.operationType == 4) {
                anotherLacoOpened = false;
                continue;
            }
            else if (anotherLacoOpened == true) {
                continue;
            }

            var outputName = item.expectedOutputName;
            if (outputName != null && outputName.indexOf("[") > 0)
                outputName = outputName.substr(0, outputName.indexOf("["));

            if (outputName != null && outputName.length > 0 && outputName == StringObjName)
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

        try {
            $("#casosTable tbody").sortable('destroy');
        } catch (e) {
        }

        var fixHelperModified = function (e, ui) {
            var $originals = ui.children();
            var $helper = ui.clone();
            $helper.children().each(function (index) {
                $(this).width($originals.eq(index).width());
            });
            return $helper;
        }

        $("#casosTable tbody").sortable({
            axis: "y",
            handle: ".casoHandle",
            helper: fixHelperModified,
            start: $scope.onStartDragCasos,
            stop: $scope.onStopDragCasos
        }).disableSelection();
    }

    $scope.onStartDragCasos = function (e, ui) {
        ui.item.data('start', ui.item.index());
    }

    $scope.onStopDragCasos = function (e, ui) {
        var start = ui.item.data('start');
        var end = ui.item.index();

        // Remove item to prevent DOM desynchronization.
        $(ui.item).remove();
        
        $scope.$apply(function () {
            $scope.exercicio.casosTeste.splice(end, 0, $scope.exercicio.casosTeste.splice(start, 1)[0]);

            for (var i = 0; i < $scope.exercicio.casosTeste.length; i++) {
                $scope.exercicio.casosTeste[i].ordem = i + 1;
            }
        });
    }

    $scope.updatePassosControls = function () {

        $(".passoDataType").autocomplete({
            source: function (request, response) {
                var index = $(this.element[0]).attr("data-index");
                var passo = $scope.editingCasoTeste.passos[index];
                response($scope.getKnowTypes(request, passo, false));
            },
            select: function (event, ui) {
                $(event.target).val(ui.item.value);
                $(event.target).trigger("change");
            },
            minLength: 0
        }).focus(function () {
            if (this.value == "")
            {
                $(this).autocomplete("search");
            }
        });
        $(".passoObject").autocomplete({
            source: function (request, response) {
                var index = $(this.element[0]).attr("data-index");
                var passo = $scope.editingCasoTeste.passos[index];
                response($scope.getKnowObjects(request, passo));
            },
            select: function (event, ui) {
                $(event.target).val(ui.item.value);
                $(event.target).trigger("change");
            },
            minLength: 0
        }).focus(function () {
            if (this.value == "")
            {
                $(this).autocomplete("search");
            }
        });
        $(".passoDataTypeOrObject").autocomplete({source: function (request, response) {
                var index = $(this.element[0]).attr("data-index");
                var passo = $scope.editingCasoTeste.passos[index];
                var data = $.merge($scope.getKnowObjects(request, passo), $scope.getKnowTypes(request, passo, passo.operationType == 3));
                response(data);
            },
            select: function (event, ui) {
                $(event.target).val(ui.item.value);
                $(event.target).trigger("change");
            },
            minLength: 0
        }).focus(function () {
            if (this.value == "")
            {
                $(this).autocomplete("search");
            }
        });
        $(".passoMethod").autocomplete({
            source: function (request, response) {
                var index = $(this.element[0]).attr("data-index");
                var passo = $scope.editingCasoTeste.passos[index];
                response($scope.getKnowMethods(request, passo));
            },
            select: function (event, ui) {
                $(event.target).val(ui.item.value);
                $(event.target).trigger("change");
            }, minLength: 0
        }).focus(function () {
            if (this.value == "")
            {
                $(this).autocomplete("search");
            }
        });

        var fixHelperModified = function (e, ui) {
            var $originals = ui.children();
            var $helper = ui.clone();
            $helper.children().each(function (index) {
                $(this).width($originals.eq(index).width());
            });
            return $helper;
        }

        $("#passosTable tbody").sortable({
            axis: "y",
            handle: ".passoHandle",
            helper: fixHelperModified,
            start: $scope.onStartDragPassos,
            update: $scope.onUpdateDragPassos
        }).disableSelection();
    }

    $scope.onStartDragPassos = function (e, ui) {
        ui.item.data('startIndex', ui.item.index());
    }

    $scope.onUpdateDragPassos = function (e, ui) {
        var start = ui.item.data('startIndex');
        var end = ui.item.index();

        // Remove item to prevent DOM desynchronization.
        $(ui.item).remove();

        $scope.$apply(function () {
            $scope.editingCasoTeste.passos.splice(end, 0, $scope.editingCasoTeste.passos.splice(start, 1)[0]);

            for (var i = 0; i < $scope.editingCasoTeste.passos.length; i++) {
                $scope.editingCasoTeste.passos[i].ordem = i + 1;
            }
        });

    }

    $.ajaxSetup({cache: false});
    $scope.init();
    $scope.OperationTypes = [
        {value: 1, label: 'Atribui à'},
        {value: 2, label: 'Executa'},
        {value: 3, label: 'Verifica se'},
        {value: 4, label: 'Abre Laço'},
        {value: 5, label: 'Fecha Laço'}
    ]
    $scope.ComparisionTypes = [
        {value: 1, label: '=='},
        {value: 2, label: '!='},
        {value: 3, label: '>'},
        {value: 4, label: '>='},
        {value: 5, label: '<'},
        {value: 6, label: '<='}
    ]
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
