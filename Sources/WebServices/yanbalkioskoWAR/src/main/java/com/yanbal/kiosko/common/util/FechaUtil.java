package com.yanbal.kiosko.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.yanbal.kiosko.aplicacion.Application;
import com.yanbal.kiosko.common.log.EventoLogBuilder;
import com.yanbal.kiosko.entities.Usuario;

/**
 * Clase utilitaria para conversion y manejo de fechas
 * 
 * @author alex.contreras
 * 
 */
public class FechaUtil {

  public static final String FORMATO_FECHA_HORA_MILLIS = "dd/MM/yyyy HH:mm:ss:SSS";
  public static final String FORMATO_FECHA_HORA = "dd/MM/yyyy HH:mm:ss";
  public static final String FORMATO_FECHA = "dd/MM/yyyy";

  private FechaUtil() {}

  /**
   * Devuelve la fecha con formato dd/MM/yyyy
   * 
   * @param fecha en Date
   * @return fecha y hora con formato dd/MM/yyyy
   */
  public static String getFechaFormatoFecha(final Date fecha) {
    return getFechaConFormato(fecha, FORMATO_FECHA);
  }


  /**
   * Devuelve la fecha con formato dd/MM/yyyy HH:mm:ss
   * 
   * @param fecha en Date
   * @return fecha y hora con formato dd/MM/yyyy HH:mm:ss
   */
  public static String getFechaFormatoFechaYHora(final Date fecha) {
    return getFechaConFormato(fecha, FORMATO_FECHA_HORA);
  }

  /**
   * Devuelve la fecha con formato dd/MM/yyyy HH:mm:ss,SSS
   * 
   * @param fecha en Date
   * @return fecha y hora con formato dd/MM/yyyy HH:mm:ss,SSS
   */
  public static String getFechaFormatoFechaHoraYMillis(final Date fecha) {
    return getFechaConFormato(fecha, FORMATO_FECHA_HORA_MILLIS);
  }

  /**
   * Devuelve la fecha con formato segun parametro
   * 
   * @param fecha en Date
   * @return fecha y hora con formato segun parametro
   */
  private static String getFechaConFormato(final Date fecha, final String formato) {
    String fechaFormato = "";
    if (fecha != null) {
      final SimpleDateFormat horaSdf = new SimpleDateFormat(formato);
      fechaFormato = horaSdf.format(fecha);
    }
    return fechaFormato;
  }

  /**
   * Metodo que retorna la fecha con el formato dd/MM/yyyy
   * 
   * @param fecha: fecha que se desea formatear
   * @return valor de fecha formateada
   * @throws Exception
   */
  public static Date getFechaFormatoFecha(final String fecha) {
    return getFechaConFormato(fecha, FORMATO_FECHA);
  }

  /**
   * Metodo que retorna la fecha con el formato dd/MM/yyyy HH:mm:ss
   * 
   * @param fecha: fecha que se desea formatear
   * @return valor de fecha formateada
   * @throws Exception
   */
  public static Date getFechaFormatoFechaYHora(final String fecha) {
    return getFechaConFormato(fecha, FORMATO_FECHA_HORA);
  }

  /**
   * Devuelve un objeto Date, el parametro es de la forma que se ingrese en el parametro formato
   */
  private static Date getFechaConFormato(final String fecha, final String formato) {
    if (fecha == null || fecha.trim().isEmpty()) {
      return null;
    }
    Date fechaFormateada = new Date();
    final SimpleDateFormat fechaSdf = new SimpleDateFormat(formato);
    try {
      fechaFormateada = fechaSdf.parse(fecha);
    } catch (final ParseException e) {
      Usuario usuario = new Usuario();
      usuario.setNombreUsuario(Application.USUARIO_SISTEMA);
      EventoLogBuilder.obtenerEvento(FechaUtil.class.getClass(), e, usuario);
      fechaFormateada = null;
    }
    return fechaFormateada;
  }

  /**
   * Metodo que retorna la fecha actual
   * 
   * @return fecha actual
   */
  public static Date getFechaActual() {
    return new Date();
  }

}
