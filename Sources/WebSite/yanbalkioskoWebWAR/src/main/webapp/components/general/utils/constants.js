angular.module('app.constants', [])

.constant('serviceUrlConstants', {
	//"urlServidor": "http://192.168.4.102:9081/test", //--TEST
	//"urlServidor": "http://201.234.55.22:9082/kioskows", //--YANBAL
	//"urlServidor": "http://201.234.55.22:9082/YanbalKioskoServicesDev", //--DEV LINUX EXT
	//"urlServidor": "http://192.168.4.102:9081/YanbalKioskoServicesDev", //--DEV LINUX INTERNA
	"urlServidor": "http://201.234.55.22:9082/YanbalKioskoServices", //--->QA	LINUX EXT
	//"urlServidor": "http://192.168.4.102:9081/YanbalKioskoServicesQA", //--->QA	LINUX INTERNA	
    'urlServidorYambal': 'http://proxynp.unique-yanbal.com',
    'urlRegistrarSesion': '/web/registrarsesion',
    'urlListarPaises': '/web/listarPaises',
    'urlListarUmbrales': '/web/listarUmbrales',
    'urlObtenerUmbral': '/web/obtenerUmbral',
    'urlActualizarUmbral': '/web/actualizarUmbral',
    'urlObtenerRutaOrigen': '/web/obtenerRutaOrigen',
    'urlListarColecciones': '/web/listarColecciones',
    'urlInsertarColeccion': '/web/insertarColeccion',
    'urlActualizarColeccion': '/web/actualizarColeccion',
    'urlEliminarColeccion': '/web/eliminarColeccion',
    'urlObtenerColeccion': '/web/obtenerColeccion',
    'urlListarArchivos': '/web/listarArchivos',
    'urlActualizarPais': '/web/actualizarPais',
    'urlYanbalValidarSesion' : '/web/obtenerDatosUsuario',
    'urlObtenerPorNotificar' : '/web/obtenerPorNotificar',
    'urlCerrarSesion': '/web/cerrarsesion',
    'urlMoverArchivo': '/web/moverArchivo',
    'urlObtenerArchivo': '/web/obtenerArchivo',
    'urlActualizarArchivo': '/web/actualizarArchivo',
    'urlInsertarArchivo': '/web/insertarArchivo',
    'urlEnviarNotificacionPush': '/web/enviarNotificacionPush',
    'urlEliminarArchivos': '/web/eliminarArchivos',
    'urlObtenerUsuarioToken': '/web/obtenerUsuarioToken'
})

.constant('responseConstants', {
    'HTTP_STATUS_OK': 'HttpStatus.OK',
    'RESPUESTA_OK' : '0',
    'RESPUESTA_LOGIN_INVALIDO': '3',
    'RESPUESTA_OK_YANBAL' : '0000',
    'FLAG_VALID_OK_YANBAL': '1',
    'RESPUESTA_ERROR_PRIVILEGIOS' : '6',
    'RESPUESTA_ERROR_NOMBRE_DUPLICADO' : '7'
})

.constant('sesionConstants', {
    'LOGIN_DATA': 'loginData',
    'SESION_DATA': 'datosUsuario',
    'SESION_PAIS': 'sesionPais',
    'TIPO_USER': 'tipoUser',
    'ROL_USER': 'rolUser',
    'ID_ROL_USER': 'idRolUser',
    //1 es tipo Staff y 2 fuerza de ventas
    'TIPO_USER_STAFF': '1',
    //Codigo para el rol Corporativo
    'ROL_USER_CORP': '2',
    //Codigo para el rol Unidad de Negocio
    'ROL_USER_UN': '3',
    //Id del correlativo del rol para el Rol Corportivo
    'ID_ROL_USER_CORP': 6,
    //Id del correlativo pais para CORPORATIVO
    'CODIGO_CORP': 6,
    //Id del correlativo pais por defecto(Peru)
    'CODIGO_PAIS_DEFAULT': 1,
    //Expiracion expresado en numero de dias
    'EXPIRACION': 1 ,
    'PERFIL_CORP' : 'kioscoAdminCorp',
    'PERFIL_UN' : 'kioscoAdminUN'
})
.constant('coloresConstants', {
    color1: {
        hexa: '#eb6d30',
        texto: 'Naranja'
    },
    color2: {
        hexa: '#ff6666',
        texto: 'Salmon'
    },
    color3: {
        hexa: '#9966cc',
        texto: 'Morado'
    },
    color4: {
        hexa: '#3366cc',
        texto: 'Azul'
    },
    color5: {
        hexa: '#33cccc',
        texto: 'Turquesa'
    }
})
.constant('mensajesConstants', {
    indicadorVersion : "KIOSKO 2.1.1",
    general: {
        aceptar: 'Aceptar',
        cancelar: 'Cancelar',
        si: 'S\u00ED',
        no: 'No',
        size: 400
    },
    errorGenerico: {
        titulo: 'Error',
        mensaje: 'Ocurri\u00f3 un error, vuelve a intentarlo en unos minutos.',
        boton: 'Aceptar'
    },
    editarUmbral: {
        size: 400,
        titulo: 'Editar umbral de carga y descarga',
        boton: 'Aceptar'
    },
    umbralMensajeActualizacion: {
        size: 400,
        titulo: '\u00C9xito',
        mensaje: 'Se ha editado la configuraci\u00f3n seleccionada con \u00e9xito.',
        boton: 'Aceptar'
    },
    umbralMensajeErrorNoEditable: {
        size: 400,
        titulo: 'Error',
        mensaje: 'Usted no tiene privilegios para editar el umbral seleccionado.',
        boton: 'Aceptar'
    },
    umbralMensajeErrorActualizacion: {
        size: 400,
        titulo: 'Error',
        mensaje: 'Usted no tiene privilegios para editar el umbral seleccionado.',
        boton: 'Aceptar'
    },
    umbralValidacion: {
        size: 400,
        titulo: 'Error',
        mensajeErrorCarga: 'Debe ingresar el umbral m\u00E1ximo en carga.'
    },
    loginMensajeError: {
        size: 400,
        titulo: 'Inicio de Sesi\u00f3n',
        mensaje: 'Usuario, Contrase\u00F1a o Pa\u00eds incorrectos, por favor intente de nuevo.',
        boton: 'Aceptar'
    },
    enviarNotificacion: {
        size: 400,
        titulo: 'Notificaci\u00F3n',
        mensaje: 'Se ha enviado notificaciones a los dispositivos m\u00F3viles.'
    },
    editarArchivo: {
        size: 400,
        titulo: 'Editar Archivo',
        tituloPopup: 'Editar Archivo',
        mensajeExito: 'Se ha editado el archivo seleccionado con \u00e9xito.',
        mensajeErrorPrivilegios: 'Usted no tiene los permisos para editar el archivo seleccionado.',
        mensajeCambiarArchivo: 'Si desea cambiar el archivo, seleccione la lupa.',
        mensajeCambiarImagen: 'Si desea cambiar la imagen, seleccione la lupa.'
    },
    eliminarArchivos: {
        size: 400,
        titulo: 'Eliminar Archivo',
        mensajeExito: 'Se ha eliminado el(los) archivo(s) seleccionado(s) con \u00e9xito.',
        mensajeErrorPrivilegios: 'Usted no tiene los permisos para eliminar el(los) siguiente(s) archivo(s) seleccionado(s):\n',
        mensajeConfirmacion: '\u00BFEst\u00E1 seguro de eliminar el(los) archivo(s) seleccionado(s)?'
    },
    verColeccion: {
        size: 400,
        titulo: 'Detalle de Colecci\u00F3n',
        mensajeErrorPrivilegios: 'Usted no tiene permisos para editar la Colecci\u00F3n seleccionada.'
    },
    nuevaColeccion: {
        size: 400,
        titulo: 'Crear Colecci\u00F3n',
        mensajeErrorPrivilegios: 'Usted no tiene permisos para editar la Colecci\u00F3n seleccionada.'
    },
    editarColeccion: {
        size: 400,
        titulo: 'Editar Colecci\u00F3n',
        mensajeErrorPrivilegios: 'Usted no tiene permisos para editar la Colecci\u00F3n seleccionada.'
    },
    eliminarColeccion: {
        size: 400,
        titulo: 'Eliminar Colecci\u00F3n',
        mensajeErrorPrivilegios: 'Usted no tiene permisos para eliminar la Colecci\u00F3n seleccionada.'
    },
    nuevoArchivo: {
        size: 400,
        titulo: 'Cargar Archivo',
        tituloPopup: 'Cargar Archivo',
        mensajeExito: 'Se ha cargado un nuevo archivo con \u00e9xito.'
    },
    verArchivo: {
        tituloPopup: 'Detalle de Archivo'
    },
    moverArchivo: {
        size: 400,
        titulo: 'Mover Archivo',
        mensajeExito: 'Se ha movido el archivo exitosamente.',
        mensajeErrorPrivilegios: 'Usted no tiene los permisos para mover el archivo seleccionado.',
        mensajeErrorNombreDuplicado: 'Ya existe un archivo con el mismo nombre en colecci\u00F3n seleccionada.',
        mensajeErrorColeccionDestino: 'No se puede mover archivo a la misma colecci\u00F3n.'
    },
    cargarArchivoValidacion: {
        size: 400,
        errorRoles: 'Debe seleccionar al menos un rol para continuar.',
        errorFechas: 'Rango de fecha no v\u00E1lida, revisar.',
        errorTamanioMaximo: 'El archivo excede el tama\u00F1o permitido, por favor intente de nuevo. Umbral para este tipo de archivos: {0} MB.',
        errorNombre: 'Debe ingresar el nombre.',
        errorFechaInicio: 'Debe ingresar la fecha de inicio.',
        errorArchivo: 'Debe seleccionar un archivo.',
        errorExtensionArchivo: 'Formato de archivo no v\u00E1lido, solo est\u00E1 permitido: jpg, pdf, mp3, mp4.',
        errorExtensionPreview: 'Formato de archivo no v\u00E1lido, solo est\u00E1 permitido: jpg.',
        errorNroOrden: 'Debe ingresar un n\u00FAmero de orden v\u00E1lido.',
        errorNombreDuplicado: 'Ya existe un archivo con el mismo nombre.',
        tamanhoMaximoPreviewEnKB: 10,
        leyendaVistaPrevia: 'Tama\u00F1o de archivo no mayor a {0} Kb',
        errorTamanioMaximoPreview: 'El archivo de vista previa excede el tama\u00F1o permitido, por favor intente de nuevo.'
    },
    coleccionNoEliminada: {
        titulo: 'Error',
        mensajeError: 'No se puede eliminar la Colecci\u00F3n seleccionada, la Colecci\u00F3n no est\u00E1 vac\u00EDa. Primero elimine el contenido e intente nuevamente.',
        botonAceptar: 'Aceptar'
    },
    coleccionConfirmacionEliminar: {
        titulo: 'Eliminar Colecci\u00F3n',
        mensaje: '\u00BFEst\u00E1 seguro de eliminar la colecci\u00F3n seleccionada?',
        botonConfirmar: 'S\u00ed',
        botonCancelar: 'No',
        size: 400,
        mensajeExito: 'Se ha eliminado la Colecci\u00F3n seleccionada con \u00e9xito.'
    },
    coleccionInsercion: {
        titulo: '\u00C9xito',
        mensaje: 'Se ha creado la nueva Colecci\u00f3n con \u00e9xito.',
        botonAceptar: 'Aceptar'
    },
    coleccionActualizacion: {
        titulo: '\u00C9xito',
        mensaje: 'Se ha editado la Colecci\u00f3n seleccionada con \u00e9xito.',
        botonAceptar: 'Aceptar'
    },
    coleccionDatosValidacion: {
        size: 400,
        tituloNuevo: 'Crear Colecci\u00f3n',
        tituloEditar: 'Editar Colecci\u00f3n',
        errorNombre: 'Debe ingresar un nombre.',
        errorNroOrden: 'Debe ingresar un n\u00FAmero de orden v\u00E1lido.',
        errorColor: 'Debe seleccionar un color.',
        errorNombreDuplicado: 'Ya existe una colecci\u00f3n con el mismo nombre.'
    },
    archivoConfirmacionEliminar: {
        titulo: 'Eliminar Archivo',
        mensaje: '\u00BFEst\u00E1 seguro de eliminar el archivo seleccionado?',
        botonConfirmar: 'S\u00ed',
        botonCancelar: 'No',
        size: 400,
        mensajeExito: 'Se ha eliminado el archivo seleccionada con \u00e9xito.'
    }
})

.constant('gridEtiquetasConstants', {
    gridUmbrales: {
        title: 'Administraci\u00f3n de umbrales',
        fieldExtension: 'Extensi\u00f3n',
        fieldDescripcion: 'Descripci\u00f3n',
        fieldCarga: 'Umbral M\u00E1x. Carga',
        fieldDescarga: 'Umbral M\u00E1x. Descarga',
        fieldEditar: 'Editar'
    }
})

.constant('stringConstants', {
    raiz: 'Home'
})

.constant('roles', [
        {
            id: '2',
            text: 'Consultora'
        }, {
            id: '3',
            text: 'Estrella'
        }, {
            id: '4',
            text: 'Staff'
        }, {
            id: '5',
            text: 'Aspirante'
        }, {
            id: '1',
            text: 'Directora'
        }
])

.constant('navConstants', {
    lista: [
            {
                text: 'Administraci\u00f3n de colecciones',
                sref: 'colecciones',
                url: '/coleccion',
                'class': 'adm-coleccion'
            },
                {
                    text: 'Configuraci\u00f3n de umbrales',
                    sref: 'umbrales',
                    url: '/umbral',
                    'class': 'config-umbral'
                }
            ]
});
