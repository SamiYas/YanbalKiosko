package com.yanbal.kiosko.common.json;

import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * validar archivo rol
 * 
 * @author alex.contreras
 * 
 */
public class JsonValidarArchivoRolDataResponse implements JsonKioskoDataResponse {

  private Long codigoRespuesta;

  public Long getCodigoRespuesta() {
    return codigoRespuesta;
  }

  public void setCodigoRespuesta(Long codigoRespuesta) {
    this.codigoRespuesta = codigoRespuesta;
  }

}
