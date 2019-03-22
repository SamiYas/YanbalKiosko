package com.yanbal.kiosko.common.core.enumeration;

/**
 * Enum para la lista de servicios del backend
 */
public enum Servicio {

  OBTENER_COLECCIONES_ARCHIVOS("obtenerColeccionesArchivos"), 
  LISTAR_COLECCIONES("listarColecciones"), 
  INSERTAR_COLECCION("insertarColeccion"),
  ELIMINAR_COLECCION("eliminarColeccion"),
  OBTENER_COLECCION("obtenerColeccion"),
  ACTUALIZAR_COLECCION("actualizarColeccion"),
  LISTAR_ARCHIVOS("listarArchivos"),
  OBTENER_ARCHIVO("obtenerArchivo"),
  ACTUALIZAR_ARCHIVO("actualizarArchivo"),  
  MOVER_ARCHIVO("moverArchivo"),  
  INSERTAR_ARCHIVO("insertarArchivo"),
  ELIMINAR_ARCHIVOS("eliminarArchivos"),
  OBTENER_RUTA_ORIGEN("obtenerRutaOrigen"),
  ENVIAR_NOTIFICACION_PUSH("enviarNotificacionPush"),
  OBTENER_POR_NOTIFICAR("obtenerPorNotificar"),
  LISTAR_UMBRALES("listarUmbrales"),
  OBTENER_UMBRAL("obtenerUmbral"),
  ACTUALIZAR_UMBRAL("actualizarUmbral"),
  LISTAR_ROLES("listarRoles"),
  LISTAR_PAISES("listarPaises"),
  ACTUALIZAR_PAIS("actualizarPais"),
  REGISTRAR_BITACORA("registrarBitacora"),
  REGISTRAR_SESION_MOVIL("registrarSesionMovil"),
  REGISTRAR_SESION_WEB("registrarSesionWeb"),
  CERRAR_SESION_WEB("cerrarSesionWeb"),
  CERRAR_SESION_MOVIL("cerrarSesionMovil"), 
  OBTENER_DATOS_USUARIO("obtenerDatosUsuario"),
  OBTENER_USUARIO_TOKEN("obtenerUsuarioToken"),
  VALIDAR_ARCHIVO_ROL("validarArchivoRol");

  private String nombre;
  
  Servicio(String nombre) {
    this.nombre = nombre;
  }

  public String getNombre() {
    return nombre;
  }

}
