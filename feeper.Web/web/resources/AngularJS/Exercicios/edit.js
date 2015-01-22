var app = angular.module('feeper', []);
app.controller('editExercicios', function ($scope, $http) {

    var exercicioId = window.location.pathname.substr(window.location.pathname.lastIndexOf("/") + 1, window.location.pathname.length);
    $scope.init = function () {

        $scope.initControls();

        $http({
            url: baseUrl + 'exercicios/getJson',
            method: 'GET',
            params: {
                'exercicioId': exercicioId
            }
        }).success(function (data) {
            $scope.exercicio = data;
        });
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

    $scope.adicionaPasso = function (casoTeste) {
        if (casoTeste.passos == null)
            casoTeste.passos = [];

        var passo = new Object();


        //iniciar aqui o Passo
        passo.ordem = casoTeste.passos.length + 1;
        passo.operationType = '1';

        casoTeste.passos.push(passo);

    }

    $scope.deletaPasso = function (casoTeste, passo) {

        var index = casoTeste.passos.indexOf(passo);
        casoTeste.passos.splice(index, 1);
    }

    $scope.adicionaParametro = function (passo) {
        if (passo.inputParameters == null)
            passo.inputParameters = [];

        var parametro = new Object();

        //iniciar aqui o Parametro
        parametro.ordem = passo.inputParameters.length + 1;

        passo.inputParameters.push(parametro);

    }

    $scope.deletaParametro = function (passo, parametro) {

        var index = passo.inputParameters.indexOf(parametro);
        passo.inputParameters.splice(index, 1);
    }

    $scope.save = function () {

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

    $scope.initControls = function () {

        $("#menu-lista-exercicio").addClass("active");
        $(".editor").jqte();
        $('#upload_descricao').uploadify({
            'swf': baseUrl + 'resources/uploadify/uploadify.swf',
            'uploader': baseUrl + 'exercicios/uploadDescricao',
            'fileTypeDesc': 'Arquivos PDF',
            'fileTypeExts': '*.pdf',
            'fileSizeLimit': '500KB',
            'buttonText': uploadDescricaoText,
            'multi': false,
            'fileObjName': 'filedata',
            'checkExisting': false,
            'width': 146,
            'height': 34,
            'removeCompleted': false,
            'onUploadSuccess': function (file, data, response) {
                $scope.exercicio.Descricao = JSON.parse(data);
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
            'buttonText': uploadInterfaceText,
            'multi': false,
            'fileObjName': 'filedata',
            'checkExisting': false,
            'width': 146,
            'height': 34,
            'removeCompleted': false,
            'onUploadSuccess': function (file, data, response) {
                $scope.exercicio.interfaceSolucao = JSON.parse(data);
            },
            'onUploadError': function (file, errorCode, errorMsg, errorString) {
                alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
            }
        });
    }
    $scope.init();
});