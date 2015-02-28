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

        //hack, para crash do uploadify
        setTimeout(function () {
            $scope.initControls();
        }, 0);

        $http({
            url: baseUrl + 'exercicios/getJson',
            method: 'GET',
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

            for (var i = 0; i < $scope.exercicio.classesAuxiliares.length; i++) {
                if ($scope.exercicio.classesAuxiliares[i].assinatura == null) {
                    alert(erroCarregarAssinaturas);
                    break;
                }
            }
        });
    }

    $scope.save = function () {

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

    $scope.visualizaTelaClasse = function () {

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

        var classe = JSON.parse(data);

        if (classe.ehInterface == true) {

            var interface = $scope.getInterface();
            if (interface != null) {
                var index = $scope.exercicio.classesAuxiliares.indexOf(interface);
                $scope.exercicio.classesAuxiliares.splice(index, 1);
            }
        }

        if (classe.ehInterface != true) {
            $.fancybox.close();
        }

        $scope.exercicio.classesAuxiliares.push(classe);
        $scope.carregarAssinaturas();
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
                    alert(erroCarregarAssinaturas);
                    break;
                }
            }
        }).error(function (message) {
            alert(erroCarregarAssinaturas);
        });
    }

    $scope.adicionaClasse = function () {

        var novaClasse = new Object();
        novaClasse.nomeClasse = $scope.NomeNovaClasse;
        novaClasse.ehInterface = false;

        novaClasse.codigo = $scope.NewClassContent.replace(/#@#CLASSE#@#/gi, novaClasse.nomeClasse).replace(/#n#/gi, "\n");
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

    $scope.deletaClasse = function () {

        $("#containerEditor").hide();

        var index = $scope.exercicio.classesAuxiliares.indexOf($scope.editingClass);
        $scope.exercicio.classesAuxiliares.splice(index, 1);
    }

    $scope.visualizaTelaInterface = function () {
        $.fancybox("#divInterface", {
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

    $scope.getInterface = function () {

        if ($scope.exercicio == null)
            return null;

        for (var i = 0; i < $scope.exercicio.classesAuxiliares.length; i++) {
            if ($scope.exercicio.classesAuxiliares[i].ehInterface) {
                return $scope.exercicio.classesAuxiliares[i].assinatura;
            }
        }
        return null;
    }

    $scope.concluiTelaInterface = function () {

        $.fancybox.close();

        var interface = $scope.getInterface();
        if (interface == null)
            return;

        if ($scope.gerarTestesGetSet == true) {
            if ($scope.exercicio.casosTeste == null)
                $scope.exercicio.casosTeste = [];


            for (var i = 0; i < interface.membros.length; i++) {
                var membro = interface.membros[i];
                //somente fields não publicas de determinados tipos
                if (membro.memberType == 1 && membro.modifier != 1 && $scope.canAutomateFieldTest(membro)) {

                    var setName = 'set' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
                    var getName = 'get' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);

                    var caso = new Object();
                    //iniciar aqui o caso
                    caso.ativo = true;
                    caso.ordem = $scope.exercicio.casosTeste.length + 1;
                    caso.mensagemPersonalizada = "O método " + setName + " ou o " + getName + " não funcionou corretamente!";
                    caso.passos = [];
                    var passo1 = new Object();
                    passo1.ordem = 1;
                    passo1.operationType = 1;
                    passo1.expectedOutputType = interface.nomeClasse;
                    passo1.expectedOutputName = interface.nomeClasse.toLowerCase() + '1';
                    passo1.objectName = interface.nomeClasse;
                    caso.passos.push(passo1);
                    var passo2 = new Object();
                    passo2.ordem = 2;
                    passo2.operationType = 2;
                    passo2.objectName = interface.nomeClasse.toLowerCase() + '1';
                    passo2.methodName = setName;
                    passo2.inputParameters = []
                    var parametro = new Object();
                    parametro.ordem = 1;
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
                    passo3.ordem = 3;
                    passo3.operationType = 3;
                    passo3.expectedOutputType = membro.type;
                    passo3.expectedOutputValue = valor;
                    passo3.objectName = passo2.objectName = interface.nomeClasse.toLowerCase() + '1';
                    passo3.methodName = getName;
                    caso.passos.push(passo3);
                    $scope.exercicio.casosTeste.push(caso);
                }
            }
        }
    }

    $scope.adicionaCasoTeste = function () {
        if ($scope.exercicio.casosTeste == null)
            $scope.exercicio.casosTeste = [];
        var caso = new Object();
        //iniciar aqui o caso
        caso.ativo = true;
        caso.ordem = $scope.exercicio.casosTeste.length + 1;
        $scope.exercicio.casosTeste.unshift(caso);
    }

    $scope.duplicaCasoTeste = function (casoTeste) {

        var novoCaso = JSON.parse(JSON.stringify(casoTeste))
        novoCaso.id = 0;

        for (var i = 0; i < novoCaso.passos.length; i++) {
            var passo = novoCaso.passos[i];
            passo.id = 0;
            passo.idCasoTeste = 0;

            for (var j = 0; j < passo.inputParameters.length; j++) {
                var item = passo.inputParameters[j];
                item.id = 0;
                item.idPasso = 0;
            }
        }
        var index = $scope.exercicio.casosTeste.indexOf(casoTeste);
        $scope.exercicio.casosTeste.splice(index, 0, novoCaso);
    }

    $scope.deletaCasoTeste = function (casoTeste) {

        var index = $scope.exercicio.casosTeste.indexOf(casoTeste);
        $scope.exercicio.casosTeste.splice(index, 1);
    }

    $scope.editaPassosCasoTeste = function (casoTeste) {
        $scope.editingCasoTeste = casoTeste;
        $.fancybox("#divEditPassos", {
            'openEffect': 'fade',
            'closeEffect': 'fade',
            'closeClick': false,
            helpers: {
                overlay: {
                    locked: false
                }
            }
        });

        setTimeout(function () {
            $scope.updatePassosTexts();
        }, 100);
    }

    $scope.adicionaPasso = function () {
        if ($scope.editingCasoTeste.passos == null)
            $scope.editingCasoTeste.passos = [];
        var passo = new Object();
        //iniciar aqui o Passo
        passo.ordem = $scope.editingCasoTeste.passos.length + 1;
        passo.operationType = 1;
        $scope.editingCasoTeste.passos.push(passo);
        $.fancybox.update();

        setTimeout(function () {
            $scope.updatePassosTexts();
        }, 100);
    }

    $scope.duplicaPasso = function (passo) {

        var novoPasso = JSON.parse(JSON.stringify(passo))
        novoPasso.id = 0;

        for (var j = 0; j < novoPasso.inputParameters.length; j++) {
            var item = novoPasso.inputParameters[j];
            item.id = 0;
        }

        var index = $scope.editingCasoTeste.passos.indexOf(passo);
        $scope.editingCasoTeste.passos.splice(index, 0, novoPasso);
        $.fancybox.update();

        setTimeout(function () {
            $scope.updatePassosTexts();
        }, 100);
    }

    $scope.deletaPasso = function (passo) {

        var index = $scope.editingCasoTeste.passos.indexOf(passo);
        $scope.editingCasoTeste.passos.splice(index, 1);
        $.fancybox.update();
    }

    $scope.operationTypeChanged = function (passo) {
        if (passo.operationType == 2) {
            passo.expectedOutputName = "";
            passo.expectedOutputType = "";
        }

        if (passo.operationType != 3) {
            passo.expectedOutputValue = "";
        }

        setTimeout(function () {
            $scope.updatePassosTexts();
        }, 100);
    }

    $scope.expectedOutputNameChanged = function (passo) {
        if ($scope.isDeclaredObject(passo.expectedOutputName, passo)) {
            passo.expectedOutputType = "";
        }
    }

    $scope.expectedOutputValueChanged = function (passo) {
        if ($scope.isDeclaredObject(passo.expectedOutputValue, passo)) {
            passo.expectedOutputType = "";
        }
    }

    $scope.objectNameChanged = function (passo) {
        if (passo.objectName == 'System.Out') {
            passo.methodName = "";
            passo.inputParameters = [];
        }

        setTimeout(function () {
            $scope.updatePassosTexts();
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
                        parametro.ordem = passo.inputParameters.length + 1;
                        parametro.objectType = membro.type;
                        passo.inputParameters.push(parametro);
                        $.fancybox.update();

                        setTimeout(function () {
                            $scope.updatePassosTexts();
                        }, 100);
                        return;
                    }
                }
                //se é um método
                if (membro.memberType == 3 && passo.methodName == membro.name) {

                    passo.inputParameters = [];
                    for (var i = 0; i < membro.parametros.length; i++) {

                        var parametro = new Object();
                        parametro.ordem = passo.inputParameters.length + 1;
                        parametro.objectType = membro.parametros[i].type;
                        passo.inputParameters.push(parametro);
                    }
                    $.fancybox.update();

                    setTimeout(function () {
                        $scope.updatePassosTexts();
                    }, 100);
                    return;
                }
            }
        }
    }

    $scope.adicionaParametro = function (passo) {
        if (passo.inputParameters == null)
            passo.inputParameters = [];
        var parametro = new Object();
        //iniciar aqui o Parametro
        parametro.ordem = passo.inputParameters.length + 1;
        passo.inputParameters.push(parametro);
        $.fancybox.update();

        setTimeout(function () {
            $scope.updatePassosTexts();
        }, 100);
    }

    $scope.deletaParametro = function (passo, parametro) {

        var index = passo.inputParameters.indexOf(parametro);
        passo.inputParameters.splice(index, 1);
        $.fancybox.update();
    }

    $scope.parametroValueChanged = function (passo, parametro) {

        if ($scope.isDeclaredObject(parametro.objectValue, passo)) {
            parametro.objectType = "";
        }
    }

    $scope.canAutomateFieldTest = function (membro) {

        switch (membro.type.toLowerCase()) {
            case 'object':
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

        for (var i = 0; i < $scope.exercicio.classesAuxiliares.length; i++) {

            var assinatura = $scope.exercicio.classesAuxiliares[i].assinatura;
            if (assinatura != null && membro.type.toLowerCase() == assinatura.nomeClasse) {
                return true;
            }
        }
        return false;
    }

    $scope.getKnowTypes = function (request, passo, showSystemOut) {
        var knowTypes = [];

        for (var i = 0; i < $scope.exercicio.classesAuxiliares.length; i++) {

            var assinatura = $scope.exercicio.classesAuxiliares[i].assinatura;
            if (assinatura != null && assinatura.nomeClasse.toLowerCase().indexOf(request.term.toLowerCase()) >= 0) {
                knowTypes.push({label: assinatura.nomeClasse, value: assinatura.nomeClasse});
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
                    }
                    else if (membro.memberType == 3
                            && $scope.containsValue(knowMethods, membro.name) == false
                            && membro.name.toLowerCase().indexOf(request.term.toLowerCase()) >= 0) {

                        knowMethods.push({label: membro.name, value: membro.name});
                    }
                }
            }
        }

        for (var i = 0; i < $scope.editingCasoTeste.passos.length; i++) {
            var item = $scope.editingCasoTeste.passos[i];

            if (item != passo
                    && item.methodName != null
                    && item.methodName.length > 0
                    && $scope.getObjectType(item.objectName) == objectType
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
        var index = $scope.editingCasoTeste.passos.indexOf(passo);

        for (var i = 0; i < index; i++) {
            var item = $scope.editingCasoTeste.passos[i];
            if (item.expectedOutputName != null && item.expectedOutputName.length > 0 && item.expectedOutputName == objectName)
                return true;
        }
        return false;
    }

    $scope.initControls = function () {

        $("#menu-lista-exercicio").addClass("active");
        $(".editor").jqte();
        $('#upload_descricao').uploadify({
            'swf': baseUrl + 'resources/uploadify/uploadify.swf',
            'uploader': baseUrl + 'exercicios/uploaddescricao',
            'fileTypeDesc': 'Arquivos PDF',
            'fileTypeExts': '*.pdf',
            'fileSizeLimit': '500KB',
            'buttonText': escolherArquivoText,
            'multi': false,
            'fileObjName': 'filedata',
            'checkExisting': false,
            'width': 146,
            'height': 34,
            'removeCompleted': true,
            'onUploadSuccess': function (file, data, response) {

                $scope.$apply($scope.onDescricaoCarregada(data));
            },
            'onUploadError': function (file, errorCode, errorMsg, errorString) {
                alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
            }
        });
        $('#upload_Interface_Solucao').uploadify({
            'swf': baseUrl + 'resources/uploadify/uploadify.swf',
            'uploader': baseUrl + 'exercicios/uploadClasseAuxiliar?ehInterface=true',
            'fileTypeDesc': 'Arquivos Java',
            'fileTypeExts': '*.Java',
            'fileSizeLimit': '500KB',
            'buttonText': escolherArquivoText,
            'multi': false,
            'fileObjName': 'filedata',
            'checkExisting': false,
            'width': 146,
            'height': 34,
            'removeCompleted': true,
            'onUploadSuccess': function (file, data, response) {

                $scope.$apply($scope.onClasseCarregada(data));
            },
            'onUploadError': function (file, errorCode, errorMsg, errorString) {
                alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
            }
        });
        $('#upload_Classe_Auxiliar').uploadify({
            'swf': baseUrl + 'resources/uploadify/uploadify.swf',
            'uploader': baseUrl + 'exercicios/uploadClasseAuxiliar',
            'fileTypeDesc': 'Arquivos Java',
            'fileTypeExts': '*.Java',
            'fileSizeLimit': '500KB',
            'buttonText': adicionarclasseexistenteText,
            'multi': false,
            'fileObjName': 'filedata',
            'checkExisting': false,
            'width': 160,
            'height': 22,
            'removeCompleted': true,
            'onUploadSuccess': function (file, data, response) {

                $scope.$apply($scope.onClasseCarregada(data));
            },
            'onUploadError': function (file, errorCode, errorMsg, errorString) {
                alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
            }
        });
    }

    $scope.updateMessagesAutocompletes = function () {

        $(".mensagemPersonalizada").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: baseUrl + 'mensagenspersonalizadas/search?term=' + request.term,
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
            change: function (event, ui) {
                $(event.target).trigger("change");
            },
            select: function (event, ui) {
                $(event.target).trigger("change");
            },
            minLength: 0
        });
    }

    $scope.updatePassosTexts = function () {

        $(".passoDataType").autocomplete({
            source: function (request, response) {
                var index = $(this.element[0]).attr("data-index");
                var passo = $scope.editingCasoTeste.passos[index];
                response($scope.getKnowTypes(request, passo, false));
            },
            change: function (event, ui) {
                $(event.target).trigger("change");
            },
            select: function (event, ui) {
                $(event.target).trigger("change");
            },
            minLength: 0
        });

        $(".passoObject").autocomplete({
            source: function (request, response) {
                var index = $(this.element[0]).attr("data-index");
                var passo = $scope.editingCasoTeste.passos[index];
                response($scope.getKnowObjects(request, passo));
            },
            change: function (event, ui) {
                $(event.target).trigger("change");
            },
            select: function (event, ui) {
                $(event.target).trigger("change");
            },
            minLength: 0
        });

        $(".passoDataTypeOrObject").autocomplete({
            source: function (request, response) {
                var index = $(this.element[0]).attr("data-index");
                var passo = $scope.editingCasoTeste.passos[index];
                var data = $.merge($scope.getKnowObjects(request, passo), $scope.getKnowTypes(request, passo, passo.operationType == 3));
                response(data);
            },
            change: function (event, ui) {
                $(event.target).trigger("change");
            },
            select: function (event, ui) {
                $(event.target).trigger("change");
            },
            minLength: 0
        });

        $(".passoMethod").autocomplete({
            source: function (request, response) {
                var index = $(this.element[0]).attr("data-index");
                var passo = $scope.editingCasoTeste.passos[index];
                response($scope.getKnowMethods(request, passo));
            },
            change: function (event, ui) {
                $(event.target).trigger("change");
            },
            select: function (event, ui) {
                $(event.target).trigger("change");
            },
            minLength: 0
        });
    }

    $.ajaxSetup({cache: false});
    $scope.init();
    $scope.OperationTypes = [
        {value: 1, label: 'Atribui à'},
        {value: 2, label: 'Executa'},
        {value: 3, label: 'Verifica se'}
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
