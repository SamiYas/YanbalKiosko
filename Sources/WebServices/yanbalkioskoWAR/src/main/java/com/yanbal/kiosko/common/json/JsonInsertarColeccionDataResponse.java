package com.yanbal.kiosko.common.json;

import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * insertar coleccion
 * 
 * @author alex.contreras
 * 
 */
public class JsonInsertarColeccionDataResponse implements JsonKioskoDataResponse {

  private String resultado;

  public String getResultado() {
    return resultado;
  }

  public void setResultado(String resultado) {
    this.resultado = resultado;
  }

}
