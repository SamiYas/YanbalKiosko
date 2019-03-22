angular.module('YanbalKiosko.utils')

.factory('archivoHelper', ['$q', '$cordovaFileOpener2', '$cordovaFile', '$cordovaFileTransfer', '$cordovaSQLite', '$localstorage', 'libreriaCorporativa', 'BdHelper', 'dateUtil',
    'textConstants', 'queryConstants',
    function ($q, $cordovaFileOpener2, $cordovaFile, $cordovaFileTransfer, $cordovaSQLite, $localstorage, libreriaCorporativa, BdHelper, dateUtil, textConstants, queryConstants) {
    var self = this;

    var trustHosts = true;
    var options = {};

    self.AbrirArchivo = function (item, mimeType) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        if (ionic.Platform.isWindowsPhone()) {
            cordova.plugins.fileOpener2.open(item.ARCHIVO_DESCARGADO, mimeType, {
                error: function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject('ERRORGENERICO');
                },
                success: function () {
                    BdHelper.query(queryConstants.insertLog, ["0", textConstants.abrirArchivoDescripcion, textConstants.abrirArchivoAccion, item.NOMBRE, item.TAMANO_ARCHIVO, item.EXTENSION, item.DESCARGABLE, name, $localstorage.get('platform'), $localstorage.get('uuid'), dateUtil.getToday()]).then(function () {
                        //registro de logs exitoso
                        deferred.resolve('archivo se abrio y ya no es nuevo wifi');
                    });
                }
            });
        } else {
            $cordovaFileOpener2.open(item.ARCHIVO_DESCARGADO, mimeType).then(function () {
                BdHelper.query(queryConstants.insertLog, ["0", textConstants.abrirArchivoDescripcion, textConstants.abrirArchivoAccion, item.NOMBRE, item.TAMANO_ARCHIVO, item.EXTENSION, item.DESCARGABLE, name, $localstorage.get('platform'), $localstorage.get('uuid'), dateUtil.getToday()]).then(function () {
                    //registro de logs exitoso
                    deferred.resolve('archivo se abrio y ya no es nuevo wifi');
                });
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
                deferred.reject('ERRORGENERICO');
            });
        }

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

    self.DescargarAbrirArchivo = function (item, fullPath, name, mimeType) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        if (ionic.Platform.isWindowsPhone()) {
            var fileTransfer = new FileTransfer();
            fileTransfer.download(item.RUTA_ARCHIVO, fullPath, function () {
                BdHelper.query(queryConstants.updateDescargandoArchivo, [0, item.CORRELATIVO_ARCHIVO]).then(function () {
                    cordova.plugins.fileOpener2.open(fullPath, mimeType, {
                        error: function (err) {
                            libreriaCorporativa.RaygunSendError(err);
                            deferred.reject('ERRORGENERICO');
                        },
                        success: function () {
                            $q.all([
                            BdHelper.query(queryConstants.insertLog, ["0", textConstants.abrirArchivoDescripcion, textConstants.abrirArchivoAccion, item.NOMBRE, item.TAMANO_ARCHIVO, item.EXTENSION, item.DESCARGABLE, name, $localstorage.get('platform'), $localstorage.get('uuid'), dateUtil.getToday()])
                            ]).then(function () {
                                //registro de logs exitoso
                                deferred.resolve('archivo se abrio y ya no es nuevo wifi');
                            }, function (err) {
                                libreriaCorporativa.RaygunSendError(err);
                                deferred.reject('ERRORGENERICO');
                            });
                        }
                    });
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                });
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
                BdHelper.query(queryConstants.updateDescargandoArchivo, [0, item.CORRELATIVO_ARCHIVO]).then(function () {
                    deferred.reject('ERRORGENERICO');
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject('ERRORGENERICO');
                });
            });
        } else {
            $cordovaFileTransfer.download(item.RUTA_ARCHIVO, fullPath, options, trustHosts).then(function () {
                BdHelper.query(queryConstants.updateDescargandoArchivo, [0, item.CORRELATIVO_ARCHIVO]).then(function () {
                    $cordovaFileOpener2.open(fullPath, mimeType).then(function () {
                        $q.all([
                            BdHelper.query(queryConstants.insertLog, ["0", textConstants.abrirArchivoDescripcion, textConstants.abrirArchivoAccion, item.NOMBRE, item.TAMANO_ARCHIVO, item.EXTENSION, item.DESCARGABLE, name, $localstorage.get('platform'), $localstorage.get('uuid'), dateUtil.getToday()])
                        ]).then(function () {
                            //registro de logs exitoso
                            deferred.resolve('archivo se abrio y ya no es nuevo wifi');
                        }, function (err) {
                            libreriaCorporativa.RaygunSendError(err);
                            deferred.reject('error al registrar log, archivo abierto');
                        });
                    }, function (err) {
                        libreriaCorporativa.RaygunSendError(err);
                        deferred.reject('error al abrir archivo');
                    });
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                });
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
                BdHelper.query(queryConstants.updateDescargandoArchivo, [0, item.CORRELATIVO_ARCHIVO]).then(function () {
                    deferred.reject('error al guardar archivo');
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject('error al guardar archivo - error al actualizar flag descargando');
                });
            });
        }

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

    self.GuardarArchivo = function (item, fullPath, name) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        if (ionic.Platform.isWindowsPhone()) {

            var fileTransfer = new FileTransfer();
            fileTransfer.download(item.RUTA_ARCHIVO, fullPath, function () {
                $q.all([
                    BdHelper.query(queryConstants.updateArchivoRuta, [fullPath, item.CORRELATIVO_ARCHIVO]),
                    BdHelper.query(queryConstants.insertLog, ["0", textConstants.guardarArchivoDescripcion, textConstants.guardarArchivoAccion, item.NOMBRE, item.TAMANO_ARCHIVO, item.EXTENSION, item.DESCARGABLE, name, $localstorage.get('platform'), $localstorage.get('uuid'), dateUtil.getToday()]),
                    BdHelper.query(queryConstants.insertLog, ["1", textConstants.guardarArchivoDescripcion, textConstants.guardarArchivoAccion, item.NOMBRE, item.TAMANO_ARCHIVO, item.EXTENSION, item.DESCARGABLE, name, $localstorage.get('platform'), $localstorage.get('uuid'), dateUtil.getToday()]),
                    BdHelper.query(queryConstants.updateDescargandoArchivo, [0, item.CORRELATIVO_ARCHIVO])
                ]).then(function () {
                    //registro de logs exitoso
                    deferred.resolve('archivo se guardo y ya no es nuevo');
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject('error al registrar log, archivo guardado');
                });
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
                BdHelper.query(queryConstants.updateDescargandoArchivo, [0, item.CORRELATIVO_ARCHIVO]).then(function () {
                    deferred.reject('ERRORGENERICO');
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject('ERRORGENERICO');
                });
            });
        } else {
            $cordovaFileTransfer.download(item.RUTA_ARCHIVO, fullPath, options, trustHosts).then(function () {
                $q.all([
                    BdHelper.query(queryConstants.updateArchivoRuta, [fullPath, item.CORRELATIVO_ARCHIVO]),
                    BdHelper.query(queryConstants.insertLog, ["0", textConstants.guardarArchivoDescripcion, textConstants.guardarArchivoAccion, item.NOMBRE, item.TAMANO_ARCHIVO, item.EXTENSION, item.DESCARGABLE, name, $localstorage.get('platform'), $localstorage.get('uuid'), dateUtil.getToday()]),
                    BdHelper.query(queryConstants.insertLog, ["1", textConstants.guardarArchivoDescripcion, textConstants.guardarArchivoAccion, item.NOMBRE, item.TAMANO_ARCHIVO, item.EXTENSION, item.DESCARGABLE, name, $localstorage.get('platform'), $localstorage.get('uuid'), dateUtil.getToday()]),
                    BdHelper.query(queryConstants.updateDescargandoArchivo, [0, item.CORRELATIVO_ARCHIVO])
                ]).then(function () {
                    //registro de logs exitoso
                    deferred.resolve('archivo se guardo y ya no es nuevo wifi');
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject('error al registrar log, archivo guardado');
                });
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
                BdHelper.query(queryConstants.updateDescargandoArchivo, [0, item.CORRELATIVO_ARCHIVO]).then(function () {
                    deferred.reject('ERRORGENERICO');
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject('ERRORGENERICO');
                });
            });
        }

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

    self.GuardarVistaPrevia = function (url_imagen, fullPath) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        if (ionic.Platform.isWindowsPhone()) {
            var fileTransfer = new FileTransfer();
            fileTransfer.download(url_imagen, fullPath,
                function () {
                    deferred.resolve('vista previa guardada');
                },
                function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject('ERRORGENERICO');
                });
        } else {
            $cordovaFileTransfer.download(url_imagen, fullPath, options, trustHosts).then(function () {
                deferred.resolve('vista previa guardada');
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
                deferred.reject('ERRORGENERICO');
            });
        }

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

    self.eliminarArchivo = function (item, dir, name, removeRecord) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        if (dir) {
            //Si el archivo esta descargado fisicamente entonces eliminar primero el archivo
            $cordovaFile.removeFile(dir, name).then(function () {
                BdHelper.query(queryConstants.selectVariable, [textConstants.USR]).then(function (usrName) {
                    var userName = BdHelper.getById(usrName).VALOR;
                    var array = [];
                    if (removeRecord) {
                        array.push(BdHelper.query(queryConstants.deleteArchivo, [item.CORRELATIVO_ARCHIVO]));
                    } else {
                        array.push(BdHelper.query(queryConstants.updateArchivoRuta, ['', item.CORRELATIVO_ARCHIVO]));
                        array.push(BdHelper.query(queryConstants.insertLog, ["0", textConstants.eliminarArchivoDescripcion, textConstants.eliminarArchivoAccion, item.NOMBRE, item.TAMANO_ARCHIVO, item.EXTENSION, item.DESCARGABLE, userName, $localstorage.get('platform'), $localstorage.get('uuid'), dateUtil.getToday()]));
                    }
                    $q.all(array).then(function (data) {
                        deferred.resolve(data[0]);
                    }, function (err) {
                        libreriaCorporativa.RaygunSendError(err);
                        deferred.reject(err);
                    });
                });
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
                deferred.reject(err);
            });
        } else {
            //Si el archivo no esta descargado fisicamente entonces eliminar solo el registro de bd
            BdHelper.query(queryConstants.selectVariable, [textConstants.USR]).then(function (usrName) {
                var userName = BdHelper.getById(usrName).VALOR;
                var array = [];
                if (removeRecord) {
                    array.push(BdHelper.query(queryConstants.deleteArchivo, [item.CORRELATIVO_ARCHIVO]));
                } else {
                    array.push(BdHelper.query(queryConstants.updateArchivoRuta, ['', item.CORRELATIVO_ARCHIVO]));
                    array.push(BdHelper.query(queryConstants.insertLog, ["0", textConstants.eliminarArchivoDescripcion, textConstants.eliminarArchivoAccion, item.NOMBRE, item.TAMANO_ARCHIVO, item.EXTENSION, item.DESCARGABLE, userName, $localstorage.get('platform'), $localstorage.get('uuid'), dateUtil.getToday()]));
                }
                $q.all(array).then(function (data) {
                    deferred.resolve(data[0]);
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject(err);
                });
            });
        }

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

    self.reemplazarArchivo = function (item, fullPath) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        BdHelper.query(queryConstants.selectArchivoById, [item.CORRELATIVO]).then(function (selectArchivo) {
            var archivo = BdHelper.getById(selectArchivo);
            if (archivo) {
                if (item.RUTAARCHIVO !== archivo.RUTA_ARCHIVO && archivo.ARCHIVO_DESCARGADO) {
                    var name = archivo.NOMBRE.replace(/\s/g, "") + '.' + archivo.EXTENSION.toLowerCase();
                    var dir = archivo.ARCHIVO_DESCARGADO.replace(name, '');
                    $cordovaFile.removeFile(dir, name).then(function () {
                        BdHelper.query(queryConstants.replaceArchivo, [item.CORRELATIVO, item.CORRELATIVOCOLECCION, item.NOMBRE, item.RUTAARCHIVO, fullPath, item.DESTACADO, item.DESCARGABLE, 1, item.NROORDEN, '', item.TAMANHO, item.FECHAINICIO, item.FECHACADUCIDAD, item.EXTENSION, 0]).then(function (insertArchivo) {
                            deferred.resolve(insertArchivo);
                        }, function (err) {
                            libreriaCorporativa.RaygunSendError(err);
                            deferred.reject(err);
                        });
                    }, function (err) {
                        libreriaCorporativa.RaygunSendError(err);
                        deferred.reject(err);
                    });
                } else if (item.RUTAARCHIVO === archivo.RUTA_ARCHIVO && archivo.ARCHIVO_DESCARGADO) {
                    BdHelper.query(queryConstants.replaceArchivo, [item.CORRELATIVO, item.CORRELATIVOCOLECCION, item.NOMBRE, item.RUTAARCHIVO, fullPath, item.DESTACADO, item.DESCARGABLE, 1, item.NROORDEN, archivo.ARCHIVO_DESCARGADO, item.TAMANHO, item.FECHAINICIO, item.FECHACADUCIDAD, item.EXTENSION, 0]).then(function (insertArchivo) {
                        deferred.resolve(insertArchivo);
                    }, function (err) {
                        libreriaCorporativa.RaygunSendError(err);
                        deferred.reject(err);
                    });
                } else {
                    BdHelper.query(queryConstants.replaceArchivo, [item.CORRELATIVO, item.CORRELATIVOCOLECCION, item.NOMBRE, item.RUTAARCHIVO, fullPath, item.DESTACADO, item.DESCARGABLE, 1, item.NROORDEN, '', item.TAMANHO, item.FECHAINICIO, item.FECHACADUCIDAD, item.EXTENSION, 0]).then(function (insertArchivo) {
                        deferred.resolve(insertArchivo);
                    }, function (err) {
                        libreriaCorporativa.RaygunSendError(err);
                        deferred.reject(err);
                    });
                }
            } else {
                BdHelper.query(queryConstants.replaceArchivo, [item.CORRELATIVO, item.CORRELATIVOCOLECCION, item.NOMBRE, item.RUTAARCHIVO, fullPath, item.DESTACADO, item.DESCARGABLE, 1, item.NROORDEN, '', item.TAMANHO, item.FECHAINICIO, item.FECHACADUCIDAD, item.EXTENSION, 0]).then(function (insertArchivo) {
                    deferred.resolve(insertArchivo);
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject(err);
                });
            }
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

    return self;
}]);