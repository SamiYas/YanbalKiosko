package com.yanbal.lib.corp.auditoria;

import com.yanbal.lib.corp.bean.EventoAuditoriaAccesos;


/**
 * Implementación de la auditoria de accesos mediante base de datos
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */
public class AudAccesosDB implements com.yanbal.lib.corp.interfaces.IAuditoriaAccesos {
  /** Objeto de conexcion a la BD */
  public Object conexionDB;

  /**
   * Escribe una linea de log
   * 
   * @param linea bean que representa una linea de log
   */
  public void escribir(EventoAuditoriaAccesos linea) {

  }


}
