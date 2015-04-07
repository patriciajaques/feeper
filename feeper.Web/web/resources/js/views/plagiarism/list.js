var app = angular.module('feeper', []);
app.controller('listPlagiarism', function ($scope, $http) {

    $scope.init = function () {

        $http({
            url: baseUrl + 'plagiarism/getJson',
            method: 'POST'
        }).success(function (data) {
            $scope.exercicios = data;
        });
    }

    $scope.performCheck = function () {

        if ($scope.exercicioId == null || $scope.exercicioId.lenght == 0) {
            alert(selecionarExercicio);
        }

        MostraCarregando();
        $http({
            url: baseUrl + 'plagiarism/performCheck',
            method: 'POST',
            data: $scope.exercicioId
        }).success(function (data) {
            $scope.dados = data;
            RemoveCarregando();
        });
    }

    $scope.toggleDado = function (dado) {
        if (dado.expanded == true) {
            dado.expanded = false;
        }
        else {
            dado.expanded = true;
        }
    }

    $scope.toggleColega = function (colega) {
        if (colega.expanded == true) {
            colega.expanded = false;
        }
        else {
            colega.expanded = true;
        }
    }

    $scope.mostraClasse = function (aluno, classe) {
        $.fancybox({
            'openEffect': 'fade',
            'closeEffect': 'fade',
            'autoResize': false,
            'autoSize': true,
            'type': 'iframe',
            'arrows': false,
            'width': '90%',
            'height': '90%',
            'href': baseUrl + "classes/showversion/" + classe.id + "/" + aluno.id,
            helpers: {
                overlay: {
                    locked: false
                }
            }
        });
    }

    $scope.comparaClasses = function (plagItem, classeColega) {

        var classeAluno = null;
        for (var i = 0; i < plagItem.classes.length; i++) {
            if (plagItem.classes[i].nomeClasse == classeColega.nomeClasse) {
                classeAluno = plagItem.classes[i];
                break;
            }
        }

        if (classeAluno == null) {
            alert("Não foi encontrada uma classe com o mesmo nome na solução de " + plagItem.aluno.nome);
            return;
        }

        $.fancybox({
            'openEffect': 'fade',
            'closeEffect': 'fade',
            'autoResize': false,
            'autoSize': true,
            'type': 'iframe',
            'arrows': false,
            'width': '90%',
            'height': '90%',
            'href': baseUrl + "classes/diffclasses/" + classeAluno.id + "/" + classeColega.id,
            helpers: {
                overlay: {
                    locked: false
                }
            }
        });
    }

    $scope.voltar = function () {
        window.location.href = baseUrl;
    }

    $scope.init();
});