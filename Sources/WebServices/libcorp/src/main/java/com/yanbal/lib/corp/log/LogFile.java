package com.yanbal.lib.corp.log;

/**
 * Clase que implementa el Log en archivo /**
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */

import com.yanbal.lib.corp.bean.EventoLog;


public class LogFile implements com.yanbal.lib.corp.interfaces.ILog {
  /*** Ruta del archivo donde se alamacena el log */
  private Object rutaArchivo;
  /*** Tama�o maximo del archivo (al alcanzar dicho tama�o se debe crear un nuevo archivo) */
  private Object tamanhoArchivo;

  /**
   * Metodo que escribe una linea de Log
   * 
   * @param linea Objeto que representa una linea de Log
   * @return
   */
  public void escribir(EventoLog linea) {

  }
}
