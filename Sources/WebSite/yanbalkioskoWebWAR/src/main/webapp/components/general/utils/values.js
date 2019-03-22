angular.module('app.values', [])

.value('tramaYanbal', {
    IntegracionWSReq: {
        Cabecera: {
            CodigoInterfaz: 'CVALUSR',
            UsuarioAplicacion: '',
            CodigoAplicacion: 'KIOSKO',
            CodigoPais: '',
            CodigosPaisOD: {
                CodigoPaisOD: {
                    Valor: ''
                }
            }
        },
        Detalle: {
            Parametros: {
                TipoUsuario: '',
                Usuario: '',
                Password: ''
            }
        }
    }
})
.value('tramaIniciarSesion', {
    Header: {
        'ApplicationId': 'KIOSKO2WEB',
        'ServiceName': 'registrarsesion'
    },
    Body: {
        Request: {
            usuario: '',
            clave: '',
            pais: '',
            rol: ''
        }
    }

})
.value('tramaObtenerUsuarioToken', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'obtenerUsuarioToken'
    },
    Body: {
        Request: {
            token: ''
        }
    }
})
.value('tramaListarUmbrales', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'listarUmbrales'
    },
    Body: {
        Request: {
            token: ''
        }
    }
})
.value('tramaObtenerUmbral', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'obtenerUmbral'
    },
    Body: {
        Request: {
            token: '',
            id: ''
        }
    }
})
.value('tramaActualizarUmbral', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'actualizarUmbral'
    },
    Body: {
        Request: {
            token: '',
            id: '',
            extension: '',
            descripcion: '',
            carga: '',
            descarga: ''
        }
    }
})
.value('tramaEliminarColeccion', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'eliminarColeccion'
    },
    Body: {
        Request: {
            token: '',
            id_coleccion: ''
        }
    }
})
.value('tramaObtenerRutaOrigen', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'obtenerRutaOrigen'
    },
    Body: {
        Request: {
            token: '',
            id: ''
        }
    }
})
.value('tramaListarColecciones', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'listarColecciones'
    },
    Body: {
        Request: {
            token: '',
            id_coleccion_padre: ''
        }
    }
})
.value('tramaListarArchivos', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'listarColecciones'
    },
    Body: {
        Request: {
            token: '',
            id_coleccion: '',
            nombre: '',
            descripcion: ''
        }
    }
})
.value('tramaActualizarPais', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'actualizarPais'
    },
    Body: {
        Request: {
            token: ''
        }
    }
})
.value('tramaCerrarSesion', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'cerrarsesion'
    },
    Body: {
        Request: {
            token: ''
        }
    }
})
.value('tramaObtenerPorNotificar', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'obtenerPorNotificar'
    },
    Body: {
        Request: {
            token: '',
            pais: ''
        }
    }
})
.value('tramaEnviarNotificacionPush', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'enviarNotificacionPush'
    },
    Body: {
        Request: {
            token: '',
            mensaje: '',
            pais: ''
        }
    }
})
.value('tramaObtenerColeccion', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'obtenerColeccion'
    },
    Body: {
        Request: {
            token: '',
            id_coleccion: ''
        }
    }
})
.value('tramaInsertarColeccion', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'insertarColeccion'
    },
    Body: {
        Request: {
            token: '',
            id_coleccion_padre: '',
            id_pais: '',
            id_rol: '',
            nombre: '',
            nivel: '',
            orden: '',
            color: '',
            descripcion: ''
        }
    }
})
.value('tramaActualizarColeccion', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'actualizarColeccion'
    },
    Body: {
        Request: {
            token: '',
            id_coleccion: '',
            id_coleccion_padre: '',
            id_pais: '',
            id_rol: '',
            nombre: '',
            nivel: '',
            orden: '',
            color: '',
            descripcion: ''
        }
    }
})
.value('tramaInsertarArchivo', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'insertarArchivo'
    },
    Body: {
        Request: {
            token: '',
            id_coleccion: '',
            nombre: '',
            descripcion: '',
            tamanho: '',
            extension: '',
            nro_orden: '',
            descargable: '',
            destacado: '',
            fec_inicio: '',
            fec_caducidad: ''
        }
    }
})
.value('tramaMoverArchivo', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'moverArchivo'
    },
    Body: {
        Request: {
            token: '',
            id_archivo: '',
            id_coleccion: ''
        }
    }
})
.value('tramaObtenerArchivo', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'obtenerArchivo'
    },
    Body: {
        Request: {
            token: '',
            id_archivo: ''
        }
    }
})
.value('tramaActualizarArchivo', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'actualizarArchivo'
    },
    Body: {
        Request: {
            token: '',
            id_archivo: '',
            nombre: '',
            descripcion: '',
            nro_orden: '',
            descargable: '',
            destacado: '',
            fec_inicio: '',
            fec_caducidad: ''
        }
    }
})
.value('tramaEliminarArchivos', {
    Header: {
        ApplicationId: 'KIOSKO2WEB',
        ServiceName: 'eliminarArchivo'
    },
    Body: {
        Request: {
            token: '',
            ids_archivo: ''
        }
    }
});
