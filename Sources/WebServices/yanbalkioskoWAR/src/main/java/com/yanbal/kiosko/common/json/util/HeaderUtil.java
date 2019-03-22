package com.yanbal.kiosko.common.json.util;

import com.yanbal.kiosko.aplicacion.Application;
import com.yanbal.kiosko.common.core.enumeration.CodigosError;
import com.yanbal.kiosko.common.json.response.JsonKioskoHeaderResponse;

/**
 * Clase utilitario para obtener la cabecera del response
 * 
 * @author alex.contreras
 * 
 */
public class HeaderUtil {

  private HeaderUtil() {    
  }

  /**
   * Metodo que devuelve un header con el nombre del servicio
   * 
   * @param nombreServicio el nombre del servicio
   * @return objeto header para el parseo
   */
  public static JsonKioskoHeaderResponse getHeader(String nombreServicio) {
    JsonKioskoHeaderResponse header = new JsonKioskoHeaderResponse();
    header.setApplicationId(Application.KIOSKO2WEB);
    header.setResponseCode(String.valueOf(CodigosError.EXITO_KIOSKO.getCodigo()));
    header.setServiceName(nombreServicio);
    return header;
  }

  /**
   * Metodo que devuelve un header con el nombre del servicio y el codigo de error
   * 
   * @param nombreServicio el nombre del servicio
   * @param codigoError el codigo de error
   * @return objeto header para el parseo
   */
  public static JsonKioskoHeaderResponse getHeader(CodigosError codigoError, String nombreServicio) {
    JsonKioskoHeaderResponse header = new JsonKioskoHeaderResponse();
    header.setApplicationId(Application.KIOSKO2WEB);
    header.setResponseCode(String.valueOf(codigoError.getCodigo()));
    header.setServiceName(nombreServicio);
    return header;
  }


}
