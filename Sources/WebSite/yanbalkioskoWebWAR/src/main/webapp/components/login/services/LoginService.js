angular.module('app.services', [])

.service('LoginService',
    ['$http', '$q', 'serviceUrlConstants', 'responseConstants',
    'sesionConstants', 'tramaYanbal', 'tramaIniciarSesion',
    'tramaActualizarPais', 'tramaCerrarSesion', 'tramaObtenerUsuarioToken',
    function($http, $q, serviceUrlConstants, responseConstants, sesionConstants,
    tramaYanbal, tramaIniciarSesion, tramaActualizarPais, tramaCerrarSesion,
    tramaObtenerUsuarioToken) {

    return {
        autenticarUsuario: function(objJson) {
            var deferred = $q.defer();
            var promise = deferred.promise;

            tramaYanbal.IntegracionWSReq.Cabecera.UsuarioAplicacion = objJson.username;
            tramaYanbal.IntegracionWSReq.Cabecera.CodigoPais = objJson.country;
            tramaYanbal.IntegracionWSReq.Cabecera.CodigosPaisOD.CodigoPaisOD.Valor = objJson.country;
            tramaYanbal.IntegracionWSReq.Detalle.Parametros.Usuario = objJson.username;
            tramaYanbal.IntegracionWSReq.Detalle.Parametros.Password = objJson.password;
            tramaYanbal.IntegracionWSReq.Detalle.Parametros.TipoUsuario = sesionConstants.TIPO_USER_STAFF;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlYanbalValidarSesion,
                method: 'POST',
                data: JSON.stringify(tramaYanbal)
            }).success(function(result) {
                try {
                    var codigoRespuesta = result.IntegracionWSResp.Detalle.Respuesta.CodigoRespuesta;
                    var flagValidacion = result.IntegracionWSResp.Detalle.Respuesta.Datos.FlagValidacion;
                    var tipoUsuario = result.IntegracionWSResp.Detalle.Respuesta.Datos.TipoUsuario;

                    if (responseConstants.RESPUESTA_OK_YANBAL === codigoRespuesta) {
                        if (responseConstants.FLAG_VALID_OK_YANBAL === flagValidacion &&
                                sesionConstants.TIPO_USER_STAFF === tipoUsuario) {
                            deferred.resolve(result.IntegracionWSResp.Detalle.Respuesta.Datos);
                        }else {
                            deferred.reject(result);
                        }
                    }else {
                        deferred.reject(result);
                    }
                }catch (err) {
                    deferred.reject(err);
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
        registrarSesion: function(objJson) {
            var deferred = $q.defer();
            var promise = deferred.promise;

            tramaIniciarSesion.Body.Request = {
                    usuario: objJson.username,
                    clave: objJson.password,
                    pais: objJson.country,
                    rol: objJson.rol
            };

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlRegistrarSesion,
                method: 'POST',
                data: JSON.stringify(tramaIniciarSesion)
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
        obtenerUsuarioToken: function() {
            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlObtenerUsuarioToken,
                method: 'POST',
                data: JSON.stringify(tramaObtenerUsuarioToken)
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
        actualizarPais: function() {
            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlActualizarPais,
                method: 'POST',
                data: JSON.stringify(tramaActualizarPais)
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
        listarPaises: function() {
            var deferred = $q.defer();
            var promise = deferred.promise;

            $http.get(serviceUrlConstants.urlServidor + serviceUrlConstants.urlListarPaises).
            success(function(data) {
                if (data && data != null) {
                    if (data.Header.ResponseCode === responseConstants.RESPUESTA_OK) {
                        deferred.resolve(data.Body.Response.paises);
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
        cerrarSesion: function() {
            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlCerrarSesion,
                method: 'POST',
                data: JSON.stringify(tramaCerrarSesion)
            }).success(function(data) {
                if (data != null && data.Header.ResponseCode) {
                    if (data.Header.ResponseCode === responseConstants.RESPUESTA_OK) {
                        deferred.resolve(data.Header.ResponseCode.resultado);
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
