angular.module('YanbalKiosko.services')

.factory('ArchivoService', ['$q', '$cordovaFileTransfer', 'BdHelper', 'queryConstants', '$cordovaFile', '$cordovaFileOpener2', '$ionicPlatform', '$cordovaSQLite', '$cordovaNetwork',
    '$filter', 'textConstants', '$localstorage', 'dateUtil', 'libreriaCorporativa', 'archivoHelper', 'stringUtil', '$rootScope', 'RestService', 'tramaValidarArchivoRol',
    function ($q, $cordovaFileTransfer, BdHelper, queryConstants, $cordovaFile, $cordovaFileOpener2, $ionicPlatform, $cordovaSQLite, $cordovaNetwork, $filter,
        textConstants, $localstorage, dateUtil, libreriaCorporativa, archivoHelper, stringUtil, $rootScope, RestService, tramaValidarArchivoRol) {
    var self = this;

    var targetPath;
    var cacheTargetPath;

    if (ionic.Platform.isAndroid()) {
        targetPath = cordova.file.externalDataDirectory;
        cacheTargetPath = cordova.file.externalCacheDirectory;
    } else if (ionic.Platform.isWindowsPhone()) {
        targetPath = '///';
    } else {
        targetPath = cordova.file.dataDirectory;
        cacheTargetPath = cordova.file.cacheDirectory;
    }

    self.GetNotificacion = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;
        BdHelper.query(queryConstants.selectArchivoNotificacion, []).then(function (selectNotificacion) {
            var resultVisible = BdHelper.getAll(selectNotificacion).filter(function (notif) {
                return notif.PUBLICADO === 1;
            });
            $rootScope.notificationClass = resultVisible.length > 0 ? textConstants.CLASSACTIVE : '';
            deferred.resolve(BdHelper.getAll(selectNotificacion));
        }, function (err) {
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

    self.GetArchivo = function (item) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        var mime = [{ variable: "JPG", valor: "image/jpeg" }, { variable: "PDF", valor: "application/pdf" }, { variable: "MP3", valor: "audio/mpeg3" }, { variable: "MP4", valor: "video/mpeg" }];
        var mimeType;
        for (var i = 0; i < mime.length; i++) {
            if (mime[i].variable === item.EXTENSION) {
                mimeType = mime[i].valor;
            }
        }

        archivoHelper.AbrirArchivo(item, mimeType).success(function (data) {
            deferred.resolve(data);
        }).error(function (err) {
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

    self.GetArchivoNoDescargado = function (item) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        var mime = [{ variable: "JPG", valor: "image/jpeg" }, { variable: "PDF", valor: "application/pdf" }, { variable: "MP3", valor: "audio/mpeg3" }, { variable: "MP4", valor: "video/mpeg" }];
        var mimeType;
        for (var i = 0; i < mime.length; i++) {
            if (mime[i].variable === item.EXTENSION) {
                mimeType = mime[i].valor;
            }
        }
        $q.all([
            BdHelper.query(queryConstants.selectVariable, [textConstants.NOMBRE_CARPETA]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.USR]),
            BdHelper.query(queryConstants.updateArchivoNuevo, [item.CORRELATIVO_ARCHIVO]),
            BdHelper.query(queryConstants.updateDescargandoArchivo, [1, item.CORRELATIVO_ARCHIVO])
        ]).then(function (selectVariables) {
            var folderName = BdHelper.getById(selectVariables[0]).VALOR;
            var fullPath = cacheTargetPath + folderName + '/' + stringUtil.getLastString(item.RUTA_ARCHIVO);
            if (ionic.Platform.isWindowsPhone()) {
                cacheTargetPath = targetPath + textConstants.windowsPhoneCacheFolder + '/';
                fullPath = cacheTargetPath + stringUtil.getLastString(item.RUTA_ARCHIVO);
            }
            var name = BdHelper.getById(selectVariables[1]).VALOR;
            archivoHelper.DescargarAbrirArchivo(item, fullPath, name, mimeType).success(function (data) {
                deferred.resolve(data);
            }).error(function (err) {
                deferred.reject(err);
            });
        }, function (err) {
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

    self.GuardarLocalmente = function (item) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        $q.all([
            BdHelper.query(queryConstants.selectVariable, [textConstants.NOMBRE_CARPETA]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.USR]),
            BdHelper.query(queryConstants.updateArchivoNuevo, [item.CORRELATIVO_ARCHIVO]),
            BdHelper.query(queryConstants.updateDescargandoArchivo, [1, item.CORRELATIVO_ARCHIVO])
        ]).then(function (selectVariables) {
            var folderName = BdHelper.getById(selectVariables[0]).VALOR;
            var fullPath = targetPath + folderName + '/' + stringUtil.getLastString(item.RUTA_ARCHIVO);
            var name = BdHelper.getById(selectVariables[1]).VALOR;
            return archivoHelper.GuardarArchivo(item, fullPath, name);
        }).then(function (data) {
            deferred.resolve(data);
        }).catch(function (err) {
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

    self.DeleteArchivo = function (item, removeRecord) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        var name = stringUtil.getLastString(item.RUTA_ARCHIVO);
        var dir = item.ARCHIVO_DESCARGADO.replace(name, '');
        archivoHelper.eliminarArchivo(item, dir, name, removeRecord).success(function (data) {
            deferred.resolve(data);
        }).error(function (err) {
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

    self.ObtenerDatosMovilesUmbral = function(extension){
        var deferred = $q.defer();
        var promise = deferred.promise;

        $q.all([
            BdHelper.query(queryConstants.selectVariable, [extension]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.DATOS_MOVILES])
        ]).then(function(data){
            var result = {
                umbral: BdHelper.getById(data[0]).VALOR,
                datosMoviles: BdHelper.getById(data[1]).VALOR
            };
            deferred.resolve(result);
        },function(err){
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject('ERRORGENERICO');
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

    self.ValidarArchivoRol = function (archivoId) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        $q.all([
            BdHelper.query(queryConstants.selectVariable, [textConstants.TOKEN]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.ROL])
        ]).then(function (data) {
            var token = BdHelper.getById(data[0]);
            var codigoRol = BdHelper.getById(data[1]);
            tramaValidarArchivoRol.Body.Request.id_archivo = archivoId;
            tramaValidarArchivoRol.Body.Request.token = token && token.VALOR ? token.VALOR : '';
            tramaValidarArchivoRol.Body.Request.rol = codigoRol && codigoRol.VALOR ? codigoRol.VALOR : '';
            return RestService.ValidarArchivoRol(tramaValidarArchivoRol);
        }).then(function (result) {
            if (result.Header.ResponseCode === textConstants.CODIGO_EXITO) {
                deferred.resolve(result.Body.Response.codigoRespuesta);
            } else {
                libreriaCorporativa.RaygunSendError(result.Header.ResponseCode);
                deferred.reject(result.Header.ResponseCode);
            }
        }).catch(function () {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject('ERRORGENERICO');
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

    self.ActualizarFlagDescargando = function(flag, correlativo){
        var deferred = $q.defer();
        var promise = deferred.promise;

        BdHelper.query(queryConstants.updateDescargandoArchivo, [flag, correlativo]).then(function () {
            deferred.resolve(flag);
        }, function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject('ERRORGENERICO');
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