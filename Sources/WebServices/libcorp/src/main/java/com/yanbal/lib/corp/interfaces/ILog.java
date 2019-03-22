package com.yanbal.lib.corp.interfaces;

/**
 * Interfaz para la gestion de Log de Errores
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */

import com.yanbal.lib.corp.bean.EventoLog;


public interface ILog {

  /**
   * Escribe una linea de log
   * 
   * @param linea bean que representa una linea de log
   */
  public void escribir(EventoLog linea);
}
