package com.yanbal.kiosko.common.json;

import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * eliminar coleccion
 * 
 * @author alex.contreras
 * 
 */
public class JsonEliminarColeccionDataResponse implements JsonKioskoDataResponse {

  private String resultado;
  private Long codigoRespuesta;

  public Long getCodigoRespuesta() {
    return codigoRespuesta;
  }

  public void setCodigoRespuesta(Long codigoRespuesta) {
    this.codigoRespuesta = codigoRespuesta;
  }

  public String getResultado() {
    return resultado;
  }

  public void setResultado(String resultado) {
    this.resultado = resultado;
  }

}
