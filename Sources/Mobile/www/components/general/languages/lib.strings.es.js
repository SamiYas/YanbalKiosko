angular.module('YanbalKiosko')

.config(['$translateProvider', function ($translateProvider) {
    $translateProvider.translations('es', {
        LOGINCODIGO: 'C\u00F3digo',
        LOGINCONTRASENHA: 'Contrase\u00F1a',
        LOGINPAIS: 'Pa\u00EDs',
        LOGININGRESAR: 'Ingresar',
        LOGINFALLOTITULO: 'Inicio de Sesi\u00F3n',
        LOGINFALLOTEXTO: 'C\u00F3digo, Contrase\u00F1a o Pa\u00EDs incorrectos, por favor intente de nuevo.',
        LOGINVALIDACIONTITULO: 'Inicio de Sesi\u00F3n',
        LOGINVALIDACIONTEXTO: 'C\u00F3digo, Contrase\u00F1a o Pa\u00EDs incorrectos, por favor intente de nuevo.',
        LOGINBOTONACEPTAR: 'Aceptar',
        LOGINSINCONEXION: 'Con\u00E9ctese a Internet para continuar.',
        COLECCIONTITULO: 'Colecciones',
        COLECCIONNUEVO: '\u00A1NUEVO!',
        NOTIFICACIONTITULO: 'Notificaciones',
        CONFIGURACIONTITULO: 'Configuraci\u00F3n',
        CONFIGURACIONDESCARGAR: 'Descargar a trav\u00E9s de la red m\u00F3vil',
        CONFIGURACIONFECHA: 'Fecha de \u00FAltima sincronizaci\u00F3n',
        CONFIGURACIONCERRARSESION: 'Cerrar Sesi\u00F3n',
        CONFIGURACIONALERTATITULO: 'Alerta!',
        CONFIGURACIONALERTATEXTO: 'Si cierra la sesi\u00F3n se perder\u00E1 toda la informaci\u00F3n guardada. \u00BFDesea continuar?',
        CONFIGURACIONBOTONACEPTAR: 'Aceptar',
        CONFIGURACIONBOTONCANCELAR: 'Cancelar',
        ARCHIVOABRIR: 'Abrir',
        ARCHIVOGUARDAR: 'Guardar',
        ARCHIVOELIMINAR: 'Eliminar',
        DESCARGAARCHIVOTITULO: 'Cuidado!',
        DESCARGAARCHIVOTEXTO: 'Este archivo es pesado y se encuentra usando su plan de datos m\u00F3vil, \u00BFdesea continuar?',
        ELIMINARARCHIVOTEXTO: '\u00BFEst\u00E1 seguro de eliminar el archivo?',
        DATOSNOACTIVOSTEXTOABRIR: 'No puede abrir archivo, active la opci\u00F3n "Descarga a trav\u00E9s del m\u00F3vil" en la opci\u00F3n "Configuraci\u00F3n" para continuar.',
        DATOSNOACTIVOSTEXTOGUARDAR: 'No puede guardar archivo, active la opci\u00F3n "Descarga a trav\u00E9s del m\u00F3vil" en la opci\u00F3n "Configuraci\u00F3n" para continuar.',
        ERRORGENERICOTITULO: "Ha ocurrido un error",
        ERRORGENERICO: "Ocurri\u00F3 un error, vuelve a intentarlo en unos minutos.",
        ABRIRSINCONEXION: "Con\u00E9ctese a Internet para abrir el archivo.",
        DESCARGARSINCONEXION: "Con\u00E9ctese a Internet para guardar el archivo.",
        SINCONTENIDO: "Por el momento no tiene contenido disponible.",
        SINCRONIZARSININTERNET: "Debe conectarse a Internet para sincronizar.",
        ARCHIVONODISPONIBLE: "Este archivo ya no esta disponible."
    });
    $translateProvider.preferredLanguage('es');

}]);
