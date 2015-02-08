var app = angular.module('feeper', []);
app.controller('editMensagensPersonalizadas', function ($scope, $http) {

    $scope.init = function () {

        $http({
            url: baseUrl + 'mensagenspersonalizadas/getJson',
            method: 'GET'
        }).success(function (data) {
            $scope.mensagens = data;
        });
    }
    
    $scope.adicionaMensagem = function () {
        if ($scope.mensagens == null)
            $scope.mensagens = [];

        var mensagem = new Object();

        $scope.mensagens.push(mensagem);
    }

    $scope.deletaMensagem = function (mensagem) {

        var index = $scope.mensagens.indexOf(mensagem);
        $scope.mensagens.splice(index, 1);
    }

    $scope.save = function () {

        $http({
            url: baseUrl + 'mensagenspersonalizadas/saveJson',
            method: 'POST',
            data: $scope.mensagens,
        }).success(function (sucess) {
            if (sucess) {
                window.location.href = baseUrl;
            }
        });
    }

    $scope.voltar = function () {
        window.location.href = baseUrl;
    }

    $scope.init();
});