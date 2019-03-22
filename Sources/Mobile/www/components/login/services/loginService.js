/*jshint loopfunc: true */
angular.module('YanbalKiosko.services')

.factory('LoginService', ['$q', '$ionicHistory', 'BdHelper', '$http', '$localstorage', '$cordovaDevice', '$cordovaSQLite', 'queryConstants', 'serviceUrlConstants', 'tramaYambal',
    'tramaRegistrarSesion', 'tramaCerrarSesion', '$cordovaNetwork', '$filter', 'libreriaCorporativa', 'textConstants', 'dateUtil', 'RestService',
    function ($q, $ionicHistory, BdHelper, $http, $localstorage, $cordovaDevice, $cordovaSQLite, queryConstants, serviceUrlConstants, tramaYambal, tramaRegistrarSesion,
        tramaCerrarSesion, $cordovaNetwork, $filter, libreriaCorporativa, textConstants, dateUtil, RestService) {
    var self = this;

    self.autenticarUsuario = function (name, pass, country) {
        var deferred = $q.defer();
        var promise = deferred.promise;
        //ingresar datos a trama yambal
        tramaYambal.IntegracionWSReq.Cabecera.UsuarioAplicacion = name;
        tramaYambal.IntegracionWSReq.Cabecera.CodigoPais = country;
        tramaYambal.IntegracionWSReq.Cabecera.CodigosPaisOD.CodigoPaisOD.Valor = country;
        tramaYambal.IntegracionWSReq.Detalle.Parametros.Usuario = name;
        tramaYambal.IntegracionWSReq.Detalle.Parametros.Password = pass;
        tramaYambal.IntegracionWSReq.Detalle.Parametros.TipoUsuario = textConstants.USR_FFVV_YANBAL;
        if ($cordovaNetwork.isOnline()) {
            RestService.autenticacionYanbal(tramaYambal).success(function (result) {
                deferred.resolve(result);
            }).error(function () {
                deferred.reject('LOGINFALLOTEXTO');
            });
        } else {
            deferred.reject('LOGINSINCONEXION');
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

    self.autenticarUsuarioOffline = function (name, pass, country) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        $q.all([
                    BdHelper.query(queryConstants.selectVariable, [textConstants.USR]),
                    BdHelper.query(queryConstants.selectVariable, [textConstants.PASS]),
                    BdHelper.query(queryConstants.selectVariable, [textConstants.PAIS])
        ]).then(function (selectUserPass) {
            if (!BdHelper.getById(selectUserPass[0]) || !BdHelper.getById(selectUserPass[0]).VALOR) {
                //sin conexion sin haber iniciado sesion previamente
                deferred.reject($filter('translate')('LOGINSINCONEXION'));
                return;
            }
            var nameBd = libreriaCorporativa.DesencriptarCadena(BdHelper.getById(selectUserPass[0]).VALOR);
            var passBd = libreriaCorporativa.DesencriptarCadena(BdHelper.getById(selectUserPass[1]).VALOR);
            var paisBd = BdHelper.getById(selectUserPass[2]).VALOR;
            if (name.replace(/\s/g, "") === nameBd.replace(/\s/g, "") && pass.replace(/\s/g, "") === passBd.replace(/\s/g, "") && country === paisBd) {
                deferred.resolve('autenticado sin conexion');
            } else {
                deferred.reject($filter('translate')('LOGINFALLOTEXTO'));
            }
        }, function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject($filter('translate')('ERRORGENERICO'));
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

    self.loginUser = function (name, pass, country, result) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        BdHelper.query(queryConstants.selectVariable, ['USR']).then(function (selectUser) {
            if (result) {
                    var user;
                    var today = dateUtil.getToday();
                    var perfil = result.IntegracionWSResp.Detalle.Respuesta.Datos.Perfil.length > 2 ? 0 : result.IntegracionWSResp.Detalle.Respuesta.Datos.Perfil;
                    if (BdHelper.getById(selectUser)) {
                        user = libreriaCorporativa.DesencriptarCadena(BdHelper.getById(selectUser).VALOR);
                        if (user.trim() !== name.trim()) {
                            $localstorage.set('notSameUser', textConstants.LOCALSTORAGETRUE);
                        } else {
                            $localstorage.set('notSameUser', textConstants.LOCALSTORAGEFALSE);
                        }
                    }
                    var encriptedName = libreriaCorporativa.EncriptarCadena(name);
                    var encriptedPass = libreriaCorporativa.EncriptarCadena(pass);
                    var querys = [
                        BdHelper.query(queryConstants.insertVariable, [textConstants.USR, encriptedName]),
                        BdHelper.query(queryConstants.insertVariable, [textConstants.PASS, encriptedPass]),
                        BdHelper.query(queryConstants.insertVariable, [textConstants.PAIS, country]),
                        BdHelper.query(queryConstants.insertVariable, [textConstants.ROL, perfil]),
                        BdHelper.query(queryConstants.insertVariable, [textConstants.TIPOUSR, result.IntegracionWSResp.Detalle.Respuesta.Datos.TipoUsuario]),
                        //Log de iniciar sesion
                        BdHelper.query(queryConstants.insertLog, ["0", textConstants.inicioSesionDescripcion, textConstants.inicioSesionAccion, "", "", "", "", name, $localstorage.get('platform'), $localstorage.get('uuid'), today]),
                        BdHelper.query(queryConstants.insertLog, ["1", textConstants.inicioSesionDescripcion, textConstants.inicioSesionAccion, "", "", "", "", name, $localstorage.get('platform'), $localstorage.get('uuid'), today])
                    ];
                    return $q.all(querys);
            } else {
                deferred.resolve('log in sin conexion');
            }
        }).then(function () {
            deferred.resolve('autenticado');
        }).catch(function (err) {
            libreriaCorporativa.RaygunSendError(err);
            deferred.reject($filter('translate')('ERRORGENERICO'));
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

    self.registrarSesion = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;
        var notificationId;

        libreriaCorporativa.ManejarNotificacion().then(function (regId) {
            //Se registro con exito
            notificationId = regId;
            return $q.all([
                BdHelper.query(queryConstants.selectVariable, [textConstants.USR]),
                BdHelper.query(queryConstants.selectVariable, [textConstants.PASS]),
                BdHelper.query(queryConstants.selectVariable, [textConstants.PAIS])
            ]);
        }).then(function (transaction) {
            //ingresar datos trama registrar sesion en servidor
            tramaRegistrarSesion.Body.Request.usuario = BdHelper.getById(transaction[0]) ? BdHelper.getById(transaction[0]).VALOR : '';
            tramaRegistrarSesion.Body.Request.clave = BdHelper.getById(transaction[1]) ? BdHelper.getById(transaction[1]).VALOR : '';
            tramaRegistrarSesion.Body.Request.notificacion_id = notificationId;
            tramaRegistrarSesion.Body.Request.pais = BdHelper.getById(transaction[2]) ? BdHelper.getById(transaction[2]).VALOR : 'PER';
            tramaRegistrarSesion.Body.Request.dispositivo_id = $cordovaDevice.getUUID();

            tramaRegistrarSesion.Body.Request.dispositivo_so = ionic.Platform.isAndroid() ? textConstants.ANDROID_PLATFORM : ionic.Platform.isWindowsPhone() ? textConstants.WINDOWS_PLATFORM : '';
            if (tramaRegistrarSesion.Body.Request.dispositivo_so === '') {
                tramaRegistrarSesion.Body.Request.dispositivo_so = (ionic.Platform.isIOS() || ionic.Platform.isIPad()) ? textConstants.IOS_PLATFORM : '';
            }
            return RestService.registrarSesion(tramaRegistrarSesion);
        }).then(function (resultToken) {
            if (resultToken.Header.ResponseCode === textConstants.CODIGO_EXITO) {
                $localstorage.set('SesionRegistrada', textConstants.LOCALSTORAGETRUE);
                return BdHelper.query(queryConstants.insertVariable, [textConstants.TOKEN, resultToken.Body.Response.token]);
            } else {
                deferred.reject(resultToken.Header.ResponseCode);
            }
        }).then(function (insertedToken) {
            deferred.resolve(insertedToken);
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

    self.cerrarSesion = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;
        var today = dateUtil.getToday();
        BdHelper.query(queryConstants.selectVariable, ['TOKEN']).then(function (selectToken) {
            tramaCerrarSesion.Body.Request.token = BdHelper.getById(selectToken).VALOR;
            return $q.all([
                BdHelper.query(queryConstants.updateVariable, ['', textConstants.TOKEN]),
                BdHelper.query(queryConstants.selectVariable, [textConstants.USR])
            ]);
        }).then(function (data) {
            $localstorage.set('SesionRegistrada', textConstants.LOCALSTORAGEFALSE);
            $localstorage.set('Sincronizar', textConstants.LOCALSTORAGEFALSE);
            $localstorage.set('registroNotificacion', textConstants.LOCALSTORAGEFALSE);
            var name = BdHelper.getById(data[1]);
            BdHelper.query(queryConstants.insertLog, ["0", textConstants.cerrarSesionDescripcion, textConstants.cerrarSesionAccion, "", "", "", name, $localstorage.get('platform'), $localstorage.get('uuid'), today]).then(function () {
                deferred.resolve(data);
            }, function (err) {
                libreriaCorporativa.RaygunSendError(err);
                deferred.reject(err);
            });
            //No se encadenan pues no es requerido esperar al registrar sesion en el servidor para continuar con el cierre de sesion
            RestService.cerrarSesion(tramaCerrarSesion);
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

    self.getPaises = function (search) {
        var deferred = $q.defer();
        var promise = deferred.promise;

        BdHelper.query(queryConstants.selectPaises, [search]).then(function (result) {
            deferred.resolve(BdHelper.getAll(result));
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

    self.updatePaises = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;

        RestService.obtenerPaises().success(function (result) {
            if (result.Header.ResponseCode === textConstants.CODIGO_EXITO) {
                var listaPaises = [];
                var resultPaises = result.Body.Response.paises;
                for (var i = 0; i < resultPaises.length; i++) {
                    listaPaises.push(BdHelper.query(queryConstants.replacePais, [resultPaises[i].id, resultPaises[i].nombre, resultPaises[i].codigo]));
                }
                $q.all(listaPaises).then(function (result) {
                    deferred.resolve(result);
                }, function (err) {
                    libreriaCorporativa.RaygunSendError(err);
                    deferred.reject(err);
                });
            } else {
                deferred.reject(res);
            }
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

    self.cleanToken = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;

        BdHelper.query(queryConstants.updateVariable, ['', textConstants.TOKEN]).then(function () {
            deferred.resolve();
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
