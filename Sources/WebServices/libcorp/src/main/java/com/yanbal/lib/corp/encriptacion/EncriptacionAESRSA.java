package com.yanbal.lib.corp.encriptacion;

/**
 * Clase de la cual deben extender los DAOS de la aplicación
 * 
 * @author jcabrera
 * @author ovalencia
 * @version 1.0 Abril 2015
 */


public class EncriptacionAESRSA implements com.yanbal.lib.corp.interfaces.IEncriptacion {
  /** Nombre del algoritmo (valores posibles AES o RSA) */
  private Object algoritmo;
  /** Llave publica a usar para la encripatacion */
  private Object llavePrivada;
  /** Llave privada a usar para la encripatacion */
  private Object llavePublica;
  /** Tamaño de las llaves */
  private Object tamanoLlaveAES;

  /**
   * Encripta el mensaje
   * 
   * @param mensaje mensaje a encriptar
   */
  public void encriptar(Object mensaje) {

  }

  /**
   * Desencripta el mensaje
   * 
   * @param mensaje mensaje a desencriptar
   */
  public void desencriptar(Object mensajeEncriptado) {

  }


}
