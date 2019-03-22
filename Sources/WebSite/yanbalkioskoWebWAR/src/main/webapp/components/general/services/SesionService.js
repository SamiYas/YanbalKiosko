angular.module('app.services')

.service('SesionService',
    ['localStorageService', 'sesionConstants',
    function(localStorageService, sesionConstants) {

    return {
        loginUsername: function() {
            if (localStorageService.get(sesionConstants.LOGIN_DATA) &&
                localStorageService.get(sesionConstants.LOGIN_DATA).username) {
                return localStorageService.get(sesionConstants.LOGIN_DATA).username;
            }
            return '';
        },
        loginPassword: function() {
            if (localStorageService.get(sesionConstants.LOGIN_DATA) &&
                localStorageService.get(sesionConstants.LOGIN_DATA).password) {
                return localStorageService.get(sesionConstants.LOGIN_DATA).password;
            }
            return '';
        },
        loginPais: function() {
            if (localStorageService.get(sesionConstants.LOGIN_DATA) &&
                localStorageService.get(sesionConstants.LOGIN_DATA).country) {
                return localStorageService.get(sesionConstants.LOGIN_DATA).country;
            }
            return '';
        },
        token: function() {
            return localStorageService.get(sesionConstants.SESION_DATA).token;
        },
        pais: function() {
            return localStorageService.get(sesionConstants.SESION_PAIS);
        },
        paisCodigo: function() {
            return localStorageService.get(sesionConstants.SESION_PAIS).codigo;
        },
        paisId: function() {
            return localStorageService.get(sesionConstants.SESION_PAIS).id;
        },
        rol: function() {
            return localStorageService.get(sesionConstants.ROL_USER);
        },
        clearAll: function() {
            return localStorageService.clearAll();
        },
        estaAutenticado: function() {
            return localStorageService.get(sesionConstants.SESION_DATA);
        },
        esUsuarioRolCorporativo: function() {
            return localStorageService.get(sesionConstants.SESION_DATA) &&
            localStorageService.get(sesionConstants.SESION_DATA).resultado &&
            localStorageService.get(sesionConstants.SESION_DATA).resultado
            .rol === sesionConstants.ID_ROL_USER_CORP;
        },
        setValor: function(key, valor) {
            localStorageService.set(key, valor);
        }
    };
}]);
