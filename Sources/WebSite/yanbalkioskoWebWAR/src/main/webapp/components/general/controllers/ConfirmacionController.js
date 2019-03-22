angular.module('app.controllers')

.controller('confirmacionController',
    ['$scope', '$modalInstance', '$modal', 'modalUtil', 'titulo', 'mensaje',
    'botonConfirmar', 'botonCancelar', 'item', 'event', 'ColeccionService',
    'ArchivoService', 'mensajesConstants', 'responseConstants',
    function($scope, $modalInstance, $modal, modalUtil, titulo, mensaje,
    botonConfirmar, botonCancelar, item, event, ColeccionService,
    ArchivoService, mensajesConstants, responseConstants) {

    $scope.init = function() {
        $scope.tituloConfirmacion = titulo;
        $scope.mensajeConfirmacion = mensaje;
        $scope.botonConfirmar = botonConfirmar;
        $scope.botonCancelar = botonCancelar;
        $scope.event = event;
    };

    $scope.ok = function() {
        switch (event) {
            case 'eliminarColeccion':
                var jsonRequestEliminarColeccion = {id_coleccion: item.id_coleccion};
                ColeccionService.eliminarColeccion(jsonRequestEliminarColeccion)
                .success(function() {
                    $modalInstance.close();
                    var titulo = mensajesConstants.coleccionConfirmacionEliminar.titulo;
                    var mensajeExito = mensajesConstants.coleccionConfirmacionEliminar.mensajeExito;
                    $scope.mostrarMensaje(titulo, mensajeExito);

                }).error(function() {
                    $modalInstance.close();
                });
                break;
            case 'eliminarArchivos':
                var jsonRequestEliminarArchivos = {ids_archivo: item};
                ArchivoService.eliminarArchivos(jsonRequestEliminarArchivos)
                .success(function(data) {
                    var codigoRespuesta = data.Header.ResponseCode;
                    var titulo = '';
                    if (responseConstants.RESPUESTA_OK === codigoRespuesta) {
                        $modalInstance.close();
                        titulo = mensajesConstants.eliminarArchivos.titulo;
                        var mensajeExito = mensajesConstants.eliminarArchivos.mensajeExito;
                        $scope.mostrarMensaje(titulo, mensajeExito);

                    } else if (responseConstants.RESPUESTA_ERROR_PRIVILEGIOS === codigoRespuesta) {
                        $modalInstance.close();
                        titulo = mensajesConstants.eliminarArchivos.titulo;
                        var archivosSinPrivilegios = data.Body.Response.archivos;
                        var mensajeError = mensajesConstants.eliminarArchivos.mensajeErrorPrivilegios;
                        $scope.mostrarMensaje(titulo, mensajeError, archivosSinPrivilegios);
                    }
                }).error(function() {
                    $modalInstance.close();
                });
                break;
            default:
                break;
        }
    };

    $scope.cancel = function() {
        $modalInstance.close();
    };

    $scope.mostrarMensaje = function(tituloModal, mensajeModal, lista) {
        switch ($scope.event) {
        case 'eliminarColeccion':
            var jsonModal = {
                    titulo: tituloModal,
                    mensaje: mensajeModal
            };
            modalUtil.openModal(jsonModal);
            break;

        case 'eliminarArchivos':
            $modal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: '../coleccion/views/mensaje-eliminar-archivos.html',
                controller: 'mensajeEliminarArchivosController',
                size: mensajesConstants.eliminarArchivos.size,
                resolve: {
                    titulo: function() {
                        return tituloModal;
                    },
                    mensaje: function() {
                        return mensajeModal;
                    },
                    lista: function() {
                        return lista;
                    },
                    boton: function() {
                        return mensajesConstants.general.aceptar;
                    }
                }
            });
            break;

        default:
            break;
        }
    };

    $scope.init();

}]);
