package com.yanbal.kiosko.common.json;

import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * obtener por notificar
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerPorNotificarResponse implements JsonKioskoDataResponse {

  private int porNotificar;

  public int getPorNotificar() {
    return porNotificar;
  }

  public void setPorNotificar(int porNotificar) {
    this.porNotificar = porNotificar;
  }

}
