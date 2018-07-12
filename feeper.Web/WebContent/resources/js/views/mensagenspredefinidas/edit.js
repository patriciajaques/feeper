var app = angular.module('feeper', []);
app.controller('editMensagensPredefinidas', function ($scope, $http) {

    $scope.init = function () {

        $http({
            url: baseUrl + 'mensagenspredefinidas/getJson',
            method: 'POST'
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
            url: baseUrl + 'mensagenspredefinidas/saveJson',
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