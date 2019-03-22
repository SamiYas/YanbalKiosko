package com.yanbal.lib.corp.interfaces;

/**
 * Interfaz para la escritura de log de auditoria de accesos
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */


import com.yanbal.lib.corp.bean.EventoAuditoriaAccesos;


public interface IAuditoriaAccesos {

  /**
   * Escribe una linea de log
   * 
   * @param linea bean que representa una linea de log
   */
  public abstract void escribir(EventoAuditoriaAccesos linea);
}
