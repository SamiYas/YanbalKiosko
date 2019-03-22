angular.module('app.utils', [])

.factory('modalUtil',
    ['$modal', 'mensajesConstants',
    function($modal, mensajesConstants) {

    return {

        openModal: function(jsonModal) {

            var sizeModal = mensajesConstants.general.size;
            if (jsonModal && jsonModal.size) {
                sizeModal = jsonModal.size;
            }

            var tituloModal = '';
            if (jsonModal && jsonModal.titulo) {
                tituloModal = jsonModal.titulo;
            }

            var mensajeModal = '';
            if (jsonModal && jsonModal.mensaje) {
                mensajeModal = jsonModal.mensaje;
            }

            var botonModal = mensajesConstants.general.aceptar;
            if (jsonModal && jsonModal.boton) {
                botonModal = jsonModal.boton;
            }

            $modal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: '../general/views/mensaje-template.html',
                controller: 'mensajeController',
                size: function() {
                    return sizeModal;
                },
                resolve: {
                    titulo: function() {
                        return tituloModal;
                    },
                    mensaje: function() {
                        return mensajeModal;
                    },
                    boton: function() {
                        return botonModal;
                    }
                }
            });

        },
        openModalConfirmacion: function(jsonModal) {

            var sizeModal = mensajesConstants.general.size;
            if (jsonModal && jsonModal.size) {
                sizeModal = jsonModal.size;
            }

            var tituloModal = '';
            if (jsonModal && jsonModal.titulo) {
                tituloModal = jsonModal.titulo;
            }

            var mensajeModal = '';
            if (jsonModal && jsonModal.mensaje) {
                mensajeModal = jsonModal.mensaje;
            }

            var botonConfirmarModal = mensajesConstants.general.si;
            if (jsonModal && jsonModal.botonConfirmar) {
                botonConfirmarModal = jsonModal.botonConfirmar;
            }

            var botonCancelarModal = mensajesConstants.general.no;
            if (jsonModal && jsonModal.botonCancelar) {
                botonCancelarModal = jsonModal.botonCancelar;
            }

            var itemModal = null;
            if (jsonModal && jsonModal.item) {
                itemModal = jsonModal.item;
            }

            var eventModal = '';
            if (jsonModal && jsonModal.event) {
                eventModal = jsonModal.event;
            }

            var modalInstance = $modal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: '../general/views/confirmacion-template.html',
                controller: 'confirmacionController',
                size: function() {
                    return sizeModal;
                },
                resolve: {
                    titulo: function() {
                        return tituloModal;
                    },
                    mensaje: function() {
                        return mensajeModal;
                    },
                    botonConfirmar: function() {
                        return botonConfirmarModal;
                    },
                    botonCancelar: function() {
                        return botonCancelarModal;
                    },
                    item: function() {
                        return itemModal;
                    },
                    event: function() {
                        return eventModal;
                    }
                }
            });
            return modalInstance;
        },
        openModalCargarArchivo: function(jsonModal) {

            var templateModal = '';
            if (jsonModal.tipoAccion === 'nuevo') {
                templateModal = '../coleccion/views/insertar-archivo-modal.html';
            } else if (jsonModal.tipoAccion === 'editar') {
                templateModal = '../coleccion/views/actualizar-archivo-modal.html';
            } else if (jsonModal.tipoAccion === 'ver') {
                templateModal = '../coleccion/views/cargar-archivo-modal.html';
            }
            var sizeModal = mensajesConstants.general.size;
            if (jsonModal && jsonModal.size) {
                sizeModal = jsonModal.size;
            }

            var itemsModal = null;
            if (jsonModal && jsonModal.items) {
                itemsModal = jsonModal.items;
            }

            var tipoAccionModal = '';
            if (jsonModal && jsonModal.tipoAccion) {
                tipoAccionModal = jsonModal.tipoAccion;
            }

            var ordenMaximoGrillaModal = null;
            if (jsonModal && jsonModal.ordenMaximoGrilla) {
                ordenMaximoGrillaModal = jsonModal.ordenMaximoGrilla;
            }
            var modalInstance = $modal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: templateModal,
                controller: 'archivoController',
                size: function() {
                    return sizeModal;
                },
                resolve: {
                    items: function() {
                        return itemsModal;
                    },
                    tipoAccion: function() {
                        return tipoAccionModal;
                    },
                    ordenMaximoGrilla: function() {
                        return ordenMaximoGrillaModal;
                    }
                }
            });
            return modalInstance;
        }

    };

}]);
