angular.module('YanbalKiosko.controllers')

.controller('NavButtonsController', ['$scope', '$state', function ($scope, $state) {
    $scope.goHome = function () {
        $state.go('tab.coleccion');
    };
}]);