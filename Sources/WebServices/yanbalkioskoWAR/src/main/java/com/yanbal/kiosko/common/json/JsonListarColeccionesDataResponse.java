package com.yanbal.kiosko.common.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.yanbal.kiosko.common.json.model.JsonColeccion;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * listar colecciones
 * 
 * @author alex.contreras
 * 
 */
public class JsonListarColeccionesDataResponse implements JsonKioskoDataResponse {

  @SerializedName("lista")
  private List<JsonColeccion> colecciones;

  public List<JsonColeccion> getColecciones() {
    return colecciones;
  }

  public void setColecciones(List<JsonColeccion> colecciones) {
    this.colecciones = colecciones;
  }

}
