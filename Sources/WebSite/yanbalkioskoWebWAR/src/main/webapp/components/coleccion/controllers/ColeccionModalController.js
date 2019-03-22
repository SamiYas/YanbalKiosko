angular.module('app.controllers')

.controller('coleccionModalController',
    ['$scope', '$modalInstance', '$state', '$modal', '$timeout', 'modalUtil',
    'responseConstants' , 'mensajesConstants', 'ColeccionService',
    'SesionService', 'id_coleccion', 'coleccion_padre', 'nivel', 'row',
    'tipoAcion', 'coloresConstants', 'nroOrden',
    function($scope, $modalInstance, $state, $modal, $timeout, modalUtil,
    responseConstants, mensajesConstants, ColeccionService, SesionService,
    id_coleccion, coleccion_padre, nivel, row, tipoAcion, coloresConstants,
    nroOrden) {

    $scope.modalTitulo = '';

    $scope.init = function() {
        $scope.id_coleccion = id_coleccion;
        $scope.coleccion_padre = coleccion_padre;
        $scope.nivel = nivel;
        $scope.selected = row;
        $scope.errorNombre = responseConstants.RESPUESTA_ERROR_NOMBRE_DUPLICADO;
        $scope.coleccionDatos = {
            id_coleccion: '',
            id_coleccion_padre: '',
            nombre_coleccion_padre: '',
            id_pais: '',
            id_rol: '',
            nombre: '',
            nivel: '',
            orden: '',
            descripcion: ''
        };
        $scope.disableControls = true;
        $scope.colores = [];
        $scope.disableColor = true;
        var jsonData = {
            token: SesionService.token(),
            id_coleccion: $scope.id_coleccion
        };
        $scope.tipoTitulo = tipoAcion;

        if (tipoAcion === 'nuevo') {
            $scope.modalTitulo = mensajesConstants.nuevaColeccion.titulo;
            //Nuevo
            var newItem = {
                id_coleccion: '',
                id_coleccion_padre: '',
                id_pais: '',
                id_rol: '',
                nombre: '',
                nivel: '',
                orden: '',
                descripcion: ''
            };
            nroOrden.push(newItem);
            $scope.nroOrden = nroOrden;
            $timeout(function() {
            var orderDropdown = $('#ordenDropdown').selectize({
                onFocus: function() {
                    $('#ordenDropdown').parent()
                    .find('input[type=text]').prop('disabled', true);
                }
            });
            var orderSelectize = orderDropdown[0].selectize;
            orderSelectize.setValue(nroOrden.length);
            }, 0);
            $scope.coleccionDatos
            .id_coleccion_padre = $scope.coleccion_padre.id;
            $scope.coleccionDatos
            .nombre_coleccion_padre = $scope.coleccion_padre.nombre;
            $scope.coleccionDatos.nivel = $scope.nivel;
            $scope.coleccionDatos.id_pais = 1;
            $scope.coleccionDatos.id_rol = 1;
            $scope.coleccionDatos.temp = {
                    nivel: $scope.nivel
            };
            $scope.disableControls = false;
            if ($scope.nivel === 1) {
                $scope.disableColor = false;
            }
        } else if (tipoAcion === 'editar') {
            $scope.modalTitulo = mensajesConstants.editarColeccion.titulo;
            //Editar
            $scope.nroOrden = nroOrden;
            jsonData.id_coleccion = id_coleccion;
            $scope.obtenerColeccion(jsonData);
            $scope.disableControls = false;
            if ($scope.nivel === 1) {
                $scope.disableColor = false;
            }
        } else if (tipoAcion === 'ver') {
            $scope.modalTitulo = mensajesConstants.verColeccion.titulo;
            //Ver
            $scope.nroOrden = nroOrden;
            jsonData.id_coleccion = id_coleccion;
            $scope.obtenerColeccion(jsonData);
            $scope.disableControls = true;
            $scope.disableColor = true;
        }
        var listaColores = [
        coloresConstants.color1,
        coloresConstants.color2,
        coloresConstants.color3,
        coloresConstants.color4,
        coloresConstants.color5
        ];

        $scope.colores = listaColores;

        // add placeholder for IE
        $timeout(function() {
            $('input').placeholder();
        }, 100);
    };

    $scope.obtenerColeccion = function(objJson) {
        ColeccionService.obtenerColeccion(objJson).success(function(data) {
            $scope.coleccionDatos = data;
            $scope.coleccionDatos.temp = {
                    nivel: data.nivel
            };
            $scope.coleccionDatos
            .id_coleccion_padre = $scope.coleccion_padre.id;
            $scope.coleccionDatos
            .nombre_coleccion_padre = $scope.coleccion_padre.nombre;
            $timeout(function() {
                var colorDropdown = $('#colorDropdown').selectize();
                var colorSelectize = colorDropdown[0].selectize;
                colorSelectize.setValue(data.color);
                var orderDropdown = $('#ordenDropdown').selectize();
                var orderSelectize = orderDropdown[0].selectize;
                orderSelectize.setValue(data.orden);
            },0);
        });
    };
    $scope.insertarColeccion = function(objJson) {
        $scope.$parent.$broadcast('showLoadingDiv');
        ColeccionService.insertarColeccion(objJson).success(function(data) {
            if (responseConstants.RESPUESTA_OK === data.ResponseCode) {
                $scope.coleccionDatos.id_coleccion = objJson.id_coleccion;
                $scope.coleccionDatos
                .id_coleccion_padre = objJson.id_coleccion_padre;
                $scope.coleccionDatos.nombre = objJson.nombre;
                $scope.coleccionDatos.nivel = objJson.nivel;
                $scope.coleccionDatos.orden = objJson.orden;
                $scope.coleccionDatos.color = objJson.color;
                $scope.coleccionDatos.descripcion = objJson.descripcion;
                $modalInstance.close($scope.coleccionDatos);
                $scope.mensajeInsercion(400);
            } else if ($scope.errorNombre === data.ResponseCode) {
                $scope.mostrarErrorFormulario(
                    mensajesConstants.coleccionDatosValidacion
                    .errorNombreDuplicado);
            }
            $scope.$parent.$broadcast('hideLoadingDiv');
        }).error(function() {
            $scope.$parent.$broadcast('hideLoadingDiv');
        });
    };

    $scope.actualizarColeccion = function(objJson) {
        $scope.$parent.$broadcast('showLoadingDiv');
        ColeccionService.actualizarColeccion(objJson).success(function(data) {
            if (responseConstants.RESPUESTA_OK === data.ResponseCode) {
                $scope.selected.id_coleccion = objJson.id_coleccion;
                $scope.selected.id_coleccion_padre = objJson.id_coleccion_padre;
                $scope.selected.nombre = objJson.nombre;
                $scope.selected.nivel = objJson.nivel;
                $scope.selected.orden = objJson.orden;
                $scope.selected.color = objJson.color;
                $scope.selected.descripcion = objJson.descripcion;
                $modalInstance.close($scope.selected);
                $scope.mensajeActualizacion(400);
            } else if ($scope.errorNombre === data.ResponseCode) {
                $scope.mostrarErrorFormulario(
                    mensajesConstants.coleccionDatosValidacion
                    .errorNombreDuplicado);
            }
            $scope.$parent.$broadcast('hideLoadingDiv');
        }).error(function() {
            $scope.$parent.$broadcast('hideLoadingDiv');
        });
    };

    $scope.ok = function() {
        var jsonData = {
                 id_coleccion: $scope.coleccionDatos.id_coleccion,
                 id_coleccion_padre: $scope.coleccionDatos.id_coleccion_padre,
                 nombre: $scope.coleccionDatos.nombre,
                 nivel: $scope.coleccionDatos.temp.nivel,
                 orden: $scope.coleccionDatos.orden,
                 color: $scope.coleccionDatos.color,
                 descripcion: $scope.coleccionDatos.descripcion
        };

        if (tipoAcion === 'nuevo') {

            if ($scope.validarFormulario()) {
                $scope.insertarColeccion(jsonData);
            }

        }else if (tipoAcion === 'editar') {
            if ($scope.validarFormulario()) {
                $scope.actualizarColeccion(jsonData);
            }
        }
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };

    $scope.mensajeActualizacion = function(sizeModal) {
        var jsonModal = {
                titulo: mensajesConstants.coleccionActualizacion.titulo,
                mensaje: mensajesConstants.coleccionActualizacion.mensaje,
                size: sizeModal
            };
        modalUtil.openModal(jsonModal);
    };

    $scope.mensajeInsercion = function(sizeModal) {
        var jsonModal = {
                titulo: mensajesConstants.coleccionInsercion.titulo,
                mensaje: mensajesConstants.coleccionInsercion.mensaje,
                size: sizeModal
            };
        modalUtil.openModal(jsonModal);
    };

    $scope.validarFormulario = function() {
        if ($scope.coleccionDatos.nombre.trim().length === 0) {
            $scope.mostrarErrorFormulario(
                mensajesConstants.coleccionDatosValidacion.errorNombre);
            return false;
        }
        if (!$scope.coleccionDatos.orden) {
            $scope.mostrarErrorFormulario(
                mensajesConstants.coleccionDatosValidacion.errorNroOrden);
            return false;
        }
        if ($scope.coleccionDatos.nivel === 1) {
            if (!$scope.coleccionDatos.color) {
                $scope.mostrarErrorFormulario(
                    mensajesConstants.coleccionDatosValidacion.errorColor);
                return false;
            }
        }

        return true;
    };

    $scope.mostrarErrorFormulario = function(mensajeModal) {

        var tituloModal = '';

        if ($scope.tipoTitulo === 'nuevo') {
            tituloModal = mensajesConstants
            .coleccionDatosValidacion.tituloNuevo;
        } else if ($scope.tipoTitulo === 'editar') {
            tituloModal = mensajesConstants
            .coleccionDatosValidacion.tituloEditar;
        }

        var jsonModal = {
                titulo: tituloModal,
                mensaje: mensajeModal
            };
        modalUtil.openModal(jsonModal);

    };

    $scope.init();

}]);
