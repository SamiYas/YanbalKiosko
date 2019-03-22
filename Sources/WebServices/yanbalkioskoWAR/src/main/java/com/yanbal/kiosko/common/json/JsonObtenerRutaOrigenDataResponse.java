package com.yanbal.kiosko.common.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.yanbal.kiosko.common.json.model.JsonRuta;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * obtener ruta
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerRutaOrigenDataResponse implements JsonKioskoDataResponse {

  @SerializedName("data")
  private List<JsonRuta> rutas;

  public List<JsonRuta> getRutas() {
    return rutas;
  }

  public void setRutas(List<JsonRuta> rutas) {
    this.rutas = rutas;
  }

}
