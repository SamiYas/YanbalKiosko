angular.module('YanbalKiosko.services', [])

.factory('BdHelper', ['$cordovaSQLite', '$q', '$ionicPlatform', '$localstorage', 'queryConstants', '$cordovaFile', '$cordovaDevice', 'textConstants', 'dateUtil', 'paisesConstants',
    function ($cordovaSQLite, $q, $ionicPlatform, $localstorage, queryConstants, $cordovaFile, $cordovaDevice, textConstants, dateUtil, paisesConstants) {
    var self = this;
    var db;
    // inicializacion de base de datos
    self.init = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;
        var targetPath;
        var cacheTargetPath;
        $localstorage.set('platform', ionic.Platform.platform());
        $localstorage.set('uuid', $cordovaDevice.getUUID());
        if (ionic.Platform.isAndroid()) {
            targetPath = cordova.file.externalDataDirectory;
            cacheTargetPath = cordova.file.externalCacheDirectory;
        } else if (ionic.Platform.isWindowsPhone()) {
            targetPath = '///';
        } else {
            targetPath = cordova.file.dataDirectory;
            cacheTargetPath = cordova.file.cacheDirectory;
        }

        $ionicPlatform.ready(function () {
            try {
                //creacion de BD
                if (window.cordova) {
                    // en applicativo
                    db = $cordovaSQLite.openDB("yambalKiosko.db", 2);
                } else {
                    // usando el ionic serve (web)
                    db = window.openDatabase("yambalKiosko.db", '1', 'yambalKiosko', 1024 * 1024 * 100);
                }
                // if primer inicio
                if ($localstorage.get('InicioApp') !== textConstants.LOCALSTORAGETRUE) {
                    $localstorage.set('InicioApp', textConstants.LOCALSTORAGETRUE);
                    var folderName = dateUtil.getTodayJustNumbers();
                    $cordovaFile.createDir(targetPath, folderName, true).then(function () {
                        if (ionic.Platform.isWindowsPhone()) {
                            cacheTargetPath = targetPath;
                            folderName = textConstants.windowsPhoneCacheFolder;
                        }
                        return $cordovaFile.createDir(cacheTargetPath, folderName, true);
                    }).then(function () {
                        //creacion de tablas
                        return $q.all([
                            $cordovaSQLite.execute(db, queryConstants.createTableColeccion),
                            $cordovaSQLite.execute(db, queryConstants.createTableArchivo),
                            $cordovaSQLite.execute(db, queryConstants.createTableVariables),
                            $cordovaSQLite.execute(db, queryConstants.createTableBitacora),
                            $cordovaSQLite.execute(db, queryConstants.createTablePais)
                        ]);
                    }).then(function () {
                        //insertar paises fijos
                        var paises = [paisesConstants.Pais1, paisesConstants.Pais2, paisesConstants.Pais3, paisesConstants.Pais4, paisesConstants.Pais5, paisesConstants.Pais6, paisesConstants.Pais7];
                        var querys = [];
                        for (var i = 0; i < paises.length; i++) {
                            querys.push($cordovaSQLite.execute(db, queryConstants.replacePais, [(i + 1), paises[i].nombre, paises[i].codigo]));
                        }
                        //insertar datos mobiles activos por defecto
                        querys.push($cordovaSQLite.execute(db, queryConstants.insertVariable, [textConstants.DATOS_MOVILES, '0']));
                        //crear e insertar nombre de carpeta
                        querys.push($cordovaSQLite.execute(db, queryConstants.insertVariable, [textConstants.NOMBRE_CARPETA, folderName]));
                        return $q.all(querys);
                    }).then(function (resultset) {
                        deferred.resolve(resultset);
                    }).catch(function (err) {
                        libreriaCorporativa.RaygunSendError(err);
                        deferred.reject(err);
                    });
                } else {
                    deferred.resolve('init');
                }
            } catch (err) {
                libreriaCorporativa.RaygunSendError(err);
                deferred.reject(err);
            }
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

    // Manejo de query y posibles errores
    self.query = function (query, parameters) {
        parameters = parameters || [];
        var q = $q.defer();

        $ionicPlatform.ready(function () {
            $cordovaSQLite.execute(db, query, parameters)
              .then(function (result) {
                  q.resolve(result);
              }, function (error) {
                  q.reject(error);
              });
        });
        return q.promise;
    };

    // procesar result set
    self.getAll = function (result) {
        var output = [];

        for (var i = 0; i < result.rows.length; i++) {
            output.push(result.rows.item(i));
        }
        return output;
    };

    // procesar resultado unitario
    self.getById = function (result) {
        var output = null;
        output = angular.copy(result.rows.item(0));
        return output;
    };

    return self;
}]);