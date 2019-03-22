angular.module('YanbalKiosko')

.config(['$translateProvider', function ($translateProvider) {
    $translateProvider.translations('en', {
        LOGINCODIGO: 'Code',
        LOGINCONTRASENHA: 'Password',
        LOGINPAIS: 'Country',
        LOGININGRESAR: 'Log in',
        LOGINFALLOTITULO: 'Log in failed!',
        LOGINFALLOTEXTO: 'Incorrect code or password',
        LOGINVALIDACIONTITULO: 'Validation error',
        LOGINVALIDACIONTEXTO: 'Please insert a username, password and country',
        LOGINBOTONACEPTAR: 'Ok',
        LOGINSINCONEXION: 'Connect to the internet to continue',
        COLECCIONTITULO: 'Collections',
        COLECCIONNUEVO: 'NEW!',
        NOTIFICACIONTITULO: 'Notifications',
        CONFIGURACIONTITULO: 'Settings',
        CONFIGURACIONDESCARGAR: 'Download through the mobile network',
        CONFIGURACIONFECHA: 'Last update date',
        CONFIGURACIONCERRARSESION: 'Sign out',
        CONFIGURACIONALERTATITULO: 'Alert!',
        CONFIGURACIONALERTATEXTO: 'are you sure you want to log out?',
        CONFIGURACIONBOTONACEPTAR: 'Ok',
        CONFIGURACIONBOTONCANCELAR: 'Cancel',
        ARCHIVOABRIR: 'Open',
        ARCHIVOGUARDAR: 'Save',
        ARCHIVOELIMINAR: 'Delete',
        DESCARGAARCHIVOTITULO: 'Warning!',
        DESCARGAARCHIVOTEXTO: 'This file is heavy and you are currently using your mobile network, are you sure you want to proceed?',
        ELIMINARARCHIVOTEXTO: 'are you sure you want to delete this file?',
        DATOSNOACTIVOSTEXTOABRIR: "You can't open the file, please turn on the switch 'Download through the mobile network' on the 'Settings' option to continue.",
        DATOSNOACTIVOSTEXTOGUARDAR: "You can't save file, please turn on the switch 'Download through the mobile network' on the 'Settings' option to continue.",
        ERRORGENERICOTITULO: "An error has ocurred",
        ERRORGENERICO: "An error occurred, try again in a few minutes",
        ABRIRSINCONEXION: "Connect to the internet to open the file.",
        DESCARGARSINCONEXION: "Connect to the internet to download the file.",
        SINCONTENIDO: "Currently you have no content available",
        SINCRONIZARSININTERNET: "You must connect to the internet to sincronize",
        ARCHIVONODISPONIBLE: "This file is no longer available."
    });

}]);
