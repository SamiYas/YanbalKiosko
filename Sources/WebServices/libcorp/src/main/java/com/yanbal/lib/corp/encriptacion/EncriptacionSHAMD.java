package com.yanbal.lib.corp.encriptacion;

/**
 * Clase de la cual deben extender los DAOS de la aplicación
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */


public class EncriptacionSHAMD implements com.yanbal.lib.corp.interfaces.IEncriptacion {
  /** Nombre del algoritmo (valores posibles SHA o MD) */
  private Object algoritmo;

  /**
   * Encripta el mensaje
   * 
   * @param mensaje mensaje a encriptar
   */
  public void encriptar(Object mensaje) {

  }

  public void desencriptar(Object mensajeEncriptado) {
    // TODO Auto-generated method stub

  }


}
