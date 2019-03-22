package com.yanbal.lib.corp.interfaces;

/**
 * Interfaz para el trabajo con Stored Procedures
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */

public interface IDAO {


  /**
   * Debe obtener la Base de datos de la aplicación
   */
  void obtenerBaseDatos();

  /**
   * Metodo para la ejecucion de Stored Procedures
   * 
   * @param conexion
   * @param storedProcedure
   * @param objEntrada
   * @param objSalida
   */
  public void ejecutarSP(Object conexion, Object storedProcedure, Object objEntrada,
      Object objSalida);
}
