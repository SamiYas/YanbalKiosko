angular.module('app')

.controller('navController',
    ['$rootScope', '$location', '$scope', '$state', 'LoginService',
    'SesionService', 'sesionConstants', 'navConstants',
    function($rootScope, $location, $scope, $state, LoginService, SesionService,
    sesionConstants, navConstants) {

    $scope.navOptions = [];
    $scope.paisesNav = [];
    $scope.comboInhabilitado = true;

    $scope.init = function() {

        $scope.navOptions = navConstants.lista;

        $scope.paisSeleccionado = SesionService.pais();
        if (SesionService.esUsuarioRolCorporativo()) {
            $scope.listarPaises();
        }

        $rootScope.$broadcast('actualizarNav');
    };

    $scope.selectPais = function(item) {
        $scope.paisSeleccionado = item;
        SesionService.setValor(sesionConstants.SESION_PAIS, item);
        $rootScope.$broadcast('actualizarGridUmbrales');
        $rootScope.$broadcast('actualizarColecciones');
    };

    $scope.selectNav = function(item) {
        if ($scope.isNavSelected(item)) {
            return;
        } else {
            $scope.selectedNav = item;
        }

        if (SesionService.estaAutenticado()) {
            $scope.autenticacionInterna();
        }else {
            $scope.limpiarSesion();
        }
    };

    $scope.autenticacionInterna = function() {
        LoginService.obtenerUsuarioToken().success(function(datos) {
            var jsonData = {
                    username: datos.usuario,
                    password: datos.clave,
                    country: datos.pais
            };

            LoginService.autenticarUsuario(jsonData).success(function() {
            }).error(function() {
                $scope.limpiarSesion();
            });
        }).error(function() {
            $scope.limpiarSesion();
        });
    };

    $scope.isNavSelected = function(item) {
        return $scope.selectedNav === item;
    };

    $scope.habilitarBoton = function() {
        $scope.paisSeleccionado = SesionService.pais();
        $scope.comboInhabilitado = true;
        if (SesionService.esUsuarioRolCorporativo()) {
            $scope.comboInhabilitado = false;
        }
        return $scope.comboInhabilitado;
    };

    $scope.listarPaises = function() {
        LoginService.listarPaises().success(function(listaPaises) {
            $scope.paisesNav = listaPaises;
        });
    };

    $scope.cerrarSesion = function() {
        LoginService.cerrarSesion().success(function() {
            $scope.limpiarSesion();
        }).error(function() {
            $scope.limpiarSesion();
        });
    };

    $rootScope.$on('actualizarListaPaises', function() {
        $scope.habilitarBoton();
        $scope.listarPaises();
    });

    $rootScope.$on('actualizarNav', function() {
        if ($location.path() === '/umbral') {
            $scope.selectedNav = $scope.navOptions[1];
        } else {
            $scope.selectedNav = $scope.navOptions[0];
        }
    });

    $scope.limpiarSesion = function() {
        $scope.selectedNav = $scope.navOptions[0];
        SesionService.clearAll();
        $state.go('login');
    };

    $scope.init();

}]);
