package com.yanbal.kiosko.common.json;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.yanbal.kiosko.common.json.model.JsonUmbral;
import com.yanbal.kiosko.common.json.response.JsonKioskoDataResponse;

/**
 * Clase plantilla para el parseo del contenido response al JSON que se devuelve en el servicio de
 * listar umbrales
 * 
 * @author alex.contreras
 * 
 */
public class JsonListarUmbralesDataResponse implements JsonKioskoDataResponse {

  private Integer totalRegistros;

  @SerializedName("lista")
  private List<JsonUmbral> umbrales;

  public Integer getTotalRegistros() {
    return totalRegistros;
  }

  public List<JsonUmbral> getUmbrales() {
    return umbrales;
  }

  public void setTotalRegistros(Integer totalRegistros) {
    this.totalRegistros = totalRegistros;
  }

  public void setUmbrales(List<JsonUmbral> umbrales) {
    this.umbrales = umbrales;
  }

}
