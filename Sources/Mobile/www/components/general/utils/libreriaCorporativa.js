angular.module('YanbalKiosko.utils')

.factory('libreriaCorporativa', ['$cordovaSQLite', '$q', '$ionicPlatform', '$localstorage', 'queryConstants', '$rootScope', '$cordovaDevice', 'androidConfig', '$cordovaPush', 'configurationsConstants',
    'BdHelper', 'tramaEnviarBitacora', 'serviceUrlConstants', '$http', 'textConstants',
    function ($cordovaSQLite, $q, $ionicPlatform, $localstorage, queryConstants, $rootScope, $cordovaDevice, androidConfig, $cordovaPush, configurationsConstants, BdHelper, tramaEnviarBitacora,
        serviceUrlConstants, $http, textConstants) {
    var self = this;
    var llave;
    var salt;
    var tipo_de_salida = configurationsConstants.tipoSalidaBase64;

    var getSalt = function () {
        cordova.plugins.ConfigReader.get('salt', {
            success: function (saltKey) {
                salt = CryptoJS.enc.Utf8.parse(saltKey);
            }
        });
    };

    var getLlave = function () {
        cordova.plugins.ConfigReader.get('llave', {
            success: function (key) {
                llave = CryptoJS.enc.Utf8.parse(key);
                return getSalt();
            }
        });
    };

    var esperarVariable = function () {
        if (typeof window.raygunGAinit !== "undefined") {
            getLlave();
        }
        else {
            setTimeout(function () {
                esperarVariable();
            }, 250);
        }
    };

    document.addEventListener("deviceready", function () {
        esperarVariable();
    });

    self.EncriptarCadena = function (cadena) {
        var padMsg = self.padString(cadena);
        var encrypted = CryptoJS.AES.encrypt(padMsg, llave, { iv: salt, padding: CryptoJS.pad.Pkcs7, mode: CryptoJS.mode.CBC });
        if (tipo_de_salida === configurationsConstants.tipoSalidaHex) {
            return encrypted.ciphertext;
        } else {
            return encrypted;
        }
    };

    self.DesencriptarCadena = function (cadena) {
        var decrypted = CryptoJS.AES.decrypt(cadena, llave, { iv: salt, padding: CryptoJS.pad.Pkcs7, mode: CryptoJS.mode.CBC }).toString(CryptoJS.enc.Utf8);
        return decrypted;
    };

    self.ManejarNotificacion = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;

        var ProcesoRecepcionAndroid = function () {
            if ($localstorage.get('registroNotificacion') !== textConstants.LOCALSTORAGETRUE) {
                $localstorage.set('registroNotificacion', textConstants.LOCALSTORAGETRUE);
                $rootScope.$on('$cordovaPush:notificationReceived', function (event, notification) {
                    switch (notification.event) {
                        case 'registered':
                            if (notification.regid.length > 0) {
                                deferred.resolve(notification.regid);
                            }
                            break;
                        case 'message':
                            //esta es la notificacion su formato dependera del servidor
                            break;
                        case 'error':
                        default:
                            break;
                    }
                });
            }
        };

        var tokenHandler = function (result) {
            deferred.resolve(result);
        };
        var errorHandler = function (error) {
            deferred.reject(error);
        };
        var onNotificationAPN = function (event) {
            if (event.sound) {
                var snd = new Media(event.sound);
                snd.play();
            }
            if (event.badge) {
                pushNotification.setApplicationIconBadgeNumber(successHandler, errorHandler, event.badge);
            }
        };
        //ios notification handler
        function channelHandler(result) {
            deferred.resolve(result.uri);
        }
        //wp8 notification handler
        function onNotificationWP8(e) {
            if (e.type === "toast" && e.jsonContent) {
                pushNotification.showToastNotification(successHandler, errorHandler, {
                    "Title": e.jsonContent["wp:Text1"], "Subtitle": e.jsonContent["wp:Text2"], "NavigationUri": e.jsonContent["wp:Param"]
                });
            }
        }
        function jsonErrorHandler(error) {
            deferred.reject(error);
        }

        //push notifications
        if (ionic.Platform.isAndroid()) {
            $cordovaPush.register(androidConfig).then(function () {
                //se registro
            }, function (err) {
                self.RaygunSendError(err);
                deferred.reject(err);
            });
            ProcesoRecepcionAndroid();
        } else if (ionic.Platform.isIOS() || ionic.Platform.isIPad()) {
            //ios plugin sin ng cordova
            window.plugins.pushNotification.register(tokenHandler, errorHandler, {
                "badge": "true",
                "sound": "true",
                "alert": "true",
                "ecb": onNotificationAPN
            });
        } else if (ionic.Platform.isWindowsPhone()) {
            //wp plugin sin ng cordova
            window.plugins.pushNotification.register(channelHandler, errorHandler, {
                "channelName": "channelName",
                "ecb": onNotificationWP8,
                "uccb": channelHandler,
                "errcb": jsonErrorHandler
            });
        } else {
            deferred.reject('plataforma desconocida');
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

    self.RaygunSendError = function (err) {
        Raygun.send(err);
    };

    self.EnviarBitacora = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;
        $q.all([
            BdHelper.query(queryConstants.selectVariable, ['TOKEN']),
            BdHelper.query(queryConstants.selectBitacora, [])
        ]).then(function (selectVariables) {
            var detalle = [];
            var bitacoras = BdHelper.getAll(selectVariables[1]);
            for (var i = 0; i < bitacoras.length; i++) {
                var bitacora = {};
                bitacora.descripcion = bitacoras[i].DESCRIPCION;
                bitacora.accion = bitacoras[i].ACCION;
                bitacora.nombreArchivo = bitacoras[i].NOMBRE_ARCHIVO;
                bitacora.tamanhoArchivo = bitacoras[i].TAMANHO_ARCHIVO;
                bitacora.tipoArchivo = bitacoras[i].TIPO_ARCHIVO;
                bitacora.tipoDescarga = bitacoras[i].TIPO_DESCARGA;
                bitacora.usuario = bitacoras[i].USUARIO;
                bitacora.plataforma = bitacoras[i].PLATAFORMA;
                bitacora.idDispositivo = bitacoras[i].DISPOSITIVO_IDENTIFICADOR;
                bitacora.fecha = bitacoras[i].FECHA_BITACORA;
                detalle.push(bitacora);
            }
            tramaEnviarBitacora.Body.Request.token = BdHelper.getById(selectVariables[0]).VALOR;
            tramaEnviarBitacora.Body.Request.detalle = detalle;
            $http({
                url: serviceUrlConstants.servidorDesarrollo + serviceUrlConstants.enviarBitacora,
                method: 'POST',
                headers: {
                    'Access-Control-Allow-Origin': '*',
                    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
                    'Content-Type': 'application/json'
                },
                data: tramaEnviarBitacora
            }).success(function () {
                deferred.resolve();
            }).error(function (err) {
                self.RaygunSendError(err);
            });
            //limpiar tabla bitacora
            BdHelper.query(queryConstants.limpiarBitacora, []).then(function () {
                //se limpio la bitacora correctamente
            }, function (err) {
                self.RaygunSendError(err);
            });
        }, function (err) {
            self.RaygunSendError(err);
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

    self.EnviarMetricaGA = function () {
        var deferred = $q.defer();
        var promise = deferred.promise;

        BdHelper.query(queryConstants.selectGA, []).then(function (data) {
            var selectGA = BdHelper.getAll(data);
            try {
                for (var i = 0; i < selectGA.length; i++) {
                    ga('send', 'event', 'option', selectGA[i].ACCION, selectGA[i].DESCRIPCION);
                }
            } catch (e) {
                deferred.resolve('error al enviar metricas al GA');
            }
            //limpiar tabla bitacora
            BdHelper.query(queryConstants.limpiarGA, []).then(function () {
                //se limpio la bitacora correctamente
                deferred.resolve('ga enviados');
            }, function (err) {
                self.RaygunSendError(err);
            });
        }, function (err) {
            self.RaygunSendError(err);
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

    self.padString = function (source) {
        var paddingChar = ' ';
        var size = 16;
        var x = source.length % size;
        var padLength = size - x;
        for (var i = 0; i < padLength; i++) {
            source += paddingChar;
        }
        return source;
    };

    return self;
}]);