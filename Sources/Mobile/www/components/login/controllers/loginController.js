angular.module('YanbalKiosko.controllers', [])

.controller('LoginController', ['$scope', '$filter', 'LoginService', '$state', '$ionicHistory', 'popUpUtil', '$cordovaNetwork', 'textConstants', '$localstorage',
    function ($scope, $filter, LoginService, $state, $ionicHistory, popUpUtil, $cordovaNetwork, textConstants, $localstorage) {
    $scope.data = {};
    $scope.countries = [];
    $scope.data.country = {};
    $scope.data.countryText = '';

    var init = function () {
        LoginService.cleanToken().then(function () {
            $ionicHistory.clearHistory();
            $ionicHistory.nextViewOptions({
                historyRoot: true,
                disableBack: true
            });
        }, function () {
            popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
        });
    };
    $scope.getPaises = function () {
        LoginService.getPaises($scope.data.countryText).success(function (data) {
            $scope.data.country = null;
            $scope.countries = data;
        }).error(function () {
            popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
        });
    };
    $scope.selectPais = function (country) {
        $scope.data.country = country;
        $scope.data.countryText = country.NOMBRE;
    };
    $scope.paisSeleccionado = function (country) {
        return $scope.data.country === country;
    };
    $scope.login = function () {
        if (!$scope.data.username || !$scope.data.password || !$scope.data.country) {
            popUpUtil.alertPopup('LOGINVALIDACIONTITULO', 'LOGINBOTONACEPTAR', $filter('translate')('LOGINVALIDACIONTEXTO'));
        } else {
            if ($cordovaNetwork.isOnline()) {
                LoginService.autenticarUsuario($scope.data.username, $scope.data.password, $scope.data.country.CODIGO_PAIS).then(function (result) {
                    return LoginService.loginUser($scope.data.username, $scope.data.password, $scope.data.country.CODIGO_PAIS, result);
                }).then(function () {
                    $localstorage.set('Sincronizar', textConstants.LOCALSTORAGETRUE);
                    $localstorage.set('SesionRegistrada', textConstants.LOCALSTORAGEFALSE);
                    $state.go('tab.coleccion');
                }).catch(function (err) {
                    popUpUtil.alertPopup('LOGINFALLOTITULO', 'LOGINBOTONACEPTAR', err);
                });
            } else {
                //Login sin conexion
                LoginService.autenticarUsuarioOffline($scope.data.username, $scope.data.password, $scope.data.country.CODIGO_PAIS).then(function () {
                    return LoginService.loginUser($scope.data.username, $scope.data.password, $scope.data.country.CODIGO_PAIS);
                }).then(function () {
                    $localstorage.set('SincronizarOffline', textConstants.LOCALSTORAGETRUE);
                    $state.go('tab.coleccion');
                }).catch(function (err) {
                    popUpUtil.alertPopup('LOGINFALLOTITULO', 'LOGINBOTONACEPTAR', err);
                });
            }
        }
    };

    $scope.reset = function ($event) {
        angular.element($event.currentTarget).parent().children('label').children('input')[0].value = '';
    };

    init();
}]);