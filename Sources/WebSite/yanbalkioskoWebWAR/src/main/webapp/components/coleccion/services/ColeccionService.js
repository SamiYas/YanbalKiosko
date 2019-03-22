angular.module('app.services')

.service('ColeccionService',
    ['$http', '$q', 'serviceUrlConstants', 'tramaEliminarColeccion',
    'tramaObtenerRutaOrigen', 'tramaListarColecciones', 'tramaListarArchivos',
    'tramaObtenerPorNotificar', 'tramaEnviarNotificacionPush',
    'tramaObtenerColeccion', 'tramaInsertarColeccion',
    'tramaActualizarColeccion',
    function($http, $q, serviceUrlConstants, tramaEliminarColeccion,
    tramaObtenerRutaOrigen, tramaListarColecciones, tramaListarArchivos,
    tramaObtenerPorNotificar, tramaEnviarNotificacionPush,
    tramaObtenerColeccion, tramaInsertarColeccion, tramaActualizarColeccion) {

    return {
        eliminarColeccion: function(objJson) {

            tramaEliminarColeccion.Body.Request = {
                    id_coleccion: objJson.id_coleccion
            };

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlEliminarColeccion,
                method: 'POST',
                data: JSON.stringify(tramaEliminarColeccion)
            }).success(function(data) {
                deferred.resolve(data.Body.Response);
            });

            promise.success = function(fn) {
                promise.then(fn);
                return promise;
            };
            promise.error = function(fn) {
                promise.then(null, fn);
                return promise;
            };
            return promise;
        },
        obtenerRutaOrigen: function(objJson) {

            tramaObtenerRutaOrigen.Body.Request = {
                    token: objJson.token,
                    id: objJson.id
            };

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlObtenerRutaOrigen,
                method: 'POST',
                data: JSON.stringify(tramaObtenerRutaOrigen)
            }).success(function(data) {
                deferred.resolve(data.Body.Response);
            });

            promise.success = function(fn) {
                promise.then(fn);
                return promise;
            };
            promise.error = function(fn) {
                promise.then(null, fn);
                return promise;
            };
            return promise;
        },
        listarColecciones: function(objJson) {

            tramaListarColecciones.Body.Request = {
                    token: objJson.token,
                    id_coleccion_padre: objJson.id_coleccion_padre
            };

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlListarColecciones,
                method: 'POST',
                data: JSON.stringify(tramaListarColecciones)
            }).success(function(data) {
                deferred.resolve(data.Body.Response);
            });

            promise.success = function(fn) {
                promise.then(fn);
                return promise;
            };
            promise.error = function(fn) {
                promise.then(null, fn);
                return promise;
            };
            return promise;
        },
        listarArchivos: function(objJson) {

            tramaListarArchivos.Body.Request = {
                    token: objJson.token,
                    id_coleccion: objJson.id_coleccion,
                    nombre: objJson.nombre,
                    descripcion: objJson.descripcion
            };

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlListarArchivos,
                method: 'POST',
                data: JSON.stringify(tramaListarArchivos)
            }).success(function(data) {
                deferred.resolve(data.Body.Response);
            });

            promise.success = function(fn) {
                promise.then(fn);
                return promise;
            };
            promise.error = function(fn) {
                promise.then(null, fn);
                return promise;
            };
            return promise;
        },
        getNotificationAmount: function(objJson) {

            tramaObtenerPorNotificar.Body.Request = {
                    token: objJson.token,
                    pais: objJson.pais
            };

            var deferred = $q.defer();
            var promise = deferred.promise;
            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlObtenerPorNotificar,
                method: 'POST',
                data: JSON.stringify(tramaObtenerPorNotificar)
            }).success(function(data) {
                deferred.resolve(data.Body.Response);
            });
            promise.success = function(fn) {
                promise.then(fn);
                return promise;
            };
            promise.error = function(fn) {
                promise.then(null, fn);
                return promise;
            };
            return promise;
        },
        enviarNotificacionPush: function(objJson) {

            tramaEnviarNotificacionPush.Body.Request = {
                    mensaje: objJson.mensaje
            };

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlEnviarNotificacionPush,
                method: 'POST',
                data: JSON.stringify(tramaEnviarNotificacionPush)
            }).success(function(data) {
                deferred.resolve(data.Header.ResponseCode);
            });

            promise.success = function(fn) {
                promise.then(fn);
                return promise;
            };
            promise.error = function(fn) {
                promise.then(null, fn);
                return promise;
            };
            return promise;
        },
        obtenerColeccion: function(objJson) {

            tramaObtenerColeccion.Body.Request = {
                    id_coleccion: objJson.id_coleccion
            };

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlObtenerColeccion,
                method: 'POST',
                data: JSON.stringify(tramaObtenerColeccion)
            }).success(function(data) {
                deferred.resolve(data.Body.Response);
            });

            promise.success = function(fn) {
                promise.then(fn);
                return promise;
            };
            promise.error = function(fn) {
                promise.then(null, fn);
                return promise;
            };
            return promise;
        },
        insertarColeccion: function(objJson) {

            tramaInsertarColeccion.Body.Request = {
                    id_coleccion_padre: objJson.id_coleccion_padre,
                    id_rol: objJson.id_rol,
                    nombre: objJson.nombre,
                    nivel: objJson.nivel,
                    orden: objJson.orden,
                    color: objJson.color,
                    descripcion: objJson.descripcion
            };

            var deferred = $q.defer();
            var promise = deferred.promise;
            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlInsertarColeccion,
                method: 'POST',
                data: JSON.stringify(tramaInsertarColeccion)
            }).success(function(data) {
                deferred.resolve(data.Header);
            });

            promise.success = function(fn) {
                promise.then(fn);
                return promise;
            };
            promise.error = function(fn) {
                promise.then(null, fn);
                return promise;
            };
            return promise;
        },
        actualizarColeccion: function(objJson) {

            tramaActualizarColeccion.Body.Request = {
                    id_coleccion: objJson.id_coleccion,
                    id_coleccion_padre: objJson.id_coleccion_padre,
                    id_rol: objJson.id_rol,
                    nombre: objJson.nombre,
                    nivel: objJson.nivel,
                    orden: objJson.orden,
                    color: objJson.color,
                    descripcion: objJson.descripcion
            };

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlActualizarColeccion,
                method: 'POST',
                data: JSON.stringify(tramaActualizarColeccion)
            }).success(function(data) {
                deferred.resolve(data.Header);
            });

            promise.success = function(fn) {
                promise.then(fn);
                return promise;
            };
            promise.error = function(fn) {
                promise.then(null, fn);
                return promise;
            };
            return promise;
        }
    };
}]);
