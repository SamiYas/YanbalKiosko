angular.module('app.controllers')

.controller('moverArchivoController',
    ['$scope', '$modalInstance', '$modal', 'modalUtil', 'localStorageService',
    'ColeccionService', 'items', 'Upload', 'sesionConstants',
    'responseConstants', 'mensajesConstants', 'stringConstants',
    'ArchivoService', function($scope, $modalInstance, $modal, modalUtil,
    localStorageService, ColeccionService, items, Upload, sesionConstants,
    responseConstants, mensajesConstants, stringConstants, ArchivoService) {

    $scope.jsonRaiz = {
        nombre: stringConstants.raiz,
        id: null,
        nivel: -1,
        active: false
    };
    $scope.breadcrumbOrigen = [];
    $scope.breadcrumbDestino = [];
    $scope.formats = [];
    $scope.events = [];
    $scope.coleccion = [];
    $scope.errorNombre = responseConstants.RESPUESTA_ERROR_NOMBRE_DUPLICADO;

    $scope.init = function() {
        $scope.items = items;

        $scope.cargarBreadcrumbOrigen(items.archivoSeleccionado.Coleccion);
    };

    $scope.cargarBreadcrumb = function(id_coleccion, tipo) {
        var jsonRequest = {id: id_coleccion};

        ColeccionService.obtenerRutaOrigen(jsonRequest)
            .success(function(datos) {
                 var bcResult = datos.data;

                 switch (tipo) {
                 case 1:
                     $scope.breadcrumbOrigen = [];
                     $scope.breadcrumbOrigen[0] = $scope.jsonRaiz;
                     for (var i = 0; i < bcResult.length; i++) {
                         $scope.breadcrumbOrigen.push(
                            {
                                nombre: bcResult[i].nombre,
                                id: bcResult[i].id,
                                nivel: bcResult[i].nivel,
                                active: false
                            });
                     }
                     break;
                 case 2:
                     $scope.breadcrumbDestino = [];
                     $scope.breadcrumbDestino[0] = $scope.jsonRaiz;
                     for (var j = 0; j < bcResult.length; j++) {
                         $scope.breadcrumbDestino.push(
                            {nombre: bcResult[j].nombre,
                                id: bcResult[j].id,
                                nivel: bcResult[j].nivel,
                                active: false
                            });
                     }
                     $scope.activarBreadcrumbDestino();
                     break;
                 default:
                     break;
                 }
              });
    };

    $scope.activarBreadcrumbDestino = function() {
        var cantidad = $scope.breadcrumbDestino.length;
        if (cantidad > 0) {
            for (var i = 0; i < cantidad - 1; i++) {
                $scope.breadcrumbDestino[i].active = false;
            }
            $scope.breadcrumbDestino[cantidad - 1].active = true;
        }
    };

    $scope.cargarBreadcrumbOrigen = function(id_coleccion) {
        $scope.cargarBreadcrumb(id_coleccion, 1);
    };

    $scope.cargarBreadcrumbDestino = function(id_coleccion) {
        $scope.cargarBreadcrumb(id_coleccion, 2);
    };

    $scope.check = function(item) {
        item.value = !item.value;
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };

    $scope.ok = function() {
        var jsonRequest = {
            id_archivo: items.archivoSeleccionado.Id,
            id_coleccion: $scope.shownColeccion.id_coleccion
        };
        if (items.archivoSeleccionado.Coleccion === $scope.shownColeccion.id_coleccion) {
            var jsonModal = {
                    titulo: mensajesConstants.moverArchivo.titulo,
                    mensaje: mensajesConstants.moverArchivo.mensajeErrorColeccionDestino
            };
            modalUtil.openModal(jsonModal);
        } else {
            ArchivoService.moverArchivo(jsonRequest).
            success(function(codigoRespuesta) {
                if (responseConstants.RESPUESTA_OK === codigoRespuesta) {

                    var jsonModalExito = {
                            titulo: mensajesConstants.moverArchivo.titulo,
                            mensaje: mensajesConstants.moverArchivo.mensajeExito
                    };
                    modalUtil.openModal(jsonModalExito);
                    $modalInstance.close();

                } else if ($scope.errorNombre === codigoRespuesta) {

                    var jsonModalErrorNombre = {
                            titulo: mensajesConstants.moverArchivo.titulo,
                            mensaje: mensajesConstants.moverArchivo.mensajeErrorNombreDuplicado
                    };
                    modalUtil.openModal(jsonModalErrorNombre);

                }
            })
            .error(function() {
                $modalInstance.close();
            });
        }
    };

    $scope.tieneSubColecciones = function(coleccion) {
        return coleccion.coleccionesHijas > 0;
    };

    $scope.getDayClass = function(date, mode) {
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date(
                    $scope.events[i].date).setHours(0, 0, 0, 0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }
        return '';
    };

    $scope.toggleColeccion = function(item) {
        $scope.shownColeccion = item;
    };

    $scope.isColeccionShown = function(item) {
        return $scope.shownColeccion === item;
    };

    $scope.openColeccion = function(item) {
        if (item.nivel < 4) {
            var idColeccion = null;
            if (item.id == null) {
                idColeccion = item.id_coleccion;
            }else {
                idColeccion = item.id;
            }
            $scope.cargarBreadcrumbDestino(idColeccion);
            $scope.cargarColeccion(idColeccion);
        }
    };

    $scope.openColeccionContenido = function(coleccion) {
        if ($scope.tieneSubColecciones(coleccion)) {
            $scope.openColeccion(coleccion);
        }
    };

    $scope.cargarColeccion = function(id_col_padre) {
        var jsonRequest = {id_coleccion_padre: id_col_padre};

        ColeccionService.listarColecciones(jsonRequest)
            .success(function(data) {
                $scope.coleccion = data.lista;
                if ($scope.coleccion != null && $scope.coleccion.length > 0) {
                    $scope.toggleColeccion($scope.coleccion[0]);
                }
            });
    };


    $scope.cargarBreadcrumbDestino(null);
    $scope.cargarColeccion(null);

    $scope.init();

}]);
