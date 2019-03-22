angular.module('app.controllers', [])

.controller('loginController',
    ['$rootScope', '$scope', '$modal', '$timeout', 'modalUtil', 'LoginService',
    '$state' , 'mensajesConstants', 'sesionConstants', 'localStorageService',
    function($rootScope, $scope, $modal, $timeout, modalUtil, LoginService,
    $state, mensajesConstants, sesionConstants, localStorageService) {

    $scope.data = {};
    $scope.paises = [];

    $scope.init = function() {
        $scope.title = 'Home';
        $scope.showNav = 'hide';
        $scope.listarPaises();
        // add placeholder for IE
        $timeout(function() {
            $('input').placeholder();
        }, 200);
    };

    $scope.login = function() {
        var tipoUsuario = '';
        var perfilUsuario = '';
        var pais = {id: '', nombre: '', codigo: ''};
        if ($scope.data.country) {
            pais = JSON.parse($scope.data.country);
        }
        var jsonData = { username: '', password: '', country: '', rol: ''};
        var paisSeleccionadoId = pais.id;

        jsonData.username = $scope.data.username;
        jsonData.password = $scope.data.password;
        jsonData.country = pais.codigo;

        if ($scope.validarFormulario(jsonData)) {
            LoginService.autenticarUsuario(jsonData)
            .success(function(dataYanbal) {
                var jsonDataAutenticacion = {
                    username: '',
                    password: '',
                    country: '',
                    rol: ''
                };
                jsonDataAutenticacion.username = $scope.data.username;
                jsonDataAutenticacion.password = $scope.data.password;
                jsonDataAutenticacion.country = pais.codigo;
                localStorageService.set(
                    sesionConstants.LOGIN_DATA, jsonDataAutenticacion);

                tipoUsuario = dataYanbal.TipoUsuario;
                perfilUsuario = $scope.obtenerPerfilDeUsuario(
                    dataYanbal.Perfil);

                jsonData.country = pais.id;

                if (sesionConstants.PERFIL_CORP === perfilUsuario) {
                    jsonData.rol = sesionConstants.ROL_USER_CORP;
                } else if (sesionConstants.PERFIL_UN === perfilUsuario) {
                    jsonData.rol = sesionConstants.ROL_USER_UN;
                } else {
                    $scope.mostrarMensajeError();
                }

                LoginService.registrarSesion(jsonData).success(function(data) {
                    var paisSeleccionado = $scope.obtenerPaisSeleccionado($scope.paises, paisSeleccionadoId);
                    localStorageService.set(sesionConstants.SESION_PAIS, paisSeleccionado);
                    localStorageService.set(sesionConstants.SESION_DATA, data);
                    localStorageService.set(
                        sesionConstants.TIPO_USER, tipoUsuario);
                    localStorageService.set(
                        sesionConstants.ROL_USER, jsonData.rol);
                    if (sesionConstants.ROL_USER_CORP === jsonData.rol) {
                        LoginService.actualizarPais().success(function() {
                            $rootScope.$broadcast('actualizarListaPaises');
                        });
                    }
                    $state.go('colecciones');
                }).error(function() {
                    $scope.mostrarMensajeError();
                });
            }).error(function() {
                $scope.mostrarMensajeError();
            });
        } else {
            $scope.mostrarMensajeError();
        }
    };

    $scope.listarPaises = function() {
        LoginService.listarPaises().success(function(listaPaises) {
            $scope.paises = $scope.paises.concat(listaPaises);
        });
    };

    $scope.obtenerPaisSeleccionado = function(paises, idPais) {
        var pais = {nombre: '', codigo: ''};

        if (sesionConstants.CODIGO_CORP === idPais) {
            idPais = sesionConstants.CODIGO_PAIS_DEFAULT;
        }

        for (var i = 0; i < paises.length; i++) {
            if (paises[i].id === idPais) {
                pais = paises[i];
            }
        }

        return pais;
    };

    $scope.mostrarMensajeError = function() {
        var jsonModal = {
                titulo: mensajesConstants.loginMensajeError.titulo,
                mensaje: mensajesConstants.loginMensajeError.mensaje
        };
        modalUtil.openModal(jsonModal);
    };

    $scope.validarFormulario = function(objJson) {
        var username = objJson.username;
        var password = objJson.password;
        var pais = objJson.country;

        if (username && password && pais && username !== '' &&
            password !== '' && pais !== '') {
            var EXPREG_USERNAME = /^[0-9a-zA-Z\x2E\x2D]*$/;
            var EXPREG_PASSWORD = /^[A-Za-z0-9\x2D\x5F\x5C\x2F\x5E\x24\x2A\x2B\x3F\x21\x25\x26\x2E\x3A\x2C\x3D\x40\x23\x28\x29\x7C\x5B\x5D\x7B\x7D]*$/;
            return EXPREG_USERNAME.test(username) &&
            EXPREG_PASSWORD.test(password);
        }

        return false;
    };


    $scope.obtenerPerfilDeUsuario = function(perfil) {
        // obtener el perfil de usuario
        var cadenaInicio = 'CN=';
        var cadenaFin = ',';
        var indiceInicio = perfil.indexOf(cadenaInicio);
        var cadenaCN = perfil.substring(indiceInicio);
        var indiceFin = cadenaCN.indexOf(cadenaFin);
        var perfilUsuario = cadenaCN.substring(cadenaInicio.length, indiceFin);
        return perfilUsuario;
    };

    $scope.init();

}]);
