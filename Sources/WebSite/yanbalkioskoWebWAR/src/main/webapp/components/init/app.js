angular.module('app',
    ['ui.router', 'ui.bootstrap', 'ui.grid', 'ui.grid.selection',
    'ngFileUpload', 'app.controllers', 'app.services', 'app.directives',
    'app.values', 'app.constants', 'app.utils', 'LocalStorageModule'])
.run(['$rootScope', '$location', 'SesionService', '$modalStack', '$window',
    'mensajesConstants',
    function ($rootScope, $location, SesionService, $modalStack, $window,
        mensajesConstants) {
        $rootScope.location = $location;
        $rootScope.$on('$locationChangeStart', function () {
            if ($location.path() !== '') {
                $modalStack.dismissAll();
            }
            if (!SesionService.estaAutenticado()) {
                $location.path('/login');
            }
            if (SesionService.estaAutenticado() &&
                ($location.path() === '/login' || $location.path() === '')) {
                $location.path('/coleccion');
            }
            $window.document.title = mensajesConstants.indicadorVersion;
            $rootScope.$broadcast('actualizarNav');
        });
}])
.config([
    '$urlRouterProvider', '$stateProvider', '$httpProvider',
    'localStorageServiceProvider', 'sesionConstants',
    function($urlRouterProvider, $stateProvider, $httpProvider,
        localStorageServiceProvider, sesionConstants) {
        localStorageServiceProvider.setStorageType('localStorage');
        localStorageServiceProvider.setStorageCookie(
            sesionConstants.EXPIRACION, '/');

        $httpProvider.defaults.useXDomain = true;
        $httpProvider.interceptors.push('sesionInterceptor');

        $urlRouterProvider.otherwise('/coleccion');
        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: '../login/views/login.html',
                controller: 'loginController'
            })
            .state('colecciones', {
                url: '/coleccion',
                templateUrl: '../coleccion/views/administrar-colecciones.html',
                controller: 'coleccionController'
            })
            .state('umbrales', {
                url: '/umbral',
                templateUrl: '../umbral/views/administrar-umbrales.html',
                controller: 'umbralController'
            });
    }
])
.factory('sesionInterceptor', ['$q', '$injector', 'SesionService',
    'responseConstants',
    function($q, $injector, SesionService, responseConstants) {

    var myInterceptor = {
        request: function(config) {
            if (config.data && config.url.indexOf('.html') === -1) {

                // agregando headers generales
                config.headers['Access-Control-Allow-Origin'] = '*';
                var metodos = 'GET, POST, PUT, DELETE, OPTIONS';
                config.headers['Access-Control-Allow-Methods'] = metodos;
                delete config.headers['X-Requested-With'];

                var hideUrlInsertarArchivo = '/web/insertarArchivo';
                var hideUrlActualizarArchivo = '/web/actualizarArchivo';
                var hideInsertarArchivo = config.url
                .indexOf(hideUrlInsertarArchivo);
                var hideActualizarArchivo = config.url
                .indexOf(hideUrlActualizarArchivo);

                if (SesionService.estaAutenticado()) {
                    if (hideInsertarArchivo === -1 &&
                        hideActualizarArchivo === -1) {
                        var datosJson = JSON.parse(config.data);
                        if (datosJson.Body) {
                            datosJson.Body.Request
                            .token = SesionService.token();
                            datosJson.Body.Request
                            .pais = SesionService.paisId();
                            config.data = JSON.stringify(datosJson);

                            // agregando header content type
                            config.headers['Content-Type'] = 'application/json';
                        }
                    } else {
                        // agregando header indefinido
                        config.headers['Content-Type'] = undefined;
                    }
                }
            }
            return config;
        },
        response: function(response) {
            var respuestaValida = responseConstants.RESPUESTA_LOGIN_INVALIDO;
            if (response.data && response.data.Header &&
            response.data.Header.ResponseCode &&
            response.data.Header.ResponseCode === respuestaValida) {
                SesionService.clearAll();
                var stateService = $injector.get('$state');
                stateService.go('login');
            }
            return response;
        }
    };

    return myInterceptor;
}]);
