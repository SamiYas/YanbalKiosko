package com.yanbal.kiosko.aplicacion;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase de aplicacion para metodos generales del sistema gestion de los archivos.
 * 
 * @author lennin.davila
 * 
 */
public class Application {
  public static final String KIOSKO2WEB = "KIOSKO2WEB";
  public static final String USUARIO_SISTEMA = "SISTEMA";
  public static final String DIRECCION_IP_HOST = obtenerDireccionIPHost();
  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  /**
   * Constructor vacio por defecto
   * @throws Exception 
   */  
  private Application(){
    
  }
  
  /**
   * Metodo que obtiene la direccion IP
   * @throws Exception 
   */
  private static String obtenerDireccionIPHost(){
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {      
      LOG.error("Error en lectura de la ip del servidor: ", e);    
    }
    return "";
  }
}
