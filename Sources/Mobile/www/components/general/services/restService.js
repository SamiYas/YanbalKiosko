angular.module('YanbalKiosko.services')

.factory('RestService', ['$cordovaSQLite', '$q', '$http', '$ionicPlatform', 'libreriaCorporativa', 'serviceUrlConstants', 'textConstants',
    function ($cordovaSQLite, $q, $http, $ionicPlatform, libreriaCorporativa, serviceUrlConstants, textConstants) {
    var self = this;

    self.autenticacionYanbal = function (tramaYambal) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $http({
            url: serviceUrlConstants.servidorYanbal + serviceUrlConstants.autenticarUsuario,
            method: 'POST',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            params: {
                formato: 'json',
                trama: JSON.stringify(tramaYambal)
            },
            data: ''
        }).success(function (result) {
            //Si el usuario se valida correctamente como F.V.
            if (result.IntegracionWSResp.Detalle.Respuesta.CodigoRespuesta === textConstants.CODIGO_TRANSACCION_OK_YANBAL && result.IntegracionWSResp.Detalle.Respuesta.Datos.FlagValidacion === textConstants.CODIGO_EXITO_YANBAL) {
                deferred.resolve(result);
            } else if (result.IntegracionWSResp.Detalle.Respuesta.CodigoRespuesta === textConstants.CODIGO_USUARIO_NO_EXISTE_YANBAL) {
                //Si el usuario no se encuentra como F.V. entonces intentar como staff
                tramaYambal.IntegracionWSReq.Detalle.Parametros.TipoUsuario = textConstants.CODIGO_USUARIO_STAFF_YANBAL;
                $http({
                    url: serviceUrlConstants.servidorYanbal + serviceUrlConstants.autenticarUsuario,
                    method: 'POST',
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    params: {
                        formato: 'json',
                        trama: JSON.stringify(tramaYambal)
                    },
                    data: ''
                }).success(function (staff) {
                    if (result.IntegracionWSResp.Detalle.Respuesta.CodigoRespuesta === textConstants.CODIGO_TRANSACCION_OK_YANBAL && staff.IntegracionWSResp.Detalle.Respuesta.Datos.FlagValidacion === textConstants.CODIGO_EXITO_YANBAL) {
                        deferred.resolve(staff);
                    } else {
                        deferred.reject(result.IntegracionWSResp.Detalle.Respuesta.MensajeRespuesta);
                    }
                }).error(function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject(result.IntegracionWSResp.Detalle.Respuesta.MensajeRespuesta);
                });
            } else {
                deferred.reject(result.IntegracionWSResp.Detalle.Respuesta.MensajeRespuesta);
            }
        }).error(function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject(err);
        });

        promise.success = function (fn) {
            promise.then(fn);
            return promise;
        };
        promise.error = function (fn) {
            promise.then(null, fn);
            return promise;
        };
        return promise;
    };

    self.registrarSesion = function (tramaRegistrarSesion) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        $http({
            url: serviceUrlConstants.servidorDesarrollo + serviceUrlConstants.registrarSesion,
            method: 'POST',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
                'Content-Type': 'application/json'
            },
            data: tramaRegistrarSesion
        }).success(function (resultToken) {
            deferred.resolve(resultToken);
        }).error(function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject(err);
        });

        promise.success = function (fn) {
            promise.then(fn);
            return promise;
        };
        promise.error = function (fn) {
            promise.then(null, fn);
            return promise;
        };
        return promise;
    };

    self.cerrarSesion = function (tramaCerrarSesion) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        $http({
            url: serviceUrlConstants.servidorDesarrollo + serviceUrlConstants.cerrarSesion,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: tramaCerrarSesion
        }).success(function (resultCerrarSesion) {
            deferred.resolve(resultCerrarSesion);
        }).error(function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject(err);
        });

        promise.success = function (fn) {
            promise.then(fn);
            return promise;
        };
        promise.error = function (fn) {
            promise.then(null, fn);
            return promise;
        };
        return promise;
    };

    self.obtenerPaises = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;

        $http({
            url: serviceUrlConstants.servidorDesarrollo + serviceUrlConstants.listarPaises,
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function (result) {
            deferred.resolve(result);
        }).error(function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject(err);
        });

        promise.success = function (fn) {
            promise.then(fn);
            return promise;
        };
        promise.error = function (fn) {
            promise.then(null, fn);
            return promise;
        };
        return promise;
    };

    self.obtenerColeccionesContenido = function (tramaObtenerColeccionesArchivos) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        $http({
            url: serviceUrlConstants.servidorDesarrollo + serviceUrlConstants.obtenerColeccionesArchivos,
            method: 'POST',
            headers: {
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
                'Content-Type': 'application/json'
            },
            data: tramaObtenerColeccionesArchivos
        }).success(function (resultContenido) {
            deferred.resolve(resultContenido);
        }).error(function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject(err);
        });

        promise.success = function (fn) {
            promise.then(fn);
            return promise;
        };
        promise.error = function (fn) {
            promise.then(null, fn);
            return promise;
        };
        return promise;
    };

    self.ValidarArchivoRol = function (tramaValidarArchivoRol) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        $http({
            url: serviceUrlConstants.servidorDesarrollo + serviceUrlConstants.validarArchivoRol,
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            data: tramaValidarArchivoRol
        }).success(function (result) {
            deferred.resolve(result);
        }).error(function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject(err);
        });

        promise.success = function (fn) {
            promise.then(fn);
            return promise;
        };
        promise.error = function (fn) {
            promise.then(null, fn);
            return promise;
        };
        return promise;
    };

    return self;
}]);