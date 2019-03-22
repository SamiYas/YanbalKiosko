package com.yanbal.kiosko.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de seguridad.
 * 
 * @author lennin.davila
 */
public class SeguridadUtil {

  private static final Logger LOG = LoggerFactory.getLogger(SeguridadUtil.class);

  /**
   * Constructor de clase
   */  
  private SeguridadUtil(){
    
  }
  
  /**
   * metodo para obtener el valor del token que se genera cuando se inicia sesion web.
   * 
   * @param strBase cadena base en la cual se basara el algoritmo para generar el token unico .
   * @return retorna una cadena que representa al token de seguridad.
   * @see retorna el tipo de dato String.
   */
  public static String cadenaMD5(String strBase) {
    try {
      MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
      StringBuilder sb = new StringBuilder(strBase);
      sb.append(obtenerAleatorio());
      digest.update(sb.toString().getBytes());
      byte[] messageDigest = digest.digest();
      StringBuilder hexString = new StringBuilder();
      for (byte aMessageDigest : messageDigest) {
        hexString.append(Integer.toHexString(0xFF & aMessageDigest));
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      LOG.error("Error en la generacion de clave unica", e);
      return null;
    }
  }
  
  /**
   * metodo para obtener un numero aleatorio que se adjunta a la cadena token.
   *    
   */  
  private static int obtenerAleatorio(){
    Random rnd = new Random();    
    return rnd.nextInt();
  }
}
