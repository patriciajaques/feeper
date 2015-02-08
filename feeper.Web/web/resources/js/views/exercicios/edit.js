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
        $scope.initControls();
        $http({
            url: baseUrl + 'exercicios/getJson',
            method: 'GET',
            params: {
                'exercicioId': exercicioId
            }
        }).success(function (data) {
            $scope.exercicio = data;
            $scope.arquivoPDFURLs = [];
            var url = {domain: $sce.trustAsResourceUrl(baseUrl + 'exercicios/verdescricao?exercicioId=' + $scope.exercicio.id)};
            $scope.arquivoPDFURLs.push(url);
            $(".jqte_editor").html($scope.exercicio.descricaoHtml);
        });
    }

    $scope.visualizaTelaInterface = function () {
        $.fancybox("#divUploadInterface", {
            'openEffect': 'fade',
            'closeEffect': 'fade',
            'closeClick': false
        });
    }

    $scope.concluiUploadInterface = function () {

        $.fancybox.close();
        if ($scope.gerarTestesGetSet == true) {
            if ($scope.exercicio.casosTeste == null)
                $scope.exercicio.casosTeste = [];
            for (var i = 0; i < $scope.exercicio.interfaceSolucao.membros.length; i++) {
                var membro = $scope.exercicio.interfaceSolucao.membros[i];
                //somente fields não publicas de determinados tipos
                if (membro.memberType == 1 && membro.modifier != 1 && $scope.canAutomateFieldTest(membro)) {

                    var caso = new Object();
                    //iniciar aqui o caso
                    caso.ativo = true;
                    caso.ordem = $scope.exercicio.casosTeste.length + 1;
                    caso.mensagemPersonalizada = "Por favor verifique o Getter e o Setter da propriedade " + membro.name + ". O valor retornado pelo Getter difere do valor enviado ao Setter!";
                    caso.passos = [];
                    var passo1 = new Object();
                    passo1.ordem = 1;
                    passo1.operationType = 1;
                    passo1.expectedOutputType = $scope.exercicio.interfaceSolucao.nomeClasse;
                    passo1.expectedOutputName = $scope.exercicio.interfaceSolucao.nomeClasse.toLowerCase() + '1';
                    passo1.objectName = $scope.exercicio.interfaceSolucao.nomeClasse;
                    caso.passos.push(passo1);
                    var passo2 = new Object();
                    passo2.ordem = 2;
                    passo2.operationType = 2;
                    passo2.objectName = $scope.exercicio.interfaceSolucao.nomeClasse.toLowerCase() + '1';
                    passo2.methodName = 'set' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
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
                    passo3.objectName = passo2.objectName = $scope.exercicio.interfaceSolucao.nomeClasse.toLowerCase() + '1';
                    passo3.methodName = 'get' + membro.name.substr(0, 1).toUpperCase() + membro.name.substr(1);
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
        $scope.exercicio.casosTeste.push(caso);
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
            'closeClick': false
        });
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
    }

    $scope.deletaPasso = function (passo) {

        var index = $scope.editingCasoTeste.passos.indexOf(passo);
        $scope.editingCasoTeste.passos.splice(index, 1);
        $.fancybox.update();
    }

    $scope.adicionaParametro = function (passo) {
        if (passo.inputParameters == null)
            passo.inputParameters = [];
        var parametro = new Object();
        //iniciar aqui o Parametro
        parametro.ordem = passo.inputParameters.length + 1;
        passo.inputParameters.push(parametro);
        $.fancybox.update();
    }

    $scope.deletaParametro = function (passo, parametro) {

        var index = passo.inputParameters.indexOf(parametro);
        passo.inputParameters.splice(index, 1);
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
    }

    $scope.objectNameChanged = function (passo) {
        if (passo.objectName == 'System.Out') {
            passo.methodName = "";
            passo.inputParameters = [];
        }
    }

    $scope.methodNameChanged = function (passo) {

        if ($scope.exercicio.interfaceSolucao == null || (passo.inputParameters != null && passo.inputParameters.length > 0))
            return;

        var objectType = $scope.getObjectType(passo.objectName);
        if ($scope.exercicio.interfaceSolucao.nomeClasse != objectType)
            return;

        for (var i = 0; i < $scope.exercicio.interfaceSolucao.membros.length; i++) {
            var membro = $scope.exercicio.interfaceSolucao.membros[i];

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
                return;
            }
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
            case 'bool':
            case 'boolean':
                return true;
        }
        return false;
    }

    $scope.getKnowTypes = function (request, passo, showSystemOut) {
        var knowTypes = [];
        if ("object".indexOf(request.term.toLowerCase()) >= 0)
            knowTypes.push({label: "Object", value: "Object"});
        if ("string".indexOf(request.term.toLowerCase()) >= 0)
            knowTypes.push({label: "String", value: "String"});
        if ("short".indexOf(request.term.toLowerCase()) >= 0)
            knowTypes.push({label: "short", value: "short"});
        if ("int".indexOf(request.term.toLowerCase()) >= 0)
            knowTypes.push({label: "int", value: "int"});
        if ("integer".indexOf(request.term.toLowerCase()) >= 0)
            knowTypes.push({label: "Integer", value: "Integer"});
        if ("long".indexOf(request.term.toLowerCase()) >= 0)
            knowTypes.push({label: "long", value: "long"});
        if ("double".indexOf(request.term.toLowerCase()) >= 0)
            knowTypes.push({label: "Double", value: "Double"});
        if ("bool".indexOf(request.term.toLowerCase()) >= 0)
            knowTypes.push({label: "bool", value: "bool"});
        if ("boolean".indexOf(request.term.toLowerCase()) >= 0)
            knowTypes.push({label: "Boolean", value: "Boolean"});
        if ("system.out".indexOf(request.term.toLowerCase()) >= 0 && showSystemOut)
            knowTypes.push({label: "System.Out", value: "System.Out"});

        if ($scope.exercicio.interfaceSolucao != null
                && $scope.exercicio.interfaceSolucao.nomeClasse.toLowerCase().indexOf(request.term.toLowerCase()) >= 0) {

            knowTypes.push({label: $scope.exercicio.interfaceSolucao.nomeClasse, value: $scope.exercicio.interfaceSolucao.nomeClasse});
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


        return knowTypes;
    }

    $scope.getKnowMethods = function (request, passo) {

        var knowMethods = [];

        var objectType = $scope.getObjectType(passo.objectName);
        if ($scope.exercicio.interfaceSolucao != null && $scope.exercicio.interfaceSolucao.nomeClasse == objectType) {

            for (var i = 0; i < $scope.exercicio.interfaceSolucao.membros.length; i++) {
                var membro = $scope.exercicio.interfaceSolucao.membros[i];

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

    $scope.save = function () {

        $scope.exercicio.descricaoHtml = $(".jqte_editor").html();
        $http({
            url: baseUrl + 'exercicios/saveJson',
            method: 'POST',
            data: $scope.exercicio,
        }).success(function (sucess) {
            if (sucess) {
                window.location.href = baseUrl + 'exercicios';
            }
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

    $scope.onInterfaceCarregada = function (data) {

        $scope.exercicio.interfaceSolucao = JSON.parse(data);
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
            'uploader': baseUrl + 'exercicios/uploadinterfacesolucao',
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

                $scope.$apply($scope.onInterfaceCarregada(data));
            },
            'onUploadError': function (file, errorCode, errorMsg, errorString) {
                alert(erroUploadInterfaceText);
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

    $scope.updatePassosAutocompletes = function (passo, showSystemOut) {

        $(".passoDataType").autocomplete({
            source: function (request, response) {
                response($scope.getKnowTypes(request, passo, showSystemOut));
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
                var data = $.merge($scope.getKnowObjects(request, passo), $scope.getKnowTypes(request, passo, showSystemOut));
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
    $scope.init();
    $scope.OperationTypes = [
        {value: 1, label: 'Atribui à'},
        {value: 2, label: 'Executa'},
        {value: 3, label: 'Verifica se'}
    ]
});