package com.yanbal.kiosko.common.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.yanbal.kiosko.common.json.model.JsonArchivo;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * listar archivos
 * 
 * @author alex.contreras
 * 
 */
public class JsonListarArchivosDataResponse implements JsonKioskoDataResponse {

  @SerializedName("lista")
  private List<JsonArchivo> archivos;

  public List<JsonArchivo> getArchivos() {
    return archivos;
  }

  public void setArchivos(List<JsonArchivo> archivos) {
    this.archivos = archivos;
  }

}
