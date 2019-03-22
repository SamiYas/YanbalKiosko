angular.module('app.controllers')

.controller('umbralModalController',
    ['$scope', '$modalInstance', '$modal', 'modalUtil', 'SesionService',
    'UmbralService', 'responseConstants', 'mensajesConstants',
    'sesionConstants', 'id', 'row',
    function($scope, $modalInstance, $modal, modalUtil, SesionService,
        UmbralService, responseConstants, mensajesConstants, sesionConstants,
        id, row) {

    $scope.modalTitulo = '';
    $scope.init = function() {
        $scope.id = id;
        $scope.selected = row;
        $scope.umbralEdicion = {
            id: '',
            extension: '',
            descripcion: '',
            carga: '',
            descarga: ''
        };

        $scope.modalTitulo = mensajesConstants.editarUmbral.titulo;

        var jsonData = { 'token': '', 'id': '' };
        jsonData.id = id;
        $scope.obtenerUmbral(jsonData);
    };

    $scope.obtenerUmbral = function(objJson) {
        UmbralService.obtenerUmbral(objJson).success(function(data) {
            if (!SesionService.esUsuarioRolCorporativo()) {
                $modalInstance.close($scope.selected);
            }
            $scope.umbralEdicion = data;
        });
    };

    $scope.actualizarUmbral = function() {
        var errorCarga = mensajesConstants.umbralValidacion.mensajeErrorCarga;
        if ($scope.umbralEdicion.carga === '') {
            var jsonModal = {
                    titulo: mensajesConstants.umbralValidacion.titulo,
                    mensaje: errorCarga
            };
            modalUtil.openModal(jsonModal);
            return;
        }

        var jsonData = {
            token: '',
            id: '',
            extension: '',
            descripcion: '',
            carga: null,
            descarga: null
        };
        jsonData.id = id;
        jsonData.extension = $scope.umbralEdicion.extension;
        jsonData.descripcion = $scope.umbralEdicion.descripcion;
        jsonData.carga = $scope.umbralEdicion.carga;
        if ($scope.umbralEdicion.descarga !== '') {
            jsonData.descarga = $scope.umbralEdicion.descarga;
        }
        UmbralService.actualizarUmbral(jsonData).success(function() {
            $scope.selected.descripcion = jsonData.descripcion;
            $scope.selected.carga = jsonData.carga;
            $scope.selected.descarga = jsonData.descarga;

            $modalInstance.close($scope.selected);

            $scope.mensajeActualizacion();
        }).error(function() {
            $modalInstance.close($scope.selected);
            $scope.mensajeErrorActualizacion();
        });
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };

    $scope.mensajeActualizacion = function() {
        var jsonModal = {
            titulo: mensajesConstants.umbralMensajeActualizacion.titulo,
            mensaje: mensajesConstants.umbralMensajeActualizacion.mensaje
        };
        modalUtil.openModal(jsonModal);
    };

    $scope.mensajeErrorActualizacion = function() {
        var jsonModal = {
            titulo: mensajesConstants.umbralMensajeErrorActualizacion.titulo,
            mensaje: mensajesConstants.umbralMensajeErrorActualizacion.mensaje
        };
        modalUtil.openModal(jsonModal);
    };

    $scope.init();

}]);
