angular.module('YanbalKiosko.controllers')

.controller('ConfiguracionController', ['$scope', '$ionicPopup', '$filter', 'ConfiguracionService', 'popUpUtil', '$state', 'LoginService', '$ionicHistory', '$rootScope',
    function ($scope, $ionicPopup, $filter, ConfiguracionService, popUpUtil, $state, LoginService, $ionicHistory, $rootScope) {
    $scope.UpdateMobileData = function () {
        ConfiguracionService.UpdateMobileData($scope.settings.mobileNetwork).success(function () {
            //se inserto con exito
        }).error(function () {
            popUpUtil.alertPopup('LOGINFALLOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
        });
    };
    var GetMobileData = function () {
        ConfiguracionService.GetMobileData().success(function (data) {
            $scope.settings = {
                mobileNetwork: data === '1' ? true : false
            };
        }).error(function () {
            popUpUtil.alertPopup('LOGINFALLOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
        });
    };
    var GetSincDate = function () {
        ConfiguracionService.GetSincDate().success(function (data) {
            $scope.fechaSinc = data;
        }).error(function () {
            popUpUtil.alertPopup('LOGINFALLOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
        });
    };
    $scope.cerrarSesion = function () {
        popUpUtil.confirmPopup('CONFIGURACIONCERRARSESION', 'CONFIGURACIONALERTATEXTO', 'CONFIGURACIONBOTONCANCELAR', 'CONFIGURACIONBOTONACEPTAR', function () {
            LoginService.cerrarSesion().success(function () {
                //Remover listener resume
                $rootScope.offResume();
                $ionicHistory.nextViewOptions({
                    historyRoot: true
                });
                $state.go('login');
            }).error(function () {
                popUpUtil.alertPopup('LOGINFALLOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
            });
        });
    };
    var init = function () {
        $scope.configurationTitle = $filter('translate')('CONFIGURACIONTITULO');
        GetMobileData();
        GetSincDate();
    };
    init();
}]);