package com.yanbal.kiosko.common.log;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ibm.icu.text.SimpleDateFormat;
import com.yanbal.kiosko.aplicacion.Application;
import com.yanbal.kiosko.common.core.enumeration.CodigosError;
import com.yanbal.kiosko.entities.Usuario;
import com.yanbal.lib.corp.bean.EventoLog;
import com.yanbal.lib.corp.log.LogDB;

/**
 * Clase que gestiona objetos de la clase Evento Log
 * 
 * @author alex.contreras
 *
 */
public class EventoLogBuilder {

  /**
   * Constructor de clase vacio por defecto.
   */
  private EventoLogBuilder() {
  }

  /**
   * Envia el detalle de la excepcion a la libreria corporativa, setea los parametros que la lib
   * corp solicita.
   * 
   * @param classobject Clase donde se produce el error
   * @param ex Clase de la excepcion
   * @param obj Clase que representa a la entidad de usuarios
   * 
   *        Cabe indicar que aun se encuentra en desarrollo y por lo tanto existen parametros que se
   *        estan inicializando con algun valor por defecto.
   */
  public static EventoLog obtenerEvento(Class classobject, Exception ex, Usuario usuario) {
    ex.printStackTrace();
    EventoLog event = new EventoLog();
    event.setAplicacion(Application.KIOSKO2WEB);
    event.setClase(classobject.getSimpleName());
    event.setExcepcion(ex.toString());
    event.setMensaje(getMensajeConFecha(ex.getMessage()));
    event.setNivelError(CodigosError.ERROR_KIOSKO.toString());
    event.setUsuario(obtenerUsuario(usuario));
    event.setDireccionIP(Application.DIRECCION_IP_HOST);
    event.setRefObjPrincipal("");
    event.setPila(ex.toString());
    new LogDB().escribir(event);
    return event;
  }

  /**
   * Obtiene el usuario que genera la excepcion
   * @param usuario usuario
   * @return nombre usuario
   */
  private static Object obtenerUsuario(Usuario usuario) {
    if(usuario != null && StringUtils.isNotBlank(usuario.getNombreUsuario())){
      return usuario.getNombreUsuario();
    } else {
      return Application.USUARIO_SISTEMA;
    }
}

/**
   * Metodo estatico que retorna una cadena con la hora y fecha y el mensaje del evento
   * 
   * @param mensaje: se le ajuntara la hora y fecha del evento al inicio de la cadena
   * @return valor cadena con el mensaje y la fecha
   */
  public static String getMensajeConFecha(String mensaje) {
    StringBuilder builder = new StringBuilder();

    Date fechaActual = new Date();
    final SimpleDateFormat horaSdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String fecha = horaSdf.format(fechaActual);

    builder.append(fecha).append(" : ").append(mensaje);
    return builder.toString();
  }

}
