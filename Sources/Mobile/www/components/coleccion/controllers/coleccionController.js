angular.module('YanbalKiosko.controllers')

.controller('ColeccionController', ['$scope', '$ionicHistory', 'ColeccionService', 'LoginService', 'ArchivoService', '$localstorage', '$ionicModal', '$timeout', '$state', '$stateParams', '$ionicPlatform',
    '$cordovaPush', 'libreriaCorporativa', 'popUpUtil', 'templateUrlConstants', '$cordovaNetwork', 'textConstants', '$ionicScrollDelegate', '$location', '$rootScope', '$filter',
    function ($scope, $ionicHistory, ColeccionService, LoginService, ArchivoService, $localstorage, $ionicModal, $timeout, $state, $stateParams, $ionicPlatform, $cordovaPush, libreriaCorporativa,
        popUpUtil, templateUrlConstants, $cordovaNetwork, textConstants, $ionicScrollDelegate, $location, $rootScope, $filter) {
    $scope.colecciones = [];
    $scope.coleccionItems = [];
    $scope.coleccionId = 0;
    $scope.shownItems = 1;
    //Obtener datos desde la bd
    var GetColeccionesContenido = function (coleccionId) {
        ColeccionService.GetColeccionesContenido(coleccionId).then(function (selectresult) {
            //si no existen items a visualizar mostrar mensaje por defecto
            var result = selectresult.filter(function (obj) {
                return obj.HIJOS > 0;
            });
            $scope.shownItems = result.length;
            //si es vista principal agregar items y mostrar seleccionada la ultima coleccion seleccionada
            if (coleccionId === 0) {
                $scope.colecciones = result;
                //ocultar icono cargando
                $scope.$broadcast('scroll.refreshComplete');
                $scope.$broadcast('loading.hide');
                //si no existe un coleccion seleccionada previamente mostrar la primera por defecto
                $scope.toggleGroup(!$scope.shownGroup ? $scope.colecciones[0] : $scope.shownGroup);
            } else {
                if (selectresult.length === 0) {
                    $ionicHistory.nextViewOptions({
                        historyRoot: true
                    });
                    $state.go('tab.coleccion');
                } else {
                    $scope.coleccionItems = selectresult;
                    $scope.$broadcast('scroll.refreshComplete');
                    $scope.$broadcast('loading.hide');
                }
            }
            $scope.activeRefresh = 1;
        });
    };
    //Obtener y enviar datos del y al servidor
    var sincronizar = function (coleccionId) {
        if ($cordovaNetwork.isOnline()) {
            //Si se tiene conexion actualizar flag de sincronizacion offline
            $localstorage.set('SincronizarOffline', textConstants.LOCALSTORAGEFALSE);
            //llamar a sincronizacion
            ColeccionService.sincronizar().then(function () {
                GetColeccionesContenido(coleccionId);
            }, function () {
                popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                GetColeccionesContenido(coleccionId);
            });
        } else if ($localstorage.get('SincronizarOffline') === textConstants.LOCALSTORAGETRUE) {
            //Si esta offline e hizo login offline carga data de bd sin mostrar popup la primera vez
            GetColeccionesContenido(coleccionId);
        } else {
            popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'SINCRONIZARSININTERNET');
            $scope.$broadcast('scroll.refreshComplete');
            $scope.$broadcast('loading.hide');
        }
    };
    var closeModal = function () {
        $scope.modal.hide();
    };
    var ProcesarAperturaDatosMobiles = function (item) {
        ArchivoService.ObtenerDatosMovilesUmbral(item.EXTENSION).then(function (data) {
            if (data.datosMoviles === '1') {
                if (!data.umbral || item.TAMANO_ARCHIVO <= data.umbral) {
                    ArchivoService.GetArchivoNoDescargado(item).success(function () {
                        $scope.$broadcast('loading.show');
                        GetColeccionesContenido($scope.coleccionId);
                        closeModal();
                    }).error(function () {
                        popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                    });
                } else {
                    popUpUtil.confirmPopupArchivo('DESCARGAARCHIVOTITULO', 'DESCARGAARCHIVOTEXTO', 'CONFIGURACIONBOTONCANCELAR', 'CONFIGURACIONBOTONACEPTAR').then(function (respuesta) {
                        if (respuesta) {
                            ArchivoService.GetArchivoNoDescargado(item).success(function () {
                                $scope.$broadcast('loading.show');
                                GetColeccionesContenido($scope.coleccionId);
                                closeModal();
                            }).error(function () {
                                popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                            });
                        } else {
                            ArchivoService.ActualizarFlagDescargando(0, item.CORRELATIVO_ARCHIVO).success(function () {
                                $scope.$broadcast('loading.show');
                                GetColeccionesContenido($scope.coleccionId);
                                closeModal();
                            }).error(function () {
                                popUpUtil.alertPopup('CONFIGURACIONALERTATITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                            });
                        }
                        $scope.$broadcast('loading.show');
                        GetColeccionesContenido($scope.coleccionId);
                        closeModal();
                    });
                }
            } else {
                ArchivoService.ActualizarFlagDescargando(0, item.CORRELATIVO_ARCHIVO).success(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'DATOSNOACTIVOSTEXTOABRIR');
                    $scope.$broadcast('loading.show');
                    GetColeccionesContenido($scope.coleccionId);
                    closeModal();
                }).error(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                });
            }
        }, function () {
            popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
            $scope.$broadcast('loading.show');
            GetColeccionesContenido($scope.coleccionId);
        });
    };
    var ProcesarDescargaDatosMobiles = function (item) {
        ArchivoService.ObtenerDatosMovilesUmbral(item.EXTENSION).then(function (data) {
            if (data.datosMoviles === '1') {
                if (!data.umbral || item.TAMANO_ARCHIVO <= data.umbral) {
                    ArchivoService.GuardarLocalmente(item).success(function () {
                        $scope.$broadcast('loading.show');
                        GetColeccionesContenido($scope.coleccionId);
                        closeModal();
                    }).error(function () {
                        popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                    });
                } else {
                    popUpUtil.confirmPopupArchivo('DESCARGAARCHIVOTITULO', 'DESCARGAARCHIVOTEXTO', 'CONFIGURACIONBOTONCANCELAR', 'CONFIGURACIONBOTONACEPTAR').then(function (respuesta) {
                        if (respuesta) {
                            ArchivoService.GuardarLocalmente(item).success(function () {
                                $scope.$broadcast('loading.show');
                                GetColeccionesContenido($scope.coleccionId);
                                closeModal();
                            }).error(function () {
                                popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                            });
                        } else {
                            ArchivoService.ActualizarFlagDescargando(0, item.CORRELATIVO_ARCHIVO).success(function () {
                                $scope.$broadcast('loading.show');
                                GetColeccionesContenido($scope.coleccionId);
                                closeModal();
                            }).error(function () {
                                popUpUtil.alertPopup('CONFIGURACIONALERTATITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                            });
                        }
                        $scope.$broadcast('loading.show');
                        GetColeccionesContenido($scope.coleccionId);
                        closeModal();
                    });
                }
            } else {
                ArchivoService.ActualizarFlagDescargando(0, item.CORRELATIVO_ARCHIVO).success(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'DATOSNOACTIVOSTEXTOGUARDAR');
                    $scope.$broadcast('loading.show');
                    GetColeccionesContenido($scope.coleccionId);
                    closeModal();
                }).error(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                });
            }
        }, function () {
            popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
            $scope.$broadcast('loading.show');
            GetColeccionesContenido($scope.coleccionId);
        });
    };
    //anhadir listener para evento de volver al aplicativo desde segundo plano
    $rootScope.offResume = $ionicPlatform.on('resume', function () {
        $scope.$broadcast('loading.show');
        sincronizar($scope.coleccionId);
        $localstorage.set('Sincronizar', textConstants.LOCALSTORAGEFALSE);
    });

    //Seleccionar coleccion padre
    $scope.toggleGroup = function (group) {
        $location.path(group.CORRELATIVO_COLECCION);
        $ionicScrollDelegate.anchorScroll(true);
        $scope.shownGroup = !group ? $scope.shownGroup : group;
        for (var coleccion in $scope.colecciones) {
            if ($scope.colecciones[coleccion].CORRELATIVO_COLECCION === $scope.shownGroup.CORRELATIVO_COLECCION) {
                $scope.colecciones[coleccion].mostrarColeccion = 1;
            } else {
                $scope.colecciones[coleccion].mostrarColeccion = 0;
            }
        }
    };
    $scope.isGroupShown = function (group) {
        return $scope.shownGroup === group;
    };
    //Seleccionar item dentro de coleccion
    $scope.selectItem = function (item) {
        $scope.activeItem = item;
    };
    $scope.isItemActive = function (item) {
        return $scope.activeItem === item;
    };
    //Modal de archivo
    $ionicModal.fromTemplateUrl(templateUrlConstants.menuArchivoUrl, {
        scope: $scope,
        animation: 'slide-in-up'
    }).then(function (modal) {
        $scope.modal = modal;
    });
    $scope.openModal = function (item) {
        $scope.modal.show();
        $scope.selectItem(item);
    };
    //limpiar el modal una vez teminemos de usarlo
    $scope.$on('$destroy', function () {
        $scope.modal.remove();
    });

    $scope.$on('modal.hidden', function () {
        $scope.$broadcast('loading.show');
        GetColeccionesContenido($scope.coleccionId);
    });

    $scope.$on('modal.removed', function () {
        //ejecuta accion al remover el modal
    });

    $scope.openColeccion = function (id, parentTitle) {
        $state.go('tab.coleccion-detail', { coleccionId: id, title: parentTitle });
    };

    $scope.doRefresh = function () {
        //Cambiar estado de sincronizacion offline a falso al intentar sincronizar manualmente
        $localstorage.set('SincronizarOffline', textConstants.LOCALSTORAGEFALSE);
        sincronizar($scope.coleccionId);
    };

    $scope.closeLoading = function () {
        $scope.$broadcast('loading.hide');
    };
    //Abrir archivo
    $scope.GetArchivo = function (item) {
        var connectionType = $cordovaNetwork.getNetwork();
        ArchivoService.ValidarArchivoRol(item.CORRELATIVO_ARCHIVO).then(function (result) {
            if (!result) {
                popUpUtil.alertPopup('CONFIGURACIONALERTATITULO', 'LOGINBOTONACEPTAR', 'ARCHIVONODISPONIBLE');
                sincronizar($scope.coleccionId);
                return;
            }
            if (item.ARCHIVO_DESCARGADO) {
                ArchivoService.GetArchivo(item).success(function () {
                    $scope.$broadcast('loading.show');
                    GetColeccionesContenido($scope.coleccionId);
                    closeModal();
                }).error(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                });
            } else if ($cordovaNetwork.isOnline() && connectionType === 'wifi') {
                ArchivoService.GetArchivoNoDescargado(item).success(function () {
                    $scope.$broadcast('loading.show');
                    GetColeccionesContenido($scope.coleccionId);
                    closeModal();
                }).error(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                    $scope.$broadcast('loading.show');
                    GetColeccionesContenido($scope.coleccionId);
                    closeModal();
                });
            } else if ($cordovaNetwork.isOnline() && connectionType !== 'wifi' && connectionType !== 'none' && connectionType !== 'unknown') {
                ProcesarAperturaDatosMobiles(item);
            } else {
                popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ABRIRSINCONEXION');
                $scope.$broadcast('loading.show');
                GetColeccionesContenido($scope.coleccionId);
            }
        });
        closeModal();
    };
    //Guardar archivo
    $scope.GuardarLocalmente = function (item) {
        var connectionType = $cordovaNetwork.getNetwork();
        ArchivoService.ValidarArchivoRol(item.CORRELATIVO_ARCHIVO).then(function (result) {
            if (!result) {
                popUpUtil.alertPopup('CONFIGURACIONALERTATITULO', 'LOGINBOTONACEPTAR', 'ARCHIVONODISPONIBLE');
                $scope.$broadcast('loading.show');
                sincronizar($scope.coleccionId);
                return;
            }
            if ($cordovaNetwork.isOnline() && connectionType === 'wifi') {
                ArchivoService.GuardarLocalmente(item).success(function () {
                    $scope.$broadcast('loading.show');
                    GetColeccionesContenido($scope.coleccionId);
                    closeModal();
                }).error(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                    $scope.$broadcast('loading.show');
                    GetColeccionesContenido($scope.coleccionId);
                    closeModal();
                });
            } else if ($cordovaNetwork.isOnline() && connectionType !== 'wifi' && connectionType !== 'none' && connectionType !== 'unknown') {
                ProcesarDescargaDatosMobiles(item);
            } else {
                popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'DESCARGARSINCONEXION');
                $scope.$broadcast('loading.show');
                GetColeccionesContenido($scope.coleccionId);
            }
        });
        closeModal();
    };
    //Eliminar archivo
    $scope.DeleteArchivo = function (item) {
        popUpUtil.confirmPopupArchivo('DESCARGAARCHIVOTITULO', 'ELIMINARARCHIVOTEXTO', 'CONFIGURACIONBOTONCANCELAR', 'CONFIGURACIONBOTONACEPTAR').then(function (respuesta) {
            if (respuesta) {
                ArchivoService.DeleteArchivo(item, 0).success(function () {
                    closeModal();
                }).error(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                });
            } else {
                closeModal();
            }
        });
    };
    var init = function () {
        $scope.parentTitle = $stateParams.title ? $stateParams.title : $filter('translate')('COLECCIONTITULO');
        $scope.coleccionId = $stateParams.coleccionId ? $stateParams.coleccionId : 0;
        //Desactivar ion refresher durante la primera sincronizacion
        $scope.activeRefresh = 0;
        if ($localstorage.get('Sincronizar') === textConstants.LOCALSTORAGETRUE) {
            $localstorage.set('Sincronizar', textConstants.LOCALSTORAGEFALSE);
            sincronizar($scope.coleccionId);
        } else {
            GetColeccionesContenido($scope.coleccionId);
        }
    };
    init();
}]);