angular.module('YanbalKiosko.utils', [])

.factory('$localstorage', ['$window', function ($window) {
    return {
        set: function (key, value) {
            $window.localStorage[key] = value;
        },
        get: function (key, defaultValue) {
            return $window.localStorage[key] || defaultValue;
        },
        setObject: function (key, value) {
            $window.localStorage[key] = JSON.stringify(value);
        },
        getObject: function (key) {
            return JSON.parse($window.localStorage[key] || '{}');
        }
    };
}])

.factory('dateUtil', [function () {
    return {
        getToday: function () {
            var todayLong = new Date();
            var sec = todayLong.getSeconds();
            var min = todayLong.getMinutes();
            var hor = todayLong.getHours();
            var dia = todayLong.getDate();
            var mes = todayLong.getMonth() + 1;
            var anho = todayLong.getFullYear();
            var today = (dia < 10 ? '0' : '') + dia + '/' + (mes < 10 ? '0' : '') + mes + '/' + anho + ' ' + (hor < 10 ? '0' : '') + hor + ':' + (min < 10 ? '0' : '') + min + ':' + (sec < 10 ? '0' : '') + sec;
            return today;
        },
        getTodayJustNumbers: function () {
            var today = new Date();
            var sec = today.getSeconds();
            var min = today.getMinutes();
            var hor = today.getHours();
            var dia = today.getDate();
            var mes = today.getMonth() + 1;
            var anho = today.getFullYear();
            var todayJustNumbers = "" + anho + mes + dia + hor + min + sec;
            return todayJustNumbers;
        }
    };
}])

.factory('popUpUtil', ['$q', '$filter', '$ionicPopup', 'LoginService', 'archivoHelper', '$state', function ($q, $filter, $ionicPopup, LoginService, archivoHelper, $state) {
    return {
        confirmPopup: function (titulo, texto, cancelarTexto, aceptarTexto, callback) {
            var descripcion = $filter('translate')(texto) ? $filter('translate')(texto) : texto;
            $ionicPopup.confirm({
                title: $filter('translate')(titulo),
                template: descripcion,
                buttons: [
                    { text: $filter('translate')(cancelarTexto) },
                    {
                        text: aceptarTexto,
                        type: 'yanbalButton',
                        onTap: callback
                    }
                ]
            });
        },
        confirmPopupArchivo: function (titulo, texto, cancelarTexto, aceptarTexto) {
            var deferred = $q.defer();
            var promise = deferred.promise;

            var descripcion = $filter('translate')(texto) ? $filter('translate')(texto) : texto;
            $ionicPopup.confirm({
                title: $filter('translate')(titulo),
                template: descripcion,
                buttons: [
                    {
                        text: $filter('translate')(cancelarTexto),
                        onTap: function () {
                            deferred.resolve(0);
                        }
                    },
                    {
                        text: $filter('translate')(aceptarTexto),
                        type: 'yanbalButton',
                        onTap: function () {
                            deferred.resolve(1);
                        }
                    }
                ]
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
        },
        alertPopup: function (titulo, aceptarTexto, data) {
            var descripcion = $filter('translate')(data) ? $filter('translate')(data) : data;
            $ionicPopup.alert({
                title: $filter('translate')(titulo),
                template: descripcion,
                buttons: [
                    {
                        text: $filter('translate')(aceptarTexto),
                        type: 'yanbalButton'
                    }
                ]
            });
        },
        alertPopupValidarArchivo: function (titulo, aceptarTexto, data) {
            var descripcion = $filter('translate')(data) ? $filter('translate')(data) : data;
            $ionicPopup.alert({
                title: $filter('translate')(titulo),
                template: descripcion,
                buttons: [
                    {
                        text: $filter('translate')(aceptarTexto),
                        type: 'yanbalButton',
                        onTap: function () {
                            $scope.$broadcast('loading.show');
                            sincronizar($scope.coleccionId);
                        }
                    }
                ]
            });
        }
    };
}])

.factory('stringUtil', [function(){
    return {
        getLastString: function (cadena) {
            var partes = cadena.split('/');
            if (partes.length) {
                var nombre = partes[partes.length - 1];
                return nombre;
            } else {
                return '';
            }
        }
    };
}]);