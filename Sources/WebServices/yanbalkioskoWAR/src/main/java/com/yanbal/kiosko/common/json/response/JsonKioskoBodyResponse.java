package com.yanbal.kiosko.common.json.response;

import com.google.gson.annotations.SerializedName;

/**
 * Clase generica que define la estructura del cuerpo de los response JSON que se devuelven en los
 * servicios
 * 
 * @author alex.contreras
 * 
 */
public class JsonKioskoBodyResponse {

  @SerializedName("Response")
  private JsonKioskoDataResponse data;

  public JsonKioskoDataResponse getData() {
    return data;
  }

  public void setData(JsonKioskoDataResponse data) {
    this.data = data;
  }

}
