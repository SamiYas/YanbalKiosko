angular.module('app.controllers')

.controller('coleccionController',
    ['$rootScope', '$scope', '$state', '$modal', '$timeout', 'modalUtil',
    'mensajesConstants', 'ColeccionService', 'SesionService', 'UmbralService',
    'stringConstants', 'roles', 'sesionConstants',
    function($rootScope, $scope, $state, $modal, $timeout, modalUtil,
    mensajesConstants, ColeccionService, SesionService, UmbralService,
    stringConstants, roles, sesionConstants) {

    $scope.items = [];
    $scope.breadcrumb = [];
    $scope.coleccion = [];
    $scope.coleccionPadre = {
        id_coleccion: '',
        id_coleccion_padre: '',
        nombre: '',
        nivel: '',
        orden: '',
        color: '',
        descripcion: ''
    };
    $scope.gridOptions = {};
    $scope.notificationAmount = 0;
    $scope.filtrosArchivo = {nombre: '', descripcion: ''};
    $scope.listaUmbrales = [];
    $scope.sinResultados = false;

    $scope.init = function () {
        $rootScope.$broadcast('actualizarListaPaises');
        /* general */
        $scope.title = 'Administracion de colecciones';
        $scope.animationsEnabled = true;
        $scope.items.roles = roles;

        $scope.archivosSeleccionados = 0;
        $scope.obtenerPorNotificar();
        $scope.cargarBreadcrumb(null);
        $scope.cargarColeccion(null);
        $scope.limpiarGrilla(1);
        $scope.info = {};

        $scope.listarUmbrales();

        $scope.disableArchivoOptions = $scope.archivosSeleccionados === 0 || $scope.archivosSeleccionados > 1;

        // add placeholder for IE
        $timeout(function() {
            $('input').placeholder();
        }, 200);
    };

    /*grilla de archivos*/
    $scope.gridOptions = {
        columnDefs: [
          { field: 'Id', visible: false },
          {
              field: 'Nombre', enableColumnMenu: false, width: 240,
              cellTooltip: function(row) {
                  return row.entity.Nombre;
              }
          },
          {
           displayName: 'Tama\u00F1o', field: 'Tamano', enableColumnMenu: false,
           width: 90, cellFilter: 'number:1', type: 'number'
          },
          { field: 'Ext', enableColumnMenu: false, width: 60 },
          {
              field: ' ', headerCellClass: 'descargable',
              enableColumnMenu: false, width: 60,
              cellClass: function(grid, row, col) {
                  if (grid.getCellValue(row, col)) {
                      return 'text-center yanbal-cell-bool yanbal-cell-check';
                  } else {
                      return 'text-center yanbal-cell-bool';
                  }
              }
          },
          {
              field: 'd', headerCellClass: 'destacado',
              enableColumnMenu: false, width: 60,
              cellClass: function(grid, row, col) {
                  if (grid.getCellValue(row, col)) {
                      return 'text-center yanbal-cell-bool yanbal-cell-check';
                  } else {
                      return 'text-center yanbal-cell-bool';
                  }
              }
          },
          { field: 'Inicio', headerCellClass: 'text-center',
              enableColumnMenu: false, width: 100,
              sortingAlgorithm: function(a, b) {
                  var d1 = $scope.obtenerDateDeString(a);
                  var d2 = $scope.obtenerDateDeString(b);
                  return (d1 > d2) ? 1 : -1;
              }
          },
          { field: 'Caducidad', headerCellClass: 'text-center',
              enableColumnMenu: false, width: 110,
              sortingAlgorithm: function(a, b) {
                  var d1 = $scope.obtenerDateDeString(a);
                  var d2 = $scope.obtenerDateDeString(b);
                  return (d1 > d2) ? 1 : -1;
              }
          },
          { field: 'Estado', enableColumnMenu: false, width: 100 },
          { field: 'Orden', enableColumnMenu: false, width: 80,
              type: 'number'
          },
          { field: 'Roles', enableColumnMenu: false, width: 100 },
          { field: 'Coleccion', visible: false },
          { field: 'tipoUsuario', enableColumnMenu: false, visible: false}
        ],
        enableRowSelection: true,
        enableSelectAll: false,
        selectionRowHeaderWidth: 35,
        rowHeight: 35
    };

    $scope.obtenerDateDeString = function(string) {
        if (string === '') {
            return '';
        }
        var fechaSplit = string.split('/');
        return new Date(fechaSplit[2], fechaSplit[1] - 1, fechaSplit[0]);
    };

    $scope.gridOptions.multiSelect = true;

    $scope.gridOptions.onRegisterApi = function(gridApi) {
        $scope.gridApi = gridApi;
        gridApi.selection.on.rowSelectionChanged($scope, function() {
            var seleccionados = $scope.gridApi.selection.getSelectedRows();
            $scope.archivosSeleccionados = seleccionados.length;
            if ($scope.archivosSeleccionados === 1) {
                $scope.items.archivoSeleccionado = seleccionados[0];
            }
            $scope.disableArchivoOptions = $scope.archivosSeleccionados === 0 ||
            $scope.archivosSeleccionados > 1;
            // Activa y/o Desactiva Seleccionar Todos si se selecciona todos los items en la grilla
            $scope.checkboxModel.seleccionarTodos = $scope.archivosSeleccionados ===
            $scope.gridOptions.data.length;
            $scope.enableDeleteOption = $scope.archivosSeleccionados > 0;
        });

        gridApi.selection.on.rowSelectionChangedBatch($scope, function() {
            var seleccionados = $scope.gridApi.selection.getSelectedRows();
            $scope.archivosSeleccionados = seleccionados.length;
            $scope.disableArchivoOptions = $scope.archivosSeleccionados === 0 ||
            $scope.archivosSeleccionados > 1;
            $scope.enableDeleteOption = $scope.archivosSeleccionados > 0;
        });
    };

    $scope.checkboxModel = {
        seleccionarTodos: false
    };

    $scope.seleccionarTodos = function() {
        var seleccionarTodos = $scope.checkboxModel.seleccionarTodos;
        $scope.checkboxModel.seleccionarTodos = !seleccionarTodos;
        if ($scope.checkboxModel.seleccionarTodos) {
            $scope.gridApi.selection.selectAllRows();
        } else {
            $scope.gridApi.selection.clearSelectedRows();
        }
    };

    $scope.limpiarGrilla = function(tipo) {
        $scope.checkboxModel.seleccionarTodos = false;
        $scope.disableArchivoOptions = true;
        $scope.enableDeleteOption = false;
        $scope.gridOptions.data = [];
        if (tipo) {
            $scope.esBusquedaArchivos = tipo === 2;
        }
    };

    $scope.cargarTab = function(indice) {
        if (indice === 1) {
            // tab colecciones
            $scope.limpiarGrilla(1);
            $scope.sinResultados = false;
            if (!!$scope.shownColeccion) {
                $scope.obtenerArchivos($scope.shownColeccion.id_coleccion);
            }
        } else if (indice === 2) {
            // tab busqueda
            $scope.filtrosArchivo.nombre = '';
            $scope.filtrosArchivo.descripcion = '';
            $scope.limpiarGrilla(2);
            $scope.sinResultados = false;
            $('#btnBuscarArchivos')[0].setAttribute('disabled', true);
        }
    };

    $scope.cargarBreadcrumb = function(id_coleccion) {
        var jsonRequest = {
                id: id_coleccion
        };

        ColeccionService.obtenerRutaOrigen(jsonRequest)
            .success(function(datos) {
                if (datos && datos.data) {
                    var bcResult = datos.data;
                    /* breadcrumb */
                    $scope.breadcrumb = [];
                    $scope.breadcrumb[0] = {
                        nombre: stringConstants.raiz,
                        id: null,
                        nivel: 0,
                        active: false
                    };
                    for (var i = 0; i < bcResult.length; i++) {
                        $scope.breadcrumb.push({
                                nombre: bcResult[i].nombre,
                                id: bcResult[i].id,
                                nivel: bcResult[i].nivel,
                                active: false
                       });
                    }
                     $scope.activarBreadcrumb();
                } else {
                    $scope.coleccion = [];
                }
            });

    };

    $scope.activarBreadcrumb = function() {
        var cantidad = $scope.breadcrumb.length;
        if (cantidad > 0) {
            $scope.breadcrumb[cantidad - 1].active = true;
        }
    };

    $scope.cargarColeccion = function(id_col_padre) {
        var jsonRequest = {token: '', id_coleccion_padre: id_col_padre};
        jsonRequest.token = SesionService.token();

        ColeccionService.listarColecciones(jsonRequest)
            .success(function(data) {
                if (!!data) {
                    $scope.coleccion = data.lista;
                    // actualizar datos para shownColeccion
                    if (!!$scope.shownColeccion) {
                        for (var i = 0; i < $scope.coleccion.length; i++) {
                            if ($scope.coleccion[i].id_coleccion === $scope.shownColeccion.id_coleccion) {
                                $scope.shownColeccion.tipoUsuario = $scope.coleccion[i].tipoUsuario;
                                var idColeccionPadre = $scope.coleccion[i].id_coleccion_padre;
                                $scope.shownColeccion.id_coleccion_padre = idColeccionPadre;
                                $scope.shownColeccion.orden = $scope.coleccion[i].orden;
                                break;
                            }
                        }
                        // scroll to shownColeccion
                        $timeout(function() {
                            $('.list-group')
                            .scrollTop(41 * ($scope.shownColeccion.orden - 1));
                        }, 200);
                    }
                } else {
                    $scope.coleccion = [];
                }
            });
    };

    $rootScope.$on('actualizarColecciones', function() {
        $scope.archivosSeleccionados = 0;
        $scope.obtenerPorNotificar();
        $scope.cargarBreadcrumb(null);
        $scope.cargarColeccion(null);
        $scope.info = {};
        $scope.shownColeccion = null;
        if ($scope.esBusquedaArchivos) {
            $scope.cargarTab(2);
        } else {
            $scope.limpiarGrilla(1);
        }
    });

    $scope.mensajeColeccionNoEliminada = function(sizeModal) {
        var jsonModal = {
                titulo: mensajesConstants.coleccionNoEliminada.titulo,
                mensaje: mensajesConstants.coleccionNoEliminada.mensajeError,
                size: sizeModal
        };
        modalUtil.openModal(jsonModal);
    };

    $scope.confirmacionEliminarColeccion = function(size, coleccion) {
        if ($scope.shownColeccion != null) {

            var jsonModal = {
                    titulo: mensajesConstants.coleccionConfirmacionEliminar.titulo,
                    mensaje: mensajesConstants.coleccionConfirmacionEliminar.mensaje,
                    item: coleccion,
                    event: 'eliminarColeccion'
                };

            var modalInstance = modalUtil.openModalConfirmacion(jsonModal);

            modalInstance.result.then(function() {
                $scope.shownColeccion = null;
                $scope.cargarBreadcrumb(coleccion.id_coleccion_padre);
                $scope.cargarColeccion(coleccion.id_coleccion_padre);
            });
        }
    };

    $scope.verificarColeccionEliminable = function() {
        if (SesionService.esUsuarioRolCorporativo() ||
            $scope.shownColeccion.tipoUsuario !== sesionConstants.ID_ROL_USER_CORP) {
            var coleccion = $scope.shownColeccion;
            if ($scope.gridOptions.data.length > 0 || $scope.tieneSubColecciones(coleccion)) {
                $scope.mensajeColeccionNoEliminada(400);
            } else {
                $scope.confirmacionEliminarColeccion(null, coleccion);
            }
        } else {
            // mensaje permisos
            var jsonModal = {
                    titulo: mensajesConstants.eliminarColeccion.titulo,
                    mensaje: mensajesConstants.eliminarColeccion.mensajeErrorPrivilegios
            };
            modalUtil.openModal(jsonModal);
        }
    };

    $scope.nuevoArchivo = function(sizeModal) {
        $scope.items.shownColeccion = $scope.shownColeccion;
        $scope.items.listaUmbrales = $scope.listaUmbrales;

        var jsonModal = {
                items: $scope.items,
                tipoAccion: 'nuevo',
                ordenMaximoGrilla: $scope.gridOptions.data.length + 1,
                size: sizeModal
        };

        var modalInstance = modalUtil.openModalCargarArchivo(jsonModal);

        modalInstance.result.then(function() {
            $scope.recargarGrillaArchivos();
        });
    };

    $scope.moveArchivo = function(size) {
        if (SesionService.esUsuarioRolCorporativo() ||
            $scope.items.archivoSeleccionado.tipoUsuario !== sesionConstants.ID_ROL_USER_CORP) {
            var modalInstance = $modal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: '../coleccion/views/mover-archivo-modal.html',
                controller: 'moverArchivoController',
                windowClass: 'modalMoverArchivo',
                size: size,
                resolve: {
                    items: function() {
                        return $scope.items;
                    }
                }
            });

            modalInstance.result.then(function() {
                $scope.recargarGrillaArchivos();
            });
        } else {
            // mensaje permisos
            var jsonModal = {
                    titulo: mensajesConstants.moverArchivo.titulo,
                    mensaje: mensajesConstants.moverArchivo.mensajeErrorPrivilegios
            };
            modalUtil.openModal(jsonModal);
        }

    };

    $scope.recargarGrillaArchivos = function() {
        $scope.obtenerPorNotificar();
        if ($scope.esBusquedaArchivos) {
            $scope.buscarArchivos();
        } else {
            $scope.obtenerArchivos($scope.shownColeccion.id_coleccion);
        }
    };

    $scope.recargarColecciones = function() {
        var id_coleccion = $scope.obtenerBreadCrumbActivo().id;
        $scope.cargarColeccion(id_coleccion);
    };

    $scope.crearColeccion = function(size) {

        var modalInstance = $modal.open({
            animation: true,
            backdrop: 'static',
            templateUrl: '../coleccion/views/agregar-coleccion-modal.html',
            controller: 'coleccionModalController',
            size: size,
            resolve: {
                id_coleccion: function() {
                    if ($scope.shownColeccion && $scope.shownColeccion.id_coleccion) {
                        return $scope.shownColeccion.id_coleccion;
                    } else {
                        return null;
                    }
                },
                coleccion_padre: function() {
                    if ($scope.breadcrumb) {
                        return $scope.obtenerBreadCrumbActivo();
                    } else {
                        return null;
                    }
                },
                nivel: function() {
                    var breadcrumbActivo = $scope.obtenerBreadCrumbActivo();
                    if ($scope.breadcrumb && breadcrumbActivo && breadcrumbActivo.nivel) {
                        return breadcrumbActivo.nivel + 1;
                    } else {
                        return 1;
                    }
                },
                row: function() {
                    return $scope.shownColeccion;
                },
                tipoAcion: function() {
                    return 'nuevo';
                },
                nroOrden: function() {
                    var nroOrden = [];
                    nroOrden.push.apply(nroOrden, $scope.coleccion);
                    return nroOrden;
                }
            }
        });
        modalInstance.result.then(function() {
            $scope.recargarColecciones();
        });
    };

    $scope.editarColeccion = function(size) {

        if (SesionService.esUsuarioRolCorporativo() ||
            $scope.shownColeccion.tipoUsuario !== sesionConstants.ID_ROL_USER_CORP) {
            var modalInstance = $modal.open({
                animation: true,
                backdrop: 'static',
                templateUrl: '../coleccion/views/agregar-coleccion-modal.html',
                controller: 'coleccionModalController',
                size: size,
                resolve: {
                    id_coleccion: function() {
                        if ($scope.shownColeccion && $scope.shownColeccion.id_coleccion) {
                            return $scope.shownColeccion.id_coleccion;
                        } else {
                            return null;
                        }
                    },
                    coleccion_padre: function() {
                        return $scope.breadcrumb ? $scope.obtenerBreadCrumbActivo() : null;
                    },
                    nivel: function() {
                        var breadcrumbActivo = $scope.obtenerBreadCrumbActivo();
                        if ($scope.breadcrumb && breadcrumbActivo && breadcrumbActivo.nivel) {
                            return breadcrumbActivo.nivel + 1;
                        } else {
                            return 1;
                        }
                    },
                    row: function() {
                        return $scope.shownColeccion;
                    },
                    tipoAcion: function() {
                        return 'editar';
                    },
                    nroOrden: function() {
                        var nroOrden = [];
                        nroOrden.push.apply(nroOrden, $scope.coleccion);
                        return nroOrden;
                    }
                }
            });
            modalInstance.result.then(function() {
                $scope.recargarColecciones();
            });
        } else {
            // mensaje permisos
            var jsonModal = {
                    titulo: mensajesConstants.editarColeccion.titulo,
                    mensaje: mensajesConstants.editarColeccion.mensajeErrorPrivilegios
            };
            modalUtil.openModal(jsonModal);
        }
    };

    $scope.verColeccion = function(size) {

        $modal.open({
            animation: true,
            backdrop: 'static',
            templateUrl: '../coleccion/views/agregar-coleccion-modal.html',
            controller: 'coleccionModalController',
            size: size,
            resolve: {
                id_coleccion: function() {
                    if ($scope.shownColeccion != null) {
                        return $scope.shownColeccion.id_coleccion;
                    }else {
                        return null;
                    }
                },
                coleccion_padre: function() {
                    if ($scope.breadcrumb != null) {
                        return $scope.obtenerBreadCrumbActivo();
                    }else {
                        return null;
                    }
                },
                nivel: function() {
                    if ($scope.breadcrumb != null) {
                        return $scope.obtenerBreadCrumbActivo().nivel + 1;
                    }else {
                        return 1;
                    }
                },
                row: function() {
                    return $scope.shownColeccion;
                },
                tipoAcion: function() {
                    return 'ver';
                },
                nroOrden: function() {
                    var nroOrden = [];
                    nroOrden.push.apply(nroOrden, $scope.coleccion);
                    return nroOrden;
                }
            }
        });
    };

    $scope.obtenerBreadCrumbActivo = function() {
        return $scope.breadcrumb[$scope.breadcrumb.length - 1];
    };

    $scope.toggleColeccion = function(item) {
        $scope.shownColeccion = item;
        $scope.obtenerArchivos(item.id_coleccion);
        $scope.checkboxModel.seleccionarTodos = false;
    };

    $scope.isColeccionShown = function(item) {
        if (!item || !$scope.shownColeccion) {
            return false;
        }
        return $scope.shownColeccion.id_coleccion === item.id_coleccion;
    };

    $scope.obtenerArchivos = function(coleccion_id) {
        var jsonRequest = {
                id_coleccion: coleccion_id
        };
        $scope.cargarArchivos(jsonRequest);
    };

    $scope.buscarArchivos = function() {
        var jsonRequest = {
                nombre: $scope.filtrosArchivo.nombre.trim(),
                descripcion: $scope.filtrosArchivo.descripcion.trim()
        };
        $scope.cargarArchivos(jsonRequest);
    };

    $rootScope.$on('recargarGrillaArchivosEnColeccion', function(event, args) {
    	$scope.limpiarGrilla();
    	$scope.cargarDatosEnGrilla(args.lista);
    });
    
    $scope.cargarArchivos = function(jsonRequest) {
        ColeccionService.listarArchivos(jsonRequest)
            .success(function(data) {
                $scope.limpiarGrilla();
                var archivos = data.lista;

                $scope.cargarDatosEnGrilla(archivos);

                $scope.sinResultados = false;
                if ($scope.esBusquedaArchivos && archivos.length <= 0) {
                    $scope.sinResultados = true;
                }

            });
    };

    $scope.cargarDatosEnGrilla = function(archivos) {
        for (var i = 0; i < archivos.length; i++) {
            $scope.gridOptions.data.push({
                Id: archivos[i].id_archivo,
                Nombre: archivos[i].nombre,
                Tamano: archivos[i].tamanho,
                Ext: archivos[i].extension,
                ' ': archivos[i].descargable,
                'd': archivos[i].destacado,
                Inicio: archivos[i].fec_inicio,
                Caducidad: archivos[i].fec_caducidad,
                Estado: archivos[i].estado,
                Orden: archivos[i].nro_orden,
                Roles: archivos[i].roles,
                Coleccion: archivos[i].id_coleccion,
                tipoUsuario: archivos[i].tipoUsuario
            });
        }
    };

    $scope.tieneSubColecciones = function(coleccion) {
        var idColeccion = coleccion.id_coleccion;
        var cantidadColeccionesHijas = 0;
        for (var i = 0; i < $scope.coleccion.length; i++) {
            if ($scope.coleccion[i].id_coleccion === idColeccion) {
                cantidadColeccionesHijas = $scope.coleccion[i].coleccionesHijas;
                break;
            }
        }
        return cantidadColeccionesHijas > 0;
    };

    $scope.openColeccion = function(item) {
        $scope.shownColeccion = null;
        $scope.limpiarGrilla();

        if (item.nivel < 4) {
            var idColeccion = null;
            // obtener el idColeccion
            if (item.id == null) {
                idColeccion = item.id_coleccion;
            }else {
                idColeccion = item.id;
            }
            if (item.nivel < $scope.breadcrumb.length) {
                // recargar coleccion seleccionada
                var coleccion = $scope.breadcrumb[item.nivel + 1];
                if (!!coleccion) {
                    coleccion.id_coleccion = coleccion.id;
                    $scope.toggleColeccion(coleccion);
                }
            }
            $scope.cargarBreadcrumb(idColeccion);
            $scope.cargarColeccion(idColeccion);
        }
    };

    $scope.obtenerPorNotificar = function(mostrarMensaje) {
        var jsonRequest = {token: '', pais: ''};
        ColeccionService.getNotificationAmount(jsonRequest)
        .success(function(data) {
            if (!!data) {
                $scope.notificationAmount = data.porNotificar;
            } else {
                $scope.notificationAmount = 0;
            }
            if (mostrarMensaje) {
                // mensaje confirmacion
                var jsonModal = {
                        titulo: mensajesConstants.enviarNotificacion.titulo,
                        mensaje: mensajesConstants.enviarNotificacion.mensaje
                };
                modalUtil.openModal(jsonModal);
            }
        }).error(function() {
            $scope.notificationAmount = 0;
        });
    };

    // notificacion
    $scope.enviarNotificacion = function(mensajeNotificacion) {
        if (!mensajeNotificacion.trim().length) {
            $('#btnNotificar')[0].setAttribute('disabled', true);
            return;
        }

        var jsonRequest = {
                mensaje: mensajeNotificacion.trim()
        };

        ColeccionService.enviarNotificacionPush(jsonRequest)
            .success(function() {
                // limpiar input
                $scope.mensajeNotificacion = '';
                $('#btnNotificar')[0].setAttribute('disabled', true);
                // vuelve a mostrar el input de placeholder
                $('#notificationInputReal').blur();
                // recargar info
                $scope.obtenerPorNotificar(true);
            });

    };

    // ver archivo
    $scope.verArchivo = function() {

        var jsonModal = {
                items: $scope.items,
                tipoAccion: 'ver',
                ordenMaximoGrilla: Math.max(
                  $scope.gridOptions.data.length,
                  $scope.items.archivoSeleccionado.Orden)
        };

        var modalInstance = modalUtil.openModalCargarArchivo(jsonModal);

        modalInstance.result.then(function(selectedItem) {
            $scope.selected = selectedItem;
        }, function() {
        });
    };

    // editar archivo
    $scope.editarArchivo = function() {
        if (SesionService.esUsuarioRolCorporativo() ||
            $scope.items.archivoSeleccionado.tipoUsuario !== sesionConstants.ID_ROL_USER_CORP) {
            $scope.items.listaUmbrales = $scope.listaUmbrales;

            var jsonModalEditar = {
                    items: $scope.items,
                    tipoAccion: 'editar',
                    ordenMaximoGrilla: Math.max(
                      $scope.gridOptions.data.length,
                      $scope.items.archivoSeleccionado.Orden)
            };

            var modalInstance = modalUtil
            .openModalCargarArchivo(jsonModalEditar);

            modalInstance.result.then(function() {
                $scope.recargarGrillaArchivos();
            });
        } else {
            // mensaje permisos
            var jsonModalPermisos = {
                    titulo: mensajesConstants.editarArchivo.titulo,
                    mensaje: mensajesConstants.editarArchivo.mensajeErrorPrivilegios
            };
            modalUtil.openModal(jsonModalPermisos);
        }
    };

    // eliminar archivo
    $scope.eliminarArchivo = function() {

        var jsonModalEliminar = {
            titulo: mensajesConstants.eliminarArchivos.titulo,
            mensaje: mensajesConstants.eliminarArchivos.mensajeConfirmacion,
            item: $scope.obtenerIdsArchivosSeleccionados(),
            event: 'eliminarArchivos'
        };

        var modalInstance = modalUtil.openModalConfirmacion(jsonModalEliminar);

        modalInstance.result.then(function() {
            $scope.recargarGrillaArchivos();
        });

    };

    $scope.listarUmbrales = function() {
        var objJson = {};
        UmbralService.listarUmbrales(objJson).success(function(data) {
            var totalRegistros = data.totalRegistros;
            if (!!totalRegistros) {
                $scope.listaUmbrales = data.lista;
            }
        });
    };

    $scope.obtenerIdsArchivosSeleccionados = function() {
        var archivos = $scope.gridApi.selection.getSelectedRows();
        var idsArchivos = [];
        for (var i = 0; i < archivos.length; i++) {
            idsArchivos[i] = archivos[i].Id;
        }
        return idsArchivos;
    };

    $scope.init();

}]);
