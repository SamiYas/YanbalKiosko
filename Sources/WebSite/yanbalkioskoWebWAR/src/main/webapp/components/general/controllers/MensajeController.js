angular.module('app.controllers')

.controller('mensajeController',
    ['$scope', '$modalInstance', 'titulo', 'mensaje', 'boton',
    function($scope, $modalInstance, titulo, mensaje, boton) {

    $scope.init = function() {
        $scope.tituloMensaje = titulo;
        $scope.mensaje = mensaje;
        $scope.botonMensaje = boton;
    };

    $scope.closeModal = function() {
        $modalInstance.close();
    };

    $scope.init();

}]);
