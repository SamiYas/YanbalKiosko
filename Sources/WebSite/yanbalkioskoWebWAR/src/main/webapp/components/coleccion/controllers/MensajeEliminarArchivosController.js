angular.module('app.controllers')

.controller('mensajeEliminarArchivosController',
    ['$scope', '$modalInstance', 'titulo', 'mensaje', 'lista', 'boton',
    function($scope, $modalInstance, titulo, mensaje, lista, boton) {

    $scope.init = function() {
        $scope.tituloMensaje = titulo;
        $scope.mensaje = mensaje;
        $scope.botonMensaje = boton;
        $scope.lista = lista;
    };

    $scope.closeModal = function() {
        $modalInstance.close();
    };

    $scope.init();

}]);
