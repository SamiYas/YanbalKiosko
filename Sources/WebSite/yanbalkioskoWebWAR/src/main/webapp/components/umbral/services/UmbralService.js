angular.module('app.services')

.service('UmbralService',
    ['$http', '$q', 'serviceUrlConstants', 'responseConstants',
    'tramaListarUmbrales', 'tramaObtenerUmbral', 'tramaActualizarUmbral',
    function($http, $q, serviceUrlConstants, responseConstants,
        tramaListarUmbrales, tramaObtenerUmbral, tramaActualizarUmbral) {

    return {
        listarUmbrales: function() {

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlListarUmbrales,
                method: 'POST',
                data: JSON.stringify(tramaListarUmbrales)
            }).success(function(data) {
                if (data && data != null) {
                    if (data.Header.ResponseCode === responseConstants.RESPUESTA_OK) {
                        deferred.resolve(data.Body.Response);
                    }else {
                        deferred.reject(data.Header.ResponseCode);
                    }
                }else {
                    deferred.reject(data);
                }
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
        obtenerUmbral: function(objJson) {
            var deferred = $q.defer();
            var promise = deferred.promise;

            tramaObtenerUmbral.Body.Request = {
                    id: objJson.id
            };

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlObtenerUmbral,
                method: 'POST',
                data: JSON.stringify(tramaObtenerUmbral)
            }).success(function(data) {
                if (data && data != null) {
                    if (data.Header.ResponseCode === responseConstants.RESPUESTA_OK) {
                        deferred.resolve(data.Body.Response);
                    }else {
                        deferred.reject(data.Header.ResponseCode);
                    }
                }else {
                    deferred.reject(data);
                }
            }).error(function(err) {
                deferred.reject(err);
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
        actualizarUmbral: function(objJson) {
            var deferred = $q.defer();
            var promise = deferred.promise;

            tramaActualizarUmbral.Body.Request = {
                    id: objJson.id,
                    extension: objJson.extension,
                    descripcion: objJson.descripcion,
                    carga: objJson.carga,
                    descarga: objJson.descarga
            };

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlActualizarUmbral,
                method: 'POST',
                data: JSON.stringify(tramaActualizarUmbral)
            }).success(function(data) {
                if (data && data != null) {
                    if (data.Header.ResponseCode === responseConstants.RESPUESTA_OK) {
                        deferred.resolve(data.Body.Response.resultado);
                    }else {
                        deferred.reject(data.Header.ResponseCode);
                    }
                }else {
                    deferred.reject(data);
                }
            }).error(function(err) {
                deferred.reject(err);
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
