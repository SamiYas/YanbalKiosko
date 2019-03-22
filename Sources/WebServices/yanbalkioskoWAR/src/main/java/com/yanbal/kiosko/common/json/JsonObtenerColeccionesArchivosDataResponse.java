package com.yanbal.kiosko.common.json;

import java.util.HashMap;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.yanbal.kiosko.common.json.model.JsonUmbral;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * obtener colecciones archivos
 * 
 * @author alex.contreras
 * 
 */
public class JsonObtenerColeccionesArchivosDataResponse implements JsonKioskoDataResponse {

  @SerializedName("fecha_sincronizacion")
  private String fechaSincronizacion;

  private List<JsonUmbral> umbrales;

  @SerializedName("contenido")
  private List<HashMap<String, Object>> colecciones;

  public List<HashMap<String, Object>> getColecciones() {
    return colecciones;
  }

  public void setColecciones(List<HashMap<String, Object>> colecciones) {
    this.colecciones = colecciones;
  }

  public List<JsonUmbral> getUmbrales() {
    return umbrales;
  }

  public void setUmbrales(List<JsonUmbral> umbrales) {
    this.umbrales = umbrales;
  }

  public String getFechaSincronizacion() {
    return fechaSincronizacion;
  }

  public void setFechaSincronizacion(String fechaSincronizacion) {
    this.fechaSincronizacion = fechaSincronizacion;
  }

}
