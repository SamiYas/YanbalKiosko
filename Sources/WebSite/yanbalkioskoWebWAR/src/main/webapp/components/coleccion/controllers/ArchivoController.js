angular.module('app.controllers')

.controller('archivoController',
    ['$rootScope', '$scope', '$modalInstance', '$modal', '$timeout',
     'localStorageService', 'modalUtil', 'ColeccionService', 'ArchivoService', 'items',
     'Upload', 'sesionConstants', 'tipoAccion', 'ordenMaximoGrilla',
     'mensajesConstants', 'responseConstants',
    function($rootScope, $scope, $modalInstance, $modal, $timeout, localStorageService,
    modalUtil, ColeccionService, ArchivoService, items, Upload, sesionConstants,
    tipoAccion, ordenMaximoGrilla, mensajesConstants, responseConstants) {

    $scope.infoArchivo = {
        id_archivo: '',
        id_coleccion: '',
        nombre: '',
        descripcion: '',
        tamanho: (0.0).toFixed(1),
        extension: '',
        nro_orden: '',
        descargable: 0,
        destacado: 0,
        ruta_imgprevia: '',
        ruta_archivo: '',
        fec_inicio: '',
        fec_caducidad: '',
        estado: '',
        roles: ''
    };
    $scope.nombreColeccion = '';
    $scope.roles = [];
    $scope.picFile = [];
    $scope.fileLoad = [];
    $scope.extensionesValidasArchivo = ['JPG', 'PDF', 'MP3', 'MP4'];
    $scope.extensionValidaPreview = 'JPG';
    $scope.modalTitulo = '';
    $scope.errorNombre = responseConstants.RESPUESTA_ERROR_NOMBRE_DUPLICADO;
    $scope.rutaIsotipoYanbal = '../../img/150x200_preview.png';
    $scope.leyendaVistaPrevia =
    mensajesConstants.cargarArchivoValidacion.leyendaVistaPrevia
    .replace('{0}',
        mensajesConstants.cargarArchivoValidacion.tamanhoMaximoPreviewEnKB);

    $scope.init = function() {
        $scope.roles = items.roles;
        // iniciar roles desmarcados
        for (var i = 0; i < $scope.roles.length; i++) {
            $scope.roles[i].value = false;
        }

        $scope.dateOptions = {
            formatYear: 'yyyy',
            startingDay: 1,
            showWeeks: false
        };
        $scope.format = 'dd/MM/yyyy';

        $scope.html5Complaint = window.File && window.FileReader && window.FileList && window.Blob;

        if (tipoAccion === 'nuevo') {
            //Nuevo
            $scope.modalTitulo = mensajesConstants.nuevoArchivo.tituloPopup;
            $scope.disableControls = false;
            $scope.modoVisualizacionArchivo = 1;
            $scope.infoArchivo.nro_orden = ordenMaximoGrilla;
            $scope.maximoNroOrdenGrilla = ordenMaximoGrilla;

            //cargar orden por defecto que es el maximo orden +1
            $scope.nroOrdenArchivo = [];
            for (var j = 0; j < ordenMaximoGrilla; j++) {
                $scope.nroOrdenArchivo.push(
                    {numero: j + 1, descripcion: 'Nro de Orden ' + (j + 1)});
            }
            $timeout(function() {
                var orderDropdown = $('#ordenArchivoDropdown').selectize({
                    onFocus: function() {
                        $('#ordenArchivoDropdown').parent()
                        .find('input[type=text]').prop('disabled', true);
                    }
                });
                var orderSelectize = orderDropdown[0].selectize;
                orderSelectize.setValue($scope.nroOrdenArchivo.length);
            }, 0);
            // cargar data defecto
            // estado
            $scope.infoArchivo.estado = '';
            $scope.cargarDatosColeccion(items.shownColeccion);
            // ruta isotipo yanbal
            $scope.infoArchivo.ruta_imgprevia = $scope.rutaIsotipoYanbal;
            $scope.listaUmbrales = items.listaUmbrales;
        }else if (tipoAccion === 'editar') {
            //Editar
            $scope.modalTitulo = mensajesConstants.editarArchivo.tituloPopup;
            $scope.modoVisualizacionArchivo = 2;
            $scope.disableControls = true;

            //cargar el combo
            $scope.nroOrdenArchivo = [];
            for (var j = 0; j < ordenMaximoGrilla; j++) {
                $scope.nroOrdenArchivo.push(
                    {numero: j + 1, descripcion: 'Nro de Orden ' + (j + 1)});
            }
            // valores archivo seleccionado
            $scope.maximoNroOrdenGrilla = ordenMaximoGrilla;
            $scope.cargarInformacionArchivo(items.archivoSeleccionado.Id);
            $scope.cargarInformacionColeccion(
                items.archivoSeleccionado.Coleccion);
            $scope.listaUmbrales = items.listaUmbrales;
        }else if (tipoAccion === 'ver') {
            //Ver
            $scope.modalTitulo = mensajesConstants.verArchivo.tituloPopup;
            $scope.modoVisualizacionArchivo = 3;
            $scope.disableControls = true;
            //cargar el combo
            $scope.nroOrdenArchivo = [];
            for (var j = 0; j < ordenMaximoGrilla; j++) {
                $scope.nroOrdenArchivo.push(
                    {numero: j + 1, descripcion: 'Nro de Orden ' + (j + 1)});
            }
            // valores archivo seleccionado
            $scope.cargarInformacionArchivo(items.archivoSeleccionado.Id);
            $scope.cargarInformacionColeccion(
                items.archivoSeleccionado.Coleccion);
        }

        // add placeholder for IE
        $timeout(function() {
            $('input').placeholder();
            // disable input focus for yanbal-input-archivo
            $('.yanbal-file .yanbal-input-archivo').focus(function() {
                this.blur();
            });
            $('input[type=file]').focus(function() {
                this.blur();
            });
            $('input[type=file]').attr('title', window.URL ? ' ' : '');
        }, 100);
    };

    $scope.check = function(item) {
        item.value = !item.value;
    };

    $scope.checkDescargable = function() {
        $scope.infoArchivo
        .descargable = ($scope.infoArchivo.descargable + 1) % 2;
    };

    $scope.checkDestacado = function() {
        $scope.infoArchivo
        .destacado = ($scope.infoArchivo.destacado + 1) % 2;
    };

    // validar el archivo
    var esArchivoValido = function() {
        // validar que el archivo exista
        if (!$scope.infoArchivo.fileLoad ||
            $scope.infoArchivo.fileLoad.length === 0) {
            $scope.mostrarErrorFormulario(
                mensajesConstants.cargarArchivoValidacion.errorArchivo);
            return false;
        }

        // validar el tipo de archivo permitido
        var extension = $scope.obtenerExtension(
            $scope.infoArchivo.fileLoad.name);
        var indice = $scope.extensionesValidasArchivo.indexOf(extension);
        if (indice < 0) {
            $scope.mostrarErrorFormulario(
                mensajesConstants.cargarArchivoValidacion
                .errorExtensionArchivo);
            return false;
        }

        // validar el tamanio del archivo permitido
        var tamanioArchivo = $scope.infoArchivo.fileLoad.size;
        var tamanioCargaPermitidaMB = $scope.obtenerTamanioCarga(extension);
        var tamamioArchivoMegas = $scope.obtenerFormatoTamanhoEnMegas(
            tamanioArchivo);
        if (tamamioArchivoMegas > tamanioCargaPermitidaMB) {
            $scope.mostrarErrorFormulario(
                mensajesConstants.cargarArchivoValidacion
                .errorTamanioMaximo.replace('{0}', tamanioCargaPermitidaMB));
            return false;
        }
        return true;
    };

    var esImagenValida = function() {
        // validar formato de imagen
        var preview = $scope.infoArchivo.picFile;
        if (preview) {
            var extensionPreview = $scope.obtenerExtension(preview.name);
            // validar extension
            if (extensionPreview !== $scope.extensionValidaPreview) {
                $scope.mostrarErrorFormulario(
                    mensajesConstants.cargarArchivoValidacion
                    .errorExtensionPreview);
                return false;
            }
            // validar tamanio maximo
            if (preview.size / 1024 > mensajesConstants
                .cargarArchivoValidacion.tamanhoMaximoPreviewEnKB) {
                $scope.mostrarErrorFormulario(
                    mensajesConstants.cargarArchivoValidacion
                    .errorTamanioMaximoPreview);
                return false;
            }
        }
        return true;
    };

    var datosComunesValidos = function() {
        // validar nombre, numero de orden, imagen, roles y fechas
        var nombre = $scope.validarArchivoNombre();
        if (!nombre) {
            return false;
        }
        var orden = $scope.validarArchivoNroOrden();
        if (!orden) {
            return false;
        }
        var roles = $scope.validarArchivoRoles();
        if (!roles) {
            return false;
        }
        var fecha = $scope.validarArchivoFechas();
        if (!fecha) {
            return false;
        }
        return true;
    };

    // funcion del boton Aceptar
    $scope.ok = function() {

        var jsonRequest = {
                id_coleccion: $scope.infoArchivo.id_coleccion
        };

        ColeccionService.listarArchivos(jsonRequest).then(function(data) {
            // llamar al broadcast 
            $scope.archivos = data.lista;
            $rootScope.$broadcast('recargarGrillaArchivosEnColeccion', { lista: data.lista });

            if ($scope.modoVisualizacionArchivo === 1) {
                // nuevo
                if ($scope.validarFormularioNuevo()) {
                    $scope.$parent.$broadcast('showLoadingDiv');

                    var jsonRequestNuevo = {
                            id_coleccion: $scope.infoArchivo.id_coleccion,
                            nombre: $scope.infoArchivo.nombre,
                            descripcion: $scope.infoArchivo.descripcion,
                            tamanho: $scope.infoArchivo.tamanho,
                            extension: $scope.infoArchivo.extension,
                            nro_orden: $scope.infoArchivo.nro_orden,
                            descargable: $scope.infoArchivo.descargable,
                            destacado: $scope.infoArchivo.destacado,
                            fec_inicio: $scope.obtenerStringDeDate(
                                $scope.infoArchivo.fec_inicio),
                            fec_caducidad: $scope.obtenerStringDeDate(
                                $scope.infoArchivo.fec_caducidad),
                            roles: $scope.obtenerRolesConcatenados(),
                            archivo: $scope.infoArchivo.fileLoad,
                            img_vista_previa: $scope.infoArchivo.picFile
                    };

                    ArchivoService.insertarArchivo(jsonRequestNuevo)
                    .success(function(codigoRespuesta) {
                        $scope.$parent.$broadcast('hideLoadingDiv');
                        if (responseConstants.RESPUESTA_OK === codigoRespuesta) {
                            $scope.mensajeExito();
                        } else if (
                            $scope.errorNombre === codigoRespuesta) {
                            $scope.mostrarErrorFormulario(
                                mensajesConstants.cargarArchivoValidacion
                                .errorNombreDuplicado);
                        }
                        $modalInstance.close();
                    }).error(function() {
                        $scope.$parent.$broadcast('hideLoadingDiv');
                        $modalInstance.close();
                    });
                }

            } else if ($scope.modoVisualizacionArchivo === 2) {
                // editar
                if ($scope.validarFormularioEditar()) {
                    $scope.$parent.$broadcast('showLoadingDiv');

                    var tamanhoArchivo = (0.0).toFixed(1);
                    var extensionArchivo = '';

                    if ($scope.infoArchivo.fileLoad) {
                        tamanhoArchivo = $scope.infoArchivo.tamanho;
                        extensionArchivo = $scope.infoArchivo.extension;
                    } else {
                        tamanhoArchivo = $scope.infoArchivo.temp.tamanho;
                        extensionArchivo = $scope.infoArchivo.temp.extension;
                    }

                    var jsonRequestEditar = {
                            id_archivo: $scope.infoArchivo.id_archivo,
                            nombre: $scope.infoArchivo.nombre,
                            descripcion: $scope.infoArchivo.descripcion,
                            tamanho: tamanhoArchivo,
                            extension: extensionArchivo,
                            nro_orden: $scope.infoArchivo.nro_orden,
                            descargable: $scope.infoArchivo.descargable,
                            destacado: $scope.infoArchivo.destacado,
                            fec_inicio: $scope.obtenerStringDeDate(
                                $scope.infoArchivo.fec_inicio),
                            fec_caducidad: $scope.obtenerStringDeDate(
                                $scope.infoArchivo.fec_caducidad),
                            roles: $scope.obtenerRolesConcatenados(),
                            archivo: $scope.infoArchivo.fileLoad,
                            img_vista_previa: $scope.infoArchivo.picFile
                    };

                    ArchivoService.actualizarArchivo(jsonRequestEditar)
                    .success(function(codigoRespuesta) {
                        $scope.$parent.$broadcast('hideLoadingDiv');
                        if (responseConstants.RESPUESTA_OK === codigoRespuesta) {
                            $scope.mensajeExito();
                        } else if (
                            $scope.errorNombre === codigoRespuesta) {
                            $scope.mostrarErrorFormulario(
                                mensajesConstants.cargarArchivoValidacion
                                .errorNombreDuplicado);
                        }
                        $modalInstance.close();
                    }).error(function() {
                        $scope.$parent.$broadcast('hideLoadingDiv');
                        $modalInstance.close();
                    });
                }
            }
        });

    };

    $scope.validarFormularioNuevo = function() {
        try {
            // validar archivo
            if (!esArchivoValido()) {
                return false;
            }
            $scope.infoArchivo.extension = $scope.obtenerExtension(
                $scope.infoArchivo.fileLoad.name);
            $scope.infoArchivo.tamanho = $scope.obtenerFormatoTamanhoEnMegas(
                $scope.infoArchivo.fileLoad.size).toFixed(1);
            return datosComunesValidos();
        } catch (err) {
            $scope.mostrarErrorFormulario(
                mensajesConstants.errorGenerico.mensaje);
        }
    };

    $scope.validarFormularioEditar = function() {
        try {
            // validar archivo solo si se ha seleccionado uno nuevo
            if ($scope.infoArchivo.fileLoad) {
                if (!esArchivoValido()) {
                    return false;
                }
                $scope.infoArchivo.extension = $scope.obtenerExtension(
                    $scope.infoArchivo.fileLoad.name);
                $scope.infoArchivo.tamanho = $scope
                .obtenerFormatoTamanhoEnMegas(
                    $scope.infoArchivo.fileLoad.size).toFixed(1);
            }
            return datosComunesValidos();
        } catch (err) {
            $scope.mostrarErrorFormulario(
                mensajesConstants.errorGenerico.mensaje);
        }
    };

    $scope.validarArchivoFechas = function() {

        // validar fecha inicio obligatoria
        if ($scope.obtenerStringDeDate($scope.infoArchivo.fec_inicio) === '') {
            $scope.mostrarErrorFormulario(
                mensajesConstants.cargarArchivoValidacion.errorFechaInicio);
            return false;
        }

        var fechaCaducidad = $scope.infoArchivo.fec_caducidad;
        var fechaInicio = $scope.infoArchivo.fec_inicio;
        if ($scope.obtenerStringDeDate(fechaCaducidad) !== '') {
            // validar diferencias de fechas si existe fecha de caducidad
            if (fechaInicio.getTime() > fechaCaducidad.getTime()) {
                // fechas invalidas
                $scope.mostrarErrorFormulario(
                    mensajesConstants.cargarArchivoValidacion.errorFechas);
                return false;
            }
        }

        return true;
    };

    $scope.validarArchivoNombre = function() {
        var nombre = $scope.infoArchivo.nombre;
        if (!nombre || nombre.trim().length === 0) {
            // nombre invalido
            $scope.mostrarErrorFormulario(
                mensajesConstants.cargarArchivoValidacion.errorNombre);
            return false;
        }

        var archivos = $scope.archivos; 
        if (archivos) {
            if ($scope.modoVisualizacionArchivo === 1) {
                for (var a = 0; a < archivos.length; a++) {
                    if(nombre.toUpperCase() === archivos[a].nombre.toUpperCase()) {
                        // nombre duplicado
                        $scope.mostrarErrorFormulario(
                                mensajesConstants.cargarArchivoValidacion
                                .errorNombreDuplicado);
                        return false;
                    }
                }
            } else if ($scope.modoVisualizacionArchivo === 2) {
                for (var b = 0; b < archivos.length; b++) {
                    if(nombre.toUpperCase() === archivos[b].nombre.toUpperCase() &&
                        $scope.infoArchivo.id_archivo !== archivos[b].id_archivo) {
                        // nombre duplicado
                        $scope.mostrarErrorFormulario(
                                mensajesConstants.cargarArchivoValidacion
                                .errorNombreDuplicado);
                        return false;
                    }
                }
            }
        }
        return true;
    };

    $scope.validarArchivoNroOrden = function() {
        // validar el numero de orden
        if ($scope.infoArchivo.nro_orden === '') {
            $scope.mostrarErrorFormulario(
                mensajesConstants.cargarArchivoValidacion.errorNroOrden);
            return false;
        }
        return true;
    };

    $scope.validarArchivoRoles = function() {
        if ($scope.obtenerRolesConcatenados().length === 0) {
            // roles no seleccionados
            $scope.mostrarErrorFormulario(
                mensajesConstants.cargarArchivoValidacion.errorRoles);
            return false;
        }
        return true;
    };

    $scope.mostrarErrorFormulario = function(mensajeModal) {

        var tituloModal = '';
        if ($scope.modoVisualizacionArchivo === 1) {
            tituloModal = mensajesConstants.nuevoArchivo.titulo;
        } else if ($scope.modoVisualizacionArchivo === 2) {
            tituloModal = mensajesConstants.editarArchivo.titulo;
        }

        var jsonModal = {
            titulo: tituloModal,
            mensaje: mensajeModal
        };
        modalUtil.openModal(jsonModal);

    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };

    /*datepickers*/

    $scope.today = function() {
        $scope.infoArchivo.fec_inicio = new Date();
        $scope.infoArchivo.fec_caducidad = new Date();
    };

    $scope.clear = function() {
        $scope.infoArchivo.fec_inicio = null;
        $scope.infoArchivo.fec_caducidad = null;
    };

    $scope.open = function($event, indice) {
        $event.preventDefault();
        $event.stopPropagation();

        if (indice === '0') {
            $scope.openedInicio = !$scope.openedInicio;
            $scope.openedCaducidad = false;
        } else if (indice === '1') {
            $scope.openedInicio = false;
            $scope.openedCaducidad = !$scope.openedCaducidad;
        }

    };

    $scope.cargarInformacionArchivo = function(idArchivo) {
        var jsonRequest = {
                id_archivo: idArchivo
        };
        ArchivoService.obtenerArchivo(jsonRequest).success(function(data) {
            $scope.infoArchivo = data;
            $scope.infoArchivo.temp = {
                tamanho: data.tamanho.toFixed(1),
                    extension: data.extension
            };
            // mostrar cadenas para edicion
            if ($scope.modoVisualizacionArchivo === 2) {
                $('#pathFileSelected')[0].value = mensajesConstants
                .editarArchivo.mensajeCambiarArchivo;
                $('#pathImageSelected')[0].value = mensajesConstants
                .editarArchivo.mensajeCambiarImagen;
            }

            // modificar dates
            $scope.infoArchivo.fec_inicio = $scope.obtenerDateDeString(
                data.fec_inicio);
            $scope.infoArchivo.fec_caducidad = $scope.obtenerDateDeString(
                data.fec_caducidad);
            if (data.roles.length > 0) {
                for (var i = 0; i < $scope.roles.length; i++) {
                    for (var j = 0; j < data.roles.length; j++) {
                        if ($scope.roles[i].id === data.roles[j]) {
                            $scope.roles[i].value = true;
                            break;
                        }
                    }
                }
            }

            // coloca la imagen por defecto si no hay imagen previa
            if ($scope.infoArchivo.ruta_imgprevia.length === 0) {
                $scope.infoArchivo.ruta_imgprevia = $scope.rutaIsotipoYanbal;
            }

            $timeout(function() {
                var orderDropdown = $('#ordenArchivoDropdown').selectize();
                var orderSelectize = orderDropdown[0].selectize;
                orderSelectize.setValue($scope.infoArchivo.nro_orden);
            }, 0);
        });
    };

    $scope.obtenerDateDeString = function(string) {
        if (string === '') {
            return '';
        }
        var fechaSplit = string.split('/');
        return new Date(fechaSplit[2], fechaSplit[1] - 1, fechaSplit[0]);
    };

    $scope.obtenerStringDeDate = function(date) {
        if (date === null || date === '') {
            return '';
        }
        var dateStr = $scope.padNumber(date.getDate()) +
        '/' +
        $scope.padNumber(1 + date.getMonth()) +
        '/' +
        $scope.padNumber(date.getFullYear());
        return dateStr;
    };

    $scope.padNumber = function(i) {
        return (i < 10) ? '0' + i : '' + i;
    };

    $scope.obtenerRolesConcatenados = function() {
        var roles = '';
        for (var i = 0; i < $scope.roles.length; i++) {
            if ($scope.roles[i].value) {
                roles = roles + $scope.roles[i].id + ',';
            }
        }
        if (roles.length > 0) {
            roles = roles.substring(0, roles.length - 1);
        }
        return roles;
    };

    $scope.cargarInformacionColeccion = function(idColeccion) {
        var jsonRequest = {
            id_coleccion: idColeccion
        };
        ColeccionService.obtenerColeccion(jsonRequest).success(function(data) {
            $scope.nombreColeccion = data.nombre;
            $scope.infoArchivo.id_coleccion = data.id_coleccion;
        });
    };

    $scope.cargarDatosColeccion = function(coleccion) {
        $scope.nombreColeccion = coleccion.nombre;
        $scope.infoArchivo.id_coleccion = coleccion.id_coleccion;
    };

    $scope.mensajeExito = function() {
        var tituloModal = '';
        var mensajeModal = '';

        if ($scope.modoVisualizacionArchivo === 1) {
            tituloModal = mensajesConstants.nuevoArchivo.titulo;
            mensajeModal = mensajesConstants.nuevoArchivo.mensajeExito;
        } else if ($scope.modoVisualizacionArchivo === 2) {
            tituloModal = mensajesConstants.editarArchivo.titulo;
            mensajeModal = mensajesConstants.editarArchivo.mensajeExito;
        }

        var jsonModal = {
                titulo: tituloModal,
                mensaje: mensajeModal
        };
        modalUtil.openModal(jsonModal);

    };

    // analizar valores del archivo seleccionado
    $timeout(function () {
        if ($scope.html5Complaint) {
            // No es IE
            // manejando los cambios en el input de archivo
            document.getElementById('archivoLoad').onchange = function (e) {
                 var newValue = $('#archivoLoad')[0].files;
                 if (newValue && newValue.length === 1) {
                     var filePath = newValue[0].name;
                     $scope.procesarArchivoCargado(newValue[0], filePath);
                 } 
             };

             document.getElementById('imagenLoad').onchange = function (e) {
                 var newValue = $('#imagenLoad')[0].files;
                 if (newValue && newValue.length === 1) {
                     var filePath = newValue[0].name;
                     $scope.procesarImagenCargado(newValue[0], filePath);
                 }
             };

        } else { // es IE 
            // manejando los cambios en el input de archivo
            document.getElementById('archivoLoad').onchange = function (e) {
                var myFSO = new ActiveXObject("Scripting.FileSystemObject");
                var filePath = e.currentTarget.value;
                var thefile = myFSO.getFile(filePath);
                if (thefile) {
                    $scope.procesarArchivoCargado(thefile, filePath);
                }
            };

            document.getElementById('imagenLoad').onchange = function (e) {
                var myFSO = new ActiveXObject("Scripting.FileSystemObject");
                var filePath = e.currentTarget.value;
                var thefile = myFSO.getFile(filePath);
                if (thefile) {
                    $scope.procesarImagenCargado(thefile, filePath);
                }
            };

        }
    }, 0);

    $scope.procesarImagenCargado = function (file, filePath) {
        var extensionPreview = $scope.obtenerExtension(filePath);
        var tamanioArchivo = file.size;
        var tamamioArchivoKilos = (tamanioArchivo / 1024);
        // validar extension y tamanho
        if (extensionPreview !== $scope.extensionValidaPreview) {
            file = undefined;
            $scope.mostrarErrorFormulario(
                mensajesConstants.cargarArchivoValidacion
                .errorExtensionPreview);
        } else if (tamamioArchivoKilos > mensajesConstants
            .cargarArchivoValidacion.tamanhoMaximoPreviewEnKB) {
            file = undefined;
            $scope.mostrarErrorFormulario(mensajesConstants
                .cargarArchivoValidacion.errorTamanioMaximoPreview);
        } else {
            $('#pathImageSelected')[0].value = file.name;
            if ($scope.html5Complaint) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#vistaPrevia')[0].src = e.target.result;
                };
                reader.readAsDataURL(file);
            } else {
                var filepath = "file://" + file.Path.replace(/\\/g, "/");
                $('#vistaPrevia')[0].src = filepath;
            }
            $scope.infoArchivo.picFile = file;
        }
    };
    
    $scope.procesarArchivoCargado = function (file, filePath) {
        var extension = $scope.obtenerExtension(filePath);
        var indice = $scope.extensionesValidasArchivo.indexOf(extension);
        var tamanioArchivo = file.size;
        var tamanioCargaPermitidaMB = $scope.obtenerTamanioCarga(extension);
        var tamamioArchivoMegas = $scope.obtenerFormatoTamanhoEnMegas(
            tamanioArchivo);
        if (indice < 0) {
            file = undefined;
            $scope.mostrarErrorFormulario(mensajesConstants
                .cargarArchivoValidacion.errorExtensionArchivo);
        } else if (tamamioArchivoMegas > tamanioCargaPermitidaMB) {
            file = undefined;
            $scope.mostrarErrorFormulario(mensajesConstants
                .cargarArchivoValidacion.errorTamanioMaximo
                .replace('{0}', tamanioCargaPermitidaMB));
        } else {
            $scope.fileLoad = file;
            $scope.uploadFileLoad($scope.fileLoad);
        }
    };
    
    // mostrar datos del archivo
    $scope.uploadFileLoad = function(file) {
        $scope.infoArchivo.tamanho = $scope.obtenerFormatoTamanhoEnMegas(
        file.size).toFixed(1);
        $scope.infoArchivo.extension = $scope.obtenerExtension(
        file.name);
        $scope.infoArchivo.fileLoad = file;
        $('#pathFileSelected')[0].value = file.name;
        $scope.enforcarNombre();
    };

    $scope.enforcarNombre = function () {
        $timeout(function () {
            $('#nombreArchivo').focus();
            $('#nombreArchivo').click();
        },500);
    };
    // analizar valores de la imagen previa seleccionada
    $scope.$watch('picFile', function(newValue) {
        if (newValue && newValue.length === 1) {
            var extensionPreview = $scope.obtenerExtension(newValue[0].name);
            var tamanioArchivo = newValue[0].size;
            var tamamioArchivoKilos = (tamanioArchivo / 1024);
            // validar extension y tamanho
            if (extensionPreview !== $scope.extensionValidaPreview) {
                newValue = undefined;
                $scope.mostrarErrorFormulario(
                    mensajesConstants.cargarArchivoValidacion
                    .errorExtensionPreview);
            } else if (tamamioArchivoKilos > mensajesConstants
                .cargarArchivoValidacion.tamanhoMaximoPreviewEnKB) {
                newValue = undefined;
                $scope.mostrarErrorFormulario(mensajesConstants
                    .cargarArchivoValidacion.errorTamanioMaximoPreview);
            } else {
                $('#pathImageSelected')[0].value = newValue[0].name;
                var reader = new FileReader();
                reader.onload = function(e) {
                    $('#vistaPrevia')[0].src = e.target.result;
                };
                reader.readAsDataURL(newValue[0]);
                $scope.infoArchivo.picFile = newValue[0];
            }
        }
    });

    // obtiene el tamanio del archivo en MB
    $scope.obtenerFormatoTamanhoEnMegas = function(cantidadBytes) {
        var megas = $scope.obtenerTamanhoEnMegas(cantidadBytes);
        if (megas < 0.1) {
            megas = 0.1;
        }
        return $scope.redondeo(megas, 1);
    };

    $scope.obtenerTamanhoEnMegas = function(cantidadBytes) {
        return cantidadBytes / (1024 * 1024);
    };

    $scope.obtenerExtension = function(archivo) {
        var indexPunto = archivo.lastIndexOf('.');
        return indexPunto ?
        archivo.substring(indexPunto + 1).toUpperCase() : '';
    };

    $scope.obtenerTamanioCarga = function(extension) {
        for (var i = 0; i < $scope.listaUmbrales.length; i++) {
            if ($scope.listaUmbrales[i].extension === extension) {
                return $scope.listaUmbrales[i].carga;
            }
        }
    };

    $scope.redondeo = function(num, dec) {
        var decimales = Math.pow(10, dec);
        return Math.round(num * decimales) / decimales;
    };

    $scope.init();

}]);
