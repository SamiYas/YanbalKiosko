angular.module('app.controllers')

.controller('umbralController',
    ['$rootScope', '$scope', '$state', '$modal', 'modalUtil', 'SesionService',
    'UmbralService', 'gridEtiquetasConstants', 'mensajesConstants',
    function($rootScope, $scope, $state, $modal, modalUtil, SesionService,
    UmbralService, gridEtiquetasConstants, mensajesConstants) {

    $scope.gridOptions = {};
    $scope.info = {};
    $scope.checkboxModel = {};

    $scope.init = function() {
        $scope.title = gridEtiquetasConstants.gridUmbrales.title;
        $scope.animationsEnabled = true;
        var cellTemplateCarga = '<div class="ui-grid-cell-contents">';
        cellTemplateCarga += '{{grid.appScope.retornarPesoCarga(row)}}</div>';
        $scope.cellTemplateCarga = cellTemplateCarga;

        var cellTemplateDescarga = '<div class="ui-grid-cell-contents">';
        cellTemplateDescarga += '{{grid.appScope.retornarPesoDescarga(row)}}</div>';
        $scope.cellTemplateDescarga = cellTemplateDescarga;

        var cellTemplateEditar = '';
        if (SesionService.esUsuarioRolCorporativo()) {
            cellTemplateEditar = '<div class="ui-grid-cell-contents">';
            cellTemplateEditar += '<div class="yanbal-editar" ';
            cellTemplateEditar += 'ng-click="grid.appScope.editarUmbral(row)"></div></div>';
        } else {
            cellTemplateEditar = '<div class="ui-grid-cell-contents">';
            cellTemplateEditar += '<div class="yanbal-editar disabled" ></div></div>';
        }

        $scope.gridOptions = {
            columnDefs: [
                {
                    field: 'extension',
                    displayName: gridEtiquetasConstants.gridUmbrales.fieldExtension,
                    enableColumnMenu: false
                },
                {
                    field: 'descripcion',
                    displayName: gridEtiquetasConstants.gridUmbrales.fieldDescripcion,
                    enableColumnMenu: false
                },
                {
                    field: 'carga',
                    displayName: gridEtiquetasConstants.gridUmbrales.fieldCarga,
                    enableColumnMenu: false,
                    cellTemplate: $scope.cellTemplateCarga,
                    type: 'number'
                },
                {
                    field: 'descarga',
                    displayName: gridEtiquetasConstants.gridUmbrales.fieldDescarga,
                    enableColumnMenu: false,
                    cellTemplate: $scope.cellTemplateDescarga,
                    type: 'number'
                },
                {
                    field: 'Editar',
                    displayName: gridEtiquetasConstants.gridUmbrales.fieldEditar,
                    enableColumnMenu: false,
                    width: 100,
                    enableSorting: false,
                    cellTemplate: cellTemplateEditar
                },
                {
                    field: 'rol', enableColumnMenu: false, visible: false
                },
                {
                    field: 'id', enableColumnMenu: false, visible: false
                }
            ]
        };
        $scope.gridOptions.data = [
        ];
        $scope.checkboxModel = {
            seleccionarTodos: false
        };

        $scope.listarUmbrales();
    };

    $rootScope.$on('actualizarGridUmbrales', function() {
        $scope.listarUmbrales();
    });

    $scope.editarUmbral = function(row, size) {
        if (SesionService.esUsuarioRolCorporativo()) {
            var modalInstance = $modal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: '../umbral/views/editar-umbral.html',
                controller: 'umbralModalController',
                size: size,
                resolve: {
                    id: function() {
                        return row.entity.id;
                    },
                    row: function() {
                        return row.entity;
                    }
                }
            });

            modalInstance.result.then(function(selectedItem) {
                $scope.selected = selectedItem;
            }, function() {
            });
        }else {
            $scope.mensajeNoEditable();
        }
    };

    $scope.mensajeNoEditable = function() {
        var jsonModal = {
                titulo: mensajesConstants.umbralMensajeErrorNoEditable.titulo,
                mensaje: mensajesConstants.umbralMensajeErrorNoEditable.mensaje
        };
        modalUtil.openModal(jsonModal);
    };

    $scope.listarUmbrales = function() {
        UmbralService.listarUmbrales().success(function(data) {
            var totalRegistros = data.totalRegistros;
            if (!!totalRegistros) {
                $scope.gridOptions.data = data.lista;
            }
        });
    };

    $scope.retornarPesoDescarga = function(row) {
        var resultado = '';
        var pesoDescarga = row.entity.descarga;
        if (pesoDescarga != null && pesoDescarga !== '') {
            resultado += pesoDescarga + ' MB';
        }
        return resultado;
    };

    $scope.retornarPesoCarga = function(row) {
        var resultado = '';
        var pesoDescarga = row.entity.carga;
        if (pesoDescarga != null && pesoDescarga !== '') {
            resultado += pesoDescarga + ' MB';
        }
        return resultado;
    };

    $scope.seleccionarTodos = function() {
        var seleccionarTodos = $scope.checkboxModel.seleccionarTodos;
        $scope.checkboxModel.seleccionarTodos = !seleccionarTodos;
        if ($scope.checkboxModel.seleccionarTodos) {
            $scope.gridApi.selection.selectAllRows();
        }else {
            $scope.gridApi.selection.clearSelectedRows();
        }
    };

    $scope.init();

}]);
