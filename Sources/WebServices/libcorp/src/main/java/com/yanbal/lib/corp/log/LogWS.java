package com.yanbal.lib.corp.log;

/**
 * Clase que implementa el Log a travez de un Web Service /**
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */


import com.yanbal.lib.corp.bean.EventoLog;


public class LogWS implements com.yanbal.lib.corp.interfaces.ILog {

  /** Direccion URL del servicio web de Log */
  private Object direccionURL;

  /**
   * Metodo que escribe una linea de Log
   * 
   * @param linea Objeto que representa una linea de Log
   * @return
   */
  public void escribir(EventoLog linea) {

  }


}
