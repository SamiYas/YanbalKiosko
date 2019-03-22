angular.module('app.services')

.service('ArchivoService',
    ['$http', '$q', 'SesionService', 'serviceUrlConstants',
    'tramaInsertarArchivo', 'tramaMoverArchivo', 'tramaObtenerArchivo',
    'tramaActualizarArchivo', 'tramaEliminarArchivos',
    function($http, $q, SesionService, serviceUrlConstants,
    tramaInsertarArchivo, tramaMoverArchivo, tramaObtenerArchivo,
    tramaActualizarArchivo, tramaEliminarArchivos) {

    return {
        moverArchivo: function(objJson) {

            tramaMoverArchivo.Body.Request = {
                    id_archivo: objJson.id_archivo,
                    id_coleccion: objJson.id_coleccion
            };

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlMoverArchivo,
                method: 'POST',
                data: JSON.stringify(tramaMoverArchivo)
            }).success(function(data) {
                deferred.resolve(data.Header.ResponseCode);
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
        obtenerArchivo: function(objJson) {

            tramaObtenerArchivo.Body.Request = {
                    id_archivo: objJson.id_archivo
            };

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlObtenerArchivo,
                method: 'POST',
                data: JSON.stringify(tramaObtenerArchivo)
            }).success(function(data) {
                deferred.resolve(data.Body.Response);
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
        actualizarArchivo: function(objJson) {

            var archivoLoad = objJson.archivo;
            var imagenLoad = objJson.img_vista_previa;

            tramaActualizarArchivo.Body.Request = {
                    id_archivo: objJson.id_archivo,
                    nombre: objJson.nombre,
                    descripcion: objJson.descripcion,
                    tamanho: objJson.tamanho,
                    extension: objJson.extension,
                    nro_orden: objJson.nro_orden,
                    descargable: objJson.descargable,
                    destacado: objJson.destacado,
                    fec_inicio: objJson.fec_inicio,
                    fec_caducidad: objJson.fec_caducidad,
                    roles: objJson.roles,
                    token: SesionService.token()
            };

            $('#formRest').submit(function(){
                var $inputJson = $(this).find("input[name=json]");
                if (!$inputJson.val()) {
                    $inputJson.val(JSON.stringify(tramaActualizarArchivo));
                }
            });

            $('#formRest').ajaxForm({
                dataType: 'json',
                data: { },
                resetForm: true,
                beforeSubmit: function(a,f,o) {
                    // verificar envio de archivo null
                    if(a && a[0] && (!archivoLoad || archivoLoad.size === 0)) {
                        a[0]['value'] = null;
                    }
                    // verificar envio de vista previa null
                    if(a && a[1] && (!imagenLoad || imagenLoad.size === 0)) {
                        a[1]['value'] = null;
                    }
                },
                success: function(data) {
                    deferred.resolve(data.Header.ResponseCode);
                },
                error: function () {
                }
            });

            var formulario = $('#formRest');
            formulario.submit();

            var deferred = $q.defer();
            var promise = deferred.promise;

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
        insertarArchivo: function(objJson) {

            var archivoLoad = objJson.archivo;
            var imagenLoad = objJson.img_vista_previa;

            tramaInsertarArchivo.Body.Request = {
                    id_coleccion: objJson.id_coleccion,
                    nombre: objJson.nombre,
                    descripcion: objJson.descripcion,
                    tamanho: objJson.tamanho,
                    extension: objJson.extension,
                    nro_orden: objJson.nro_orden,
                    descargable: objJson.descargable,
                    destacado: objJson.destacado,
                    fec_inicio: objJson.fec_inicio,
                    fec_caducidad: objJson.fec_caducidad,
                    token: SesionService.token(),
                    pais: SesionService.paisId(),
                    roles: objJson.roles
            };

            $('#formRest').submit(function(){
                var $inputJson = $(this).find("input[name=json]");
                if (!$inputJson.val()) {
                    $inputJson.val(JSON.stringify(tramaInsertarArchivo));
                }
            });

            $('#formRest').ajaxForm({
                dataType: 'json',
                data: { },
                resetForm: true,
                beforeSubmit: function(a,f,o) {
                    // verificar envio de archivo null
                    if(a && a[0] && (!archivoLoad || archivoLoad.size === 0)) {
                        a[0]['value'] = null;
                    }
                    // verificar envio de vista previa null
                    if(a && a[1] && (!imagenLoad || imagenLoad.size === 0)) {
                        a[1]['value'] = null;
                    }
                },
                success: function(data) {
                    deferred.resolve(data.Header.ResponseCode);
                },
                error: function () {
                    console.log('ERROR!');
                }
            });

            var formulario = $('#formRest');
            formulario.submit();

            var deferred = $q.defer();
            var promise = deferred.promise;

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
        eliminarArchivos: function(objJson) {

            tramaEliminarArchivos.Body.Request = {
                    ids_archivo: objJson.ids_archivo
            };

            var deferred = $q.defer();
            var promise = deferred.promise;

            $http({
                url: serviceUrlConstants.urlServidor + serviceUrlConstants.urlEliminarArchivos,
                method: 'POST',
                data: JSON.stringify(tramaEliminarArchivos)
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(error){
                deferred.reject(error);
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
