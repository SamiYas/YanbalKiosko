angular.module('YanbalKiosko.controllers')

.controller('ArchivoController', ['$scope', '$ionicModal', '$state', 'ArchivoService', '$ionicHistory', 'popUpUtil', '$cordovaNetwork', '$filter',
    function ($scope, $ionicModal, $state, ArchivoService, $ionicHistory, popUpUtil, $cordovaNetwork, $filter) {
    $scope.items = [];
    var GetNotificacion = function () {
        ArchivoService.GetNotificacion().then(function (data) {
            $scope.items = data;
        });
    };
    $ionicModal.fromTemplateUrl('general/views/menu-archivo.html', {
        scope: $scope,
        animation: 'slide-in-up'
    }).then(function (modal) {
        $scope.modal = modal;
    });
    $scope.openModal = function (item) {
        $scope.modal.show();
        $scope.selectItem(item);
    };
    var closeModal = function () {
        GetNotificacion();
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
    //limpiar el modal una vez teminemos de usarlo
    $scope.$on('$destroy', function () {
        $scope.modal.remove();
    });
    $scope.$on('modal.hidden', function () {
        GetNotificacion();
    });
    $scope.$on('modal.removed', function () {
        //ejecuta accion al remover el modal
    });
    $scope.selectItem = function (item) {
        $scope.activeItem = item;
    };
    $scope.isItemActive = function (item) {
        return $scope.activeItem === item;
    };
    $scope.openColeccion = function (id, parentTitle) {
        $state.go('tab.coleccion-detail', { coleccionId: id, title: parentTitle });
    };
    //Abrir archivo
    $scope.GetArchivo = function (item) {
        var connectionType = $cordovaNetwork.getNetwork();
        ArchivoService.ValidarArchivoRol(item.CORRELATIVO_ARCHIVO).then(function (result) {
            if (!result) {
                popUpUtil.alertPopup('CONFIGURACIONALERTATITULO', 'LOGINBOTONACEPTAR', 'ARCHIVONODISPONIBLE');
                GetNotificacion();
                return;
            }
            if (item.ARCHIVO_DESCARGADO) {
                ArchivoService.GetArchivo(item).success(function () {
                    GetNotificacion();
                    closeModal();
                }).error(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                });
            } else if ($cordovaNetwork.isOnline() && connectionType === 'wifi') {
                ArchivoService.GetArchivoNoDescargado(item).success(function () {
                    GetNotificacion();
                    closeModal();
                }).error(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                    GetNotificacion();
                });
            } else if ($cordovaNetwork.isOnline() && connectionType !== 'wifi' && connectionType !== 'none' && connectionType !== 'unknown') {
                ProcesarAperturaDatosMobiles(item);
            } else {
                popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ABRIRSINCONEXION');
                GetNotificacion();
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
                GetNotificacion();
                return;
            }
            if ($cordovaNetwork.isOnline() && connectionType === 'wifi') {
                ArchivoService.GuardarLocalmente(item).success(function () {
                    GetNotificacion();
                    closeModal();
                }).error(function () {
                    popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ERRORGENERICO');
                    GetNotificacion();
                    closeModal();
                });
            } else if ($cordovaNetwork.isOnline() && connectionType !== 'wifi' && connectionType !== 'none' && connectionType !== 'unknown') {
                ProcesarDescargaDatosMobiles(item);
            } else {
                popUpUtil.alertPopup('ERRORGENERICOTITULO', 'LOGINBOTONACEPTAR', 'ABRIRSINCONEXION');
                GetNotificacion();
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
        $scope.notificationTitle = $filter('translate')('NOTIFICACIONTITULO');
        GetNotificacion();
    };
    init();
}]);