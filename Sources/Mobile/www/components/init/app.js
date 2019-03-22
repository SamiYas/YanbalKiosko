angular.module('YanbalKiosko', ['ionic', 'YanbalKiosko.controllers', 'YanbalKiosko.services', 'YanbalKiosko.directives', 'YanbalKiosko.utils', 'YanbalKiosko.constants', 'YanbalKiosko.values', 'ngCordova', 'ion-autocomplete', 'templates', 'pascalprecht.translate'])

.run(['$ionicPlatform', 'BdHelper', '$state', 'queryConstants', 'libreriaCorporativa', function ($ionicPlatform, BdHelper, $state, queryConstants, libreriaCorporativa) {
    $ionicPlatform.ready(function () {
        if (window.cordova && window.cordova.plugins && window.cordova.plugins.Keyboard) {
            cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
        }
        if (window.StatusBar) {
            // requiere org.apache.cordova.statusbar
            StatusBar.styleLightContent();
        }
        //Inicializacion de base de datos
        BdHelper.init().success(function () {
            BdHelper.query(queryConstants.selectVariable, ['TOKEN']).then(function (selectToken) {
                if (BdHelper.getAll(selectToken)[0] && BdHelper.getAll(selectToken)[0].VALOR) {
                    $state.go('tab.coleccion');
                } else {
                    $state.go('login');
                }
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
            });
        });
    });
}])

.config(['$stateProvider', '$urlRouterProvider', '$httpProvider', function ($stateProvider, $urlRouterProvider, $httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];


    // Ionic usa angular ui que a su ves usa el concepto de estados
    // para mas informacion: https://github.com/angular-ui/ui-router
    // Each state's controller can be found in js/controllers
    $stateProvider

    // login, primer estado del aplicativo
    .state('login', {
        cache: false,
        url: '/login',
        templateUrl: 'login/views/login.html',
        controller: 'LoginController'
    })

    // configurar estado abstracto para las tabs de navegacion
    .state('tab', {
        url: "/tab",
        abstract: true,
        templateUrl: "general/views/tabs.html"
    })

    // cada tab tiene su propia pila de historial

    .state('tab.coleccion', {
        cache: false,
        url: '/coleccion',
        views: {
            'tab-coleccion': {
                templateUrl: 'coleccion/views/coleccion-principal.html',
                controller: 'ColeccionController'
            }
        }
    })

    .state('tab.coleccion-detail', {
        cache: false,
        url: '/coleccion?coleccionId&title',
        views: {
            'tab-coleccion': {
                templateUrl: 'coleccion/views/coleccion.html',
                controller: 'ColeccionController'
            }
        }
    })

    .state('tab.notificacion', {
        cache: false,
        url: '/notificacion',
        views: {
            'tab-notificacion': {
                templateUrl: 'notificacion/views/notificacion.html',
                controller: 'ArchivoController'
            }
        }
    })

    .state('tab.configuracion', {
        cache: false,
        url: '/configuracion',
        views: {
            'tab-configuracion': {
                templateUrl: 'configuracion/views/configuracion.html',
                controller: 'ConfiguracionController'
            }
        }
    });

    // si ningun estado coincide se utilizara este estado como pre determinado
    $urlRouterProvider.otherwise('/');
}]);