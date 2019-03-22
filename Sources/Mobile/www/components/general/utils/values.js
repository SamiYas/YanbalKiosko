angular.module('YanbalKiosko.values', [])

.value('tramaYambal', {
    IntegracionWSReq: {
        Cabecera: {
            CodigoInterfaz: "CVALUSR",
            UsuarioAplicacion: "",
            CodigoAplicacion: "KIOSKO",
            CodigoPais: "",
            CodigosPaisOD: {
                CodigoPaisOD: {
                    Valor: ""
                }
            }
        },
        Detalle: {
            Parametros: {
                TipoUsuario: "2",
                Usuario: "",
                Password: ""
            }
        }
    }
})
.value('tramaRegistrarSesion', {
    Header: {
        ApplicationId: "KIOSKO2MOVIL",
        ServiceName: "RegistrarSesion"
    },
    Body: {
        Request: {
            usuario: "",
            notificacion_id: "",
            pais: 1,
            dispositivo_id: "",
            dispositivo_so: "",
            clave: ""
        }
    }
})
.value('tramaCerrarSesion', {
    Header: {
        ApplicationId: "KIOSKO2MOVIL",
        ServiceName: "CerrarSesion"
    },
    Body: {
        Request: {
            token: ""
        }
    }
})
.value('tramaObtenerColeccionesArchivos', {
    Header: {
        ApplicationId: "KIOSKO2MOVIL",
        ServiceName: "ObtenerColeccionesArchivos"
    },
    Body: {
        Request: {
            token: "",
            pais: "",
            rol: "",
            fecha_sincronizacion: ""
        }
    }
})
.value('tramaEnviarBitacora', {
    Header: {
        ApplicationId: "KIOSKO2MOVIL",
        ServiceName: "enviarBitacora"
    },
    Body: {
        Request: {
            token: ""
        }
    }
})
.value('tramaValidarArchivoRol', {
    Header: {
        ApplicationId: "KIOSKO2MOVIL",
        ServiceName: "validarArchivoRol"
    },
    Body: {
        Request: {
            id_archivo: 0,
            token: "",
            rol: ""
        }
    }
})
.value('androidConfig', {
    "senderID": "240781730165"
})
.value('iosConfig', {
    "badge": true,
    "sound": true,
    "alert": true
});