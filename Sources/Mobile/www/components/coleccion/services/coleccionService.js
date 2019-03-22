/*jshint loopfunc: true */
angular.module('YanbalKiosko.services')

.factory('ColeccionService', ['$q', 'BdHelper', '$http', '$localstorage', '$cordovaSQLite', 'LoginService', 'queryConstants', 'serviceUrlConstants', 'tramaObtenerColeccionesArchivos',
    '$filter', 'textConstants', 'ArchivoService', 'tipoContenidoConstants', '$cordovaFile', 'libreriaCorporativa', '$cordovaNetwork', 'archivoHelper', 'RestService', 'stringUtil',
    function ($q, BdHelper, $http, $localstorage, $cordovaSQLite, LoginService, queryConstants, serviceUrlConstants, tramaObtenerColeccionesArchivos, $filter, textConstants, ArchivoService,
        tipoContenidoConstants, $cordovaFile, libreriaCorporativa, $cordovaNetwork, archivoHelper, RestService, stringUtil) {
    var self = this;

    var targetPath;

    if (ionic.Platform.isAndroid()) {
        targetPath = cordova.file.externalDataDirectory;
    } else if (ionic.Platform.isWindowsPhone()) {
        targetPath = '///';
    } else {
        targetPath = cordova.file.dataDirectory;
    }
    //Obtener data de la bd
    self.GetColeccionesContenido = function (coleccionId) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        //Ejecutar get notificacion para actualizar icono de tab
        ArchivoService.GetNotificacion();

        if (coleccionId === 0) {
            //Vista de pantalla inicial
            BdHelper.query(queryConstants.selectColeccionPrincipal).then(function (result) {
                var colecciones = BdHelper.getAll(result);
                var querys = [];
                for (var i = 0; i < colecciones.length; i++) {
                    colecciones[i].items = [];
                    querys.push(BdHelper.query(queryConstants.selectColeccion, [colecciones[i].CORRELATIVO_COLECCION]));
                    querys.push(BdHelper.query(queryConstants.selectArchivo, [colecciones[i].CORRELATIVO_COLECCION]));
                }
                $q.all(querys).then(function (data) {
                    for (var j = 0; j < data.length; j++) {
                        colecciones[Math.floor(j / 2)].items.push.apply(colecciones[Math.floor(j / 2)].items, BdHelper.getAll(data[j]));
                    }
                    deferred.resolve(colecciones);
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject(err);
                });
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
                deferred.reject(err);
            });
        } else {
            //Vista colecciones regular
            var contenidos = [];
            var querys = [];
            querys.push(BdHelper.query(queryConstants.selectColeccion, [coleccionId]));
            querys.push(BdHelper.query(queryConstants.selectArchivo, [coleccionId]));
            $q.all(querys).then(function (resultContenidos) {
                for (var i = 0; i < resultContenidos.length; i++) {
                    contenidos.push.apply(contenidos, BdHelper.getAll(resultContenidos[i]));
                }
                deferred.resolve(contenidos);
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
                deferred.reject(err);
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

    var LimpiarColeccionesArchivos = function (validarEstructuraQuerys) {
        validarEstructuraQuerys[2] = BdHelper.query(queryConstants.selectVariable, [textConstants.FECHA_SINCRONIZACION_ALTER]);
        validarEstructuraQuerys.push(BdHelper.query(queryConstants.deleteColeccionAll));
        validarEstructuraQuerys.push(BdHelper.query(queryConstants.deleteArchivoAll));
        $localstorage.set('notSameUser', textConstants.LOCALSTORAGEFALSE);
    };

    var ProcesarColeccion = function (coleccion, selectQuerys) {
        if (coleccion.ELIMINADO) {
            //Eliminar coleccion
            selectQuerys.push(BdHelper.query(queryConstants.deleteColeccion, [coleccion.CORRELATIVO]));
        } else {
            //Insertar/actualizar coleccion
            selectQuerys.push(BdHelper.query(queryConstants.replaceColeccion, [coleccion.CORRELATIVO, coleccion.CORRELATIVOCOLECCION, coleccion.NOMBRE, coleccion.NIVEL, coleccion.NROORDEN, coleccion.COLOR, coleccion.HIJOS]));
        }
    };

    var ProcesarArchivo = function (archivo, selectQuerys, archivosEliminar, descargarQuerys, variableFolder) {
        if (archivo.ELIMINADO || archivo.ELIMINARPORROL) {
            //Seleccionar archivos a eliminar
            archivosEliminar.push(BdHelper.query(queryConstants.selectArchivoById, [archivo.CORRELATIVO]));
        } else {
            //descargar imagenes previas
            var fullPath = targetPath + variableFolder.VALOR + '/' + stringUtil.getLastString(archivo.RUTAIMGPREVIA);
            if (archivo.RUTAIMGPREVIA) {
                descargarQuerys.push(archivoHelper.GuardarVistaPrevia(archivo.RUTAIMGPREVIA, fullPath));
            } else {
                fullPath = textConstants.RutaImagenDefault;
            }
            //Insertar/Actualizar archivos
            selectQuerys.push(archivoHelper.reemplazarArchivo(archivo, fullPath));
        }
    };

    var ProcesarColeccionesArchivos = function (arrayContenido, selectQuerys, archivosEliminar, descargarQuerys, variableFolder) {
        for (var i = 0; i < arrayContenido.length; i++) {
            if (arrayContenido[i].TIPO === tipoContenidoConstants.Coleccion) {
                ProcesarColeccion(arrayContenido[i], selectQuerys);
            } else if (arrayContenido[i].TIPO === tipoContenidoConstants.Archivo) {
                ProcesarArchivo(arrayContenido[i], selectQuerys, archivosEliminar, descargarQuerys, variableFolder);
            }
        }
    };

    var ProcesarUmbrales = function (umbrales, selectQuerys) {
        for (var j = 0; j < umbrales.length; j++) {
            var umbralMax = !umbrales[j].descarga ? 0 : umbrales[j].descarga;
            selectQuerys.push(BdHelper.query(queryConstants.insertVariable, [umbrales[j].extension, umbralMax]));
        }
    };
    //Proceso de revision, validacion y carga de datos a la bd
    var ValidarEstructuraPrevia = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;
        //Variables a usar en el proceso
        var arrayContenido;
        var selectQuerys;
        var descargarQuerys;
        var archivosEliminar;
        var eliminarQuerys;
        var variableToken;
        var variablePais;
        var variableFecSinc;
        var variableTipoUsr;
        var variableRol;
        var variableFolder;
        var validarEstructuraQuerys = [
            BdHelper.query(queryConstants.selectVariable, [textConstants.TOKEN]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.PAIS]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.FECHA_SINCRONIZACION]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.TIPOUSR]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.ROL]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.NOMBRE_CARPETA])
        ];
        //si no es el mismo usuario, usar fecha de sinc inicial y borrar colecciones y archivos
        if ($localstorage.get('notSameUser') === textConstants.LOCALSTORAGETRUE) {
            LimpiarColeccionesArchivos(validarEstructuraQuerys);
        }
        $q.all(validarEstructuraQuerys).then(function (transaction) {
            variableToken = BdHelper.getById(transaction[0]);
            variablePais = BdHelper.getById(transaction[1]);
            variableFecSinc = BdHelper.getById(transaction[2]);
            variableTipoUsr = BdHelper.getById(transaction[3]);
            variableRol = BdHelper.getById(transaction[4]);
            variableFolder = BdHelper.getById(transaction[5]);
            //Llenar trama a enviar al servidor
            tramaObtenerColeccionesArchivos.Body.Request.token = variableToken && variableToken.VALOR ? variableToken.VALOR : '';
            tramaObtenerColeccionesArchivos.Body.Request.pais = variablePais && variablePais.VALOR ? variablePais.VALOR : textConstants.PAIS_DEFECTO;
            tramaObtenerColeccionesArchivos.Body.Request.fecha_sincronizacion = variableFecSinc && variableFecSinc.VALOR ? variableFecSinc.VALOR : textConstants.FECHA_INICIAL;
            //Si es un tipo usuario staff en respuesta yanbal asignar valor staff de servidor
            tramaObtenerColeccionesArchivos.Body.Request.rol = (variableTipoUsr && variableRol) ? (variableTipoUsr.VALOR === textConstants.USR_STAFF_YANBAL ? textConstants.USR_STAFF : variableRol.VALOR) : '';
            return RestService.obtenerColeccionesContenido(tramaObtenerColeccionesArchivos);
        }).then(function (resultContenido) {
            if (resultContenido.Header.ResponseCode === textConstants.CODIGO_EXITO) {
                //Colecciones y archivos
                arrayContenido = resultContenido.Body.Response.contenido;
                selectQuerys = [];
                descargarQuerys = [];
                archivosEliminar = [];
                eliminarQuerys = [];
                //Colecciones y archivos
                ProcesarColeccionesArchivos(arrayContenido, selectQuerys, archivosEliminar, descargarQuerys, variableFolder);
                //Umbrales
                ProcesarUmbrales(resultContenido.Body.Response.umbrales, selectQuerys);
                //Fecha de sincronizacion
                selectQuerys.push(BdHelper.query(queryConstants.insertVariable, [textConstants.FECHA_SINCRONIZACION, resultContenido.Body.Response.fecha_sincronizacion]));
                return $q.all(descargarQuerys);
            } else {
                libreriaCorporativa.RaygunSendError(resultContenido.Header.ResponseCode);
                deferred.reject(resultContenido.Header.ResponseCode);
            }
        }).then(function () {
            return $q.all(selectQuerys);
        }).then(function () {
            if (archivosEliminar.length > 0) {
                return $q.all(archivosEliminar);
            } else {
                deferred.resolve();
            }
        }).then(function (data) {
            if (!data) {
                return;
            }
            for (var i = 0; i < data.length; i++) {
                if (BdHelper.getById(data[i])) {
                    eliminarQuerys.push(ArchivoService.DeleteArchivo(BdHelper.getById(data[i]), 1));
                }
            }
            return $q.all(eliminarQuerys);
        }).then(function () {
            deferred.resolve();
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

    //Obtener y enviar datos del y al servidor
    self.sincronizar = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;

        $q.all([
            BdHelper.query(queryConstants.selectVariable, [textConstants.USR]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.PASS]),
            BdHelper.query(queryConstants.selectVariable, [textConstants.PAIS])
        ]).then(function (selectUserPass) {
            var name = libreriaCorporativa.DesencriptarCadena(BdHelper.getById(selectUserPass[0]).VALOR);
            var pass = libreriaCorporativa.DesencriptarCadena(BdHelper.getById(selectUserPass[1]).VALOR);
            var pais = BdHelper.getById(selectUserPass[2]).VALOR;
                //Autenticar usuario con servicio yanbal
            LoginService.autenticarUsuario(name, pass, pais).then(function () {
                //Registrar sesion solo en el primer ingreso por usuario
                if ($localstorage.get('SesionRegistrada') === textConstants.LOCALSTORAGEFALSE) {
                    return LoginService.registrarSesion();
                } else {
                    return;
                }
            }).then(function () {
                //Enviar bitacora al servidor
                return libreriaCorporativa.EnviarBitacora();
            }).then(function () {
                //Enviar metricar GA
                return libreriaCorporativa.EnviarMetricaGA();
            }).then(function () {
                //Actualizar paises
                return LoginService.updatePaises();
            }).then(function () {
                //Proceso de revision, validacion y carga de datos a la bd
                return ValidarEstructuraPrevia();
            }).then(function () {
                deferred.resolve();
            }).catch(function (err) {
                deferred.reject(err);
            });
        }, function (err) {
            libreriaCorporativa.RaygunSendError(err);
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
