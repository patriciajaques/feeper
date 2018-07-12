var app = angular.module('feeper', []);
app.controller('editConfiguracoes', function ($scope, $http) {

    $scope.init = function () {

        $http({
            url: baseUrl + 'configuracoes/getJson',
            method: 'POST'
        }).success(function (data) {
            $scope.configuracao = data;
        });
    }

    $scope.save = function () {

        $http({
            url: baseUrl + 'configuracoes/saveJson',
            method: 'POST',
            data: $scope.configuracao,
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